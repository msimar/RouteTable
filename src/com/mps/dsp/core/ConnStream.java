package com.mps.dsp.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import com.mps.dsp.config.Configuration;
import com.mps.dsp.util.Logger;

public class ConnStream implements Runnable{
	
	private final String TAG = ConnStream.class.getSimpleName();
	
	/**
	 * Socket to send/receive.
	 */
	private Socket socket;
	
	/**
	 * Streams to send/receive Objects through the socket.
	 */
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	/**
	 * True if we're still running, false otherwise.
	 */
	private boolean running = true;
	
	/**
	 * Sender and Receiver Nodes.
	 */
	private Node fromNode;
	private Node toNode;
	private Datagram datagram;
	
	public ConnStream(Node fromNode, Node toNode, Datagram datagram) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.datagram = datagram;
	}
	
	/**
	 * Construct a new ConnStream object with the given socket.
	 * @param socket	Socket to use.
	 */
	public ConnStream(Node serverNode, Socket socket) {
		Logger.d(TAG, "ConnStream() : Socket");
		this.socket = socket;

		createStreams();

		Object object = null;
		try {
			object = inputStream.readObject();
		} catch (Exception e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (object instanceof Datagram) {
			Datagram datagram = (Datagram) object;
			serverNode.receive(datagram);
		}  
	}
	
	/**
	 * Creates a socket as a sender/client node.
	 */
	private void openStream() {
		
		// Stream as a client
		if (socket == null) {
			
			// Try to connect until we succeed.
			while (socket == null && running) {
				try {
					// new Socket(hostName, portNumber);
					// Create a socket and connect it to the specified port.
					socket = new Socket(toNode.getIPAddress(), Integer.parseInt(toNode.getPort()));
					
					// Create the object streams.
					createStreams();
					
					// Send message to the other node.
					send(datagram);
					
				} catch (IOException e) {
					// Something went wrong. Keep on trying.
					Logger.e(e.getLocalizedMessage() + ": Connecting to " + this.toNode + ". Retrying in " + (Configuration.retryConnectionTime / 1000.0f) + " s.");
					try {
						Thread.sleep(Configuration.retryConnectionTime);
					} catch (InterruptedException e1) {}
				}
			}
		}
	}
	
	/**
	 * Create input/output streams for this ConnStream.
	 */
	private void createStreams() {
		Logger.d(TAG, "createStreams()");
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();

			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			Logger.e("Could not create input/output streams from socket: " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Send a message through this ConnStream.
	 * @param message the message to send
	 */
	public void send(Datagram datagram) {
		Logger.d(TAG, "send()");
		if (running) {

			Logger.d("Sending datagram : " + datagram);
			try {
				outputStream.writeObject(datagram);
				outputStream.flush();
			} catch (IOException e) {
				Logger.e("Exception when sending a message: " + e.getLocalizedMessage());
				//e.printStackTrace();
				closeStream();
			}
		} else {
			Logger.d("Failed trying sending a message for: " + this.fromNode);
		}
	}
	
	/**
	 * Close this ConnStream.
	 */
	public void closeStream() {
		Logger.d(TAG, "closeStream()");
		// Stop listening.
		running = false;

		// might possible ObjectInputStream#readObject() is preventing from actually closing,
		// force it to close.
		try {
			if(inputStream != null)
				inputStream.close();
		} catch (IOException e) {}
	}
	
	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		try {
			outputStream.close();
		} catch (Exception e) {
		}
		try {
			inputStream.close();
		} catch (Exception e) {
		}
		try {
			socket.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Listen for ConnStream messages.
	 */
	@Override
	public void run() {
		
		openStream();
		
		Logger.d("Completed socket initialization for: " + this.fromNode);

		// Keep listening for messages.
		while (running) {
			Object object = null;
			
			try {
				// Read a message object from the InputStream.
				object = inputStream.readObject();
			} catch (Exception e) {
				Logger.d("Exception:" + e.getLocalizedMessage());
				running = false;
			}

			// Send the message to the RoutingTable.
			if (object != null && object instanceof Datagram) {
				Datagram datagram = (Datagram) object;
				this.fromNode.receive(datagram);
			}
		}

		Logger.d("ConnStream finnished executing and closing ConnStream for: " + this.fromNode);
		
		// Clean up the streams and socket.
		finalize();
	}

	public UUID getId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID();
	}
}

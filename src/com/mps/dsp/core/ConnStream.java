package com.mps.dsp.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.mps.dsp.config.Configuration;
import com.mps.dsp.util.Logger;

public class ConnStream implements Runnable{
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
	
	public ConnStream(Node fromNode, Node toNode) {
		this.fromNode = fromNode;
		this.toNode = toNode;
	}
	
	/**
	 * Construct a new ConnStream object with the given socket.
	 * @param socket	Socket to use.
	 */
	public ConnStream(Socket socket) {
		this.socket = socket;

		createStreams();

		Object object = null;
		try {
			object = inputStream.readObject();
		} catch (Exception e) {
			// Something went wrong, close the socket.
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (object instanceof Message) {
			Message message = (Message) object;
			// TODO : process message	
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
					send(new Message(fromNode, toNode));
					
				} catch (IOException e) {
					// Something went wrong. Keep on trying.
					Logger.e(e.getLocalizedMessage() + " Connecting to " + this.toNode + ". Retrying in " + (Configuration.retryConnectionTime / 1000.0f) + " s.");
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
	public void send(Message message) {
		if (running) {

			Logger.d("Sending message : " + message);
			try {
				outputStream.writeObject(message);
				outputStream.flush();
			} catch (IOException e) {
				Logger.e("Exception when sending a message: " + e.getLocalizedMessage());
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
			}

			// Send the message to the RoutingTable.
			if (object != null && object instanceof Message) {
				Message message = (Message) object;
				this.fromNode.receive(message);
			}
		}

		Logger.d("ConnStream finnished executing and closing ConnStream for: " + this.fromNode);
		
		// Clean up the streams and socket.
		finalize();
	}

	public Integer getId() {
		// TODO Auto-generated method stub
		return fromNode.getIndex();
	}
}

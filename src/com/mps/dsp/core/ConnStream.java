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
	
	public boolean isRunning(){ return running; }
	
	/**
	 * Creates a socket as a sender/client node.
	 */
	private void openStream() {
		Logger.d(TAG, "openStream() ");
		
		Logger.d(TAG, "openStream() : socket : " + socket);
		Logger.d(TAG, "openStream() : running : " + running);
		
		// Stream as a client
		if (socket == null) {
			
			// Try to connect until we succeed.
			while (socket == null && running) {
				try {
					// new Socket(hostName, portNumber);
					// Create a socket and connect it to the specified port.
					socket = new Socket(toNode.getIPAddress(), Integer.parseInt(toNode.getPort()));
					
					Logger.d(TAG, "openStream() : socket : " + socket);
					
					// Create the object streams.
					createStreams();
					
					// Send message to the other node.
					send(datagram);
					
				} catch (IOException e) {
					// Something went wrong. Keep on trying.
					Logger.e(TAG, "openStream() : " + e.getLocalizedMessage() + ": Connecting to " + this.toNode + ". Retrying in " + (Configuration.retryConnectionTime / 1000.0f) + " s.");
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
			Logger.e(TAG, "createStreams() : Could not create input/output streams from socket: " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Send a message through this ConnStream.
	 * @param message the message to send
	 */
	public void send(Datagram datagram) {
		Logger.d(TAG, "send() : datagram");
		
		if (running) {
			Logger.d(TAG, "send() : Sending datagram : ");
			try {
				outputStream.writeObject(datagram.message.message);
				outputStream.flush();
			} catch (IOException e) {
				Logger.e(TAG, "send() : Exception when sending a message: " + e.getLocalizedMessage());
				closeStream();
			}
		} else {
			Logger.d(TAG, "send() : Failed trying sending a message for: " + this.fromNode);
		}
	}
	
	/**
	 * Listen for ConnStream messages.
	 */
	@Override
	public void run() {
		Logger.d(TAG, "run()");
		
		openStream();

		Logger.d(TAG, "run() : ConnStream finnished executing and closing ConnStream for: " + this.fromNode);
		
		// Clean up the streams and socket.
		finalize();
		
		closeStream();
	}
	
	/**
	 * Close this ConnStream.
	 */
	public void closeStream() {
		Logger.d(TAG, "closeStream()");
		// Stop listening.
		running = false;
 
		try {
			if(inputStream != null)
				inputStream.close();
		} catch (IOException e) {}
		
		try {
			
			socket.shutdownOutput();
			socket.shutdownInput();
			socket.close();
		} catch (Exception e) {
		}
	}
	
	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		Logger.d(TAG, "finalize()");
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
}

package com.mps.dsp.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.mps.dsp.util.Logger;

public class ServerTrait implements Runnable {
	
	private final String TAG = ServerTrait.class.getSimpleName();

	private ServerSocket serverSocket;
	private Socket clientSocket;

	private Node serverNode;
	
	/**
	 * Streams to send/receive Objects through the socket.
	 */
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	public ServerTrait(Node node) {
		this.serverNode = node;
	}

	/**
	 * Run method for the ServerTrait Thread
	 */
	@Override
	public void run() {
		Logger.d(TAG, "run()");
		try {
			// Start a listening socket.
			Logger.d(TAG, "Starting sockets");
			// new ServerSocket(portNumber);
			serverSocket = new ServerSocket(Integer.parseInt(serverNode.getPort()));

		} catch (IOException e) {
			// Most likely : port already occupied, print an error message.
			Logger.e(e.getLocalizedMessage());
		}

		// Listen and start sockets if needed.
		Logger.d(TAG, "Starting to listen (" + serverNode + ")");

		while (serverSocket != null && !serverSocket.isClosed()) {
			try {
				// Create a ConnStream for every incoming connections. 
				clientSocket = serverSocket.accept();

				connectToStream(this.serverNode);

			} catch (IOException e) {
				// The Socket died : ignore the IOException, clean up and exit.
				Logger.e(TAG, "Exception ServerSocket ServerTrait : " + e.getLocalizedMessage()); 
			}
		}
		
		Logger.d(TAG, "ServerSocket State : (" + serverSocket.isClosed() + ")");
		
		closeStream();
	}
	
	/**
	 * Construct a new ConnStream object with the given socket.
	 * @param socket	Socket to use.
	 */
	public void connectToStream(Node serverNode) {
		Logger.d(TAG, "connectToStream() : serverNode, socket");

		createStreams();

		Object object = null;
		try {		
			
			// read the message from the stream
			object = inputStream.readObject();
			
			Logger.d(TAG, "connectToStream() : Reading :  " + object);
			
			serverNode.receive((String)object);
			
//			// Code to keep on listening to the messages
//			while ((object = inputStream.readObject()) != null)
//			{
//				Logger.d(TAG, "connectToStream() : Reading :  " + object);
//				
//				if (object instanceof Datagram) {
//					Datagram datagram = (Datagram) object;
//					serverNode.receive(datagram);
//					
//					Logger.d(TAG, "connectToStream() : processing message ");
//				} 
//			}
			
		} catch (Exception e) {
			try {
				clientSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		finalize();
	}
	
	/**
	 * Create input/output streams for this ConnStream.
	 */
	private void createStreams() {
		Logger.d(TAG, "createStreams()");
		try {
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			outputStream.flush();

			inputStream = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			Logger.e(TAG, "createStreams() : Could not create input/output streams from socket: " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Close this ConnStream.
	 */
	public void closeStream() {
		Logger.d(TAG, "closeStream()");
 
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
			serverSocket.close();
			clientSocket.close();
		} catch (Exception e) {
		}
	}
}

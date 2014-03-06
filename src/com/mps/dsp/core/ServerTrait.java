package com.mps.dsp.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.mps.dsp.config.Configuration;
import com.mps.dsp.util.Logger;

/**
 * This class represents Trait of the Node as Server. The  
 * abstract class implements roles of Server. 
 * 
 * The Server ( or Coordinator ) trait enable handling of Client messages. 
 * The Server can handle multiple clients at the same time. The Server respond  
 * to each client using its state as message.  
 * 
 * @author msingh
 *
 */
public class ServerTrait implements Runnable {
	
	/**
	 * A ServerTrait class logger tag 
	 */
	private final String TAG = ServerTrait.class.getSimpleName();

	/**
	 * A ServerSocket instance
	 */
	private ServerSocket serverSocket;
	
	/**
	 * A Client Socket instance
	 */
	private Socket clientSocket;

	/**
	 * Node hold trait as Server Node
	 */
	private Node serverNode;
	
	/**
	 * Streams to send/receive Objects through the socket.
	 */
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	/**
	 * A constructor to ServerTrait class
	 * @param node the server node in the network
	 */
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
			
			InetAddress bindAddr = serverNode.getIPAddress();
			
			// Start a listening socket.
			Logger.d(TAG, "Starting sockets : " + bindAddr);
			Logger.d(TAG, "Starting port : " + serverNode.getPort());
			
			// new ServerSocket(portNumber);
			serverSocket = new ServerSocket(
					Integer.parseInt(serverNode.getPort()), 
					Configuration.SERVER_BACKLOG,  
					bindAddr);			

		} catch (IOException e) {
			Logger.e(TAG, "Exception Creating ServerSocket :" + e.getLocalizedMessage());
			e.printStackTrace();
		}

		// Listen and start sockets if needed.
		Logger.d(TAG, "Starting to listen (" + serverSocket + ")");

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
	 * Construct a new ConnStream with the given socket.
	 * @param serverNode the serverNode require to bind to the connection stream.
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
	 * Create input/output streams for the connection stream.
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
	 * Close the connection stream.
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

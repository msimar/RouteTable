package com.mps.dsp.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mps.dsp.util.Logger;

public class ServerTrait implements Runnable {

	/**
	 * To bootstrap the channels we use a serverSocket to listen for incoming
	 * connections.
	 */
	private ServerSocket serverSocket;

	/**
	 * A map containing node ids and their respective channels.
	 */
	private final Map<Integer, ConnStream> idsToChannels = new ConcurrentHashMap<Integer, ConnStream>();

	private Node serverNode;

	public ServerTrait(Node node) {
		this.serverNode = node;
	}

	/**
	 * Run method for the ServerTrait Thread
	 */
	@Override
	public void run() {
		try {
			// Start a listening socket.
			Logger.d("Starting sockets");
			// new ServerSocket(portNumber);
			serverSocket = new ServerSocket(Integer.parseInt(serverNode
					.getPort()));

		} catch (IOException e) {
			// Most likely : port already occupied, print an error message.
			Logger.e(e.getLocalizedMessage());
		}

		// Listen and start sockets if needed.
		Logger.d("Starting to listen");

		while (serverSocket != null && !serverSocket.isClosed()) {
			try {
				// Create a ConnStream for every incoming connections.
				Socket clientSocket = serverSocket.accept();

				ConnStream connStream = new ConnStream(clientSocket);

				// Add the ConnStream to a map for easy access.
				idsToChannels.put(connStream.getId(), connStream);

				// Start the ConnStream.
				new Thread(connStream).start();

			} catch (IOException e) {
				// The Socket died : ignore the IOException, clean up and exit.
			}
		}

		// The Socket closed, clean up and exit.
		for (ConnStream connStream : idsToChannels.values()) {
			connStream.closeStream();
		}
	}
}

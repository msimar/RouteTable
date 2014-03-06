package com.mps.dsp.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.mps.dsp.config.ConfigFileWriter;
import com.mps.dsp.util.Logger;

public class ServerTrait implements Runnable {
	
	private final String TAG = ServerTrait.class.getSimpleName();

	private ServerSocket serverSocket;

	/**
	 * A map containing node ids and their respective channels.
	 */
	private final Map<UUID, ConnStream> idsToChannels = new ConcurrentHashMap<UUID, ConnStream>();

	private Node serverNode;

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
			Logger.d("Starting sockets");
			// new ServerSocket(portNumber);
			serverSocket = new ServerSocket(
					Integer.parseInt(serverNode.getPort()));

		} catch (IOException e) {
			// Most likely : port already occupied, print an error message.
			Logger.e(e.getLocalizedMessage());
		}

		// Listen and start sockets if needed.
		Logger.d("Starting to listen (" + serverNode + ")");

		while (serverSocket != null && !serverSocket.isClosed()) {
			try {
				// Create a ConnStream for every incoming connections. 
				Socket clientSocket = serverSocket.accept();

				ConnStream connStream = new ConnStream(this.serverNode, clientSocket);

				// Add the ConnStream to a map for easy access.
				idsToChannels.put(connStream.getId(), connStream);

				// Start the ConnStream.
				new Thread(connStream).start();

			} catch (IOException e) {
				// The Socket died : ignore the IOException, clean up and exit.
				Logger.e("Exception ServerSocket ServerTrait : " + e.getLocalizedMessage()); 
			}
		}

		// The Socket closed, clean up and exit.
		for (ConnStream connStream : idsToChannels.values()) {
			connStream.closeStream();
		}
	}
}

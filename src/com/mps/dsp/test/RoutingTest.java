package com.mps.dsp.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import com.mps.dsp.core.Datagram;
import com.mps.dsp.core.Message;
import com.mps.dsp.core.Node;
import com.mps.dsp.core.NodeRegistry;
import com.mps.dsp.util.Logger;

public class RoutingTest {

	private final String TAG = RoutingTest.class.getSimpleName();

	class RouteCommand {
		public final Node sourceNode;
		public final Node destintionNode;
		public final Datagram datagram;

		public RouteCommand(Node sourceNode, Node destintionNode,
				Datagram datagram) {
			this.sourceNode = sourceNode;
			this.destintionNode = destintionNode;
			this.datagram = datagram;
		}
	}

	private RouteCommand command;

	public RouteCommand getRouteCommand() {
		return command;
	}

	/**
	 * This method parse the configuration file and transform content of
	 * configuration file into set of Nodes. These compiled Nodes are stored
	 * into the the Resource class.
	 */
	public void parseCommandFile(String fileName) {
		Logger.d(TAG, "parseCommandFile()");

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("./" + fileName);

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(input))) {

			for (String line; (line = br.readLine()) != null;) {
				if (line.charAt(0) != '#')
					buildCommand(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method parse the configuration file and transform content of
	 * configuration file into set of Nodes. These compiled Nodes are stored
	 * into the the Resource class.
	 */
	public void parseCommandFile() {
		// Logger.d(TAG, "parseCommandFile()");

		String workingDir = System.getProperty("user.dir");
		String packagePath = "/src/com/mps/dsp/test/";

		// System.out.println(workingDir);

		File file = new File(workingDir + packagePath + "/command.txt");

		// if file does not exists, show message
		if (!file.exists()) {
			System.err
					.println("command.txt does not exist!!, Configure command.txt file.");
		}

		if (file.exists()) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)))) {

				for (String line; (line = br.readLine()) != null;) {
					if (line.charAt(0) != '#')
						buildCommand(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void buildCommand(String line) {
		// split the lines to tokens
		String[] nodeToken = line.split("\\s+");
		// create a command from tokens
		StringBuffer cmdBuffer = new StringBuffer(nodeToken[0]);
		try {

			Node sourceNode = NodeRegistry.getInstance().getNodesMap()
					.get(Integer.parseInt(nodeToken[1]));

			cmdBuffer.append(" ");
			cmdBuffer.append("[").append(sourceNode.getIndex()).append("]");
			cmdBuffer.append(sourceNode.getIPAddress().getHostAddress());

			Node destintionNode = NodeRegistry.getInstance().getNodesMap()
					.get(Integer.parseInt(nodeToken[2]));

			cmdBuffer.append(" ");
			cmdBuffer.append("[").append(destintionNode.getIndex()).append("]");
			cmdBuffer.append(destintionNode.getIPAddress().getHostAddress());

			command = new RouteCommand(sourceNode, destintionNode,
					new Datagram(sourceNode, destintionNode, new Message(
							sourceNode, destintionNode, nodeToken[3])));

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cmdBuffer.append(" ");
		cmdBuffer.append(nodeToken[3]);

		System.out.println(cmdBuffer.toString());
	}

}

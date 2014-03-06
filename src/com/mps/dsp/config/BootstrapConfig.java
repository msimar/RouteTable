package com.mps.dsp.config;

import java.net.ServerSocket;

import com.mps.dsp.core.Node;

/**
 * Bootstrap class to configure overlay with new nodes.
 * 
 * @author msingh
 * 
 */
public class BootstrapConfig {

	private Node bootstrapNode;

	/**
	 * A serverSocket to listen for incoming
	 * connections.
	 */
	private ServerSocket serverSocket;

	/**
	 * Constructor to the BootstrapConfig class
	 * @param node the Node act as bootstrap
	 */
	public BootstrapConfig(Node node) {
		this.setBootstrapNode(node);
	}

	/**
	 * Get the bootstrap node for network
	 * @return the bootstrap node
	 */
	public Node getBootstrapNode() {
		return bootstrapNode;
	}

	/**
	 * Set the bootstrap node for network
	 * @param bootstrapNode the bootstrap node
	 */
	public void setBootstrapNode(Node bootstrapNode) {
		this.bootstrapNode = bootstrapNode;
	}
}

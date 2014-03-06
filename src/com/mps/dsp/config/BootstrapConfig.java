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

	public BootstrapConfig(Node node) {
		this.setBootstrapNode(node);
	}

	public Node getBootstrapNode() {
		return bootstrapNode;
	}

	public void setBootstrapNode(Node bootstrapNode) {
		this.bootstrapNode = bootstrapNode;
	}
}

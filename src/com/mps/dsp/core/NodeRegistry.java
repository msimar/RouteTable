package com.mps.dsp.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


public class NodeRegistry {

	private static NodeRegistry INSTANCE;
	
	private final ConcurrentHashMap<Integer, Node> nodesMap;
	
	/**
	 * All nodes in the network.
	 */
	private final CopyOnWriteArraySet<Node> nodes ;

	private NodeRegistry() {
		// singleton constructor
		nodes = new CopyOnWriteArraySet<Node>();
		nodesMap = new ConcurrentHashMap<Integer, Node>();
	}
	
	/**
	 * @return
	 */
	public static synchronized NodeRegistry getInstance(){
		if( INSTANCE == null )
			INSTANCE = new NodeRegistry();
		return INSTANCE;
	}

	public boolean register(Node node) {
		node.setIdentifier(nodesMap.size());
		// add node to map
		nodesMap.put(node.getIdentifier(), node);
		// add node to the list
		return nodes.add(node);
	}
	
	public CopyOnWriteArraySet<Node> getNodes() {
		return nodes;
	}

	public ConcurrentHashMap<Integer, Node> getNodesMap() {
		return nodesMap;
	}
}

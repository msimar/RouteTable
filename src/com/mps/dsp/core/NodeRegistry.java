package com.mps.dsp.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * NodeRegistry as a Resource Pool for the App. Nodes
 * will register in the Resource Pool. It holds the nodes
 * data structure for the communication.
 * 
 * @author msingh
 *
 */
public class NodeRegistry {

	/**
	 * An instance of NodeRegistry class
	 */
	private static NodeRegistry INSTANCE;
	
	/**
	 * A ConcurrentHashMap to hold Nodes using Identifier
	 */
	private final ConcurrentHashMap<Integer, Node> nodesMap;
	
	/**
	 * All nodes in the network.
	 */
	private final CopyOnWriteArraySet<Node> nodes ;

	/**
	 * Creates an NodeRegistry object.
	 */
	private NodeRegistry() {
		// singleton constructor
		nodes = new CopyOnWriteArraySet<Node>();
		nodesMap = new ConcurrentHashMap<Integer, Node>();
	}
	
	/**
	 * Returns the unique NodeRegistry object associated with this System, if any.
	 * @return instance the NodeRegistry instance
	 */
	public static synchronized NodeRegistry getInstance(){
		if( INSTANCE == null )
			INSTANCE = new NodeRegistry();
		return INSTANCE;
	}

	/**
	 * Node register in the NodeRegistry
	 * @param node the new created node instance
	 * @return True if successfully added to the registry, false otherwise.
	 */
	public boolean register(Node node) {
		node.setIdentifier(nodesMap.size());
		// add node to map
		nodesMap.put(node.getIdentifier(), node);
		// add node to the list
		return nodes.add(node);
	}
	
	/**
	 * Return the All the nodes in the Network
	 * @return the collection of nodes as CopyOnWriteArraySet
	 */
	public CopyOnWriteArraySet<Node> getNodes() {
		return nodes;
	}

	/**
	 * Return the All the nodes as mapper to their identifier in the Network
	 * @return the collection of nodes map as ConcurrentHashMap
	 */
	public ConcurrentHashMap<Integer, Node> getNodesMap() {
		return nodesMap;
	}
}

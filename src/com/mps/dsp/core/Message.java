package com.mps.dsp.core;

import java.io.Serializable;

/**
 * Encode a general message. This message is delivered to the RoutingTable after
 * it is received from a neighbor Node.
 * @author msingh
 *
 */
public class Message implements Serializable{	
	
	private static final long serialVersionUID = -1196856163917615605L;
	
	public final Node fromNode;
	public final Node toNode;
	
	public Message(Node fromNode, Node toNode) {
		 this.fromNode = fromNode;
		 this.toNode = toNode;
	}

	@Override
	public String toString() {
		return "(" + fromNode + " -> " + toNode + ")";
	}
}

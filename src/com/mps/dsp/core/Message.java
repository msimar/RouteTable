package com.mps.dsp.core;

import java.io.Serializable;

/**
 * Encode a general message. This message is delivered to the RoutingTable after
 * it is received from a neighbor Node.
 * 
 * @author msingh
 *
 */
public class Message implements Serializable{	
	/**
	 * A Serializable unique long id
	 */
	private static final long serialVersionUID = -1196856163917615605L;
	
	/**
	 * Sender and Receiver Nodes.
	 */
	public final Node fromNode;
	public final Node toNode;
	
	/**
	 * A message streamed between source and destination
	 */
	public final String message;
	
	/**
	 * Creates an Message object.
	 * @param fromNode the sender of the Message
	 * @param toNode the receiver of the Message
	 * @param message the message in the Message
	 */
	public Message(Node fromNode, Node toNode, String message) {
		 this.fromNode = fromNode;
		 this.toNode = toNode;
		 this.message = message;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + fromNode + " -> " + toNode + ") :" + message;
	}
}

package com.mps.dsp.core;

import java.io.Serializable;

public final class Datagram implements Serializable {
	
	/**
	 * A Datagram class logger tag 
	 */
	private static final long serialVersionUID = -1196856161917615605L;

	/**
	 * Sender and Receiver Nodes.
	 */
	private Node source;
	private Node destination;
	/**
	 * A message streamed between source and destination
	 */
	public final Message message;
	 
	/**
	 * Creates an Datagram object.
	 * @param fromNode the sender of the datagram
	 * @param toNode the receiver of the datagram
	 * @param message the message in the datagram
	 */
	public Datagram(Node fromNode, Node toNode, Message message) {
		setSource(fromNode);
		setDestination(toNode);
		this.message = message;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + source + " -> " + destination + ") : Routing message : " + message ;
	}
	
	/**
	 * Returns the source Node
	 * @return  the source Node
	 */
	public Node getSource() {
		return source;
	}

	/**
	 * Set the source Node
	 * @param source the source Node
	 */
	public void setSource(Node source) {
		this.source = source;
	}

	/**
	 * Returns the destination Node
	 * @return  the destination Node
	 */
	public Node getDestination() {
		return destination;
	}

	/**
	 * Set the destination Node
	 * @param destination the destination Node
	 */
	public void setDestination(Node destination) {
		this.destination = destination;
	}
}

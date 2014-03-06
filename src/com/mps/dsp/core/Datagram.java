package com.mps.dsp.core;

import java.io.Serializable;

public final class Datagram implements Serializable {
	private static final long serialVersionUID = -1196856161917615605L;

	private Node source;
	private Node destination;
	public final Message message;

	public Datagram(Node source, Node destination, Message message) {
		setSource(source);
		setDestination(destination);
		this.message = message;
	}

	@Override
	public String toString() {
		return "(" + source + " -> " + destination + ") : Routing message : " + message ;
	}
	
	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}
}

package com.mps.dsp.core;

public class Message {	
	
	private String message;
	private Node source;
	private Node destination;
	
	public Message(Node source, Node destination, String message) {
		setSource(source);
		setDestination(destination);
		setMessage(message);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}

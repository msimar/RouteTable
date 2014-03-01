package com.mps.dsp.core;

import java.util.LinkedList;

public class Node extends UnitNode {
	
	public Node(int address, int port) {
		super(address, port);
		// TODO Auto-generated constructor stub
	}

	// routing pointer for immediate successor
	private Node successor;

	// n+2^1, n+2^2, n+2^3,…, n+2^L (all arithmetic operations modulo N)
	private final LinkedList<Node> moduloSuccessorList = new LinkedList<Node>();;

	public UnitNode getSuccessor() {
		return successor;
	}

	public void setSuccessor(Node successor) {
		this.successor = successor;
	}

	public LinkedList<Node> getModuloSuccessorList() {
		return moduloSuccessorList;
	}

	public boolean add(Node node) {
 
		for( int i = 0 ; i < Util.MAX_NODE ; i++){
			//node.address
		}
		
		return true;
	}
}

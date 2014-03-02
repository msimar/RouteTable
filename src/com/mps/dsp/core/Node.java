package com.mps.dsp.core;

import java.util.LinkedList;

public class Node extends UnitNode {
	
	public static final String LTAG = Node.class.getSimpleName();
	
	public Node(int index, int address, int port) {
		super(index, address, port);
		
		successor = null;
		predecessor = null;
	}

	// routing pointer for immediate successor
	private Node successor;
	
	private Node predecessor;

	// n+2^1, n+2^2, n+2^3,…, n+2^L (all arithmetic operations modulo N)
	private final LinkedList<Node> moduloSuccessorList = new LinkedList<Node>();;

	public void speedUpLookup() {
		
		int routeNodePointerIndex;
		
		//Logger.INSTANCE.d(LTAG,"--- current Node : " + this.index );
		
		for( int i = 0 ; i < Util.MAX_ROUTE_TABLE_ENTRIES ; i++){
			routeNodePointerIndex = ( this.index + (int)Math.pow(2, i) )  ;
				
			if( routeNodePointerIndex >= Util.MAX_IDENTIFIER )
				routeNodePointerIndex = routeNodePointerIndex - Util.MAX_IDENTIFIER ; 
			
			//Logger.INSTANCE.d(LTAG, routeNodePointerIndex);
			
			// ping the node -- is node still action ? 
			// YES
			moduloSuccessorList.add(NodeRegistry.getInstance().getNodesMap().get(routeNodePointerIndex));
			
			// add successor
			successor = NodeRegistry.getInstance().getNodesMap().get(this.index + 1);
			
			// add Predecessor
			// predecessor = NodeRegistry.getInstance().getNodesMap().get(this.index - 1);
			
			// NO
			
		}
	}
	
	// getters and setters
	
	public UnitNode getSuccessor() {
		return successor;
	}

	public void setSuccessor(Node successor) {
		this.successor = successor;
	}

	public LinkedList<Node> getModuloSuccessorList() {
		return moduloSuccessorList;
	}

	public Node getPredeccessor() {
		return predecessor;
	}

	public void setPredeccessor(Node predecessor) {
		this.predecessor = predecessor;
	}
}

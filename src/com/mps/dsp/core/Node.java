package com.mps.dsp.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mps.dsp.config.Configuration;
import com.mps.dsp.util.Logger;
import com.mps.dsp.util.Util;

public class Node extends UnitNode {

	public static final String LTAG = Node.class.getSimpleName();

	private ServerTrait serverTrait;
	/**
	 * Node processes messages asynchronously. Similar to concept of
	 * Producer/Consumer queue.
	 */
	private final BlockingQueue<Message> q = new LinkedBlockingQueue<Message>();

	/**
	 * Receive a message.
	 * 
	 * @param message
	 *            the received message
	 */
	public void receive(Message message) {
		q.offer(message);
	}

	/**
	 * The routing table for this node.
	 */
	public final RoutingTable routingTable;

	public Node(int index, String address, String port) {
		super(index, address, port);

		this.routingTable = new RoutingTable();
 	}
	
	/**
	 * Execute the Node
	 */
	public void execute(){
		this.serverTrait = new ServerTrait(this);
		this.serverTrait.run();

		// Start a consumer thread to process messages for this Node.
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						// Get the next message, if any.
						Message message = q.take();

						// TODO Process the specific message
						Logger.d("Message Recevied: " + message);

					} catch (InterruptedException e) {

					}
				}
			}
		}).start();
	}

	/**
	 * Perform speed lookup by configuring routing table
	 */
	public void speedUpLookup() {

		int routeNodePointerIndex;

		// Logger.INSTANCE.d(LTAG,"--- current Node : " + this.index );

		for (int i = 0; i < Configuration.MAX_ROUTE_TABLE_ENTRIES; i++) {
			routeNodePointerIndex = (this.index + (int) Math.pow(2, i));

			if (routeNodePointerIndex >= Configuration.MAX_IDENTIFIER)
				routeNodePointerIndex = routeNodePointerIndex
						- Configuration.MAX_IDENTIFIER;

			// Logger.INSTANCE.d(LTAG, routeNodePointerIndex);

			// ping the node -- is node still action ?
			// YES

			if (routeNodePointerIndex != this.index) {
				this.routingTable.addToModuloSuccessorList(
						NodeRegistry.getInstance().getNodesMap()
								.get(routeNodePointerIndex));

				// add successor
				this.routingTable.setSuccessor(NodeRegistry.getInstance()
						.getNodesMap().get(this.index + 1));
			}
		}
	}
	
	/**
	 * Route the message from Source Node to Destination Node.
	 * @param destination the destination Node.
	 * @param message the message for destination Node. 
	 */
	public void route(Node destination, Message message) {
		 
		Node nextHopNode = null;
		int minimumHopDistance = Util.INVALID_INDEX;
		
		for (Node successorNode : this.routingTable.getModuloSuccessorList()) {

			if( minimumHopDistance == Util.INVALID_INDEX ){
				// update the minimum hop node distance
				minimumHopDistance = destination.getIndex() - successorNode.getIndex();
				// update the next hope node
				nextHopNode = successorNode;
			}else{
				if (minimumHopDistance > (destination.getIndex() - successorNode.getIndex())) {
					// update the min hop node distance
					minimumHopDistance = destination.getIndex() - successorNode.getIndex();
					// update the next hope node
					nextHopNode = successorNode;
				}
			}
		}
		
		if(nextHopNode != null) {
			Logger.d(this + " >>>> " + nextHopNode);
		}else{
			return;
		}
		
		if( nextHopNode.getIndex() == destination.getIndex() ) return;
		
		// route to the next hop
		nextHopNode.route(destination,message);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + index + "]Node (" + address + ":" + port + ")";
	}
}

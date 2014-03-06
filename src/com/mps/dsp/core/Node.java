package com.mps.dsp.core;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mps.dsp.config.Configuration;
import com.mps.dsp.util.Logger;

public class Node extends UnitNode implements Serializable {

	private final String TAG = Node.class.getSimpleName();

	private static final long serialVersionUID = -1196856161917615615L;

	private ServerTrait serverTrait;
	/**
	 * Node processes messages asynchronously. Similar to concept of
	 * Producer/Consumer queue.
	 */
	private final BlockingQueue<Datagram> messageQue = new LinkedBlockingQueue<Datagram>();

	/**
	 * Receive a message.
	 * 
	 * @param message
	 *            the received message
	 */
	public void receive(Datagram message) {
		Logger.d(TAG,"receive()"); 
		messageQue.offer(message);
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
	public void execute() {
		// Start a consumer thread to process messages for this Node.
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						// Get the next message, if any.
						Datagram datagram = messageQue.take();

						Logger.d(TAG, "Message Blocking Queue :: Datagram Recevied: " + datagram);
					} catch (InterruptedException e) {
						
					}
				}
			}
		}).start();
	}

	/**
	 * Perform speed lookup by configuring routing table
	 * 
	 * @throws UnknownHostException
	 */
	public void speedUpLookup() throws UnknownHostException {

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
				Node closestSuccessorNode = NodeRegistry.getInstance()
						.getNodesMap().get(routeNodePointerIndex);
				this.routingTable.addTableEntry(this, closestSuccessorNode);
				this.routingTable
						.addToModuloSuccessorList(closestSuccessorNode);

				// add successor
				this.routingTable.setSuccessor(NodeRegistry.getInstance()
						.getNodesMap().get(this.index + 1));
			}
		}
	}

	/**
	 * Route the message from Source Node to Destination Node.
	 * 
	 * @param destination
	 *            the destination Node.
	 * @param message
	 *            the message for destination Node.
	 */
	public void route(Node destination, Datagram datagram) {

		this.routingTable.getHeaderTemplate();
		processRouting(this, datagram);
	}

	private void processRouting(Node source, Datagram datagram) {
		Logger.d(TAG, "------------------");
		Logger.d(TAG, "processRouting() : source : " + source);
		// find the closest node to route the message near to the destination
		// node
		Node nextHopNode = source.routingTable.getClosestNode(source);

		// Print the route on the console
		if (nextHopNode != null) {
			try {
				Logger.d(TableEntry.getTemplate(
						source.getIndex(), 
						source.getIPAddress().getHostAddress(), 
						nextHopNode.getIndex(), 
						nextHopNode.getIPAddress().getHostAddress()));
			} catch (UnknownHostException e) {
				// ignore
			}
		} else {
			return;
		}

		// Do TCP Handshake between nodes
		datagram.setSource(source);
		datagram.setDestination(nextHopNode);
		
		doTCPHandshake(source, nextHopNode, datagram);
		
		Logger.d(TAG, "doTCPHandshake() status " + (nextHopNode.getIndex() == datagram.message.toNode.getIndex()));

		if (nextHopNode.getIndex() == datagram.message.toNode.getIndex()) {
			//update the datagram
			datagram.setSource(datagram.message.toNode);
			datagram.setDestination(datagram.message.fromNode);
			//do TCP handshake
			doTCPHandshake(datagram.message.toNode, datagram.message.fromNode, datagram);
			return;
		}

		// route to the next HOP
		processRouting(nextHopNode, datagram);
	}

	public void doTCPHandshake(Node currentNode, Node nextHopNode, Datagram datagram) {
		Logger.d(TAG, "doTCPHandshake() between " + currentNode + " to " + nextHopNode);

		serverTrait = new ServerTrait(nextHopNode);

		timer = new Timer();
		timer.schedule(new InitServerListening(), 500);

		// nextHopNode : will be server
		// currentNode will try to connect to the ServerSocket
		ConnStream stream = new ConnStream(currentNode, nextHopNode, datagram);
		new Thread(stream).run();

		// Terminate the timer thread
		timer.cancel();
	}

	private Timer timer;

	class InitServerListening extends TimerTask {
		public void run() {
			Logger.d(TAG, "InitServerListening.run()");
			// server thread to listen to the ServerSocket
			new Thread(serverTrait).start();
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + index + "] Node(" + address + ":" + port + ")";
	}
}

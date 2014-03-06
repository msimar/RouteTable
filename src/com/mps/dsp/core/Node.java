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
	private final BlockingQueue<String> messageQue = new LinkedBlockingQueue<String>();

	/**
	 * Receive a message.
	 * 
	 * @param message
	 *            the received message
	 */
	public void receive(String message) {
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
						String message = messageQue.take();

						Logger.d(TAG, "Message Blocking Queue :: Message Recevied: " + message);
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
			routeNodePointerIndex = (this.identifier + (int) Math.pow(2, i));

			if (routeNodePointerIndex >= Configuration.MAX_IDENTIFIER)
				routeNodePointerIndex = routeNodePointerIndex
						- Configuration.MAX_IDENTIFIER;

			// Logger.INSTANCE.d(LTAG, routeNodePointerIndex);

			// ping the node -- is node still action ?
			// YES

			if (routeNodePointerIndex != this.identifier) {
				Node closestSuccessorNode = NodeRegistry.getInstance().getNodesMap().get(routeNodePointerIndex);
				
				this.routingTable.addTableEntry(this, closestSuccessorNode);
				this.routingTable.addToModuloSuccessorList(closestSuccessorNode);

				// add successor
				this.routingTable.setSuccessor(NodeRegistry.getInstance()
						.getNodesMap().get(this.identifier + 1));
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
		// find the closest node to route the message near to the destination
		// node
		Node nextHopNode = source.routingTable.getClosestNode(datagram.message.toNode);

		// Print the route on the console
		if (nextHopNode != null) {
			try {
				Logger.console(TableEntry.getTemplate(
						source.getIndex(), 
						source.getIPAddress().getHostAddress(), 
						nextHopNode.getIndex(), 
						nextHopNode.getIPAddress().getHostAddress()));
				Logger.console("--------------------------------------------------------------");
			} catch (UnknownHostException e) {
				// ignore
			}
		} else {
			return;
		}
		
		Logger.d(TAG, "processRouting() : source : " + source);

		// Update the datagram for transferring packets
		datagram.setSource(source);
		datagram.setDestination(nextHopNode);
		
		// Do TCP Handshake between nodes
		doTCPHandshake(source, nextHopNode, datagram);
		
		Logger.d(TAG, "doTCPHandshake() Status " + (nextHopNode.getIdentifier() == datagram.message.toNode.getIdentifier()));

		if (nextHopNode.getIdentifier() == datagram.message.toNode.getIdentifier()) {
			// Update the datagram for transferring packets
			datagram.setSource(datagram.message.toNode);
			datagram.setDestination(datagram.message.fromNode);
			
			// Do TCP Handshake between final nodes
			doTCPHandshake(datagram.message.toNode, datagram.message.fromNode, datagram);
			
			try {
				Logger.console(TableEntry.getTemplate(
						datagram.message.toNode.getIndex(), 
						datagram.message.toNode.getIPAddress().getHostAddress(), 
						datagram.message.fromNode.getIndex(), 
						datagram.message.fromNode.getIPAddress().getHostAddress()));
				Logger.console("------------------------------------------------------");
			} catch (UnknownHostException e) {
				// ignore
			}
			
			return;
		}

		// route to the next HOP
		processRouting(nextHopNode, datagram);
	}

	public void doTCPHandshake(Node currentNode, Node nextHopNode, Datagram datagram) {
		Logger.d(TAG, "doTCPHandshake() between " + currentNode + " to " + nextHopNode);

		serverTrait = new ServerTrait(nextHopNode);
		Thread sThread = new Thread(serverTrait);
		sThread.start();

		// nextHopNode : will be server
		// currentNode will try to connect to the ServerSocket
		ConnStream stream = new ConnStream(currentNode, nextHopNode, datagram);

		timer = new Timer();
		timer.schedule(new InitServerListening(stream),100);
		
		Timer tcpTimer = new Timer();
		
		while( stream.isRunning() ){
			// waiting for processing
			tcpTimer.scheduleAtFixedRate(new TCPLogProcess(), 1000, 1000);
		}	
		System.out.println();
		Logger.d(TAG, "doTCPHandshake() stream isRunning : " + stream.isRunning());
		
		// Terminate the timer thread
		timer.cancel();
		tcpTimer.cancel();
		Logger.d(TAG, "doTCPHandshake() timer task cancel ");
		
		// do clean up
		sThread = null;
		stream = null;
		timer = null;
	}

	private Timer timer;

	class InitServerListening extends TimerTask {
		ConnStream stream;
		public InitServerListening(ConnStream stream) {
			this.stream = stream;
		}
		public void run() {
			Logger.d(TAG, "Init stream.run()");
			new Thread(stream).start();
		}
	}
	
	class TCPLogProcess extends TimerTask {
		public void run() {
			System.out.print(".");
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + index + "] Node(" + address + ":" + port + ")";
	}
}

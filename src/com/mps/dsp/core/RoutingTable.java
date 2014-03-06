package com.mps.dsp.core;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.mps.dsp.util.Logger;
import com.mps.dsp.util.Util;

public class RoutingTable {

	/**
	 * The Node instance to hold immediate successor/predecessor
	 */
	private Node successor;
	private Node predecessor;

	/**
	 * Collection to hold the set of nodes as a 
	 * n+2^1, n+2^2, n+2^3,…, n+2^L (all arithmetic operations modulo N)
	 */
	private final LinkedList<Node> moduloSuccessorList = new LinkedList<Node>();
	
	/**
	 * Routing Table Entry Map : Maps <Node designated as toNode, Table Entry>
	 */
	private final ConcurrentHashMap<Node, TableEntry> tableEntryMap;
	
	/**
	 * Collection of TableEntry as List<TableEntry>
	 */
	private final List<TableEntry> routingTableTreeSet;
	
	/**
	 * Instance of TableViewBuilder to generate views
	 */
	private final TableViewBuilder viewBuilder;

	/**
	 * Creates an RoutingTable object.
	 */
	public RoutingTable() {
		this.successor = null;
		this.predecessor = null;
		
		this.tableEntryMap = new ConcurrentHashMap<Node, TableEntry>();
		this.routingTableTreeSet = new ArrayList<TableEntry>();
		
		this.viewBuilder = new TableViewBuilder();
	}

	/**
	 * Return the successor neighbor node
	 * @return the successor node
	 */
	public UnitNode getSuccessor() {
		return successor;
	}

	/**
	 * Set the Successor to the given parameter
	 * @param successor the successor Node in Routing table
	 */
	public void setSuccessor(Node successor) {
		this.successor = successor;
	}

	/**
	 * Return the arithmetic operations modulo N successor list
	 * @return the collection as LinkedList<Node>
	 */
	public LinkedList<Node> getModuloSuccessorList() {
		return moduloSuccessorList;
	}
	
	/**
	 * Add the node to the arithmetic operations modulo N successor list
	 * @param node the node require to add in the list
	 * @return True if successfully added node to the list, false otherwise.
	 */
	public boolean addToModuloSuccessorList(Node node) {
		return moduloSuccessorList.add(node);
	}
	
	/**
	 * Add pair of Source and Destination Node as TableEntry in routing table.
	 * @param source the source Node for routing table
	 * @param destination the destination Node for routing table
	 * @throws UnknownHostException
	 */
	public void addTableEntry(Node source, Node destination) throws UnknownHostException{
		// create a table entry
		
		TableEntry tEntry = new TableEntry(source.getIPAddress().getHostAddress() + ":" + source.getPort(), 
				destination.getIPAddress().getHostAddress() + ":" + destination.getPort(), 1);
		
		tableEntryMap.put(destination, tEntry);
		
		routingTableTreeSet.add(tEntry);
	}

	/**
	 * Return the predecessor neighbor node
	 * @return the predecessor node
	 */
	public Node getPredeccessor() {
		return predecessor;
	}

	/**
	 * Set the Predecessor to the given parameter
	 * @param predecessor the predecessor Node in Routing table
	 */
	public void setPredeccessor(Node predecessor) {
		this.predecessor = predecessor;
	}
	
	/**
	 * Return the header template for TableViewBuilder
	 */
	public void getHeaderTemplate(){
		viewBuilder.getHeader();
	}
	
	/**
	 * Get the view for each TableEntry
	 */
	public void getView(){
		viewBuilder.getView();
	}
	
	/**
	 * Get closest Node to the given Node from the Routing Table
	 * 
	 * @param destination the node to locate closest node
	 * @return the closest node to the given node
	 */
	public Node getClosestNode(Node destination){
		
		Node nextHopNode = null;
		int minimumHopDistance = Util.INVALID_INDEX;
		
		for (Node successorNode : this.getModuloSuccessorList()) {

			if( minimumHopDistance == Util.INVALID_INDEX ){
				// update the minimum hop node distance
				minimumHopDistance = Math.abs(destination.getIdentifier() - successorNode.getIdentifier());
				// update the next hope node
				nextHopNode = successorNode;
			}else{
				int hopDifference = Math.abs(destination.getIdentifier() - successorNode.getIdentifier());
				if (minimumHopDistance > hopDifference ) {
					// update the min hop node distance
					minimumHopDistance = hopDifference ;
					// update the next hope node
					nextHopNode = successorNode;
				}
			}
		}
		return nextHopNode;
	}
	
	/**
	 * A View Builder class to Show formatted Routing Table
	 * @author msingh
	 *
	 */
	final class TableViewBuilder {
		
		/**
		 * Return the header template for TableViewBuilder
		 */
		public void getHeader(){
			
			final String HEADER = String.format("%15s  |  %15s  | %10s  | %4s",
					"Source", "Destination", "Interface", "Metric");;
			final String DIVIDER = "--------------------------------------------------------------";
			
			Logger.console(DIVIDER);
			Logger.console(HEADER);
			Logger.console(DIVIDER);
		}
		
		/**
		 * Get the view for each TableEntry
		 */
		public void getView(){
			Iterator<TableEntry> iterator = routingTableTreeSet.iterator();
		 
			this.getHeader();
			
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		}
		
//		public static void main(String[] args) {
//			TableEntry t1 = new TableEntry("127.127.127.127", "127.0.0.0", 1);
//			TableEntry t2 = new TableEntry("127.0.0.0", "17.0.0.0", 13);
//			TableEntry t3 = new TableEntry("127.127.127.127", "127.127.127.127", 1);
//			TableViewBuilder.header();
//			System.out.println(t1);
//			System.out.println(t2);
//			System.out.println(t3);
//		}
	}
}

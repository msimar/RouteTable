package com.mps.dsp.core;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class RoutingTable {

	// routing pointer for immediate successor
	private Node successor;

	private Node predecessor;

	// n+2^1, n+2^2, n+2^3,…, n+2^L (all arithmetic operations modulo N)
	private final LinkedList<Node> moduloSuccessorList = new LinkedList<Node>();
	
	/**
	 * Routing Table Entry Map : Maps <Node designated as toNode, Table Entry>
	 */
	private final ConcurrentHashMap<Node, TableEntry> tableEntryMap;
	
	private final TreeSet<TableEntry> routingTableTreeSet;
	
	private final TableViewBuilder viewBuilder;

	public RoutingTable() {
		this.successor = null;
		this.predecessor = null;
		
		this.tableEntryMap = new ConcurrentHashMap<Node, TableEntry>();
		this.routingTableTreeSet = new TreeSet<TableEntry>();
		
		this.viewBuilder = new TableViewBuilder();
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
	
	public boolean addToModuloSuccessorList(Node node) {
		return moduloSuccessorList.add(node);
	}
	
	public void addTableEntry(Node source, Node destination) throws UnknownHostException{
		// create a table entry
		TableEntry tEntry = new TableEntry(source.getIPAddress().toString(), destination.getIPAddress().toString(), 1);
		
		tableEntryMap.put(destination, tEntry);
		
		routingTableTreeSet.add(tEntry);
	}

	public Node getPredeccessor() {
		return predecessor;
	}

	public void setPredeccessor(Node predecessor) {
		this.predecessor = predecessor;
	}
	
	public void getView(){
		viewBuilder.getView();
	}
	
	/**
	 * A View Builder class to Show formatted Routing Table
	 * @author msingh
	 *
	 */
	final class TableViewBuilder {
		
		public void getHeader(){
			
			final String HEADER = String.format("%15s  |  %15s  | %10s  | %4s",
					"Source", "Destination", "Interface", "Metric");;
			final String DIVIDER = "--------------------------------------------------------------";
			
			System.out.println(DIVIDER);
			System.out.println(HEADER);
			System.out.println(DIVIDER);
		}
		
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

package com.mps.dsp.core;

/**
 * A class represent rows for the Routing Table of Node. 
 * It is the basic data structure used to represent row 
 * in the routing table for each Node.
 * 
 * @author msingh
 *
 */
public final class TableEntry  implements Comparable<TableEntry>{
	
	/**
	 * the Destination Node address
	 */
	public String destination;
	
	/**
	 * Number of hops between source and destination
	 */
	public int metric;
	
	/**
	 * the Source Node address
	 */
	public String source;
	
	/**
	 * the Routing interface 
	 */
	public String _interface;
	
	/**
	 * Creates TableEntry object
	 * @param source the source Node address
	 * @param destination the destination Node address
	 * @param metric the hop between nodes
	 */
	public TableEntry(String source, String destination, int metric) {
		// TODO Auto-generated constructor stub
		this.source = source;
		this.destination = destination;
		this.metric = metric;
		this._interface = "eth0";
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%15s  |  %15s  | %10s  | %4d",this.source, this.destination, this._interface, this.metric);
	}
	
	/**
	 * Create basic template for the given values to represent table entry for routing table.
	 * 
	 * @param sourceIndex the index for the source node
	 * @param source the source Node address
	 * @param destinationIndex the index for the destination node
	 * @param destination the destination Node address
	 * @return A String representation of formatted table entry
	 */
	public static String getTemplate(int sourceIndex, String source, int destinationIndex, String destination){
		return String.format("%15s  |  %15s  | %10s  | %4d", 
				"[" + sourceIndex + "]" + source, 
				"[" + destinationIndex + "]" + destination, 
				"eth0", 1);
	}

	/**
	 * @see java.lang.Comparable#compareTo
	 */
	@Override
	public int compareTo(TableEntry o) {
		// TODO Auto-generated method stub
		return metric - o.metric;
	}
	
//	/**
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + metric;
//		return result;
//	}
//
//	/**
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		TableEntry other = (TableEntry) obj;
//		if (metric != other.metric)
//			return false;
//		return true;
//	}
}

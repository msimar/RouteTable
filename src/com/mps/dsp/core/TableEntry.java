package com.mps.dsp.core;


public final class TableEntry  implements Comparable<TableEntry>{
	/**
	 * Destination address
	 */
	public String destination;
	
	/**
	 * Number of hops between source and destination
	 */
	public int metric;
	
	/**
	 * Source address
	 */
	public String source;
	
	/**
	 * Routing interface 
	 */
	public String _interface;
	
	public TableEntry(String source, String destination, int metric) {
		// TODO Auto-generated constructor stub
		this.source = source;
		this.destination = destination;
		this.metric = metric;
		this._interface = "eth0";
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%15s  |  %15s  | %10s  | %4d",this.source, this.destination, this._interface, this.metric);
	}

	/**
	 * @see java.lang.Comparable#compareTo
	 */
	@Override
	public int compareTo(TableEntry o) {
		// TODO Auto-generated method stub
		return metric - o.metric;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + metric;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableEntry other = (TableEntry) obj;
		if (metric != other.metric)
			return false;
		return true;
	}
}

package com.mps.dsp.core;

public class UnitNode{
	
	protected int address;
	protected int port;
	protected int index;

	protected boolean status;
	
	/**
	 * Identifier obtain after hashing the IP Address and Port
	 */
	protected int identifier;
	
	public UnitNode(int index, int address, int port) {
		setIndex(index);
		setAddress(address);
		setPort(port);
		// TODO set identifier as the hash value
		setIdentifier(index);
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIdentifier() {
		return identifier;
	}

	private void setIdentifier(int value) {
		this.identifier = value;
		//TODO :: verify uniqueness of hash table
		//this.identifier = Util.hash(key);
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}

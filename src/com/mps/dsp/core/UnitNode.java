package com.mps.dsp.core;

public class UnitNode{
	
	protected int address;
	protected int port;
	
	/**
	 * Identifier obtain after hashing the IP Address and Port
	 */
	protected int identifier;
	
	public UnitNode(int address, int port) {
		// TODO Auto-generated constructor stub
		setAddress(address);
		setPort(port);
		setIdentifier(address);
	}

	public int getIdentifier() {
		return identifier;
	}

	private void setIdentifier(int key) {
		this.identifier = key;
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
}

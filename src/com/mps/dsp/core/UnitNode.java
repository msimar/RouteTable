package com.mps.dsp.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UnitNode{
	
	protected String address;
	protected String port;
	protected int index;

	protected boolean status;
	
	/**
	 * Identifier obtain after hashing the IP Address and Port
	 */
	protected int identifier;
	
	public UnitNode(int index, String address, String port) {
		setIndex(index);
		setAddress(address);
		setPort(port);
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

	public void setIdentifier(int value) {
		this.identifier = value;
	}

	public String getAddress() {
		return address;
	}
	
	public InetAddress getIPAddress() throws UnknownHostException{
		return InetAddress.getByName(address);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}

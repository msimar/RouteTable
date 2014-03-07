package com.mps.dsp.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * UnitNode represents a basic unit to represent Node in the network.
 *  
 * @author msingh
 *
 */
public class UnitNode{
	
	/**
	 * A String address for the host
	 */
	protected String address;
	
	/**
	 * A String port for the host
	 */
	protected String port;
	
	/**
	 * An index for the host
	 */
	protected int index;

	/**
	 * the status for the host
	 */
	protected boolean status;
	
	/**
	 * A Identifier to distribute Node for routing
	 */
	protected int identifier;
	
	/**
	 * Creates a UnitNode Object
	 * @param index An index for the host
	 * @param address A String address for the host
	 * @param port A String port for the host
	 */
	public UnitNode(int index, String address, String port) {
		setIndex(index);
		setAddress(address);
		setPort(port);
	}
	
	
	/**
	 * Returns the index of host Node
	 * @return  the index of host Node
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Set the index of host Node
	 * @param index the index of host Node
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Returns the identifier of host Node
	 * @return  the identifier of host Node
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * Set the identifier of host Node
	 * @param value the identifier of host Node
	 */
	public void setIdentifier(int value) {
		this.identifier = value;
	}

	/**
	 * Returns the address of host Node
	 * @return  the address of host Node
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Returns the InetAddress address of host Node
	 * @return  the InetAddress address of host Node
	 */
	public InetAddress getIPAddress() throws UnknownHostException{
		return InetAddress.getByName(address);
	}

	/**
	 * Set the address of host Node
	 * @param address the address of host Node
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Returns the port of host Node
	 * @return  the port of host Node
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Set the port of host Node
	 * @param port the port of host Node
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * Returns the status of host Node
	 * @return  the status of host Node
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * Set the status of host Node
	 * @param status the status of host Node
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
}

package com.mps.dsp.test;

import com.mps.dsp.config.Configuration;
import com.mps.dsp.core.Node;
import com.mps.dsp.core.NodeRegistry;
import com.mps.dsp.test.RoutingTest.RouteCommand;

/**
 * The Class to perform App Unit testing. It 
 * validate functional aspect of algorithm to 
 * route message between nodes.
 * 
 * @author msingh
 *
 */
public class UnitTest {

	/**
	 * Test the routing table for all the nodes in the 
	 * network.
	 */
	public static void testRoutingTable() {
		// show routing table for each node
		for (int i = 0; i < Configuration.MAX_NODE; i++) {
			// get the Node
			Node node = NodeRegistry.getInstance().getNodesMap().get(i);
			// Show routing table for that node
			System.out.println("Routing Table : " + node);
			node.routingTable.getView();
			System.out.println();
		}
	}

	/**
	 * Test the routing table for each node in the 
	 * network.
	 */
	public static void testEachNodeRoutingTable(Node node) {
		// Show routing table for that node
		System.out.println("Routing Table : " + node);
		node.routingTable.getView();
		System.out.println();
	}

//	public static void testRouting() {
//		// Unit Test for Nodes
//		Node source = NodeRegistry.getInstance().getNodesMap().get(1);
//		Node destination = NodeRegistry.getInstance().getNodesMap()
//				.get(Configuration.MAX_NODE - 1);
//
//		source.route(destination, new Message(source, destination, "Chi"));
//	}
	
	/**
	 * Test the routing command in the overlay
	 * network.
	 */
	public static void testRoutingCommand(){
		RoutingTest test =  new RoutingTest();
		test.parseCommandFile();
		
		RouteCommand command = test.getRouteCommand();
		
		command.sourceNode.route(command.destintionNode, command.datagram);
	}
	
	/**
	 * Test the routing command in the overlay
	 * network as an argument as filename.
	 * 
	 * @param fileName the fileName containing route command
	 */
	public static void testRoutingCommand(String fileName){
		RoutingTest test =  new RoutingTest();
		test.parseCommandFile(fileName);
		
		RouteCommand command = test.getRouteCommand();
		
		command.sourceNode.route(command.destintionNode, command.datagram);
	}

}

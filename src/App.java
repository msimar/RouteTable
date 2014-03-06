import java.net.UnknownHostException;

import com.mps.dsp.config.ConfigFileReader;
import com.mps.dsp.config.ConfigFileWriter;
import com.mps.dsp.config.Configuration;
import com.mps.dsp.core.Node;
import com.mps.dsp.core.NodeRegistry;
import com.mps.dsp.test.UnitTest;

/**
 * Entry point class for the project.
 * 
 * @author msingh
 *
 */
public class App {

	public static void main(String[] args) throws UnknownHostException {
		
		// Configure App constants and Nodes
		Configuration.config();
		
		// Write to the nodelist.txt file
		new ConfigFileWriter().writeToConfigFile();
		
		// Read from the nodelist.txt file, build nodes for network overlay
		new ConfigFileReader().parseConfigFile();
		
		// 
		for (int i = 0; i < Configuration.MAX_NODE; i++) {
			Node node = NodeRegistry.getInstance().getNodesMap().get(i);
			
			node.execute();
			// this should be called, only when all the nodes are build in network overlay
			node.speedUpLookup();
	 		// active each node to create ServerSocket and consumer thread to listen to messages. 
			//node.execute();
			//UnitTest.testEachNodeRoutingTable(node);
		}
		// Command to test Routing Command
		UnitTest.testRoutingCommand();
		
		// Command to test routing
		//UnitTest.testRouting();
	}
}

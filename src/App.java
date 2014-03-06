import java.net.UnknownHostException;

import com.mps.dsp.config.ConfigFileReader;
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

	/**
	 * Main Call to the App
	 * @param args String arguments expected : Node lists file and Route Command file
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {

		if (args.length < 2) {
			System.out.println("Error : Command line arguments missing ");
			System.out.println("Usage : java App <node_configuration_file> <route_command_file> ");
			System.out.println("For more information, check nodelist.txt and command.txt file ");			
			System.exit(1);
			return;
		}

		// Step 1:: parse the configuration file
		// parse configuration file to data structure
		boolean isNodeExists = new ConfigFileReader().parseConfigFile(args[0]);

		// Write to the nodelist.txt file
		// new ConfigFileWriter().writeToConfigFile();

		// Read from the nodelist.txt file, build nodes for network overlay
		// boolean isNodeExists = new ConfigFileReader().parseConfigFile();

		if (isNodeExists) {
			// Configure App constants and Nodes
			Configuration.config();
			
			for (int i = 0; i < Configuration.MAX_NODE; i++) {
				Node node = NodeRegistry.getInstance().getNodesMap().get(i);

				node.execute();
				// this should be called, only when all the nodes are build in
				// network overlay
				node.speedUpLookup();
				// active each node to create ServerSocket and consumer thread
				// to listen to messages.
				// node.execute();
				// UnitTest.testEachNodeRoutingTable(node);
			}
			
			UnitTest.testRoutingCommand(args[1]);
			
			// Command to test Routing Command
			// UnitTest.testRoutingCommand();

			// Command to test routing
			// UnitTest.testRouting();
		}

	}
}

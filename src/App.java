import com.mps.dsp.config.ConfigFileReader;
import com.mps.dsp.config.ConfigFileWriter;
import com.mps.dsp.config.Configuration;
import com.mps.dsp.core.Message;
import com.mps.dsp.core.Node;
import com.mps.dsp.core.NodeRegistry;

public class App {

	public static void main(String[] args) {
		
		// Configure App constants and Nodes
		Configuration.config();
		
		// Write to the nodelist.txt file
		new ConfigFileWriter().writeToConfigFile();
		
		// Read from the nodelist.txt file, build nodes for network overlay
		new ConfigFileReader().parseConfigFile();
		
		// 
		for (int i = 0; i < Configuration.MAX_NODE; i++) {
			Node node = NodeRegistry.getInstance().getNodesMap().get(i);
			
			// this should be called, only when all the nodes are build in network overlay
			node.speedUpLookup();
			
			// active each node to create ServerSocket and consumer thread to listen to messages. 
			node.execute();
		}
		
		// Unit Test for Nodes
		Node source = NodeRegistry.getInstance().getNodesMap().get(3);
		Node destination = NodeRegistry.getInstance().getNodesMap().get(Configuration.MAX_NODE-1);
		
		source.route(destination, new Message(source, destination));
	}
}

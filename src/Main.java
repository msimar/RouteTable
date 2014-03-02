import com.mps.dsp.core.Message;
import com.mps.dsp.core.Node;
import com.mps.dsp.core.NodeRegistry;
import com.mps.dsp.core.Routing;
import com.mps.dsp.core.Util;

public class Main {

	public static void main(String[] args) {
		
		Util.config();
		
		for (int i = 0; i < Util.MAX_NODE; i++) {
			NodeRegistry.getInstance().register(new Node(i, "localhost", "default"));
		}
		
		for (int i = 0; i < Util.MAX_NODE; i++) {
			NodeRegistry.getInstance().getNodesMap().get(i).speedUpLookup();;
		}
		
		// Unit Test for Nodes
		Node source = NodeRegistry.getInstance().getNodesMap().get(3);
		Node destination = NodeRegistry.getInstance().getNodesMap().get(Util.MAX_NODE-1);
		
		Routing.getInstance().route(source, destination, new Message(source, destination, "ping"));
		 
		
	}

}

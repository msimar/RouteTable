import com.mps.dsp.core.Node;
import com.mps.dsp.core.NodeRegistry;
import com.mps.dsp.core.Routing;
import com.mps.dsp.core.Util;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < Util.MAX_NODE; i++) {
			NodeRegistry.getInstance().register(new Node(i, i, i));
		}
		
		for (int i = 0; i < Util.MAX_NODE; i++) {
			NodeRegistry.getInstance().getNodesMap().get(i).speedUpLookup();;
		}
		
		Node source = NodeRegistry.getInstance().getNodesMap().get(3);
		Node destination = NodeRegistry.getInstance().getNodesMap().get(15);
		
		Routing.getInstance().route(source, destination, "hello");
	}

}

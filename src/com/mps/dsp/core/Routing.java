package com.mps.dsp.core;

public class Routing {

	public static final String LTAG = Routing.class.getSimpleName();
	
	private Routing() {
		// Singleton constructor
	}
	
	private static Routing INSTANCE;
	
	public static synchronized Routing getInstance(){
		if( INSTANCE == null)
			INSTANCE = new Routing();
		return INSTANCE;
	}
	 
	public void route(Node source, Node destination, Message message) {
 
		Node nextHopNode = null;
		int minimumHopDistance = Util.INVALID_INDEX;
		
		for (Node successorNode : source.getModuloSuccessorList()) {

			if( minimumHopDistance == Util.INVALID_INDEX ){
				// update the minimum hop node distance
				minimumHopDistance = destination.getIndex() - successorNode.getIndex();
				// update the next hope node
				nextHopNode = successorNode;
			}else{
				if (minimumHopDistance > (destination.getIndex() - successorNode.getIndex())) {
					// update the min hop node distance
					minimumHopDistance = destination.getIndex() - successorNode.getIndex();
					// update the next hope node
					nextHopNode = successorNode;
				}
			}
		}
		
		if(nextHopNode != null) {
			Logger.INSTANCE.d(source + " >>>> " + nextHopNode);
		}else{
			return;
		}
		
		if( nextHopNode.getIndex() == destination.getIndex() ) return;
		
		// route to the next hop
		route(nextHopNode,destination,message);
	}
   
}
package implementation;

import java.util.*;

/**
 * Implements a graph. We use two maps: one map for adjacency properties 
 * (adjancencyMap) and one map (dataMap) to keep track of the data associated 
 * with a vertex. 
 * 
 * @author cmsc132
 * 
 * @param <E>
 */
public class Graph<E> {
	/* You must use the following maps in your implementation */
	private HashMap<String, HashMap<String, Integer>> adjacencyMap; //vertex name, map of neighbors: cost
	private HashMap<String, E> dataMap; //some data associated with each vertex

	public Graph() {
		this.adjacencyMap = new HashMap<>();
		this.dataMap = new HashMap<>();
	}

	
	public void addVertex(String vertexName, E data) { 
		if (vertexName == null|| data == null) {
			throw new IllegalArgumentException("Null vertex name or data"); 
		} else if (adjacencyMap.containsKey(vertexName)) {
			throw new IllegalArgumentException("Vertex with this name already exists");
		} 
		
		this.adjacencyMap.put(vertexName, new HashMap<>());
		this.dataMap.put(vertexName, data);
	}
	
	
	public void addDirectedEdge(String startVertexName, String endVertexName, int cost) {
		if (startVertexName == null || endVertexName == null || cost < 0) {
			throw new IllegalArgumentException("Null start or end Vertex name entered or invalid Cost");
		} else if (!adjacencyMap.containsKey(startVertexName)||
				!adjacencyMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("start or end vertex does not exist in the graph");
		}
		
		this.adjacencyMap.get(startVertexName).put(endVertexName, cost);
	}
	
	
	
	/* Returns a map with information about vertices adjacent to vertexName.
	 * If the vertex has no adjacents, an empty map is returned.
	*/
	public Map<String, Integer> getAdjacentVertices(String vertexName) {
		if (vertexName == null) {
			throw new IllegalArgumentException("Null vertex Name");
		} else if (!adjacencyMap.containsKey(vertexName)) {
			throw new IllegalArgumentException("vertex not in grpah");
		}
		
		return adjacencyMap.get(vertexName);
	}
	
	
	/* Returns the cost associated with the specified edge. */
	public int getCost(String startVertexName, String endVertexName) {
		if (startVertexName == null || endVertexName == null ) {
			throw new IllegalArgumentException("Null start or end Vertex name");
			
		} else if (!adjacencyMap.containsKey(startVertexName) || !adjacencyMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("start or end vertex given does not exist"); 
			
		} else if (!adjacencyMap.get(endVertexName).containsKey(endVertexName)) {
			throw new IllegalArgumentException("This neighbor does not exist (therfore the edge cost also doesn't).");
		}
		//returns the value associated with the specified neighbor
		return adjacencyMap.get(startVertexName).get(endVertexName);
	}
	
	/* Returns a Set with all the graph vertices. */
	public Set<String> getVertices() { 
		Set<String> vertices = adjacencyMap.keySet();
		return vertices;
	}
	
	/* Returns the data component associated with the specified vertex. */
	public E getData(String vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException("Invalid vertex name");
			
		} else if (!adjacencyMap.containsKey(vertex)) {
			throw new IllegalArgumentException("Vertex does not exist");
		}
		
		return this.dataMap.get(vertex);
	}
	
	/* Returns a string with information about the Graph.
	 * Notice that vertices are printed in sorted order and information about adjacent edges is
	 * printed in sorted order (by vertex name). You may not use Collections.sort or Arrays.sort
	 * in order to implement this method. See the sample output for formatting details.
	 * return string with graph information
	 */
	public String toString() {
		String result = "";
		TreeSet<String> vertices = new TreeSet<String>(adjacencyMap.keySet());
		
		result += "Vertices: ";
		result += vertices + "\n";
		result += "Edges:\n";
		for (String v: vertices) {
			result += "Vertex(" + v + ")--->{";
			
			//sorted order for neighbors list
			Map<String,Integer> neighbors = getAdjacentVertices(v);
	        TreeSet<String> sortedNeighbors = new TreeSet<>(neighbors.keySet());		
	        
	        boolean first = true;
	        for (String s : sortedNeighbors) {
	            if (!first) {
	            	result += ", ";
	            }
	            result += s + "=" + neighbors.get(s);
	            first = false;
	        }
	        result += "}\n";
		}
		
		return result;
	}
	
	public void doDepthFirstSearch(String startVertexName, CallBack<E> callBack)  {
		//Callback - Represents the processing to apply to each vertex
		if (startVertexName == null || callBack == null) {
			throw new IllegalArgumentException("Null Vertex name or callBack operation");
			
		} else if (!adjacencyMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("Vertex does not exist");
		}
		
		Set<String> visited = new HashSet<String>(); // holds vertexes already visited
		Stack<String> discovered = new Stack<String>(); //awaiting visitation
		
		discovered.add(startVertexName); //add start to stack
		
		while (!discovered.isEmpty()) {  //as long as we haven't traveresed everything...
			
			String cur = discovered.pop(); //retrieve and remove top item (LIFO)
			
			if (visited.add(cur)) { 
				callBack.processVertex(cur, dataMap.get(cur)); //process it 
				
				for (String neighbor: getAdjacentVertices(cur).keySet()) {
					if (!visited.contains(neighbor)) { //add unvisited neighbor nodes to stack 
						discovered.add(neighbor);
					}
				}
			}
		} 
	}
	
	
	public void doBreadthFirstSearch(String startVertexName, CallBack<E> callBack) {
		if (startVertexName == null || callBack == null) {
			throw new IllegalArgumentException("Null Vertex name or callBack operation");
			
		} else if (!adjacencyMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("Vertex does not exist");
		}
		
		Set<String> visited = new HashSet<>();
		Queue<String> discovered = new ArrayDeque<>(); // Awaiting visitation FIFO
		
		discovered.add(startVertexName); 
		
		while (!discovered.isEmpty()) { 
			String cur = discovered.remove(); //retrieve and remove bottom item, FIFO
			
			if (visited.add(cur)) { //returns true upon adding it 
				callBack.processVertex(cur, dataMap.get(cur));
				
				for (String neighbor: getAdjacentVertices(cur).keySet()) {
					if (!visited.contains(neighbor)) { // add neighbors to queue
						discovered.add(neighbor); 
					}
				}
			}
		}
	}
	
	
	public int doDijkstras(String startVertexName, String endVertexName, ArrayList<String> shortestPath) {
		if (startVertexName == null || endVertexName == null || shortestPath ==null) {
			throw new IllegalArgumentException("null start/end vertex name or null shortest path array");
	
		} else if (!adjacencyMap.containsKey(startVertexName) || !adjacencyMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("Invalid vertex given"); 
		}
		 
		Map<String, Integer> dist = new HashMap<>(); // map <name, cost> 
		Map<String, String> prev = new HashMap<>(); // map <name, predecessor>
		Set<String> visited = new HashSet<>(); 
		PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(vertex -> dist.get(vertex))); //so we always get the least cost
		
		//initialize everything as max_value 
		for (String vertex: adjacencyMap.keySet()) {
			dist.put(vertex, Integer.MAX_VALUE);
		} 
		
		dist.put(startVertexName, 0); //specify start is 0 cost 
		
		pq.offer(startVertexName); //add to pq for processing

		while (!pq.isEmpty()) { //as long as there is more to process
			String cur = pq.poll(); //retrieves and removes least cost vertex
			if (visited.contains(cur)) { // if prev processed 
				continue; //skip adding it to the pq 
			}
			
			visited.add(cur); //otherwise add it
			
			//processes neighbors
			for (Map.Entry<String, Integer> neighborEntry :adjacencyMap.get(cur).entrySet()) { //looks at a neighbor
				String neighbor = neighborEntry.getKey(); 
				int cost = neighborEntry.getValue(); 
				int newDist = dist.get(cur) + cost; // goal: is this path better 
				
				if (newDist < dist.get(neighbor)) { // if it is better than the previous best...
					dist.put(neighbor, newDist); //add/update distance map with new lowest cost
					prev.put(neighbor, cur); //add/update predecessor with new predessor vertex
					pq.offer(neighbor); // add neighbor to pq for processing
				}
			}
		}
		
		shortestPath.clear(); //clear path to reset it
		if(dist.get(endVertexName) == Integer.MAX_VALUE) { //if the distance remained MAX_VAL it was never visited / disconnected
			shortestPath.add("None"); // no shortest path
			return -1; 
		}
		
		LinkedList<String> path = new LinkedList<>(); //the path from start to end 
		
		for (String s = endVertexName; s!= null; s = prev.get(s)) { //traverse through prevs front end to start
			path.addFirst(s); //adds each to front of list so it reads from start to end not backwards
		}
		
		shortestPath.addAll(path); //add list to the shortest Path var 
		
		return dist.get(endVertexName); //lowest cost to desired end Node
	}
}
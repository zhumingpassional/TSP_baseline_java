package org.example.src;

import java.util.*;

public class Graph {

	private int _numNodes;
	private int _numEdges;    // V-> no. of vertices & E->no.of edges
	private LinkedList<Integer>[] _adj; // adjacency list
	private ArrayList<Integer> _eulerianCircuit = new ArrayList<Integer>();
	
	Graph(int _numNodes, int _numEdges) {
        this._numNodes = _numNodes;
        this._numEdges = _numEdges;

    }
	Graph(int numOfVertices) {
        // initialise vertex count
        this._numNodes = numOfVertices;

        // initialise adjacency list
        initGraph();
    }
	
	void initGraph() {
	        _adj = new LinkedList[_numNodes];
	        for (int i = 0; i < _numNodes; i++)
	        { 
	            _adj[i] = new LinkedList<Integer>();
	        } 
	    } 

	 // add edge u-v 
	void addEdge(Integer u, Integer v) {
	        _adj[u].add(v);
	        _adj[v].add(u);
	    } 
	  
	    // This function removes edge u-v from graph. 
	void removeEdge(Integer u, Integer v) {
	        _adj[u].remove(v);
	        _adj[v].remove(u);
	    }

			//***Eulerian Cycle******

	    /* The main function that print Eulerian Trail.
	       It first finds an odd degree vertex (if there  
	       is any) and then calls printEulerUtil() to 
	       print the path */
	void createEulerCircuit() {
	        // Find a vertex with odd degree 
	        Integer u = 0; 
	        for (int i = 0; i < _numNodes; i++) {
	            if (_adj[i].size() % 2 == 1) {
	                u = i; 
	                break; 
	            } 
	        }
	        // Print tour starting from odd v 
	        eulerUtil(u);
	    } 
	  
	    // Print Euler tour starting from vertex u 
	void eulerUtil(Integer u) {
	        // Recur for all the vertices adjacent to this vertex 
	        for (int i = 0; i < _adj[u].size(); i++) {
	            Integer v = _adj[u].get(i);
	            // If edge u-v is a valid next edge 
	            if (isValidNextEdge(u, v)) {
	                //System.out.print(u + "-" + v + " ");
	                _eulerianCircuit.add(u);
	                _eulerianCircuit.add(v);
	                // This edge is used so remove it now 
	                removeEdge(u, v);  
	                eulerUtil(v);
	            } 
	        } 
	    }
	    
	    // The function to check if edge u-v can be 
	    // considered as next edge in Euler Tout 
	boolean isValidNextEdge(Integer u, Integer v) {
	        // The edge u-v is valid in one of the 
	        // following two cases: 
	  
	        // 1) If v is the only adjacent vertex of u  
	        // ie size of adjacent vertex list is 1 
	        if (_adj[u].size() == 1) {
	            return true; 
	        } 
	  
	        // 2) If there are multiple adjacents, then 
	        // u-v is not a bridge Do following steps  
	        // to check if u-v is a bridge 
	        // 2.a) count of vertices reachable from u 
	        boolean[] isVisited = new boolean[this._numNodes];
	        int count1 = dfsCount(u, isVisited); 
	  
	        // 2.b) Remove edge (u, v) and after removing 
	        //  the edge, count vertices reachable from u 
	        removeEdge(u, v); 
	        isVisited = new boolean[this._numNodes];
	        int count2 = dfsCount(u, isVisited); 
	  
	        // 2.c) Add the edge back to the graph 
	        addEdge(u, v); 
	        return (count1 > count2) ? false : true; 
	    } 
	  
	    // A DFS based function to count reachable 
	    // vertices from v 
	int dfsCount(Integer s, boolean[] isVisited) {
	    	int count=0;
			// Initially mark all vertices as not visited

			// Create a stack for DFS
			Stack<Integer> stack = new Stack<>();

			// Push the current source node
			stack.push(s);

			while(!stack.empty())
			{
				// Pop a vertex from stack and print it
				s = stack.peek();
				stack.pop();

				// Stack may contain same vertex twice. So
				// we need to print the popped item only
				// if it is not visited.
				if(!isVisited[s])
				{
					//System.out.print(s + " ");
					isVisited[s]= true;
					count++;
				}

				// Get all adjacent vertices of the popped vertex s
				// If a adjacent has not been visited, then push it
				// to the stack.
				Iterator<Integer> itr = _adj[s].iterator();

				while (itr.hasNext()) {
					int v = itr.next();
					if(!isVisited[v]) {
						stack.push(v);
					}
				}
			}
			return count;
		}

				//******Hamiltonian Cycle*******
	ArrayList<Integer> clearRepeatedCities(ArrayList<Integer> cities) {
		// Find and remove duplicate cities
		int[] citiesArray = new int[_numNodes];
		ArrayList<Integer> resultCircuit = new ArrayList<Integer>();
		for(int i=0; i<cities.size(); i++) {
			citiesArray[cities.get(i)]++;
			if(citiesArray[cities.get(i)] == 1) {
				resultCircuit.add(cities.get(i));
			}
		}
		resultCircuit.add(resultCircuit.get(0));
		return resultCircuit;
	}

				//*****Perfect Matching******
	Edge[] findAndAddPerfectMatches(Edge[] mst, List<Node> citylist){
    	int[] neighbourCounterOnMST = new int[_numNodes];
    	
    	for(int i = 1 ; i < mst.length ; i++) {
    		int src = mst[i].get_src();
            int dest = mst[i].get_dest();
            neighbourCounterOnMST[src]++;
            neighbourCounterOnMST[dest]++;
    	}
    	
    	ArrayList<Edge> newEdgesForOddVertices = new ArrayList<Edge>();
    	List<Node> oddDegreVertex = new ArrayList<>();
    	
    	for(int i = 0 ; i < neighbourCounterOnMST.length ; i++) {
    		if(neighbourCounterOnMST[i] % 2 == 1) {
    			oddDegreVertex.add(citylist.get(i));
    		}
    	}
    	findMatchesWithNearestNeighbour(oddDegreVertex, newEdgesForOddVertices);
    	
    	//merging new edges into mst so all nodes have even number edge now
    	Edge[] newEdges = newEdgesForOddVertices.toArray(new Edge[0]);
    	int fal = mst.length-1;        //determines length of firstArray  
    	int sal = newEdges.length;   //determines length of secondArray  
    	Edge[] result = new Edge[fal + sal];  //resultant array of size first array and second array
    	System.arraycopy(mst, 0, result, 0, fal);  
    	System.arraycopy(newEdges, 0, result, fal, sal);

    	int[] neighbourCounterOnMST2 = new int[_numNodes];
        
        for (int i = 1; i < fal+sal; ++i) {
        	int src = result[i].get_src();
            int dest = result[i].get_dest();
            neighbourCounterOnMST2[src]++;
            neighbourCounterOnMST2[dest]++;
        }
        return result;
    }

	void findMatchesWithNearestNeighbour(List<Node> oddDegreVertex, ArrayList<Edge> newEdgesForOddVertices) {
		// TODO Auto-generated method stub
		int nextcityIndex=0, indexForRemove=0;
		double distance = 0;
		double min = Double.MAX_VALUE;
		Edge tempEdge;
		
//		int[] temp,temp2;
		Node temp;
		Node temp2;
		for(int i=0 ;  i < oddDegreVertex.size() ;i=nextcityIndex) {
			
			temp=oddDegreVertex.get(i);
			
			oddDegreVertex.remove(i);
			
			for (int k = 0; k < oddDegreVertex.size(); k++) {
				temp2 = oddDegreVertex.get(k);
				
//				distance = Math.round(Math.sqrt(Math.pow(temp[1] - temp2[1], 2) + Math.pow(temp[2] - temp2[2], 2)));
				distance = Util.calcDist(temp, temp2);
				if(distance<min ) {
					min=distance;
					nextcityIndex=0;
					indexForRemove=k;
				}
			}
			
			temp2=oddDegreVertex.get(indexForRemove);
			tempEdge = new Edge();
			tempEdge.set_src(temp.get_index());
			tempEdge.set_dest(temp2.get_index());
			tempEdge.set_weight(min);
			newEdgesForOddVertices.add(tempEdge);
			
			min=Double.MAX_VALUE;
			oddDegreVertex.remove(indexForRemove);
			
			if(oddDegreVertex.size()==2){
				tempEdge = new Edge();
				tempEdge.set_src(oddDegreVertex.get(0).get_index());
				tempEdge.set_dest(oddDegreVertex.get(1).get_index());
//				double weight = Math.round(Math.sqrt(Math.pow(oddDegreVertex.get(0)[1] - oddDegreVertex.get(1)[1], 2) + Math.pow(oddDegreVertex.get(0)[2] - oddDegreVertex.get(1)[2], 2)));
				double weight = Util.calcDist(oddDegreVertex.get(0), oddDegreVertex.get(1));
				tempEdge.set_weight(weight);
				newEdgesForOddVertices.add(tempEdge);
				break;
			}

		}
		
	}

				//******Prim Algorithm****

	// A utility function to find the vertex with minimum key
	// value, from the set of vertices not yet included in MST
	int minKey(int key[], Boolean mstSet[]) {
		// Initialize min value
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < _numNodes; v++)
			if (mstSet[v] == false && key[v] < min) {
				min = key[v];
				min_index = v;
			}

		return min_index;
	}

	// A utility function to print the constructed MST stored in
	// parent[]
	Edge[] getMST(int parent[], int graph[][]) {
		Edge[] mst = new Edge[_numNodes];
		for (int i = 1; i < _numNodes; i++) {
			mst[i]=new Edge();
			mst[i].set_src(parent[i]);
			mst[i].set_dest(i);
			mst[i].set_weight(graph[i][parent[i]]);
		}
		return mst;
	}

	// Function to construct and print MST for a graph represented
	// using adjacency matrix representation
	Edge[] primMST(int graph[][]) {
		// Array to store constructed MST
		int parent[] = new int[_numNodes];

		// Key values used to pick minimum weight edge in cut
		int key[] = new int[_numNodes];

		// To represent set of vertices included in MST
		Boolean mstSet[] = new Boolean[_numNodes];

		// Initialize all keys as INFINITE
		for (int i = 0; i < _numNodes; i++) {
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}

		// Always include first 1st vertex in MST.
		key[0] = 0; // Make key 0 so that this vertex is
		// picked as first vertex
		parent[0] = -1; // First node is always root of MST

		// The MST will have V vertices
		for (int count = 0; count < _numNodes - 1; count++) {
			// Pick thd minimum key vertex from the set of vertices
			// not yet included in MST
			int u = minKey(key, mstSet);

			// Add the picked vertex to the MST Set
			mstSet[u] = true;

			// Update key value and parent index of the adjacent
			// vertices of the picked vertex. Consider only those
			// vertices which are not yet included in MST
			for (int v = 0; v < _numNodes; v++)

				// graph[u][v] is non zero only for adjacent vertices of m
				// mstSet[v] is false for vertices not yet included in MST
				// Update the key only if graph[u][v] is smaller than key[v]
				if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
					parent[v] = u;
					key[v] = graph[u][v];
				}
		}

		// print the constructed MST
		return getMST(parent, graph);
	}

	public int get_numNodes() {
		return _numNodes;
	}

	public void set_numNodes(int _numNodes) {
		this._numNodes = _numNodes;
	}

	public int get_numEdges() {
		return _numEdges;
	}

	public void set_numEdges(int _numEdges) {
		this._numEdges = _numEdges;
	}

	public LinkedList<Integer>[] get_adj() {
		return _adj;
	}

	public void set_adj(LinkedList<Integer>[] _adj) {
		this._adj = _adj;
	}

	public ArrayList<Integer> get_eulerianCircuit() {
		return _eulerianCircuit;
	}

	public void set_eulerianCircuit(ArrayList<Integer> _eulerianCircuit) {
		this._eulerianCircuit = _eulerianCircuit;
	}
}

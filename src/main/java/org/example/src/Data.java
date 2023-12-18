package org.example.src;

import org.example.src.maths.MyArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Data {

	private int _numNodes;
	private int _numEdges;    // V-> no. of vertices & E->no.of edges
	private LinkedList<Integer>[] _adj; // adjacency list
	private ArrayList<Integer> _eulerianCircuit = new ArrayList<Integer>();

	private ArrayList<ArrayList<Double>> _distanceMatrix;

	private ArrayList<Node> nodes;

	Data(int _numNodes, int _numEdges) {
        this._numNodes = _numNodes;
        this._numEdges = _numEdges;
		initGraph();
    }
	Data(int numOfVertices) {
        // initialise vertex count
        this._numNodes = numOfVertices;

        // initialise adjacency list
        initGraph();
    }

	public static Data readData(String dataFilename){
		ArrayList<Node> cityList = new ArrayList<>();

		int numNodes = 0;  // Number of vertices in graph
		int numEdges = 0;  // Number of edges in graph

		// Read input file line by line by using scanner
		try {
			Scanner scanner = new Scanner(new File(dataFilename));
			while (scanner.hasNextLine()) {
				// Clean white spaces
				String cleanResult = Util.cleanInput(scanner.nextLine());
				String[] arr = cleanResult.split("\\s+");
				// Adding to linked list
				int index = Integer.parseInt(arr[0]);
				double x = Double.parseDouble(arr[1]);
				double y = Double.parseDouble(arr[2]);
				Node node = new Node(index, x, y);
				cityList.add(node);
				numNodes++; // Increment the vertices
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		numEdges = (numNodes * numNodes) - numNodes;
		ArrayList<ArrayList<Double>> distanceMatrix = MyArray.two(numNodes, numNodes, 0D);

		double distance = 0;

		//creating distance matrix(weighted-adjacency)
		for(int i=0; i<numNodes; i++) {
			for(int k=0; k<numNodes; k++) {
				// Create and edge if the vertices are different
				if(i!=k) {
					// Distance between i and k
//					distance = (int)Math.round(Math.sqrt(Math.pow(cityList.get(i).get_x()-cityList.get(k).get_x(), 2)+Math.pow(cityList.get(i).get_y()-cityList.get(k).get_y(), 2)));
					distance = Util.calcDist(cityList.get(i), cityList.get(k));
					// Add to the 2d distance matrix later used for prim algorithm
					distanceMatrix.get(i).set(k, distance);
				}
			}
		}


		//creating graph for given inputs
		Data data = new Data(numNodes, numEdges);
		data.setNodes(cityList);
		data.set_distanceMatrix(distanceMatrix);
		return data;
	}

	void initGraph() {
	        _adj = new LinkedList[_numNodes];
	        for (int i = 0; i < _numNodes; i++) {
	            _adj[i] = new LinkedList<Integer>();
	        }
	}

	 // add edge u-v
	void addEdge(Integer u, Integer v) {
	        _adj[u].add(v);
	        _adj[v].add(u);
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

	public ArrayList<ArrayList<Double>> get_distanceMatrix() {
		return _distanceMatrix;
	}

	public void set_distanceMatrix(ArrayList<ArrayList<Double>> distanceMatrix2) {
		this._distanceMatrix = distanceMatrix2;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
}

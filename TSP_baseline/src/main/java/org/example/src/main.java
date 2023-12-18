package org.example.src;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Node> cityList = new ArrayList<>();
		int distanceMatrix[][];
		String filename="D:\\cs\\code\\java\\TSP_baseline\\TSP_baseline\\src\\main\\java\\org\\example\\src\\data\\test-input-5.txt";

		int numNodes = 0;  // Number of vertices in graph
		int numEdges = 0;  // Number of edges in graph

		// Read input file line by line by using scanner
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNextLine()) {
				// Clean white spaces
				String cleanResult = Util.cleanInput(scanner.nextLine());
				String[] arr = cleanResult.split("\\s+");
				// Adding to linked list
				Node node = new Node(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Double.parseDouble(arr[2]));
				cityList.add(node);
				numNodes++; // Increment the vertices
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		numEdges = (numNodes*numNodes)-numNodes;

		distanceMatrix = new int[numNodes][numNodes];

		int distance = 0;

		//creating distance matrix(weighted-adjacency)
		for(int i=0; i<numNodes; i++) {
			for(int k=0; k<numNodes; k++) {
				// Create and edge if the vertices are different
				if(i!=k) {
					// Distance between i and k
					distance = (int)Math.round(Math.sqrt(Math.pow(cityList.get(i).get_x()-cityList.get(k).get_x(), 2)+Math.pow(cityList.get(i).get_y()-cityList.get(k).get_y(), 2)));
					// Add to the 2d distance matrix later used for prim algorithm
					distanceMatrix[i][k] = distance;
				}
			}
		}

		//creating graph for given inputs
		Graph g=new Graph(numNodes,numEdges);

		//getting Minumum spanning tree with prim algorithm
		Edge[] primsResult = g.primMST(distanceMatrix) ;

		//finding odd degree vertices and creating match between them to create euler cycle
		Edge mst[] = g.findAndAddPerfectMatches(primsResult,cityList);;

		//creating new graph for our cyclic mst
		Graph graph = new Graph(numNodes);

		for(int i=1; i<mst.length; i++) {
			graph.addEdge(mst[i].get_src(), mst[i].get_dest());
		}

		//creating euler cycle from cyclic mst
		graph.createEulerCircuit();

		//deleting repeated vertex for final form
		ArrayList<Integer> resultCircuit = graph.clearRepeatedCities(graph.get_eulerianCircuit());

		//calculating path distance
		int totalDistance = Util.calcTotalDistance(resultCircuit, distanceMatrix);

		//writing through output
		FileWriter fw = null;
		BufferedWriter bw=null;
		try {
//			fw = new FileWriter(new File(filename.replace("input","output").replace("data", "result")));
			fw = new FileWriter(filename.replace("input","output").replace("data", "result"));

			bw = new BufferedWriter(fw);
			bw.write("// totalDistance: " + totalDistance+"\n");
			for(int k=0; k<resultCircuit.size()-1; k++) {
				bw.write(resultCircuit.get(k)+"\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

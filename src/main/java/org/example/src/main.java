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
		double distanceMatrix[][];

		int numNodes = 0;  // Number of vertices in graph
		int numEdges = 0;  // Number of edges in graph

		// Read input file line by line by using scanner
		try {
			Scanner scanner = new Scanner(new File(Util.dataFilename));
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

		numEdges = (numNodes*numNodes)-numNodes;

		distanceMatrix = new double[numNodes][numNodes];

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
		double totalDistance = Util.calcTotalDist(resultCircuit, distanceMatrix);

		//writing through output
		FileWriter fw = null;
		BufferedWriter bw=null;
		try {
//			fw = new FileWriter(new File(filename.replace("input","output").replace("data", "result")));
			fw = new FileWriter(Util.resultFilename);

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

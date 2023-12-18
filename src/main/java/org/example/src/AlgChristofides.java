package org.example.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class AlgChristofides {
    private Data data;

    public AlgChristofides(Data data){
        this.data = data;
    }

    public Result run(){

        AlgChristofides algChristofides = new AlgChristofides(data);

        //getting Minumum spanning tree with prim algorithm
        Edge[] primsResult = algChristofides.primMST(data.get_distanceMatrix()) ;

        //finding odd degree vertices and creating match between them to create euler cycle
        Edge mst[] = algChristofides.findAndAddPerfectMatches(primsResult, data.getNodes());;

        //creating new graph for our cyclic mst
//		Data data = new Data(numNodes, numEdges);


        for(int i=1; i<mst.length; i++) {
            data.addEdge(mst[i].get_src(), mst[i].get_dest());
        }

//		AlgChristofides algChristofides = new AlgChristofides(data);
        //creating euler cycle from cyclic mst
        algChristofides.createEulerCircuit();

        //deleting repeated vertex for final form
        ArrayList<Integer> resultCircuit = algChristofides.clearRepeatedCities(data.get_eulerianCircuit());
        ArrayList<Node> nodes = Util.calcNodes(resultCircuit, data);
        Result result = new Result(nodes, data);
        return result;
    }
    // This function removes edge u-v from graph.
    void removeEdge(Integer u, Integer v) {
        data.get_adj()[u].remove(v);
        data.get_adj()[v].remove(u);
    }

    //***Eulerian Cycle******

    /* The main function that print Eulerian Trail.
       It first finds an odd degree vertex (if there
       is any) and then calls printEulerUtil() to
       print the path */
    public void createEulerCircuit() {
        // Find a vertex with odd degree
        Integer u = 0;
        for (int i = 0; i < data.get_numNodes(); i++) {
            if (data.get_adj()[i].size() % 2 == 1) {
                u = i;
                break;
            }
        }
        // Print tour starting from odd v
        eulerUtil(u);
    }

    // Print Euler tour starting from vertex u
    public void eulerUtil(Integer u) {
        // Recur for all the vertices adjacent to this vertex
        for (int i = 0; i < data.get_adj()[u].size(); i++) {
            Integer v = data.get_adj()[u].get(i);
            // If edge u-v is a valid next edge
            if (isValidNextEdge(u, v)) {
                //System.out.print(u + "-" + v + " ");
                data.get_eulerianCircuit().add(u);
                data.get_eulerianCircuit().add(v);
                // This edge is used so remove it now
                removeEdge(u, v);
                eulerUtil(v);
            }
        }
    }

    // The function to check if edge u-v can be
    // considered as next edge in Euler Tout
    public boolean isValidNextEdge(Integer u, Integer v) {
        // The edge u-v is valid in one of the
        // following two cases:

        // 1) If v is the only adjacent vertex of u
        // ie size of adjacent vertex list is 1
        if (data.get_adj()[u].size() == 1) {
            return true;
        }

        // 2) If there are multiple adjacents, then
        // u-v is not a bridge Do following steps
        // to check if u-v is a bridge
        // 2.a) count of vertices reachable from u
        boolean[] isVisited = new boolean[data.get_numNodes()];
        int count1 = dfsCount(u, isVisited);

        // 2.b) Remove edge (u, v) and after removing
        //  the edge, count vertices reachable from u
        removeEdge(u, v);
        isVisited = new boolean[data.get_numNodes()];
        int count2 = dfsCount(u, isVisited);

        // 2.c) Add the edge back to the graph
        data.addEdge(u, v);
        return (count1 > count2) ? false : true;
    }

    // A DFS based function to count reachable
    // vertices from v
    public int dfsCount(Integer s, boolean[] isVisited) {
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
            Iterator<Integer> itr = data.get_adj()[s].iterator();

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
    public ArrayList<Integer> clearRepeatedCities(ArrayList<Integer> cities) {
        // Find and remove duplicate cities
        int[] citiesArray = new int[data.get_numNodes()];
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
    public Edge[] findAndAddPerfectMatches(Edge[] mst, List<Node> citylist){
        int[] neighbourCounterOnMST = new int[data.get_numNodes()];

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

        int[] neighbourCounterOnMST2 = new int[data.get_numNodes()];

        for (int i = 1; i < fal+sal; ++i) {
            int src = result[i].get_src();
            int dest = result[i].get_dest();
            neighbourCounterOnMST2[src]++;
            neighbourCounterOnMST2[dest]++;
        }
        return result;
    }

    public void findMatchesWithNearestNeighbour(List<Node> oddDegreVertex, ArrayList<Edge> newEdgesForOddVertices) {
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
            tempEdge.set_src(temp.get_id());
            tempEdge.set_dest(temp2.get_id());
            tempEdge.set_weight(min);
            newEdgesForOddVertices.add(tempEdge);

            min=Double.MAX_VALUE;
            oddDegreVertex.remove(indexForRemove);

            if(oddDegreVertex.size()==2){
                tempEdge = new Edge();
                tempEdge.set_src(oddDegreVertex.get(0).get_id());
                tempEdge.set_dest(oddDegreVertex.get(1).get_id());
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
    public int minKey(double key[], Boolean mstSet[]) {
        // Initialize min value
        double min = Double.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < data.get_numNodes(); v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    // A utility function to print the constructed MST stored in
    // parent[]
    public Edge[] getMST(int parent[], ArrayList<ArrayList<Double>> graph) {
        Edge[] mst = new Edge[data.get_numNodes()];
        for (int i = 1; i < data.get_numNodes(); i++) {
            mst[i]=new Edge();
            mst[i].set_src(parent[i]);
            mst[i].set_dest(i);
            mst[i].set_weight(graph.get(i).get(parent[i]));
        }
        return mst;
    }

    // Function to construct and print MST for a graph represented
    // using adjacency matrix representation
    public Edge[] primMST(ArrayList<ArrayList<Double>> distanceMatrix) {
        // Array to store constructed MST
        int parent[] = new int[data.get_numNodes()];

        // Key values used to pick minimum weight edge in cut
        double key[] = new double[data.get_numNodes()];

        // To represent set of vertices included in MST
        Boolean mstSet[] = new Boolean[data.get_numNodes()];

        // Initialize all keys as INFINITE
        for (int i = 0; i < data.get_numNodes(); i++) {
            key[i] = Double.MAX_VALUE;
            mstSet[i] = false;
        }

        // Always include first 1st vertex in MST.
        key[0] = 0; // Make key 0 so that this vertex is
        // picked as first vertex
        parent[0] = -1; // First node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < data.get_numNodes() - 1; count++) {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < data.get_numNodes(); v++)

                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (distanceMatrix.get(u).get(v) != 0 && mstSet[v] == false && distanceMatrix.get(u).get(v) < key[v]) {
                    parent[v] = u;
                    key[v] = distanceMatrix.get(u).get(v);
                }
        }

        // print the constructed MST
        return getMST(parent, distanceMatrix);
    }

}

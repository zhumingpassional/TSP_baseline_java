package org.example.src;

import org.example.src.maths.MyArray;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {
    public static String dataFilename="D:\\cs\\code\\java\\TSP_baseline\\src\\main\\java\\org\\example\\src\\data\\test-input-1.txt";
    public static String resultFilename = calcResultFilename(dataFilename);

//    public static ArrayList<ArrayList<Double>> distanceMatrix;

//    public static void setDistanceMatrix(ArrayList<ArrayList<Double>> distanceMatrix2){
//        distanceMatrix = distanceMatrix2;
//    }

    public static ArrayList<Node> calcNodes(ArrayList<Integer> nodeIds, Data data){
        ArrayList<Node> nodes = new ArrayList<>();
        for (Integer nodeId : nodeIds) {
            Node node = data.getNodes().get(nodeId);
            nodes.add(node);
        }
        return nodes;
    }

    public static void writeResult(Result result){
        //writing through output
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
//			fw = new FileWriter(new File(filename.replace("input","output").replace("data", "result")));
            fw = new FileWriter(Util.resultFilename);

            bw = new BufferedWriter(fw);
            bw.write("// totalDistance: " + result.get_totalDist()+"\n");
            for(int k = 0; k < result.get_nodeIds().size() - 1; k++) {
                bw.write(result.get_nodeIds().get(k)+"\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String calcResultFilename(String dataFilename){
        String dataFilename2 = String.copyValueOf(dataFilename.toCharArray());
        dataFilename2 = dataFilename2.replace("input","output").replace("data", "result");
        return dataFilename2;
    }
    //clearing input for spaces
    public static String cleanInput(String input) {
        int counter = 0;
        while(input.charAt(counter) == ' ') {
            counter++;
        }
        return input.substring(counter);
    }

    //function to calculate distance for given path
    public static double calcTotalDistForNodes(ArrayList<Node> resultCircuit, Data data) {
        double sum = 0;
        int counter = 0;

        while(counter < resultCircuit.size()) {
            int idx2 = (counter + 1) % resultCircuit.size();
//            sum += distanceMatrix[resultCircuit.get(counter)][resultCircuit.get(idx2)];
            sum += data.get_distanceMatrix().get(resultCircuit.get(counter).get_id()).get(resultCircuit.get(idx2).get_id());
            counter++;
        }
        return sum;
    }

    public static double calcTotalDistForNodeIds(ArrayList<Integer> resultCircuit, Data data) {
        double sum = 0;
        int counter = 0;

        while(counter < resultCircuit.size()) {
            int idx2 = (counter + 1) % resultCircuit.size();
            sum += data.get_distanceMatrix().get(resultCircuit.get(counter)).get(resultCircuit.get(idx2));
//            sum += Util.distanceMatrix.get(resultCircuit.get(counter).get_id()).get(resultCircuit.get(idx2).get_id());
            counter++;
        }
        return sum;
    }

    public static double calcDist(Node src, Node dest){
        double weight = Math.sqrt(Math.pow(src.get_x() - dest.get_x(), 2) + Math.pow(src.get_y() - dest.get_y(), 2));
        return weight;
    }

    public static ArrayList<Point2D> loadTSPLib(String fName) {
        /*
        Load in a TSPLib instance. This example assumes that the Edge weight type
        is EUC_2D.
        It will work for examples such as rl5915.tsp. Other files such as
        fri26.tsp .To use a different format, you will have to
        modify the this code
        */

        ArrayList<Point2D> result = new ArrayList<Point2D>();
        BufferedReader br = null;
        try {
            String currentLine;
            int dimension = 0;//Hold the dimension of the problem
            boolean readingNodes = false;
            br = new BufferedReader(new FileReader(fName));
            while ((currentLine = br.readLine()) != null) {
                //Read the file until the end;
                if (currentLine.contains("EOF")) {
                    //EOF should be the last line
                    readingNodes = false;
                    //Finished reading nodes
                    if (result.size() != dimension) {
                        //Check to see if the expected number of cities have been loaded
                        System.out.println("Error loading cities");
                        System.exit(-1);
                    }
                }
                if (readingNodes) {
                    //If reading in the node data
                    String[] tokens = currentLine.split(" ");
                    //Split the line by spaces.
                    //tokens[0] is the city id and not needed in this example
                    float x = Float.parseFloat(tokens[1].trim());
                    float y = Float.parseFloat(tokens[2].trim());
                    //Use Java's built in Point2D type to hold a city
                    Point2D city = new Point2D.Float(x, y);
                    //Add this city into the arraylist
                    result.add(city);
                }
                if (currentLine.contains("DIMENSION")) {
                    //Note the expected problem dimension (number of cities)
                    String[] tokens = currentLine.split(":");
                    dimension = Integer.parseInt(tokens[1].trim());
                }
                if (currentLine.contains("NODE_COORD_SECTION")) {
                    //Node data follows this line
                    readingNodes = true;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}

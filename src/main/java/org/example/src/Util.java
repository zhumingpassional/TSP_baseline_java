package org.example.src;

import java.util.ArrayList;

public class Util {
    public static String dataFilename="D:\\cs\\code\\java\\TSP_baseline\\src\\main\\java\\org\\example\\src\\data\\test-input-5.txt";
    public static String resultFilename = calcResultFilename(dataFilename);

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
    public static double calcTotalDist(ArrayList<Integer> resultCircuit, double distanceMatrix[][]) {
        double sum = 0;
        int counter = 0;

        while(counter < resultCircuit.size()) {
            int idx2 = (counter + 1) % resultCircuit.size();
            sum += distanceMatrix[resultCircuit.get(counter)][resultCircuit.get(idx2)];
            counter++;
        }
        return sum;
    }

    public static double calcDist(Node src, Node dest){
        double weight = Math.sqrt(Math.pow(src.get_x() - dest.get_x(), 2) + Math.pow(src.get_y() - dest.get_y(), 2));
        return weight;
    }
}

package org.example.src;

import java.util.ArrayList;

public class Util {
    //clearing input for spaces
    public static String cleanInput(String input) {
        int counter = 0;
        while(input.charAt(counter) == ' ') {
            counter++;
        }
        return input.substring(counter);
    }

    //function to calculate distance for given path
    public static int calcTotalDistance(ArrayList<Integer> resultCircuit, int distanceMatrix[][]) {
        int sum = 0;
        int counter = 0;

        while(counter < resultCircuit.size()-1) {
            sum += distanceMatrix[resultCircuit.get(counter)][resultCircuit.get(counter+1)];
            counter++;
        }
        return sum;
    }

    public static double calcDist(Node src, Node dest){
        double weight = Math.sqrt(Math.pow(src.get_x() - dest.get_x(), 2) + Math.pow(src.get_y() - dest.get_y(), 2));
        return weight;
    }
}

package org.example.src;

import java.util.ArrayList;

public class AlgTwoOpt {
    // first run algNeighbour.nearest(), and then use 2-opt
    private Data data;
    public AlgTwoOpt(Data data){
        this.data = data;
    }
    public Result run() {
        AlgNearestNeighbour algNearestNeighbour = new AlgNearestNeighbour(data);
        Result resultNeighbour = algNearestNeighbour.run();
        ArrayList<Node> cities = resultNeighbour.get_nodes();
        ArrayList<Node> newTour;
        double bestDist = Util.calcTotalDistForNodes(cities, data);
        double newDist;
        int swaps = 1;
        int improve = 0;
        int iterations = 0;
        long comparisons = 0;

        while (swaps != 0) { //loop until no improvements are made.
            swaps = 0;

            //initialise inner/outer loops avoiding adjacent calculations and making use of problem symmetry to half total comparisons.
            for (int i = 1; i < cities.size() - 2; i++) {
                for (int j = i + 1; j < cities.size() - 1; j++) {
                    comparisons++;
                    //check distance of line A,B + line C,D against A,C + B,D if there is improvement, call swap method.
                    double dist1 = Util.calcDist(cities.get(i), cities.get(i - 1)) + Util.calcDist(cities.get(j + 1), cities.get(j));
                    double dist2 = Util.calcDist(cities.get(i), cities.get(j + 1)) + Util.calcDist(cities.get(i - 1), cities.get(j));

//                    if ((cities.get(i).distance(cities.get(i - 1)) + cities.get(j + 1).distance(cities.get(j))) >=
//                            (cities.get(i).distance(cities.get(j + 1)) + cities.get(i - 1).distance(cities.get(j)))) {
                    if(dist1 >= dist2){
                        newTour = swap(cities, i, j); //pass arraylist and 2 points to be swapped.

//                        newDist = Length.routeLength(newTour);
                        newDist = Util.calcTotalDistForNodes(newTour, data);
                        if (newDist < bestDist) { //if the swap results in an improved distance, increment counters and update distance/tour
                            cities = newTour;
                            bestDist = newDist;
                            swaps++;
                            improve++;
                        }
                    }
                }
            }
            iterations++;
        }
        System.out.println("Total comparisons made: " + comparisons);
        System.out.println("Total improvements made: " + improve);
        System.out.println("Total iterations made: " + iterations);
        Result result = new Result(cities, data);
        return result;
    }

    private static ArrayList<Node> swap(ArrayList<Node> cities, int i, int j) {
        //conducts a 2 opt swap by inverting the order of the points between i and j
        ArrayList<Node> newTour = new ArrayList<>();

        //take array up to first point i and add to newTour
        int size = cities.size();
        for (int c = 0; c <= i - 1; c++) {
            newTour.add(cities.get(c));
        }

        //invert order between 2 passed points i and j and add to newTour
        int dec = 0;
        for (int c = i; c <= j; c++) {
            newTour.add(cities.get(j - dec));
            dec++;
        }

        //append array from point j to end to newTour
        for (int c = j + 1; c < size; c++) {
            newTour.add(cities.get(c));
        }

        return newTour;
    }
}

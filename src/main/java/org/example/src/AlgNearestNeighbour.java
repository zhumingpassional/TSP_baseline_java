package org.example.src;

import java.util.ArrayList;

public class AlgNearestNeighbour {

    private Data data;
    public AlgNearestNeighbour(Data data){
        this.data = data;
    }

    public Result run() {
        ArrayList<Node> cities = data.getNodes();
        ArrayList<Node> resultNodes = new ArrayList<>(); //holds final result.
        Node currentCity = cities.remove(0); //set current city to first array item.
        Node closestCity = cities.get(0); //set closest city to new first array item.
        Node possible; //for holding possible city.
        double dist; //hold current node distance.

        resultNodes.add(currentCity);

        //outside loop to iterate through array
        while (cities.size() > 0) {

            dist = Double.MAX_VALUE; //reset dist to max.

            //inner loop checks distance between current city and possible.
            for (int count = 0; count < cities.size(); count++) {
                possible = cities.get(count);
                double newDist = Util.calcDist(currentCity, possible);
                if (newDist < dist) {
                    dist = newDist;
                    closestCity = possible;
                }
            }
            /*
            once inner loop finds closest node
            set current city to closest, remove closest from cities
            and add current city to result.
             */
            currentCity = closestCity;
            cities.remove(closestCity);
            resultNodes.add(currentCity);
        }
        Result result = new Result(resultNodes, data);
        return result;
    }



}

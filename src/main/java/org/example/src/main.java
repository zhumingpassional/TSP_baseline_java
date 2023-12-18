package org.example.src;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Data data = Data.readData(Util.dataFilename);
		AlgName algName = AlgName.christofides;
		if(algName == AlgName.christofides){
			AlgChristofides algChristofides = new AlgChristofides(data);
			Result resultChristofides = algChristofides.run();
			Util.writeResult(resultChristofides);
		}else if(algName == AlgName.nearestNeighbour){
			AlgNearestNeighbour algNearestNeighbour = new AlgNearestNeighbour(data);
			Result resultNeighbour = algNearestNeighbour.run();
			Util.writeResult(resultNeighbour);
		}else if(algName == AlgName.twoOpt){
			AlgTwoOpt algTwoOpt = new AlgTwoOpt(data);
			Result resultTwoOpt = algTwoOpt.run();
			Util.writeResult(resultTwoOpt);
		}








	}


}

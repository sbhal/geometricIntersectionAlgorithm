package project2_latest;

public class MainClass {
	static RandomLineGenerator randline;

	public static void main(String[] args){
		//Generating total 10 lines (horizontal + vertical)
		randline = new RandomLineGenerator(10);
		
		System.out.println(randline);
		
		//BruteForce
		BruteForce bforce = new BruteForce(randline);
		bforce.printIntersection();

		//Sweep Algorithm
		HVIntersection hvIntersect = new HVIntersection(randline);
		hvIntersect.printIntersection();
	}
}

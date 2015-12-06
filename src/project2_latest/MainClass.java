package project2_latest;

public class MainClass {
	static RandomLineGenerator randline;

	public static void main(String[] args){
		//Generating total 10 lines (horizontal + vertical)
		randline = new RandomLineGenerator(1000);

		System.out.println(randline);

		long startTime = System.currentTimeMillis();
		//BruteForce
		BruteForce bforce = new BruteForce(randline);
		bforce.printIntersection();
		long stopTime = System.currentTimeMillis();
		long elapsedTime1 = stopTime - startTime;
		System.out.println("Brute Force took " + elapsedTime1);

		startTime = System.currentTimeMillis();
		//Sweep Algorithm
		HVIntersection hvIntersect = new HVIntersection(randline);
		hvIntersect.printIntersection();
		stopTime = System.currentTimeMillis();
		long elapsedTime2 = stopTime - startTime;
		System.out.println("Brute Force took " + elapsedTime1);
		System.out.println("Sweep Line Algorithm took " + elapsedTime2);

	}
}

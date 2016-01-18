package project2_latest;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class MainClass {
	static RandomLineGenerator randline;
	static int i = 0;
	public static void mainUtility(int noOfLine, PrintWriter bwriter, PrintWriter swriter, PrintWriter rwriter, long[][] elapsed){
		//Generating total 10 lines (horizontal + vertical)
		randline = new RandomLineGenerator(noOfLine);

		rwriter.println("\n For n = "+ noOfLine);
		rwriter.println(randline);

		long startTime = System.currentTimeMillis();
		//long startTime = CPUUtils.getUserTime();
		//BruteForce
		BruteForce bforce = new BruteForce(randline);
		bwriter.println("\n For n= "+noOfLine+ ". Intersection are:");
		int totalIntersections = bforce.printIntersection(true, bwriter);
		long stopTime = System.currentTimeMillis();
		//long stopTime = CPUUtils.getUserTime();
		long elapsedTime1 = stopTime - startTime;
		//System.out.println("Brute Force took " + elapsedTime1);

		startTime = System.currentTimeMillis();
		//startTime = CPUUtils.getUserTime();
		//Sweep Algorithm
		HVIntersection hvIntersect = new HVIntersection(randline);
		swriter.println("\n For n= "+noOfLine+ ". Intersection are:");
		hvIntersect.printIntersection(true, swriter);
		stopTime = System.currentTimeMillis();
		//stopTime = CPUUtils.getUserTime();
		long elapsedTime2 = stopTime - startTime;
		System.out.println("For n= " + noOfLine + " and no of Intersection = "+totalIntersections);
		System.out.println("Brute Force took " + elapsedTime1);
		System.out.println("Sweep Line Algorithm took " + elapsedTime2);
		elapsed[i][0] = elapsedTime1;
		elapsed[i][1] = elapsedTime2;
		i++;

	}
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		int[] noOfLines = {10, 100 , 500, 1000, 5000, 10000, 20000};
		PrintWriter bwriter = new PrintWriter("BruteForce.txt", "UTF-8");
		PrintWriter rwriter = new PrintWriter("RandomLines.txt", "UTF-8");
		PrintWriter swriter = new PrintWriter("SweepLine.txt", "UTF-8");

		long[][] elapsed = new long[7][2];
		for (int noOfLine:noOfLines){
			mainUtility(noOfLine, bwriter, swriter, rwriter, elapsed);
			}
		
		bwriter.close();
		swriter.close();
		rwriter.close();
		for(int i=0; i< 7; ++i)
		{
			System.out.println(elapsed[i][0] + "\t"+ elapsed[i][1]);
		}
	}
}


class CPUUtils {

    /** Get CPU time in nanoseconds. */
    public static long getCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
            bean.getCurrentThreadCpuTime( ) : 0L;
    }

    /** Get user time in nanoseconds. */
    public static long getUserTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
            bean.getCurrentThreadUserTime( ) : 0L;
    }

    /** Get system time in nanoseconds. */
    public static long getSystemTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
            (bean.getCurrentThreadCpuTime( ) - bean.getCurrentThreadUserTime( )) : 0L;
    }

}
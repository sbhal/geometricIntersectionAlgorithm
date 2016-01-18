package project2_latest;

import java.io.PrintWriter;
import java.util.ArrayList;

public class BruteForce {
	RandomLineGenerator randLineSet;

	public BruteForce(RandomLineGenerator randLineSet){
		this.randLineSet = randLineSet;
	}

	public int printIntersection(boolean toPrint, PrintWriter bwriter){
		int totalIntersections=0;
		//reading Horizontal Lines
		ArrayList<HorizontalLines> Ahline = randLineSet.getHorizontalLine();

		//reading Vertical Lines
		ArrayList<VerticalLines> Avline = randLineSet.getVerticalLine();
System.out.println("ahline.size() is "+Ahline.size()+ " and Avline size is "+ Avline.size());
		for (int i = 0; i < Ahline.size(); i++) {
			HorizontalLines hline = Ahline.get(i);
			SegmentHV segmentH = new SegmentHV(hline.x, hline.y, hline.x+100, hline.y);
			for (int j = 0; j < Avline.size(); j++) {
				VerticalLines vline = Avline.get(j);
				SegmentHV segmentV = new SegmentHV(vline.getX(), vline.getY(), vline.getX(), vline.getY()+100);
				Boolean flag = segmentH.doIntersect(segmentV);
				if(flag){
					totalIntersections++;
					if(toPrint)
						bwriter.println(segmentV + "\t with "+ segmentH);
				}
			}
		}
		return totalIntersections;
	}
}
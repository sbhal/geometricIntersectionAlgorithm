package project2_latest;

import java.util.ArrayList;

public class BruteForce {
	RandomLineGenerator randLineSet;

	public BruteForce(RandomLineGenerator randLineSet){
		this.randLineSet = randLineSet;
	}

	public void printIntersection(){
		System.out.println("Intersection found with BruteForce are:");	
		//reading Horizontal Lines
		ArrayList<HorizontalLines> Ahline = randLineSet.getHorizontalLine();

		//reading Vertical Lines
		ArrayList<VerticalLines> Avline = randLineSet.getVerticalLine();

		for (int i = 0; i < Ahline.size(); i++) {
			HorizontalLines hline = Ahline.get(i);
			SegmentHV segmentH = new SegmentHV(hline.x, hline.y, hline.x+25, hline.y);
			for (int j = 0; j < Avline.size(); j++) {
				VerticalLines vline = Avline.get(j);
				SegmentHV segmentV = new SegmentHV(vline.getX(), vline.getY(), vline.getX(), vline.getY()+25);
				Boolean flag = segmentH.doIntersect(segmentV);
				if(flag){
					System.out.println("Intersection:  " + segmentV + "\t with "+ segmentH);
				}
			}
		}
	}
}
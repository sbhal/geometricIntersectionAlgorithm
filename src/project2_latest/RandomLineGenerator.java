package project2_latest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class RandomLineGenerator {

	static int maxGeneratedNumber = 1000000;
	private ArrayList<HorizontalLines> horizontalLine;
	private ArrayList<VerticalLines> verticalLine;

	public RandomLineGenerator(int num){
		horizontalLine=new ArrayList<HorizontalLines>();
		verticalLine=new ArrayList<VerticalLines>();

		Random rn=new Random();
		int numHzLine=rn.nextInt(num);
		for(int i=0;i<numHzLine;i++){
			HorizontalLines hLine=new HorizontalLines();
			hLine.setX(rn.nextInt(maxGeneratedNumber));
			hLine.setY(rn.nextInt(maxGeneratedNumber));

			horizontalLine.add(hLine);
		}

		Collections.sort(horizontalLine, new hLineComparator());
		for(int i=0;i<num-numHzLine;i++){
			VerticalLines vLine=new VerticalLines();
			vLine.setX(rn.nextInt(maxGeneratedNumber));
			vLine.setY(rn.nextInt(maxGeneratedNumber));
			verticalLine.add(vLine);
		}
		Collections.sort(verticalLine, new vLineComparator());

	}

	public ArrayList<HorizontalLines> getHorizontalLine() {
		return horizontalLine;
	}

	public void setHorizontalLine(ArrayList<HorizontalLines> horizontalLine) {
		this.horizontalLine = horizontalLine;
	}

	public ArrayList<VerticalLines> getVerticalLine() {
		return verticalLine;
	}

	public void setVerticalLine(ArrayList<VerticalLines> verticalLine) {
		this.verticalLine = verticalLine;
	}
	public String toString() {
		String s = "Random Lines Generated are -> \n";
		//s = "Horizonatal lines are :";
		for (int i = 0; i < horizontalLine.size(); i++) {
			HorizontalLines hline = horizontalLine.get(i);
			SegmentHV segmentH = new SegmentHV(hline.x, hline.y, hline.x+25, hline.y);
			s += segmentH + "\n";
		}
		//s += "Vertical lines are :";
		for (int j = 0; j < verticalLine.size(); j++) {
			VerticalLines vline = verticalLine.get(j);
			SegmentHV segmentV = new SegmentHV(vline.getX(), vline.getY(), vline.getX(), vline.getY()+25);
			s += segmentV + "\n";
		}

		return s;
	}

}


class hLineComparator implements Comparator<HorizontalLines> {
	@Override
	public int compare(HorizontalLines l1, HorizontalLines l2) {
		if (l1.getY() > l2.getY() ){
			return 1;
		}
		else if (l1.getY() < l2.getY()){
			return -1;
		}
		else
			return 0;

	}
}


class vLineComparator implements Comparator<VerticalLines> {
	@Override
	public int compare(VerticalLines l1, VerticalLines l2) {
		if (l1.getX() > l2.getX() ){
			return 1;
		}
		else if (l1.getX() < l2.getX()){
			return -1;
		}
		else
			return 0;

	}
}


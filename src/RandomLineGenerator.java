import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class RandomLineGenerator {

	
	private ArrayList<HorizontalLines> horizontalLine;
	private ArrayList<VerticalLines> verticalLine;
	
	public RandomLineGenerator(int num){
		int line;
		horizontalLine=new ArrayList<HorizontalLines>();
		verticalLine=new ArrayList<VerticalLines>();
		
		Random rn=new Random();
		int numHzLine=rn.nextInt(num);
		for(int i=0;i<numHzLine;i++){
			HorizontalLines hLine=new HorizontalLines();
			hLine.setX(rn.nextInt(1000000));
			hLine.setY(rn.nextInt(1000000));
			
			horizontalLine.add(hLine);
		}
		
		Collections.sort(horizontalLine, new hLineComparator());
		for(int i=0;i<num-numHzLine;i++){
			VerticalLines vLine=new VerticalLines();
			vLine.setX(rn.nextInt(1000000));
			vLine.setY(rn.nextInt(1000000));
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


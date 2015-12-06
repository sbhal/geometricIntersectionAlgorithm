package project2_latest;

public class HorizontalLines {
	private final int length=25;
	int x;
	int y;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getLength() {
		return length;
	}
	public int endPoint(int x){
		return x+25;		
	}

}

package project2_latest;

public class VerticalLines {
	private final int length=100;


	private int x;
	private int y;
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
	public int endPoint(int y){
		return y+100;		
	}
}

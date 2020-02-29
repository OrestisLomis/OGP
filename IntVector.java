package drawit;

public class IntVector {
	
	private int x;
	private int y;
	
	public IntVector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public long crossProduct(IntVector other) {
		long a = this.getX() * other.getY();
		long b = this.getY() * other.getX();
		return a - b;
	}
	
	public boolean isCollinearWith(IntVector other) {
		return this.crossProduct(other) == 0;
	}
	
	public long dotProduct(IntVector other) {
		long a = this.getX() * other.getX();
		long b = this.getY() * other.getY();
		return a + b;
	}
	
	public DoubleVector asDoublevector() {
		double a = this.getX();
		double b = this.getY();
		return DoubleVector.DoubleVector(a,b);
	}

}

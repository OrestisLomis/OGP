
public class DoublePoint {
	
	private double x;
	private double y;
	
	public DoublePoint(double x, double y) {
		this.x = x;
		this.y = y;
		
	}
	
	public double getX() {
		return this.x;
		}
	public double getY() {
		return this.y;
		}
	
	public DoubleVector minus(DoublePoint other) {
		return new DoubleVector(this.x - other.x, this.y - other.y);
	}
	
	public DoublePoint plus(DoubleVector other) {
		
		return new DoublePoint(this.getX() + other.getX(), this.getY() + other.getY());
		
	}
	
	public IntPoint round() {
		int x = (int) this.getX();
		int y = (int) this.getY();
		return new IntPoint(x, y);
	}

}

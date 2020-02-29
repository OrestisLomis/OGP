package drawit;

public class DoubleVector {
	
	private double x;
	private double y;
	
	public DoubleVector(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	
	public DoubleVector scale(double d) {
		return DoubleVector(getX() * d, getY() * d);
	}
	
	public DoubleVector plus(DoubleVector other) {
		return DoubleVector(this.getX() + other.getX(), this.getY() + other.getY());
	}
	
	public double getSize() {
		return Math.sqrt(getX() * getX() + getY() * getY());
	}
	
	public double dotProduct(DoubleVector other) { 
		return this.getX() * other.getX() + this.getY() * other.getY();
	}
	
	public double crossProduct(DoubleVector other) {
		return this.getX() * other.getY() - this.getY() * other.getX();
	}
	
	public double asAngle() {
		return Math.atan2(getY(), getX());
	}
}

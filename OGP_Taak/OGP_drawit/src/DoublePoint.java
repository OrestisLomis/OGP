/**
 * An immutable abstraction for a point in the two-dimensional plane with double coordinates.
 * 
 * @immutable
 */
public class DoublePoint {
	
	private double x;
	private double y;
	
	/**
	 * Initializes the point with the given coordinates
	 */
	public DoublePoint(double x, double y) {
		this.x = x;
		this.y = y;	
	}
	
	/**
	 * Returns this point's X coordinate.
	 * 
	 * @post This point's X coordinate equals the given x.
	 * 		| this.getX() == x
	 */
	public double getX() {
		return this.x;
		}
	
	/**
	 * Returns this point's Y coordinate.
	 * 
	 * @post This point's Y coordinate equals the given y.
	 * 		| this.getY() == y
	 */
	public double getY() {
		return this.y;
		}
	
	/**
	 * Returns an DoubleVector object representing the displacement from other to this.
	 * 
	 * @post The X coordinate of the result equals the X coordinate of this point minus the one of the other point
	 * 		| result.getX() == this.getX() - other.getX()
	 * @post The Y coordinate of the result equals the Y coordinate of this point minus the one of the other point
	 * 		| result.getY() == this.getY() - other.getY()
	 */
	public DoubleVector minus(DoublePoint other) {
		return new DoubleVector(this.x - other.x, this.y - other.y);
	}
	
	/**
	 * Returns an DoublePoint object representing the point obtained by displacing this point by the given vector.
	 * 
	 * @post The object's X coordinate equals the point's X coordinate plus the vector's X coordinate.
	 * 		| result.getX() == this.getX() + vector.getX()
	 * @post The object's Y coordinate equals the point's Y coordinate plus the vector's Y coordinate.
	 * 		| result.getY() == this.getY() + vector.getY()
	 */
	public DoublePoint plus(DoubleVector other) {
		return new DoublePoint(this.getX() + other.getX(), this.getY() + other.getY());
	}

	
	/**
	 * Returns an IntPoint object whose coordinates are obtained by rounding the coordinates of this point to the nearest integer.
	 * 
	 * @post The point's X coordinate minus the object's X coordinate is between 0 and 1.
	 * 		| this.getX() - result.getX() >= 0 && this.getX() - result.getX() < 1
	 * @post The point's Y coordinate minus the object's Y coordinate is between 0 and 1.
	 * 		| this.getY() - result.getY() >= 0 && this.getY() - result.getY() < 1
	 */		
	public IntPoint round() {
		int x = (int) this.getX();
		int y = (int) this.getY();
		return new IntPoint(x, y);
	}

}

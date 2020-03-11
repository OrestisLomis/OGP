package drawit;

/**
 * An immutable abstraction for a point in the two-dimensional plane with double
 * coordinates.
 * 
 * @immutable
 */
public class DoublePoint {

	private final double x;
	private final double y;

	/**
	 * Initializes the point with the given coordinates	 
	 * 
	 * @post This point's X coordinate equals the given x. 
	 * 		| this.getX() == x
	 * @post This point's Y coordinate equals the given y. 
	 * 		| this.getY() == y
	 */
	public DoublePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns this point's X coordinate.
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Returns this point's Y coordinate.
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Returns an DoubleVector object representing the displacement from other to
	 * this.
	 * 
	 * @post The X coordinate of the result equals the X coordinate of this point
	 *       minus the one of the other point 
	 *       | result.getX() == this.getX() - other.getX()
	 * @post The Y coordinate of the result equals the Y coordinate of this point
	 *       minus the one of the other point 
	 *       | result.getY() == this.getY() - other.getY()
	 */
	public DoubleVector minus(DoublePoint other) {
		return new DoubleVector(this.x - other.x, this.y - other.y);
	}

	/**
	 * Returns an DoublePoint object representing the point obtained by displacing
	 * this point by the given vector.
	 * 
	 * @post The object's X coordinate equals the point's X coordinate plus the
	 *       vector's X coordinate. 
	 *       | result.getX() == this.getX() + other.getX()
	 * @post The object's Y coordinate equals the point's Y coordinate plus the
	 *       vector's Y coordinate. 
	 *       | result.getY() == this.getY() + other.getY()
	 */
	public DoublePoint plus(DoubleVector other) {
		return new DoublePoint(this.getX() + other.getX(), this.getY() + other.getY());
	}

	/**
	 * Returns an IntPoint object whose coordinates are obtained by rounding the
	 * coordinates of this point to the nearest integer.
	 * 
	 * @post The difference of the results X coordinate and the given one is between -0.5 and 0.5
	 * 		| result.getX() - this.getX() >= -0.5 && result.getX() - this.getX() < 0.5
	 * @post The difference of the results Y coordinate and the given one is between -0.5 and 0.5
	 * 		| result.getY() - this.getY() >= -0.5 && result.getY() - this.getY() < 0.5
	 * 
	 */
	public IntPoint round() {
		int x = (int) this.getX();
		int y = (int) this.getY();
		if (this.getX() - x >= 0.5)
			x++;
		else if (this.getX() - x < -0.5)
			x--;
		if (this.getY() - y >= 0.5)
			y++;
		else if (this.getY() - y < -0.5)
			y--;
		return new IntPoint(x, y);
	}

}

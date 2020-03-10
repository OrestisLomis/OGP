package drawit;

public class IntVector {
	/**
	 * An instance of this class represents a displacement in the two-dimensional
	 * plane.
	 * 
	 * @immutable
	 */
	private int x;
	private int y;

	/**
	 * Initializes the point with the given integers as coordinates
	 */

	public IntVector(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns this point's X coordinate.
	 * 
	 * @post This point's X coordinate is the given x. | this.getX() == x
	 */

	public int getX() {
		return this.x;
	}

	/**
	 * Returns this point's Y coordinate.
	 * 
	 * @post This point's Y coordinate is the given y. | this.getY() == y
	 */

	public int getY() {
		return this.y;
	}

	/**
	 * Returns the cross product of this vector and the given vector.
	 * 
	 * @post | result == (long)getX() * other.getY() - (long)getY() * other.getX()
	 */

	public long crossProduct(IntVector other) {
		long a = this.getX() * other.getY();
		long b = this.getY() * other.getX();
		return a - b;
	}

	/**
	 * Returns whether this vector is collinear with the given vector.
	 * 
	 * @post The result is whether the cross product of the two vectors equals zero.
	 *       | result == (this.crossProduct(other) == 0)
	 */

	public boolean isCollinearWith(IntVector other) {
		return this.crossProduct(other) == 0;
	}

	/**
	 * Returns the dot product of this vector and the given vector.
	 * 
	 * @post The result is the dot product of the two vectors. | result ==
	 *       (long)getX() * other.getX() + (long)getY() * other.getY()
	 */

	public long dotProduct(IntVector other) {
		long a = this.getX() * other.getX();
		long b = this.getY() * other.getY();
		return a + b;
	}

	/**
	 * Returns a DoubleVector object that represents the same vector represented by
	 * this IntVector object.
	 */

	public DoubleVector asDoubleVector() {
		double a = this.getX();
		double b = this.getY();
		return new DoubleVector(a, b);
	}

}
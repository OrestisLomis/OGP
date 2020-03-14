package drawit;

/**
 * An immutable abstraction for a point in the two-dimensional plane with int
 * coordinates.
 * 
 * @immutable
 */
public class IntPoint {

	private final int x;
	private final int y;

	/**
	 * Initializes the point with the given coordinates
	 *
	 * @post This point's X coordinate equals the given x. 
	 * 		| this.getX() == x
	 * @post This point's Y coordinate equals the given y. 
	 * 		| this.getY() == y
	 */
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns this point's X coordinate.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns this point's Y coordinate.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Returns true if this point has the same coordinates as the given point;
	 * returns false otherwise.
	 */
	public boolean equals(IntPoint other) {
		return (this.getX() == other.getX()) && (this.getY() == other.getY());
	}

	/**
	 * Returns true iff this point is on open line segment bc. An open line segment
	 * does not include its endpoints.
	 */
	public boolean isOnLineSegment(IntPoint b, IntPoint c) {
		IntVector ba = this.minus(b);
		IntVector bc = c.minus(b);

		return ba.isCollinearWith(bc) && 0 < ba.dotProduct(bc) && ba.dotProduct(bc) < bc.dotProduct(bc);
	}

	/**
	 * Returns an IntVector object representing the displacement from other to this.
	 * 
	 * @post The X coordinate of the result equals the X coordinate of this point
	 *       minus the one of the other point 
	 *       | result.getX() == this.getX() - other.getX()
	 * @post The Y coordinate of the result equals the Y coordinate of this point
	 *       minus the one of the other point 
	 *       | result.getY() == this.getY() - other.getY()
	 */
	public IntVector minus(IntPoint other) {
		return new IntVector(this.getX() - other.getX(), this.getY() - other.getY());
	}

	/**
	 * Returns a DoublePoint object that represents the same 2D point represented by
	 * this IntPoint object.
	 * 
	 * @post This object X coordinate equals the point's X coordinate. 
	 * 		| result.getX() == this.getX()
	 * @post This object Y coordinate equals the point's Y coordinate. 
	 * 		| result.getY() == this.getY()
	 */
	public DoublePoint asDoublePoint() {
		return new DoublePoint(this.getX(), this.getY());
	}

	/**
	 * Returns an IntPoint object representing the point obtained by displacing this
	 * point by the given vector.
	 * 
	 * @post The object's X coordinate equals the point's X coordinate plus the
	 *       vector's X coordinate. 
	 *       | result.getX() == this.getX() + vector.getX()
	 * @post The object's Y coordinate equals the point's Y coordinate plus the
	 *       vector's Y coordinate. 
	 *       | result.getY() == this.getY() + vector.getY()
	 */
	public IntPoint plus(IntVector vector) {
		return new IntPoint(this.getX() + vector.getX(), this.getY() + vector.getY());

	}

	/**
	 * Returns true iff the open line segment ab intersects the open line segment
	 * cd.
	 * 
	 * @pre The line segments have at most one point in common.
	 */
	public static boolean lineSegmentsIntersect(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
		IntVector ab = b.minus(a);
		IntVector ac = c.minus(a);
		IntVector ad = d.minus(a);
		IntVector cd = d.minus(c);
		IntVector ca = a.minus(c);
		IntVector cb = b.minus(c);
		return ab.crossProduct(ac) * ab.crossProduct(ad) < 0 && cd.crossProduct(ca) * cd.crossProduct(cb) < 0;
	}

}


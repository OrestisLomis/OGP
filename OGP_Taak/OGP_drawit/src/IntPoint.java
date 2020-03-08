package drawit;

/**
 * An immutable abstraction for a point in the two-dimensional plane with int
 * coordinates.
 * 
 * @immutable
 */
public class IntPoint {

	private int x;
	private int y;

	/**
	 * Initializes the point with the given coordinates
	 */
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns this point's X coordinate.
	 * 
	 * @post This point's X coordinate equals the given x. | this.getX() == x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns this point's Y coordinate.
	 * 
	 * @post This point's Y coordinate equals the given y. | this.getY() == y
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
	 * Returns true if this point is on open line segment bc. An open line segment
	 * does not include its endpoints.
	 */
	public boolean IsOnLineSegment(IntPoint b, IntPoint c) {
		IntVector ab = new IntVector(b.getX() - this.getX(), b.getY() - this.getY());
		IntVector bc = new IntVector(b.getX() - c.getX(), b.getY() - c.getY());

		return (ab.isCollinearWith(bc)) && 0 < ab.dotProduct(bc) && ab.dotProduct(bc) < bc.dotProduct(bc);
	}

	/**
	 * Returns an IntVector object representing the displacement from other to this.
	 * 
	 * @post The X coordinate of the result equals the X coordinate of this point
	 *       minus the one of the other point | result.getX() == this.getX() -
	 *       other.getX()
	 * @post The Y coordinate of the result equals the Y coordinate of this point
	 *       minus the one of the other point | result.getY() == this.getY() -
	 *       other.getY()
	 */
	public IntVector minus(IntPoint other) {
		return new IntVector(this.getX() - other.getX(), this.getY() - other.getY());
	}

	/**
	 * Returns a DoublePoint object that represents the same 2D point represented by
	 * this IntPoint object.
	 * 
	 * @post This object X coordinate equals the point's X coordinate. |
	 *       result.getX() == this.getX()
	 * @post This object Y coordinate equals the point's Y coordinate. |
	 *       result.getY() == this.getY()
	 */
	public DoublePoint asDoublePoint() {
		return new DoublePoint(this.getX(), this.getY());
	}

	/**
	 * Returns an IntPoint object representing the point obtained by displacing this
	 * point by the given vector.
	 * 
	 * @post The object's X coordinate equals the point's X coordinate plus the
	 *       vector's X coordinate. | result.getX() == this.getX() + vector.getX()
	 * @post The object's Y coordinate equals the point's Y coordinate plus the
	 *       vector's Y coordinate. | result.getY() == this.getY() + vector.getY()
	 */
	public IntPoint plus(IntVector vector) {
		return new IntPoint(this.getX() + vector.getX(), this.getY() + vector.getY());

	}

	/**
	 * Returns true if the open line segment ab intersects the open line segment
	 * cd.
	 * 
	 * @pre The line segments have at most one point in common.
	 */
	public static boolean lineSegmentsIntersect​(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
		IntVector ab = new IntVector(b.getX() - a.getX(), b.getY() - a.getY());
		IntVector ac = new IntVector(c.getX() - a.getX(), c.getY() - a.getY());
		IntVector ad = new IntVector(d.getX() - a.getX(), d.getY() - a.getY());
		IntVector cd = new IntVector(d.getX() - c.getX(), d.getY() - c.getY());
		IntVector ca = new IntVector(a.getX() - c.getX(), a.getY() - c.getY());
		IntVector cb = new IntVector(b.getX() - c.getX(), b.getY() - c.getY());
		return ab.crossProduct(ac) * ab.crossProduct(ad) < 0 && cd.crossProduct(ca) * cd.crossProduct(cb) < 0;
	}

}

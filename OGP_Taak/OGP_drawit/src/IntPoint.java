
public class IntPoint {
	
	private int x;
	private int y;

	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
		}
	public int getY() {
		return this.y;
		}
	
	public boolean equals(IntPoint other) {
		return (this.getX() == other.getX()) && (this.getY() == other.getY());		
	}
	
	public boolean IsOnLineSegment(IntPoint b, IntPoint c) {
		IntVector ab = new IntVector(b.getX() - this.getX(), b.getY() - this.getY());
		IntVector bc = new IntVector(b.getX() - c.getX(), b.getY() - c.getY());
		
		return (ab.isCollinearWith(bc)) && 0 < ab.dotProduct(bc) && ab.dotProduct(bc) < bc.dotProduct(bc);
	}
	
	public IntVector minus(IntPoint other) {
		return new IntVector(this.x - other.x, this.y - other.y);		
	}
	
	public DoublePoint asDoublePoint() {
		return new DoublePoint(this.x, this.y);
	}
	
	public IntPoint plus(IntVector vector) {
		return new IntPoint(this.x + vector.getX(), this.y + vector.getY());
	
	}
	
	public static boolean lineSegmentsIntersect​(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
		IntVector ab = new IntVector(b.getX() - a.getX(), b.getY() - a.getY());
		IntVector ac = new IntVector(c.getX() - a.getX(), c.getY() - a.getY());
		IntVector ad = new IntVector(d.getX() - a.getX(), d.getY() - a.getY());
		return ab.crossProduct(ac) * ab.crossProduct(ad) < 0;		
	}

}


public class IntPoint {
	
	private int x;
	private int y;

	public IntPoint(int x, int y) {
	}

	public int getX() {return this.x;}
	public int getY() {return this.y;}
	
	public boolean equals(IntPoint other) {
		return (this.x == other.x) && (this.y == other.y);		
	}
	
	public boolean IsOnLineSegment(IntPoint b, IntPoint c) {
		int ricoab = (b.y - this.y)/(b.x - this.x);
		int ricobc = (c.y - b.y)/(c.x - b.x);
		
		int dotabbc = (b.x - this.x) * (c.x - b.x) + (b.y - this.y) * (c.y - b.y);
		int dotbcbc = (c.x - b.x) * (c.x - b.x) + (c.y - b.y) * (c.y - b.y);
		
		return (ricoab == ricobc) && 0 < dotabbc && dotabbc < dotbcbc;
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
	

package drawit;

public class RoundedPolygon {
	
	/**
	 * An instance of this class is a mutable abstraction storing a rounded polygon 
	 * defined by a set of 2D points with integer coordinates and a nonnegative corner radius.
	 * 
	 * @mutable
	 */
	private IntPoint[] vertices;
	private int radius;
	
	public RoundedPolygon() {
		IntPoint myIntPoint = new IntPoint(-100, -100);
		IntPoint myIntPoint2 = new IntPoint(-100, 100);
		IntPoint myIntPoint3 = new IntPoint(100, 100);
		IntPoint myIntPoint4 = new IntPoint(100, -100);
		
		IntPoint[] myIntPoints = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint4};
	
		this.vertices = myIntPoints;
		this.radius = 10;
	}
	
	/**
	 * Returns a new array whose elements are the vertices of this rounded polygon.
	 * 		| this.getVertices() == vertices
	 */
	public IntPoint[] getVertices() {
		return this.vertices;		
	}
	
	/** 
	 * Returns the radius of the corners of this rounded polygon.
	 * 		| this.getRadius() == radius
	 */
	public int getRadius() {
		return this.radius;	
	}
	
	/**
	 * Sets the vertices of this rounded polygon to be equal to the elements of the given array.
	 * 
	 * @mutates |this
	 * @throws IllegalArgumentException  if the given vertices do not define a proper polygon. 
	 * 		| PointArrays.checkDefinesProperPolygon(newVertices) != null
	 * @post The given vertices are now the vertices of the polygon.
	 * 		| this.getVertices() == newVertices
	 */	
	public void setVertices(IntPoint[] newVertices) {
		if (PointArrays.checkDefinesProperPolygon(newVertices) == null)
			this.vertices = newVertices;
		else 
			throw new IllegalArgumentException("These vertices do not define a proper polygon");		
	}
	
	/**
	 * Sets this rounded polygon's corner radius to the given value.
	 * 
	 * @mutates | this
	 * @throws IllegalArgumentException if the given radius is negative.
	 * 		| radius < 0
	 * @post The given radius is now the new radius of the rounded polygon.
	 * 		| this.getRadius() == radius
	 */
	public void setRadius(int radius) {
		if (radius >= 0)
			this.radius = radius;
		else 
			throw new IllegalArgumentException("The radius has to be positive");			
	}
	
	public void insert(int index, IntPoint point) {
		IntPoint[] Vertices = this.getVertices();
		IntPoint[] newVertices = PointArrays.insert(Vertices, index, point);
		this.setVertices(newVertices);
	}
	
	public void remove(int index) {
		IntPoint[] Vertices = this.getVertices();
		IntPoint[] newVertices = PointArrays.remove(Vertices, index);
		this.setVertices(newVertices);
	}
	
	public void update(int index, IntPoint point) {
		IntPoint[] Vertices = this.getVertices();
		IntPoint[] newVertices = PointArrays.update(Vertices, index, point);
		this.setVertices(newVertices);
	}
	
public boolean contains(IntPoint point) {		
		
		IntPoint [] vertices = this.getVertices();
		for  (int i = 0; i < vertices.length; i++) {
			if (point.equals(vertices[i]))
					return true;
			if (point.IsOnLineSegment(vertices[i],vertices[(i+1)%vertices.length]))
					return true;
			}
		
		int count = 0;
		
		for (int i = 0; i < vertices.length; i++) {
			IntPoint first = vertices[i];
			if (point.getY() != first.getY() || point.getX() > first.getX()) {
				
				for (int j = i+1; j < vertices.length - 1; j++ ) {
					IntPoint second = vertices[j%vertices.length];
					if (point.getY() != second.getY() || point.getX() > second.getX()) {
							
						if (j-i == 1) {
							
							IntVector VV = new IntVector(first.getX() - second.getX(), first.getY() - second.getY());
							IntVector VP = new IntVector(first.getX() - point.getX(), first.getY() - second.getY());
							IntVector Xpos = new IntVector(1,0);
							
							if (((first.getY() - point.getY()) * (second.getY() - point.getY())) < 0 &&
									VP.crossProduct(VV) * Xpos.crossProduct(VV) < 0)
								count++;
						}
						else {
							if ((first.getY() - point.getY()) * (second.getY() - point.getY()) < 0) 
								count++;						
						}
					}
				}
			}
		}		
		
	if (count % 2 == 0)
		return false;
	return true;
	}
	
	
	public String getDrawingCommands() {
		IntPoint[] vertices = this.getVertices();
		int length = vertices.length;
		String commands = "";
		if (length <= 2)
			return commands;
		
		for (int i = 1; i < length + 1; i++) {
			double BX = vertices[i%length].getX();
			double BY = vertices[i%length].getY();
			double AX = vertices[i-1].getX();
			double AY = vertices[i-1].getY();
			double CX = vertices[(i+1)%length].getX();
			double CY = vertices[(i+1)%length].getY();
			DoubleVector BA = new DoubleVector(AX - BX, AY - BY);
			DoubleVector BC = new DoubleVector(CX - BX, CY - BY);
			DoublePoint B = new DoublePoint(BX, BY);
			DoublePoint BAC = new DoublePoint((BX + AX)/2, (BY + AY)/2);
			DoublePoint BCC = new DoublePoint((BX + CX)/2, (BY + CY)/2);	
			double BACX = BAC.getX();
			double BACY = BAC.getY();
			double BCCX = BCC.getX();
			double BCCY = BCC.getY();		
			String strBACX = String.valueOf(BACX);
			String strBACY = String.valueOf(BACY);
			String strBCCX = String.valueOf(BCCX);
			String strBCCY = String.valueOf(BCCY);
			String strBX = String.valueOf(BX);
			String strBY = String.valueOf(BY);
			
			if (BA.crossProduct(BC) == 0) {
				commands +=  "line" + strBACX + strBACY + strBX + strBY + System.lineSeparator() + 
							 "line" + strBX + strBY + strBCCX + strBCCY + System.lineSeparator();
			}
			else { 
				double sizeAB = BA.getSize();
				DoubleVector BAU = BA.scale(1/sizeAB);
				double sizeBC = BC.getSize();
				DoubleVector BCU = BC.scale(1/sizeBC);
				DoubleVector BS = BAU.plus(BCU);
				double sizeBS = BS.getSize();
				DoubleVector BSU = BS.scale(1/sizeBS);
				double BAUcutoff = BAU.dotProduct(BSU);
				double UnitRadius = Math.abs(BSU.crossProduct(BAU));
				double minEdge = Math.min(BCC.minus(B).getSize(), BAC.minus(B).getSize());
				double scale = 	Math.min(minEdge, this.getRadius()/UnitRadius);
				
				DoublePoint center = B.plus(BSU.scale(scale));
				double actualRadius = UnitRadius * scale;
				double actualCutoff = BAUcutoff * scale;
				
				DoublePoint BAcut = B.plus(BAU.scale(actualCutoff));
				DoublePoint BCcut = B.plus(BCU.scale(actualCutoff));
				
				double startAngle = BA.asAngle() - Math.PI;
				double endAngle = (BC.asAngle() - Math.PI - startAngle);
				if (endAngle < -Math.PI) {
					endAngle += 2 * Math.PI;
				} 
				else if (endAngle > Math.PI) {
					endAngle -= 2 * Math.PI;
				}
				
				String strBAcutX  = String.valueOf(BAcut.getX());
				String strBAcutY  = String.valueOf(BAcut.getY());
				String strBCcutX  = String.valueOf(BCcut.getX());
				String strBCcutY  = String.valueOf(BCcut.getY());
				String strCenterX  = String.valueOf(center.getX());
				String strCenterY  = String.valueOf(center.getY());
				String strRadius = String.valueOf(actualRadius);
				String strStartAngle  = String.valueOf(startAngle);
				String strEndAngle  = String.valueOf(endAngle);
				
				commands += "line " + strBACX + " " + strBACY + " " + strBAcutX + " " + strBAcutY + System.lineSeparator() + 
							"arc "  + strCenterX + " " + strCenterY + " " + strRadius + " " + strStartAngle + " " + strEndAngle + System.lineSeparator() + 
							"line " + strBCcutX + " " + strBCcutY + " " + strBCCX + " " + strBCCY + System.lineSeparator();
			}	
		}

		return commands;
	}	
}


package drawit;

import java.awt.Color;

import drawit.shapegroups1.Extent;

public class RoundedPolygon {
	
	private IntPoint[] vertices;
	private int radius;
	private Color color; 
	private Extent extent;

	/**
	 * An instance of this class is a mutable abstraction storing a rounded polygon 
	 * defined by a set of 2D points with integer coordinates and a nonnegative corner radius.
	 * This object is initialized with the color yellow.
	 * 
	 * @post The constructed polygon is proper.
	 * 		| PointArrays.checkDefinesProperPolygon(this.getVertices()) == null
	 * @mutable
	 */
	public RoundedPolygon() {
		IntPoint myIntPoint = new IntPoint(-100, -100);
		IntPoint myIntPoint2 = new IntPoint(-100, 100);
		IntPoint myIntPoint3 = new IntPoint(100, 100);
		IntPoint myIntPoint4 = new IntPoint(100, -100);
		
		IntPoint[] myIntPoints = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint4};
	
		this.vertices = myIntPoints;
		this.radius = 10;
		this.color = Color.yellow;
		this.extent = Extent.ofLeftTopRightBottom(-100, -100, 100, 100);
	}
	
	/**
	 * Returns a new array whose elements are the vertices of this rounded polygon.
	 */
	public IntPoint[] getVertices() {
		return this.vertices;		
	}
	
	/** 
	 * Returns the radius of the corners of this rounded polygon.
	 */
	public int getRadius() {
		return this.radius;	
	}
	
	/**
	* Returns the bounding box of the polygon.
	*/
	public Extent getExtent() {
		return this.extent;
	}
	
	/**
	 * Sets the vertices of this rounded polygon to be equal to the elements of the given array.
	 * 
	 * @mutates | this
	 * @throws IllegalArgumentException  if the given vertices do not define a proper polygon. 
	 * 		| PointArrays.checkDefinesProperPolygon(newVertices) != null
	 * @post The given vertices are now the vertices of the polygon.
	 * 		| this.getVertices() == newVertices
	 */	
	public void setVertices(IntPoint[] newVertices) {
		if (PointArrays.checkDefinesProperPolygon(newVertices) == null) {
			this.vertices = newVertices;
			
			int length = newVertices.length;
			
			int left = 0;
			int top = 0;
			int right = 1;
			int bottom = 1;
			
			if (length > 0) {
				left = newVertices[0].getX();
				right = newVertices[0].getX();
				top = newVertices[0].getY();
				bottom = newVertices[0].getY();			
			
				for (int i = 1; i < length; i++) {
					IntPoint vertex = newVertices [i];
					if (vertex.getX() < left)
						left = vertex.getX();
					if (vertex.getX() > right) 
						right = vertex.getX();
					if (vertex.getY() < top)
						top = vertex.getY();
					if (vertex.getY() > bottom) 
						bottom = vertex.getY();
				}
				
				if (right == left)
					right++;
				if (bottom == top)
					bottom++;
			}
			
			this.extent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		}
		else 
			throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(newVertices));		
	}
	
	/**
	 * Sets this rounded polygon's corner radius to the given value.
	 * @mutates | this
	 * @throws IllegalArgumentException if the given radius is negative.
	 * 		| radius < 0
	 * @throws IllegalArgumentException if the polygon isn't proper.
	 * 		| PointArrays.checkDefinesProperPolygon(this.getVertices()) != null
	 * @post The given radius is now the new radius of the rounded polygon.
	 * 		| this.getRadius() == radius
	 */
	public void setRadius(int radius) {
		if (radius >= 0) {
			this.radius = radius;
			this.setVertices(this.vertices);
		}
		else 
			throw new IllegalArgumentException("The radius has to be positive");	
		
	}
	
	/**
	 * Inserts the given vertex into the polygon.
	 * 
	 * @mutates | this
	 * @throws IllegalArgumentException if the index is out of range.
	 * 		| 0 > index || index > this.getVertices().length
	 * @post The length of the vertices is increased by 1.
	 * 		| old(this.getVertices().length) + 1 == this.getVertices().length
	 */
	public void insert(int index, IntPoint point) {
		if (0 <= index && index <= this.getVertices().length) {
			IntPoint[] Vertices = this.getVertices();
			IntPoint[] newVertices = PointArrays.insert(Vertices, index, point);
			this.setVertices(newVertices);
		}
		else 
			throw new IllegalArgumentException("The index is out of range");
	}
	
	/**
	 * Removes the vertex at the given index.
	 * 
	 * @mutates | this
	 * @throws IllegalArgumentException if the index is out of range.
	 * 		| 0 > index || index >= this.getVertices().length
	 * @post The length of the vertices is decreased by 1.
	 * 		| old(this.getVertices().length) - 1 == this.getVertices().length
	 */
	public void remove(int index) {
		if (0 <= index && index < this.getVertices().length) {
			IntPoint[] Vertices = this.getVertices();
			IntPoint[] newVertices = PointArrays.remove(Vertices, index);
			this.setVertices(newVertices);
		}
		else 
			throw new IllegalArgumentException("The index is out of range");
	}
	
	/**
	 * Removes the vertex at the given index.
	 * 
	 * @mutates | this
	 * @throws IllegalArgumentException if the index is out of range.
	 * 		| 0 > index || index >= this.getVertices().length
	 * @post The length of the vertices remains the same.
	 * 		| old(this.getVertices().length) == this.getVertices().length
	 */
	public void update(int index, IntPoint point) {
		if (0 <= index && index < this.getVertices().length) {
			IntPoint[] Vertices = this.getVertices();
			IntPoint[] newVertices = PointArrays.update(Vertices, index, point);
			this.setVertices(newVertices);
		}
		else 
			throw new IllegalArgumentException("The index is out of range");
	}
	
	/**
	 * Returns true if the given point is contained by the (non-rounded) polygon defined by this rounded polygon's vertices. 
	 * <p>
	 * This method does not take into account this rounded polygon's corner radius; it assumes a corner radius of zero.
	 * <p>
	 * A point is contained by a polygon if it coincides with one of its vertices, 
	 * or if it is on one of its edges, or if it is in the polygon's interior. 
	 */
	public boolean contains(IntPoint point) {		
		IntVector ExitPathVector = new IntVector(1,0);
		IntPoint [] vertices = this.getVertices();
		for  (int i = 0; i < vertices.length; i++) {
			if (point.equals(vertices[i]))
					return true;
			if (point.isOnLineSegment(vertices[i],vertices[(i+1)%vertices.length]))
					return true;
			}
		
		int count = 0;
		
		for (int i = 0; i < vertices.length; i++) {
			IntPoint first = vertices[i];
			if (point.getY() != first.getY() || point.getX() > first.getX()) {
				
				boolean searching = true;
				int j = i + 1;
				while ((searching) && (j <= vertices.length)) {
					IntPoint second = vertices[j%vertices.length];
					if (point.getY() != second.getY() || point.getX() > second.getX()) {
						searching = false;	
						if (j-i == 1) {
							
							IntVector VV = new IntVector(second.getX() - first.getX(), second.getY() - first.getY());
							IntVector VP = new IntVector(point.getX() - first.getX(), point.getY() - first.getY());
							
							if (((first.getY() - point.getY()) * (second.getY() - point.getY())) < 0 &&
									(VP.crossProduct(VV) * ExitPathVector.crossProduct(VV)) < 0)
								count++;
						}
						
						else {
							if ((first.getY() - point.getY()) * (second.getY() - point.getY()) < 0) 
								count++;						
						}
					
					}
					j++;
				}
			}
		}
		
	if (count % 2 == 0)
		return false;
	return true;
	}
	
	/**
	 * Returns a textual representation of a set of drawing commands for drawing this rounded polygon. 
	 * The returned text consists of a sequence of drawing operators and arguments, separated by spaces. 
	 * The drawing operators are line and arc. Each argument is a decimal representation of a floating-point number.
	 * <p>
	 * <p>
	 * Operator line takes four arguments: X1 Y1 X2 Y2; it draws a line between (X1, Y1) and (X2, Y2). 
	 * arc takes five: X Y R S E. It draws a part of a circle. The circle is defined by its center (X, Y) and its radius R. 
	 * <p>
	 * <p>
	 * The part to draw is defined by the start angle A and angle extent E, both in radians. 
	 * Positive X is angle zero; positive Y is angle Math.PI / 2; negative Y is angle -Math.PI / 2.
	 * <p> 
	 * <p>
	 *  By rounding a corner, the adjacent edges are cut short by some amount. 
	 *  The corner radius to be used for a particular corner is the largest radius that is not greater than this rounded 
	 *  polygon's corner radius and that is such that no more than half of each adjacent edge is cut off by it. 
	 */
	public String getDrawingCommands() {
		IntPoint[] vertices = this.getVertices();
		int length = vertices.length;
		String commands = "";
		if (length <= 2)
			return commands;
		for (int i = 1; i < length + 1; i++) {
			DoublePoint B = vertices[i%length].asDoublePoint();
			DoublePoint A = vertices[i-1].asDoublePoint();
			DoublePoint C = vertices[(i+1)%length].asDoublePoint();
			double BX = B.getX();
			double BY = B.getY();
			double AX = A.getX();
			double AY = A.getY();
			double CX = C.getX();
			double CY = C.getY();
			DoubleVector BA = A.minus(B);
			DoubleVector AB = BA.scale(-1);
			DoubleVector BC = C.minus(B);
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
				commands +=  "line " + strBACX + " " + strBACY + " " + strBX + " " + strBY + System.lineSeparator() + 
							 "line " + strBX + " " + strBY + " " + strBCCX + " " + strBCCY + System.lineSeparator();
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
				double scale = 	Math.min(minEdge/BAUcutoff, this.getRadius()/UnitRadius);
				
				DoublePoint center = B.plus(BSU.scale(scale));
				double actualRadius = UnitRadius * scale;
				double actualCutoff = BAUcutoff * scale;
				
				DoublePoint BAcut = B.plus(BAU.scale(actualCutoff));
				DoublePoint BCcut = B.plus(BCU.scale(actualCutoff));
				
				double startAngle = AB.asAngle() - Math.PI/2;
				double endAngle = BC.asAngle() - Math.PI/2;
				
				double extendAngle = (endAngle - startAngle);
				
				
					
				if (extendAngle < -Math.PI) {
					extendAngle += 2 * Math.PI;
				} 
				else if (extendAngle > Math.PI) {
					extendAngle -= 2 * Math.PI;
				}
				
				if (AB.crossProduct(BC) < 0) {
					startAngle += Math.PI;
				}
				
				
				
				String strBAcutX  = String.valueOf(BAcut.getX());
				String strBAcutY  = String.valueOf(BAcut.getY());
				String strBCcutX  = String.valueOf(BCcut.getX());
				String strBCcutY  = String.valueOf(BCcut.getY());
				String strCenterX  = String.valueOf(center.getX());
				String strCenterY  = String.valueOf(center.getY());
				String strRadius = String.valueOf(actualRadius);
				String strStartAngle  = String.valueOf(startAngle);
				String strEndAngle  = String.valueOf(extendAngle);
				
				commands += "line " + strBACX + " " + strBACY + " " + strBAcutX + " " + strBAcutY + System.lineSeparator() + 
							"arc "  + strCenterX + " " + strCenterY + " " + strRadius + " " + strStartAngle + " " + strEndAngle + System.lineSeparator() + 
							"line " + strBCcutX + " " + strBCcutY + " " + strBCCX + " " + strBCCY + System.lineSeparator();
			}	
		}
		
		String strRvalue = String.valueOf(this.getColor().getRed());
		String strGvalue = String.valueOf(this.getColor().getGreen());
		String strBvalue = String.valueOf(this.getColor().getBlue());
		commands += "fill " + " " + strRvalue + " " + strGvalue + " " + strBvalue + System.lineSeparator();
		return commands;
	}
	
	/**
	 * Returns the color of this rounded polygon.
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Sets the color of this rounded polygon to the given color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}

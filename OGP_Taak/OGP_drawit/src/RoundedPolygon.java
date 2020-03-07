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
		this.vertices = new IntPoint[0] ;
		this.radius = 0;
	}
	
	/**
	 * Returns a new array whose elements are the vertices of this rounded polygon.
	 * 		| this.getVertices == vertices
	 */
	
	public IntPoint[] getVertices() {
		return this.vertices;		
	}
	
	/** 
	 * Returns the radius of the corners of this rounded polygon.
	 * 		| this.getRadius == radius
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
	 * 		| getRadius() == radius
	 */
	
	public void setRadius(int radius) {
		if (radius >= 0)
			this.radius = radius;
		else 
			throw new IllegalArgumentException("The radius has to be positive");			
	}
	
	public void insert(int index, IntPoint point) {
		if (index < this.getVertices().length)
			PointArrays.insert(this.getVertices(), index, point);		
	}
	
	public void remove(int index) {
		if (index < this.getVertices().length)
			PointArrays.remove(this.getVertices(), index);
	}
	
	public void update(int index, IntPoint point) {
		if (index < this.getVertices().length) {
			PointArrays.remove(this.getVertices(), index);
			PointArrays.insert(this.getVertices(), index, point);
		}
	}
	
	public boolean contains(IntPoint point) {		
		
		//Code that checks if the point is a vertices or on on of the edges of the polygon 
		
		IntPoint [] vertices = this.getVertices();
		for  (int i = 0; i < vertices.length; i++) {
			if (point.equals(vertices[i]))
					return true;
			for (int j = i+1; j < vertices.length - 1; j++ ) {
				if (point.IsOnLineSegment(vertices[i],vertices[j]))
					return true;
			}
		}
		
		int check = 0;
		int count = 0;
		
		for (int i = 0; i < vertices.length; i++) {
			//find the first vertex that isn't on the exit path
			IntPoint first = vertices[i];
			if (point.getY() != first.getY() || point.getX() > first.getX()) {
				
				for (int j = i+1; j < vertices.length - 1; j++ ) {
					//find the second vertex that isn't on the exit path
					IntPoint second = vertices[j];
					if (point.getY() != second.getY() || point.getX() > second.getX()) {
						
						//if (there are no vertices on the exit path lies between first and second)
						
						if (whatever == 0) {
							
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
			else { check++; }
			
		if (check == vertices.length)
			return false;
		
		if (count // 2 == 0)
			return false;
		if (count //2 == 1)
			return true;
				
		}		
		
	}

}

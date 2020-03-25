package drawit;

/**
 * Declares a number of methods useful for working with arrays of IntPoint
 * objects.
 * 
 * @immutable
 */
public class PointArrays {
	
	private PointArrays() {
		
	}

	/**
	 * Returns null if the given array of points defines a proper polygon;
	 * otherwise, returns a string describing why it does not. We interpret an array
	 * of N points as the polygon whose vertices are the given points and whose
	 * edges are the open line segments between point I and point (I + 1) % N, for I
	 * = 0, ..., N - 1. A proper polygon is one where no two vertices coincide and
	 * no vertex is on any edge and no two edges intersect. If there are exactly two
	 * points, the polygon is not proper. Notice that if there are more than two
	 * points and no two vertices coincide and no vertex is on any edge, then no two
	 * edges intersect at more than one point.
	 */
	public static String checkDefinesProperPolygon(IntPoint[] points) {

		if (points.length <= 2)
			return "A proper polygon is defined by at least 3 points.";

		boolean CoincidingVertices = false;
		boolean VertexOnEdge = false;
		boolean IntersectingEdges = false;

		int i = 0;
		int j = 0;
		while (!(CoincidingVertices) && !(VertexOnEdge) && !(IntersectingEdges) && i < points.length) {
			j = 0;
			while (!CoincidingVertices && !(VertexOnEdge) && !(IntersectingEdges) && j < points.length) {
				if (points[i].equals(points[j]) && i != j)
					CoincidingVertices = true;
				else if (points[i].isOnLineSegment(points[j], points[(j + 1) % points.length]))
					VertexOnEdge = true;
				else if (IntPoint.lineSegmentsIntersect(points[i], points[(i + 1) % points.length], points[j],
						points[(j + 1) % points.length]))
					IntersectingEdges = true;
				j++;
			}
			i++;
		}

		String stri = String.valueOf(i);
		String strj = String.valueOf(j);
		String strii = String.valueOf((i + 1) % points.length);
		if (strii.equals("0"))
			strii = String.valueOf(points.length);
		String strjj = String.valueOf((j + 1) % points.length);
		if (strjj.equals("0"))
			strjj = String.valueOf(points.length);
		if (CoincidingVertices)
			return "Vertex " + stri + " and " + strj + " coincide.";
		if (VertexOnEdge)
			return "Vertex " + stri + " is the edge formed by points " + strj + " and " + strjj + ".";
		if (IntersectingEdges)
			return "The edge formed by points " + stri + " and " + strii + " intersects the edge formed by points "
					+ strj + " and " + strjj + ".";

		return null;
	}
	

	/**
	 * Returns a new array with the same contents as the given array.
	 * 
	 * @post The length of the array stays the same. 
	 * 		| result.length == points.length
	 */
	public static IntPoint[] copy(IntPoint[] points) {

		IntPoint[] copy = new IntPoint[points.length];

		for (int i = 0; i < points.length; i++) {
			copy[i] = points[i];
		}

		return copy;

	}

	/**
	 * Returns a new array whose elements are the elements of the given array with
	 * the given point inserted at the given index.
	 * 
	 * @pre The given index must be in between 0 and the length of points (N included). 
	 * 		| 0 <= index && index <= points.length
	 * @post The new array's length is the length of points increased by 1. 
	 * 		| result.length == points.length + 1
	 * @post The new array has the given point on the given index. 
	 * 		| result[index] == point
	 */
	public static IntPoint[] insert(IntPoint[] points, int index, IntPoint point) {
		IntPoint[] WithInsertion = new IntPoint[points.length + 1];

		for (int i = 0; i < WithInsertion.length; i++) {
			if (i < index)
				WithInsertion[i] = points[i];		
			else if (i > index)
				WithInsertion[i] = points[i - 1];
			else
				WithInsertion[i] = point;			
		}

		return WithInsertion;
	}

	/**
	 * Returns a new array whose elements are the elements of the given array with
	 * the element at the given index removed.
	 * 
	 * @pre The given index must be in between 0 and the length of points (N excluded). 
	 * 		| 0 <= index && index < points.length
	 * @post The new array's length is the length of points decreased by 1. 
	 * 		| result.length == points.length - 1
	 */
	public static IntPoint[] remove(IntPoint[] points, int index) {
		IntPoint[] WithRemoved = new IntPoint[points.length - 1];

		for (int i = 0; i < WithRemoved.length; i++) {
			if (i < index)
				WithRemoved[i] = points[i];
			else
				WithRemoved[i] = points[i + 1];
		}

		return WithRemoved;
	}

	/**
	 * Returns a new array whose elements are the elements of the given array with
	 * the element at the given index replaced by the given point.
	 * 
	 * @pre The given index must be in between 0 and the length of points (N excluded). 
	 * 		| 0 <= index && index < points.length
	 * @post The length of the array stays the same. 
	 * 		| result.length == points.length
	 * @post The new array has the given point on the given index. 
	 * 		| result[index] == point
	 */
	public static IntPoint[] update(IntPoint[] points, int index, IntPoint point) {
		IntPoint[] WithUpdate = new IntPoint[points.length];
		
		for (int i = 0; i < WithUpdate.length; i++) {
			if (i == index)
				WithUpdate[i] = point;
			else
				WithUpdate[i] = points[i];
		}

		return WithUpdate;
	}

}



public class PointArrays {
	
	public static String checkDefinesProperPolygon(IntPoint[] points) {
		
		if (points.length <= 2)
			return "A proper polygon is defined by at least 3 points.";
		
		boolean CoincidingVertices = false;
		boolean VertexOnEdge = false;
		boolean IntersectingEdges = false;
		
		int i = 0;
		int j = 1;
		while (!(CoincidingVertices) && !(VertexOnEdge) && !(IntersectingEdges) && i < points.length) {
			while (!CoincidingVertices && !(VertexOnEdge) && !(IntersectingEdges) && j < points.length - i) {
				if (points[i].equals(points[j]))
					CoincidingVertices = true;
				else if (points[i].IsOnLineSegment(points[j], points[(j+1)%points.length]))
					VertexOnEdge = true;
				else if (IntPoint.lineSegmentsIntersect​(points[i], points[(i+1)%points.length], points[j], points[(j+1)%points.length]))
					IntersectingEdges = true;
				j++;
			}
			i++;
		}
		
		if (CoincidingVertices)
			return "A Proper polygon has no coinciding vertices.";
		if (VertexOnEdge)
			return "A Proper polygon does not have a vertex on one of its edges.";
		if (IntersectingEdges)
			return "A proper polygon does not have intersecting vertices.";
		
		return null;
	}
	
	public static IntPoint[] copy(IntPoint[] points) {
		
		IntPoint[] copy = new IntPoint[points.length];
		
		for (int i = 0; i < points.length; i++) {
			copy[i] = points[i];
		}
		
		return copy;
		
	}
	
	public static IntPoint[] insert(IntPoint[] points, int index, IntPoint point) {
		
		IntPoint[] WithInsertion = new IntPoint[points.length + 1];
		boolean inserted = false;
				
		for (int i = 0; i < WithInsertion.length; i++) {
			if (i == index) {
				WithInsertion[i] = point;
				inserted = true;
			}
			else if (inserted)
				WithInsertion[i] = points[i-1];
			else
				WithInsertion[i] = points[i];		
		}
		
		return WithInsertion;
	}
	
	public static IntPoint[] remove(IntPoint[] points, int index) {
		
		IntPoint[] WithRemoved = new IntPoint[points.length - 1];
		boolean removed = false;
		
		for (int i = 0; i < WithRemoved.length; i++) {
			if (i == index)
				removed = true;
			if (removed)
				WithRemoved[i] = points[i-1];
			else
				WithRemoved[i] = points[i];
			
		}
		
		return WithRemoved;
	}

}

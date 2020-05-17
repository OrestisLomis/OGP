package drawit;

public class PreciseRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {
	
	public PreciseRoundedPolygonContainsTestStrategy() {};
	
	/** Returns whether the given polygon contains the given point. The edges and vertices of the polygon are also considered 
	 * the inside of the polygon.
	 * @throws IllegalArgumentException when one of the arguments is null
	 * 		| polygon == null || point == null
	 * 
	 * @inspects | polygon
	 * @mutates nothing |
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		if (polygon != null && point != null) {
			return polygon.contains(point);
		}
		else 
			throw new IllegalArgumentException("At least one of the arguments is null");
	}

}

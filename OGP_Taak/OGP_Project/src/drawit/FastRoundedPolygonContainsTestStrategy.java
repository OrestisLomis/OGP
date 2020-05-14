package drawit;

public class FastRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {
	
	public FastRoundedPolygonContainsTestStrategy() {};
	
	/** Returns whether the bounding box of given polygon contains the given point. It may be inside the bounding box,
	 * but outside the polygon. That's why this is the fast test strategy.
	 * @pre Given polygon is not null.
	 * 		| polygon != null
	 * @pre Given point is not null.
	 * 		| point != null
	 * 
	 * For further documentation, see Extent.contains()
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		return polygon.getExtent().contains(point);
	}

}

package drawit;

/**
 * This interface contains one method, to test if a point is in (or close to) a polygon.
 *
 */
public interface RoundedPolygonContainsTestStrategy {

	/**
	 * The method returns whether or not a point is inside a polygon, or in its bounding box.
	 */
	boolean contains(RoundedPolygon polygon, IntPoint point);
}

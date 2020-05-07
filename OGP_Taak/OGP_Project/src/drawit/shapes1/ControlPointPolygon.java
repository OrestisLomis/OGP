package drawit.shapes1;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups1.ShapeGroup;

public class ControlPointPolygon implements ControlPoint {
	
	private final IntPoint point;
	private final RoundedPolygonShape polygon;
	private final int vertex;

	
	public ControlPointPolygon (IntPoint point, RoundedPolygonShape polygon, int vertex) {
		this.point = point;
		this.polygon = polygon;
		this.vertex = vertex;
	}
	
	public RoundedPolygonShape getPolygon() {
		return this.polygon;
	}

	@Override
	public IntPoint getLocation() {
		return this.point;
	}

	@Override
	public void move(IntVector delta) {
		IntVector fixedDelta = getPolygon().toShapeCoordinates(delta);
		IntPoint newLocation = getLocation().plus(fixedDelta);
		getPolygon().getPolygon().update(vertex, newLocation);
	}

	@Override
	public void remove() {
		getPolygon().getPolygon().remove(vertex);
	}

}
package drawit.shapes2;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups2.ShapeGroup;

public class RoundedPolygonShape implements Shape {
	
	private ShapeGroup parent;
	private RoundedPolygon polygon;
	
	public RoundedPolygonShape(drawit.shapegroups2.ShapeGroup parent, drawit.RoundedPolygon polygon) {
		this.parent = parent;
		this.polygon = polygon;
	}
	
	public RoundedPolygon getPolygon() {
		return polygon;
	}

	@Override
	public boolean contains(IntPoint p) {
		return getPolygon().contains(p);
	}

	@Override
	public ControlPoint[] createControlPoints() {
		RoundedPolygon polygon2 = getPolygon();
		IntPoint[] vertices = polygon2.getVertices();
		int length = vertices.length;
		ControlPoint[] controlPoints = new ControlPoint[length];
		for (int i = 0; i < length; i++) {
			controlPoints[i] = new ControlPointPolygon(vertices[i], polygon2, i);
		}
		return controlPoints;
	}

	@Override
	public String getDrawingCommands() {
		return getPolygon().getDrawingCommands();
	}

	@Override
	public ShapeGroup getParent() {
		return parent;
	}

	@Override
	public IntPoint toGlobalCoordinates(IntPoint p) {
		return getParent().toGlobalCoordinates(p);
	}

	@Override
	public IntPoint toShapeCoordinates(IntPoint p) {
		return getParent().toInnerCoordinates(p);
	}

}

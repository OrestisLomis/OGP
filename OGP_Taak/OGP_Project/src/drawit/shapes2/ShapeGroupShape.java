package drawit.shapes2;

import drawit.IntPoint;
import drawit.shapegroups2.ShapeGroup;

public class ShapeGroupShape implements Shape {
	
	private ShapeGroup group;
	
	public ShapeGroupShape(drawit.shapegroups2.ShapeGroup group) {
		this.group = group;
	}
	
	public ShapeGroup getShapeGroup() {
		return group;
	}

	@Override
	public boolean contains(IntPoint p) {
		return getShapeGroup().getExtent().contains(p);
	}

	@Override
	public ControlPoint[] createControlPoints() {
		return null;
	}

	@Override
	public String getDrawingCommands() {
		return getShapeGroup().getDrawingCommands();
	}

	@Override
	public ShapeGroup getParent() {
		return getShapeGroup().getParentGroup();
	}

	@Override
	public IntPoint toGlobalCoordinates(IntPoint p) {
		return getShapeGroup().toGlobalCoordinates(p);
	}

	@Override
	public IntPoint toShapeCoordinates(IntPoint p) {
		return getShapeGroup().toInnerCoordinates(p);
	}

}

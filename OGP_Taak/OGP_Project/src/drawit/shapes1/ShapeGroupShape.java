package drawit.shapes1;

import drawit.IntPoint;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.ShapeGroup;

public class ShapeGroupShape implements Shape {
	
	private ShapeGroup group;
	
	public ShapeGroupShape(drawit.shapegroups1.ShapeGroup group) {
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
		ShapeGroup shape = getShapeGroup();
		Extent extent = shape.getExtent();
		return new ControlPoint[] {new ControlPointShape(shape, true, extent), new ControlPointShape(shape, false, extent)};
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
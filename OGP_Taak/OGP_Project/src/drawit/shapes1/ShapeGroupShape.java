package drawit.shapes1;

import drawit.IntPoint;
import drawit.IntVector;
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
		return new ControlPoint[] {new ControlPointShape(this, true), new ControlPointShape(this, false)};
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
		ShapeGroup parent = getParent();
		if (parent != null)
			return parent.toGlobalCoordinates(p);
		else
			return p;
	}

	@Override
	public IntPoint toShapeCoordinates(IntPoint p) {
		ShapeGroup parent = getParent();
		if (parent != null)
			return parent.toInnerCoordinates(p);
		else
			return p;
	}

	@Override
	public IntVector toShapeCoordinates(IntVector v) {
		ShapeGroup parent = getParent();
		if (parent != null)
			return parent.toInnerCoordinates(v);
		else
			return v;
	}

}

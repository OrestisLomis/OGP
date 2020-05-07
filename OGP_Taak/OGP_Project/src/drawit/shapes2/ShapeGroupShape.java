package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups2.Extent;
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
		Extent extent = getShapeGroup().getExtent();
		return new ControlPoint[] {new ControlPointShape(this, true, extent), new ControlPointShape(this, false, extent)};
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

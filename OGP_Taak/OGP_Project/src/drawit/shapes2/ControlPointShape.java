package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups2.Extent;
import drawit.shapes2.ControlPoint;
import drawit.shapes2.ShapeGroupShape;

public class ControlPointShape implements ControlPoint {
	
	private final ShapeGroupShape group;
	private final boolean topleft;
	private final IntPoint point;
	private Extent extent;
	
	public ControlPointShape(ShapeGroupShape group, boolean topleft) {
		this.group = group;
		this.topleft = topleft;
		this.extent = group.getShapeGroup().getExtent();
		if (topleft)
			this.point = getGroup().getShapeGroup().getOriginalExtent().getTopLeft();
		else
			this.point = getGroup().getShapeGroup().getOriginalExtent().getBottomRight();
	}
	

	public ShapeGroupShape getGroup() {
		return this.group;
	}

	@Override
	public IntPoint getLocation() {
		return this.point;
	}

	@Override
	public void move(IntVector delta) {
		IntVector fixedDelta = getGroup().toShapeCoordinates(delta);
		IntPoint newLocation = getLocation().plus(fixedDelta);
		if (topleft) {
			Extent between = this.extent.withTop(newLocation.getY());
			getGroup().getShapeGroup().setExtent(between.withLeft(newLocation.getX()));
		}
		else {
			Extent between = this.extent.withBottom(newLocation.getY());
			getGroup().getShapeGroup().setExtent(between.withRight(newLocation.getX()));
		}
	}

	@Override
	public void remove() {
	}

}

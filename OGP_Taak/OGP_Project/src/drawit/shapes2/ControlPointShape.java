package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups2.Extent;
import drawit.shapegroups2.ShapeGroup;

public class ControlPointShape implements ControlPoint {
	
	private final ShapeGroup group;
	private final boolean topleft;
	private final IntPoint point;
	private Extent extent;
	
	public ControlPointShape(ShapeGroup group, boolean topleft, Extent extent) {
		this.group = group;
		this.topleft = topleft;
		this.extent = extent;
		if (topleft)
			this.point = getGroup().getOriginalExtent().getTopLeft();
		else
			this.point = getGroup().getOriginalExtent().getBottomRight();
	}
	
	public ShapeGroup getGroup() {
		return this.group;
	}

	@Override
	public IntPoint getLocation() {
		return this.point;
	}

	@Override
	public void move(IntVector delta) {
		IntPoint newLocation = getLocation().plus(delta);
		if (topleft) {
			Extent between = this.extent.withTop(newLocation.getY());
			getGroup().setExtent(between.withLeft(newLocation.getX()));
		}
		else {
			Extent between = this.extent.withBottom(newLocation.getY());
			getGroup().setExtent(between.withRight(newLocation.getX()));
		}
	}

	@Override
	public void remove() {
	}

}

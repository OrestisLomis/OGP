package drawit.shapes1;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups1.Extent;

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
			this.point = getGroup().getShapeGroup().getExtent().getTopLeft();
		else
			this.point = getGroup().getShapeGroup().getExtent().getBottomRight();
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
	/**
	 * @throws UnsupportedOperationException
	 */
	public void remove() {
		throw new UnsupportedOperationException("You can only remove vertices.");
	}

}
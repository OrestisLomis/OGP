package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups2.ShapeGroup;

public class ControlPointShape implements ControlPoint {
	
	private ShapeGroup group;
	private IntPoint point;
	
	public ControlPointShape(ShapeGroup group, IntPoint point) {
		this.group = group;
		this.point = point;
	}

	@Override
	public IntPoint getLocation() {
		return point;
	}

	@Override
	public void move(IntVector delta) {
		this.point = point.plus(delta);
	}

	@Override
	public void remove() {
	}

}

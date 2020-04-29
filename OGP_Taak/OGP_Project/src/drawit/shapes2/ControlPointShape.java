package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups2.ShapeGroup;

public class ControlPointShape implements ControlPoint {
	
	private ShapeGroup shape;
	private IntPoint point;

	@Override
	public IntPoint getLocation() {
		return point;
	}

	@Override
	public void move(IntVector delta) {
		point.plus(delta);
		
	}

	@Override
	public void remove() {
	}

}

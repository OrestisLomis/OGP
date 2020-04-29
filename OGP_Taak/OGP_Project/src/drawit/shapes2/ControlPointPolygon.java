package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ControlPointPolygon implements ControlPoint {
	
	private IntPoint controlPoint;
	private RoundedPolygon polygon;
	private int vertex;

	
	public ControlPointPolygon (IntPoint controlPoint, RoundedPolygon polygon, int vertex) {
		this.controlPoint = controlPoint;
		this.polygon = polygon;
		this.vertex = vertex;
	}
	
	public RoundedPolygon getPolygon() {
		return this.polygon;
	}

	@Override
	public IntPoint getLocation() {
		return controlPoint;
	}

	@Override
	public void move(IntVector delta) {
		controlPoint.plus(delta);
		
	}

	@Override
	public void remove() {
		getPolygon().remove(vertex);
	
		
	}

}

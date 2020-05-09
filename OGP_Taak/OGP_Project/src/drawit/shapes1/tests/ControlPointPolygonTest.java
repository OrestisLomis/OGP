package drawit.shapes1.tests;

import org.junit.jupiter.api.Test;

import drawit.*;
import drawit.shapes1.*;

class ControlPointPolygonTest {

	@Test
	void test() {
		RoundedPolygon polygon = new RoundedPolygon();
		RoundedPolygonShape shape = new RoundedPolygonShape(null, polygon);
		
		IntPoint[] vertices = polygon.getVertices();
		IntPoint point1 = vertices[0];
		IntPoint point2 = vertices[1];
		IntPoint point3 = vertices[2];
		IntPoint point4 = vertices[3];
		
		ControlPointPolygon cpoint1 = new ControlPointPolygon(point1, shape, 0);
		
		assert cpoint1.getPolygon() == shape;
		assert cpoint1.getLocation() == point1;
		
		cpoint1.move(new IntVector(100,100));
		
		IntPoint[] newVertices = polygon.getVertices();
		
		assert !(point1 == newVertices[0]);
		assert newVertices[0].equals(new IntPoint(0,0));
		assert point2 == newVertices[1];
		assert point3 == newVertices[2];
		assert point4 == newVertices[3];
		assert newVertices.length == 4;
		
		cpoint1.remove();
		IntPoint[] NewestVertices = shape.getPolygon().getVertices();
		
		assert NewestVertices.length == 3;
		assert point2 == NewestVertices[0];
		assert point3 == NewestVertices[1];
		assert point4 == NewestVertices[2];
		
		
	}

}

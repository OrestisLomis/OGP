package drawit.shapes1.tests;

import drawit.*;
import drawit.shapes1.ControlPoint;
import drawit.shapes1.ControlPointPolygon;
import drawit.shapes1.RoundedPolygonShape;
import drawit.shapegroups1.*;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class RoundedPolygonShapeTest {

	@Test
	void test() {

		RoundedPolygon myPolygon1 = new RoundedPolygon();
		LeafShapeGroup SubGroup1 = new LeafShapeGroup(myPolygon1);

		RoundedPolygon myPolygon2 = new RoundedPolygon();
		IntPoint[] myVertices = { new IntPoint(0, 0), new IntPoint(0, 200), new IntPoint(200, 200),
				new IntPoint(200, 0) };
		myPolygon2.setVertices(myVertices);
		LeafShapeGroup SubGroup2 = new LeafShapeGroup(myPolygon2);

		ShapeGroup[] subgroups = new ShapeGroup[] { SubGroup1, SubGroup2 };
		ShapeGroup mygroup = new NonleafShapeGroup(subgroups);

		RoundedPolygonShape shape1 = new RoundedPolygonShape(mygroup, myPolygon1);

		assert shape1.getParent() == mygroup;
		assert shape1.getPolygon() == myPolygon1;
		assert shape1.contains(new IntPoint(-100, 100));
		assert shape1.contains(new IntPoint(0, 0));
		assert shape1.contains(new IntPoint(0, 100));
		assertEquals(myPolygon1.getDrawingCommands(), shape1.getDrawingCommands());

		RoundedPolygonShape shape2 = new RoundedPolygonShape(mygroup, myPolygon2);

		assert shape2.getParent() == mygroup;
		assert shape2.getPolygon() == myPolygon2;
		assert shape2.contains(new IntPoint(0, 200));
		assert shape2.contains(new IntPoint(100, 100));
		assert shape2.contains(new IntPoint(100, 200));
		assert myPolygon2.getDrawingCommands().equals(shape2.getDrawingCommands());

		IntPoint[] vertices = shape1.getPolygon().getVertices();

		assert vertices.length == 4;

		ControlPointPolygon CPoint1 = new ControlPointPolygon(vertices[0], shape1, 0);
		ControlPointPolygon CPoint2 = new ControlPointPolygon(vertices[1], shape1, 1);
		ControlPointPolygon CPoint3 = new ControlPointPolygon(vertices[2], shape1, 2);
		ControlPointPolygon CPoint4 = new ControlPointPolygon(vertices[3], shape1, 3);

		ControlPoint[] controlPoints = shape1.createControlPoints();

		assert controlPoints.length == 4;
		assert controlPoints[0].getLocation() == CPoint1.getLocation();
		assert controlPoints[1].getLocation() == CPoint2.getLocation();
		assert controlPoints[2].getLocation() == CPoint3.getLocation();
		assert controlPoints[3].getLocation() == CPoint4.getLocation();

		Extent myOriginalExtent = mygroup.getOriginalExtent();
		Extent newExtent = Extent.ofLeftTopRightBottom(0, 100, 100, 200);
		mygroup.setExtent(newExtent);

		assert mygroup.getExtent() == newExtent;
		assert mygroup.getOriginalExtent() == myOriginalExtent;

		assert shape1.toGlobalCoordinates(new IntPoint(-100, -100)).equals(new IntPoint(0, 100));
		assert shape2.toGlobalCoordinates(new IntPoint(200, 200)).equals(new IntPoint(100, 200));
		assert shape1.toGlobalCoordinates(new IntPoint(50, 50)).equals(new IntPoint(50, 150));
		assert shape1.toShapeCoordinates(new IntPoint(100, 200)).equals(new IntPoint(200, 200));
		assert shape2.toShapeCoordinates(new IntPoint(100, 100)).equals(new IntPoint(200, -100));
		assert shape1.toShapeCoordinates(new IntVector(1, 3)).getX() == 3;
		assert shape2.toShapeCoordinates(new IntVector(1, 3)).getY() == 9;

		RoundedPolygonShape shape3 = new RoundedPolygonShape(null, new RoundedPolygon());

		assert shape3.toGlobalCoordinates(new IntPoint(0, 0)).equals(new IntPoint(0, 0));
		assert shape3.toGlobalCoordinates(new IntPoint(-100, 100)).equals(new IntPoint(-100, 100));
		assert shape3.toShapeCoordinates(new IntPoint(100, 100)).equals(new IntPoint(100, 100));
		assert shape3.toShapeCoordinates(new IntVector(2, 5)).getX() == 2;
		assert shape3.toShapeCoordinates(new IntVector(2, 5)).getY() == 5;

	}

}

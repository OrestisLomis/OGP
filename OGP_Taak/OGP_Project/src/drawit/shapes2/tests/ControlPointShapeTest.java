package drawit.shapes2.tests;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.*;
import drawit.shapegroups2.*;
import drawit.shapes2.*;

class ControlPointShapeTest {

	@Test
	void test() {
		RoundedPolygon myPolygon1 = new RoundedPolygon();
		RoundedPolygon myPolygon2 = new RoundedPolygon();
		IntPoint[] myVertices = { new IntPoint(0, 0), new IntPoint(0, 200), new IntPoint(200, 200),
				new IntPoint(200, 0) };
		myPolygon2.setVertices(myVertices);

		LeafShapeGroup SubGroup1 = new LeafShapeGroup(myPolygon1);
		LeafShapeGroup SubGroup2 = new LeafShapeGroup(myPolygon2);

		ShapeGroup[] subgroups = new ShapeGroup[] { SubGroup1, SubGroup2 };
		ShapeGroup mygroup = new NonleafShapeGroup(subgroups);

		ShapeGroupShape myshape = new ShapeGroupShape(mygroup);

		ControlPointShape cpoint1 = new ControlPointShape(myshape, true);
		ControlPointShape cpoint2 = new ControlPointShape(myshape, false);

		assert cpoint1.getGroup() == myshape;
		assert cpoint1.getGroup() == myshape;

		assert cpoint1.getLocation().equals(new IntPoint(-100, -100));
		assert cpoint2.getLocation().equals(new IntPoint(200, 200));

		cpoint1.move(new IntVector(100, 100));

		assert cpoint1.getGroup().getShapeGroup() == mygroup;
		assert mygroup.getExtent().getTopLeft().equals(new IntPoint(0, 0));
		assert cpoint1.getLocation().equals(new IntPoint(-100, -100));

		cpoint2.move(new IntVector(200, 200));

		assert cpoint2.getGroup().getShapeGroup() == mygroup;
		assert mygroup.getExtent().getBottomRight().equals(new IntPoint(400, 400));
		assert cpoint2.getLocation().equals(new IntPoint(200, 200));

	}

}
package drawit.shapegroups2.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import drawit.shapegroups2.Extent;
import drawit.shapegroups2.ShapeGroup;

class ShapeGroupTest {

	@Test
	void test() {
		RoundedPolygon myRoundedPolygon = new RoundedPolygon();
		ShapeGroup myShapeGroup = new ShapeGroup(myRoundedPolygon);
		
		assert myShapeGroup.getShape() == myRoundedPolygon;
		
		Extent myExtent = myShapeGroup.getExtent();
		Extent myOriginalExtent = myShapeGroup.getOriginalExtent();
		
		assert myExtent.getLeft() == -100;
		assert myOriginalExtent.getLeft() == -100;
		assert myExtent.getTop() == -100;
		assert myOriginalExtent.getTop() == -100;
		assert myExtent.getRight() == 100;
		assert myOriginalExtent.getRight() == 100;
		assert myExtent.getBottom() == 100;
		assert myOriginalExtent.getBottom() == 100;
		assert myExtent.getWidth() == 200;
		assert myOriginalExtent.getWidth() == 200;
		assert myExtent.getHeight() == 200;
		assert myOriginalExtent.getHeight() == 200;
		
		myShapeGroup.setExtent(myExtent.withLeft(-150));
		
		Extent myExtent2 = myShapeGroup.getExtent();
		Extent myOriginalExtent2 = myShapeGroup.getOriginalExtent();
		
		assert myExtent2.getLeft() == -150;
		assert myOriginalExtent2.getLeft() == -100;
		assert myExtent2.getTop() == -100;
		assert myOriginalExtent2.getTop() == -100;
		assert myExtent2.getRight() == 100;
		assert myOriginalExtent2.getRight() == 100;
		assert myExtent2.getBottom() == 100;
		assert myOriginalExtent2.getBottom() == 100;
		assert myExtent2.getWidth() == 250;
		assert myOriginalExtent2.getWidth() == 200;
		assert myExtent2.getHeight() == 200;
		assert myOriginalExtent2.getHeight() == 200;
		
		RoundedPolygon myRoundedPolygon2 = new RoundedPolygon();
		
		IntPoint myIntPoint = new IntPoint(200, 200);
		IntPoint myIntPoint2 = new IntPoint(400, 200);
		IntPoint myIntPoint3 = new IntPoint(300, 300);
		
		IntPoint[] myIntPoints = {myIntPoint, myIntPoint2, myIntPoint3};
		
		myRoundedPolygon2.setVertices(myIntPoints);
		
		ShapeGroup myShapeGroup2 = new ShapeGroup(myRoundedPolygon2);
		
		ShapeGroup[] myShapeGroups = {myShapeGroup, myShapeGroup2};
		ShapeGroup myShapeGroup3 = new ShapeGroup(myShapeGroups);
		Extent myExtent3 = myShapeGroup3.getExtent();
		
		assert myExtent3.getLeft() == -150;
		assert myExtent3.getTop() == -100;
		assert myExtent3.getBottom() == 300;
		assert myExtent3.getRight() == 400;
		assert myShapeGroup.getParentGroup() == myShapeGroup3;
		assert myShapeGroup3.getSubgroup(0) == myShapeGroup;
		assert myShapeGroup3.getSubgroupCount() == 2;
		assert Arrays.equals((myShapeGroup3.getSubgroups()).toArray(), 0, 2, myShapeGroups, 0, 2);
		assert myShapeGroup3.getSubgroupAt(myIntPoint3).equals(myShapeGroup2);
		assert myShapeGroup3.getSubgroupAt(new IntPoint(0, 0)).equals(myShapeGroup);
		assert myShapeGroup3.getSubgroupAt(new IntPoint(500, 500)) == null;
		
		myShapeGroup.setExtent(Extent.ofLeftTopRightBottom(500, -200, 550, 800));
		
		assert myShapeGroup.toInnerCoordinates(new IntPoint(500, -200)).equals(new IntPoint(-100, -100));
		assert myShapeGroup.toInnerCoordinates(new IntPoint(525, 300)).equals(new IntPoint(0, 0));
		assert myShapeGroup.toGlobalCoordinates(new IntPoint(-100, -100)).equals(new IntPoint(500, -200));
		assert myShapeGroup.toGlobalCoordinates(new IntPoint(0, 0)).equals(new IntPoint(525, 300));
		assert myShapeGroup.toInnerCoordinates(new IntVector(5, 10)).getX() == 20;
		assert myShapeGroup.toInnerCoordinates(new IntVector(5, 10)).getY() == 2;
		
		RoundedPolygon myRoundedPolygon3 = new RoundedPolygon();
		IntPoint[] myIntPoints2 = {new IntPoint(500, 400), new IntPoint(500, 200), new IntPoint(300, 0)};
		myRoundedPolygon3.setVertices(myIntPoints2);
		RoundedPolygon myRoundedPolygon4 = new RoundedPolygon();
		IntPoint[] myIntPoints3 = {new IntPoint(-100, 100), new IntPoint(0, 0), new IntPoint(100, 100)};
		myRoundedPolygon4.setVertices(myIntPoints3);
		RoundedPolygon myRoundedPolygon5 = new RoundedPolygon();
		ShapeGroup myShapeGroup4 = new ShapeGroup(myRoundedPolygon3);
		ShapeGroup myShapeGroup5 = new ShapeGroup(myRoundedPolygon4);
		ShapeGroup myShapeGroup6 = new ShapeGroup(myRoundedPolygon5);
		ShapeGroup[] myShapeGroups2 = {myShapeGroup3, myShapeGroup4, myShapeGroup5, myShapeGroup6};
		ShapeGroup myShapeGroup7 = new ShapeGroup(myShapeGroups2);
		
		myShapeGroup4.sendToBack();
		myShapeGroup4.sendToBack();

		assert Arrays.equals((myShapeGroup7.getSubgroups()).toArray(), 0, 4, new ShapeGroup[] {myShapeGroup3, myShapeGroup5, myShapeGroup6, myShapeGroup4}, 0, 4);
		
		myShapeGroup6.bringToFront();
		myShapeGroup6.bringToFront();
		
		assert Arrays.equals((myShapeGroup7.getSubgroups()).toArray(), 0, 4, new ShapeGroup[] {myShapeGroup6, myShapeGroup3, myShapeGroup5, myShapeGroup4}, 0, 4);
		
		myShapeGroup6.sendToBack();
		
		assert Arrays.equals((myShapeGroup7.getSubgroups()).toArray(), 0, 4, new ShapeGroup[] {myShapeGroup3, myShapeGroup5, myShapeGroup4, myShapeGroup6}, 0, 4);
		
		myShapeGroup6.bringToFront();
		
		assert Arrays.equals((myShapeGroup7.getSubgroups()).toArray(), 0, 4, new ShapeGroup[] {myShapeGroup6, myShapeGroup3, myShapeGroup5, myShapeGroup4}, 0, 4);
		
		
		assertEquals("pushTranslate 0 0" + System.lineSeparator() +
				"pushScale 1.0 1.0" + System.lineSeparator() +
				"pushTranslate 0 0" + System.lineSeparator() +
				"pushScale 1.0 1.0" + System.lineSeparator() +
				"line 300.0 200.0 375.85786437626905 200.0" + System.lineSeparator() +
				"arc 375.85786437626905 210.0 10.0 -1.5707963267948966 2.356194490192345" + System.lineSeparator() +
				"line 382.9289321881345 217.07106781186548 350.0 250.0" + System.lineSeparator() +
				"line 350.0 250.0 307.0710678118655 292.9289321881345" + System.lineSeparator() +
				"arc 300.0 285.85786437626905 10.0 0.7853981633974483 1.5707963267948966" + System.lineSeparator() +
				"line 292.9289321881345 292.9289321881345 250.0 250.0" + System.lineSeparator() +
				"line 250.0 250.0 217.07106781186548 217.07106781186548" + System.lineSeparator() +
				"arc 224.14213562373095 210.0 10.0 -3.9269908169872414 2.356194490192345" + System.lineSeparator() +
				"line 224.14213562373095 200.0 300.0 200.0" + System.lineSeparator() +
				"fill  255 255 0" + System.lineSeparator() +
				"popTransform " + System.lineSeparator() +
				"popTransform " + System.lineSeparator() +
				"pushTranslate 525 300" + System.lineSeparator() +
				"pushScale 0.25 5.0" + System.lineSeparator() +
				"line -100.0 0.0 -100.0 90.0" + System.lineSeparator() +
				"arc -90.0 90.0 10.0 3.141592653589793 -1.5707963267948966" + System.lineSeparator() +
				"line -90.0 100.0 0.0 100.0" + System.lineSeparator() +
				"line 0.0 100.0 90.0 100.0" + System.lineSeparator() +
				"arc 90.0 90.0 10.0 1.5707963267948966 -1.5707963267948966" + System.lineSeparator() +
				"line 100.0 90.0 100.0 0.0" + System.lineSeparator() +
				"line 100.0 0.0 100.0 -90.0" + System.lineSeparator() +
				"arc 90.0 -90.0 10.0 0.0 -1.5707963267948966" + System.lineSeparator() +
				"line 90.0 -100.0 0.0 -100.0" + System.lineSeparator() +
				"line 0.0 -100.0 -90.0 -100.0" + System.lineSeparator() +
				"arc -90.0 -90.0 10.0 -1.5707963267948966 -1.5707963267948966" + System.lineSeparator() +
				"line -100.0 -90.0 -100.0 0.0" + System.lineSeparator() +
				"fill  255 255 0" + System.lineSeparator() +
				"popTransform " + System.lineSeparator() +
				"popTransform " + System.lineSeparator() +
				"popTransform " + System.lineSeparator() +
				"popTransform " + System.lineSeparator(), myShapeGroup3.getDrawingCommands());
	}

}

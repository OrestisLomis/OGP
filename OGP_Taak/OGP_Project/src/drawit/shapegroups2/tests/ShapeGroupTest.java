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
		assert Arrays.equals(myShapeGroup3.getSubgroups(), 0, 2, myShapeGroups, 0, 2);
		assert myShapeGroup3.getSubGroupAt(myIntPoint3).equals(myShapeGroup2);
		assert myShapeGroup3.getSubGroupAt(new IntPoint(0, 0)).equals(myShapeGroup);
		assert myShapeGroup3.getSubGroupAt(new IntPoint(500, 500)) == null;
		
		myShapeGroup.setExtent(Extent.ofLeftTopRightBottom(500, -200, 550, 800));
		
		assert myShapeGroup.getHorizontalScale() == 0.25;
		assert myShapeGroup.getVerticalScale() == 5;
		assert myShapeGroup.getHorizontalTranslate() == 600;
		assert myShapeGroup.getVerticalTranslate() == -100;
		assert myShapeGroup.toInnerCoordinates(new IntPoint(500, -200)).equals(new IntPoint(-100, -100));
		assert myShapeGroup.toInnerCoordinates(new IntPoint(525, 300)).equals(new IntPoint(0, 0));
		assert myShapeGroup.toGlobalCoordinates(new IntPoint(-100, -100)).equals(new IntPoint(500, -200));
		assert myShapeGroup.toGlobalCoordinates(new IntPoint(0, 0)).equals(new IntPoint(525, 300));
		assert myShapeGroup.toInnerCoordinates(new IntVector(5, 10)).getX() == 20;
		assert myShapeGroup.toInnerCoordinates(new IntVector(5, 10)).getY() == 2;
	}

}

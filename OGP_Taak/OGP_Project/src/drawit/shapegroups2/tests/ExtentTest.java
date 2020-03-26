package drawit.shapegroups2.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.shapegroups2.Extent;

class ExtentTest {

	@Test
	void test() {
		Extent myExtent = Extent.ofLeftTopRightBottom(0, 0, 5, 5);
		
		assert myExtent.getLeft() == 0;
		assert myExtent.getTop() == 0;
		assert myExtent.getRight() == 5;
		assert myExtent.getBottom() == 5;
		assert myExtent.getHeight() == 5;
		assert myExtent.getWidth() == 5;
		
		Extent myExtent2 = Extent.ofLeftTopWidhtHeight(0, 0, 5, 5);
		
		assert myExtent2.getLeft() == 0;
		assert myExtent2.getTop() == 0;
		assert myExtent2.getRight() == 5;
		assert myExtent2.getBottom() == 5;
		assert myExtent2.getHeight() == 5;
		assert myExtent2.getWidth() == 5;
		
		IntPoint origin = new IntPoint(0, 0);
		IntPoint myIntPoint = new IntPoint(5, 5);
		IntPoint myIntPoint2 = new IntPoint(-2, 0);
		IntPoint myIntPoint3 = new IntPoint(2, 0);
		IntPoint myIntPoint4 = new IntPoint(2, 2);
		
		assert myExtent.getTopLeft().equals(origin);
		assert myExtent.getBottomRight().equals(myIntPoint);
		
		assert myExtent.contains(origin);
		assert myExtent.contains(myIntPoint);
		assert !(myExtent.contains(myIntPoint2));
		assert myExtent.contains(myIntPoint3);
		assert myExtent.contains(myIntPoint4);
		
		Extent myExtent3 = myExtent.withLeft(-5);
		
		assert myExtent3.getLeft() == -5;
		assert myExtent3.getRight() == 5;
		assert myExtent3.getWidth() == 10;
		
		Extent myExtent4 = myExtent.withRight(10);
		
		assert myExtent4.getLeft() == 0;
		assert myExtent4.getRight() == 10;
		assert myExtent4.getWidth() == 10;
		
		Extent myExtent5 = myExtent.withTop(-5);
		
		assert myExtent5.getTop() == -5;
		assert myExtent5.getBottom() == 5;
		assert myExtent5.getHeight() == 10;
		
		Extent myExtent6 = myExtent.withBottom(10);
		
		assert myExtent6.getBottom() == 10;
		assert myExtent6.getTop() == 0;
		assert myExtent6.getHeight() == 10;
		
		Extent myExtent7 = myExtent.withWidth(10);
		
		assert myExtent7.getLeft() == 0;
		assert myExtent7.getRight() == 10;
		assert myExtent7.getWidth() == 10;
		
		Extent myExtent8 = myExtent.withHeight(10);
		
		assert myExtent8.getTop() == 0;
		assert myExtent8.getBottom() == 10;
		assert myExtent8.getHeight() == 10;
		
	}

}

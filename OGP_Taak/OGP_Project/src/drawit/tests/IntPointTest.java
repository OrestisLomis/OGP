package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;

class IntPointTest {

	@Test
	void test() {
		IntPoint myIntPoint = new IntPoint(3, 4);
		assert myIntPoint.getX() == 3;
		assert myIntPoint.getY() == 4;
		
		IntPoint myIntPoint2 = new IntPoint(2, 5);
		assert myIntPoint2.getX() == 2;
		assert myIntPoint2.getY() == 5;
		assert !(myIntPoint.equals(myIntPoint2));
		
		IntPoint myIntPoint3 = new IntPoint(3, 4);
		assert myIntPoint.equals(myIntPoint3);
		
		IntPoint origin = new IntPoint(0, 0);
		assert !(myIntPoint.isOnLineSegment(origin, myIntPoint2));
		
		assert !(myIntPoint.isOnLineSegment(myIntPoint, myIntPoint2));
		assert !(myIntPoint.isOnLineSegment(myIntPoint2, myIntPoint));
		
		IntPoint myIntPointExtended = new IntPoint(6, 8);
		assert myIntPoint.isOnLineSegment(origin, myIntPointExtended);
		
		assert myIntPoint.minus(myIntPoint2).getX() == 1;
		assert myIntPoint.minus(myIntPoint2).getY() == -1;
		assert myIntPoint.minus(origin).getX() == 3;
		assert myIntPoint.minus(origin).getY() == 4;
		
		IntVector myIntVector = new IntVector(2, 2);
		assert myIntPoint.plus(myIntVector).getX() == 5;
		assert myIntPoint.plus(myIntVector).getY() == 6;
		
		assert myIntPoint.asDoublePoint().getX() == 3;
		assert myIntPoint.asDoublePoint().getY() == 4;
		
		assert !(IntPoint.lineSegmentsIntersect(origin, myIntPoint, origin, myIntPoint2));
		
		IntPoint myIntPointA = new IntPoint(-1, 0);
		IntPoint myIntPointB = new IntPoint(1, 0);
		IntPoint myIntPointC = new IntPoint(0, -1);
		IntPoint myIntPointD = new IntPoint(0, 1);
		assert IntPoint.lineSegmentsIntersect(myIntPointA, myIntPointB, myIntPointC, myIntPointD);
		assert !(IntPoint.lineSegmentsIntersect(myIntPointA, myIntPointC, myIntPointB, myIntPointD));
		
	}

}

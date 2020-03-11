package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.PointArrays;

class PointArraysTest {

	@Test
	void test() {
		IntPoint myIntPoint = new IntPoint(-1, -1);
		IntPoint myIntPoint2 = new IntPoint(-1, 1);
		IntPoint myIntPoint3 = new IntPoint(1, 1);
		IntPoint myIntPoint4 = new IntPoint(1, -1);
		
		IntPoint[] myIntPoints = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint4};
		assert PointArrays.checkDefinesProperPolygon(myIntPoints) == null;
		
		IntPoint[] myIntPoints2 = {myIntPoint, myIntPoint3, myIntPoint2, myIntPoint4};
		assert PointArrays.checkDefinesProperPolygon(myIntPoints2).equals("The edge formed by points 1 and 2 intersects the edge formed by points 3 and 4.");
		
		
		IntPoint[] myIntPoints3 = {myIntPoint, myIntPoint, myIntPoint2, myIntPoint3};
		assert PointArrays.checkDefinesProperPolygon(myIntPoints3).equals("Vertex 1 and 2 coincide.");
		
		IntPoint myIntPoint5 = new IntPoint(-1, 0);
		IntPoint[] myIntPoints4 = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint5, myIntPoint4};
		assert PointArrays.checkDefinesProperPolygon(myIntPoints4).equals("Vertex 4 is the edge formed by points 1 and 2.");
		
		IntPoint[] myIntPoints5 = {myIntPoint2, myIntPoint3, myIntPoint5, myIntPoint4, myIntPoint};
		assert PointArrays.checkDefinesProperPolygon(myIntPoints5).equals("Vertex 3 is the edge formed by points 5 and 1.");
		
		IntPoint myIntPoint6 = new IntPoint(-2, 0);
		IntPoint[] myIntPoints6 = {myIntPoint2, myIntPoint3, myIntPoint6, myIntPoint4, myIntPoint};
		assert PointArrays.checkDefinesProperPolygon(myIntPoints6).equals("The edge formed by points 2 and 3 intersects the edge formed by points 5 and 1.");
		
		IntPoint[] myIntPoints7 = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint};
		assert PointArrays.checkDefinesProperPolygon(myIntPoints7).equals("Vertex 1 and 4 coincide.");
		
		IntPoint[] myIntPoints8 = {myIntPoint, myIntPoint2};
		assert PointArrays.checkDefinesProperPolygon(myIntPoints8).equals("A proper polygon is defined by at least 3 points.");
		
		IntPoint[] myCopy = PointArrays.copy(myIntPoints);
		for (int i = 0; i < myIntPoints.length; i++) {
			assert myCopy[i] == myIntPoints[i];
		}
			
		IntPoint[] myInsertion = PointArrays.insert(myIntPoints, 3, myIntPoint5);
		for (int i = 0; i < myInsertion.length; i++) {
			assert myInsertion[i] == myIntPoints4[i];
		}
		
		IntPoint[] myRemoval = PointArrays.remove(myIntPoints4, 3);
		for (int i = 0; i < myRemoval.length; i++) {
			assert myRemoval[i] == myIntPoints[i];
		}
		
		IntPoint[] myUpdate = PointArrays.update(myIntPoints, 3, myIntPoint);
		for (int i = 0; i < myUpdate.length; i++) {
			assert myUpdate[i] == myIntPoints7[i];
		}

	}
}

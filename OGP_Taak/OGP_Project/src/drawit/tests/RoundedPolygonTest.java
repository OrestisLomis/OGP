package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;

class RoundedPolygonTest {

	@Test
	void test() {
		RoundedPolygon myRoundedPolygon = new RoundedPolygon();
		IntPoint myIntPoint = new IntPoint(-100, -100);
		IntPoint myIntPoint2 = new IntPoint(-100, 100);
		IntPoint myIntPoint3 = new IntPoint(100, 100);
		IntPoint myIntPoint4 = new IntPoint(100, -100);
		
		IntPoint[] myIntPoints = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint4};
		IntPoint[] myIntPoints2 = {myIntPoint, myIntPoint2};
		
		myRoundedPolygon.setVertices(myIntPoints);
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.setVertices(myIntPoints2));
		
		assert myRoundedPolygon.getRadius() == 10;
		assert myRoundedPolygon.getColor() == Color.yellow;
		
		myRoundedPolygon.setColor(Color.red);
		assert myRoundedPolygon.getColor() == Color.red;
		
		myRoundedPolygon.setRadius(20);
		assert myRoundedPolygon.getRadius() == 20;
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.setRadius(-20));
		
		IntPoint myIntPoint5 = new IntPoint(0, -100);
		IntPoint[] myIntPoints3 = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint4, myIntPoint5};
		
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.insert(-2, myIntPoint5));
		myRoundedPolygon.insert(4, myIntPoint5);
		for (int i = 0; i < myIntPoints3.length; i++) {
			assert myRoundedPolygon.getVertices()[i] == myIntPoints3[i];
		}
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.insert(0, myIntPoint5));
		
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.remove(-2));
		myRoundedPolygon.remove(4);
		for (int i = 0; i < myIntPoints.length; i++) {
			assert myRoundedPolygon.getVertices()[i] == myIntPoints[i];
		}
		
		IntPoint[] myIntPoints4 = {myIntPoint, myIntPoint2, myIntPoint3};
		RoundedPolygon myRoundedPolygon2 = new RoundedPolygon();
		myRoundedPolygon2.setVertices(myIntPoints4);
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon2.remove(0));
		
		IntPoint myIntPoint6 = new IntPoint(200, 200);
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon2.update(-2, myIntPoint6));
		assert !(myRoundedPolygon.contains(myIntPoint6));
		IntPoint[] myIntPoints5 = {myIntPoint, myIntPoint2, myIntPoint6, myIntPoint4};
		myRoundedPolygon.update(2, myIntPoint6);
		for (int i = 0; i < myIntPoints5.length ; i++) {
			assert myRoundedPolygon.getVertices()[i] == myIntPoints5[i];
		}
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.update(0, myIntPoint6));
		
		IntPoint origin = new IntPoint(0, 0);
		assert myRoundedPolygon.contains(origin);
		
		assert myRoundedPolygon.contains(myIntPoint5);
		assert myRoundedPolygon.contains(myIntPoint);
		
		
		IntPoint myIntPoint7 = new IntPoint(-200, -100);
		IntPoint myIntPoint8 = new IntPoint(-200, 100);
		IntPoint myIntPoint9 = new IntPoint(-200, 150);
		
		assert !(myRoundedPolygon.contains(myIntPoint7));
		assert !(myRoundedPolygon.contains(myIntPoint8));
		assert !(myRoundedPolygon.contains(myIntPoint9));
		
		
		IntPoint myIntPoint10 = new IntPoint(-10, -10);
		IntPoint myIntPoint11 = new IntPoint(0, -10);
		IntPoint myIntPoint12 = new IntPoint(10, -10);
		IntPoint myIntPoint13 = new IntPoint(20, 20);
		IntPoint myIntPoint14 = new IntPoint(0, 0);
		IntPoint myIntPoint15 = new IntPoint(-10, 20);
		IntPoint myIntPoint16 = new IntPoint(-20, 20);
		IntPoint myIntPoint17 = new IntPoint(0, 10);
		IntPoint[] myIntPoints6 = {myIntPoint16, myIntPoint10, myIntPoint11, myIntPoint12, myIntPoint13, myIntPoint14, myIntPoint15};
		RoundedPolygon myRoundedPolygon3 = new RoundedPolygon();
		
		assert myRoundedPolygon3.getExtent().getLeft() == -100;
		assert myRoundedPolygon3.getExtent().getRight() == 100;
		assert myRoundedPolygon3.getExtent().getTop() == -100;
		assert myRoundedPolygon3.getExtent().getBottom() == 100;
		
		myRoundedPolygon3.setVertices(myIntPoints6);
		
		assert !(myRoundedPolygon3.contains(myIntPoint17));
		assert myRoundedPolygon3.getExtent().getLeft() == -20;
		assert myRoundedPolygon3.getExtent().getRight() == 20;
		assert myRoundedPolygon3.getExtent().getTop() == -10;
		assert myRoundedPolygon3.getExtent().getBottom() == 20;
		
		/*assertEquals("line -100.0 0.0 -100.0 85.58481559887747" + System.lineSeparator() +
				"arc -80.0 85.58481559887747 20.0 3.141592653589793 -1.2490457723982544" + System.lineSeparator() +
				"line -86.32455532033676 104.55848155988775 50.0 150.0" + System.lineSeparator() +
				"line 50.0 150.0 162.05266807797946 187.3508893593265" + System.lineSeparator() +
				"arc 168.3772233983162 168.3772233983162 20.0 1.8925468811915387 -2.214297435588181" + System.lineSeparator() +
				"line 187.3508893593265 162.05266807797946 150.0 50.0" + System.lineSeparator() +
				"line 150.0 50.0 104.55848155988774 -86.32455532033677" + System.lineSeparator() +
				"arc 85.58481559887747 -80.0 20.0 -0.32175055439664213 -1.2490457723982544" + System.lineSeparator() +
				"line 85.58481559887747 -100.0 0.0 -100.0" + System.lineSeparator() +
				"line 0.0 -100.0 -80.0 -100.0" + System.lineSeparator() +
				"arc -80.0 -80.0 20.0 -1.5707963267948966 -1.5707963267948966" + System.lineSeparator() + 
				"line -100.0 -80.0 -100.0 0.0" + System.lineSeparator(), myRoundedPolygon.getDrawingCommands());
		
		assertEquals("line -15.0 5.0 -11.581138830084189 -5.256583509747431" + System.lineSeparator() +
				"arc -4.999999999999999 -3.0628705663860343 6.937129433613966 -2.819842099193151 1.2490457723982544" + System.lineSeparator() +
				"line -5.0 -10.0 -5.0 -10.0" + System.lineSeparator() +
				"line -5.0 -10.0 0.0 -10.0" + System.lineSeparator() +
				"line 0.0 -10.0 5.0 -10.0" + System.lineSeparator() +
				"line 5.0 -10.0 5.0 -10.0" + System.lineSeparator() +
				"arc 5.0 -3.062870566386035 6.937129433613965 -1.5707963267948966 1.2490457723982544" + System.lineSeparator() +
				"line 11.581138830084189 -5.256583509747431 15.0 5.0" + System.lineSeparator() +
				"line 15.0 5.0 15.52786404500042 6.583592135001261" + System.lineSeparator() +
				"arc 12.360679774997896 7.6393202250021 3.3385053542218923 -0.32175055439664213 2.677945044588987" + System.lineSeparator() +
				"line 10.0 10.0 10.0 10.0" + System.lineSeparator() +
				"line 10.0 10.0 7.905694150420948 7.905694150420948" + System.lineSeparator() +
				"arc 2.2075922005612645 13.603796100280634 8.058333057276144 -0.7853981633974483 -1.8925468811915387" + System.lineSeparator() +
				"line -5.0 10.0 -5.0 10.0" + System.lineSeparator() +
				"line -5.0 10.0 -7.76393202250021 15.52786404500042" + System.lineSeparator() +
				"arc -15.0 11.909830056250525 8.090169943749475 0.46364760900080615 1.1071487177940904" + System.lineSeparator() +
				"line -15.0 20.0 -15.0 20.0" + System.lineSeparator() +
				"line -15.0 20.0 -15.0 20.0" + System.lineSeparator() +
				"arc -15.0 16.396203899719367 3.6037961002806314 -4.71238898038469 1.8925468811915387" + System.lineSeparator() +
				"line -18.41886116991581 15.256583509747431 -15.0 5.0" + System.lineSeparator(), myRoundedPolygon3.getDrawingCommands());
		*/
	}

}

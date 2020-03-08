package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoundedPolygonTest {

	@Test
	void test() {
		RoundedPolygon myRoundedPolygon = new RoundedPolygon();
		IntPoint myIntPoint = new IntPoint(-100, -100);
		IntPoint myIntPoint2 = new IntPoint(-100, 100);
		IntPoint myIntPoint3 = new IntPoint(100, 100);
		IntPoint myIntPoint4 = new IntPoint(100, -100);
		
		IntPoint[] myIntPoints = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint4};
		
		myRoundedPolygon.setVertices(myIntPoints);
		
		assert myRoundedPolygon.getRadius() == 10;
		assertEquals(myRoundedPolygon.getVertices(), myIntPoints);
		
		myRoundedPolygon.setRadius(20);
		assert myRoundedPolygon.getRadius() == 20;
		
		IntPoint myIntPoint5 = new IntPoint(0, -100);
		IntPoint[] myIntPoints2 = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint4, myIntPoint5};
		
		myRoundedPolygon.insert(4, myIntPoint5);
		for (int i = 0; i < myIntPoints2.length; i++) {
			assert myRoundedPolygon.getVertices()[i] == myIntPoints2[i];
		}
		
		myRoundedPolygon.remove(4);
		for (int i = 0; i < myIntPoints.length; i++) {
			assert myRoundedPolygon.getVertices()[i] == myIntPoints[i];
		}
		
		IntPoint myIntPoint6 = new IntPoint(200, 200);
		 assert !(myRoundedPolygon.contains(myIntPoint6));
		IntPoint[] myIntPoints3 = {myIntPoint, myIntPoint2, myIntPoint6, myIntPoint4};
		myRoundedPolygon.update(2, myIntPoint6);
		for (int i = 0; i < myIntPoints3.length ; i++) {
			assert myRoundedPolygon.getVertices()[i] == myIntPoints3[i];
		}
		
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
		
		assertEquals("line -100.0 0.0 -100.0 85.58481559887747\r\n" + 
				"arc -80.0 85.58481559887747 20.0 3.141592653589793 -1.2490457723982544\r\n" + 
				"line -86.32455532033676 104.55848155988775 50.0 150.0\r\n" + 
				"line 50.0 150.0 162.05266807797946 187.3508893593265\r\n" + 
				"arc 168.3772233983162 168.3772233983162 20.0 1.8925468811915387 -2.214297435588181\r\n" + 
				"line 187.3508893593265 162.05266807797946 150.0 50.0\r\n" + 
				"line 150.0 50.0 104.55848155988774 -86.32455532033677\r\n" + 
				"arc 85.58481559887747 -80.0 20.0 -0.32175055439664213 -1.2490457723982544\r\n" + 
				"line 85.58481559887747 -100.0 0.0 -100.0\r\n" + 
				"line 0.0 -100.0 -80.0 -100.0\r\n" + 
				"arc -80.0 -80.0 20.0 4.71238898038469 -1.5707963267948966\r\n" + 
				"line -100.0 -80.0 -100.0 0.0\r\n" + 
				"", myRoundedPolygon.getDrawingCommands());
	}

}


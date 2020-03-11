package drawit.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoublePointTest {

	@Test
	void test() {
		DoublePoint myDoublePoint = new DoublePoint(3.4, 4.1);
		assert myDoublePoint.getX() == 3.4;
		assert myDoublePoint.getY() == 4.1;
		
		DoublePoint myDoublePoint2 = new DoublePoint(2, 5);
		assert myDoublePoint2.getX() == 2;
		assert myDoublePoint2.getY() == 5;
		
		assertEquals(1.4, myDoublePoint.minus(myDoublePoint2).getX(), 0.0005);
		assertEquals(-0.9, myDoublePoint.minus(myDoublePoint2).getY(), 0.0005);
		
		DoubleVector myDoubleVector = new DoubleVector(2, 2);
		assertEquals(5.4, myDoublePoint.plus(myDoubleVector).getX(), 0.0005);
		assertEquals(6.1, myDoublePoint.plus(myDoubleVector).getY(), 0.0005);
		
		assert myDoublePoint.round().getX() == 3;
		assert myDoublePoint.round().getY() == 4;
		
		DoublePoint myDoublePoint3 = new DoublePoint(6.7, -8.9);
		assert myDoublePoint3.round().getX() == 7;
		assert myDoublePoint3.round().getY() == -9;
		
		DoublePoint myDoublePoint4 = new  DoublePoint(-8.9, 6.7);
		assert myDoublePoint4.round().getX() == -9;
		assert myDoublePoint4.round().getY() == 7;
		
	}

}

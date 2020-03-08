package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoubleVectorTest {

	@Test
	void test() {
		DoubleVector myDoubleVector = new DoubleVector(3.2, 1.7);
		assert myDoubleVector.getX() == 3.2;
		assert myDoubleVector.getY() == 1.7;
		
		DoubleVector myDoubleVectorScaled = myDoubleVector.scale(3.6);
		assert myDoubleVectorScaled.getX() == 3.2 * 3.6;
		assert myDoubleVectorScaled.getY() == 1.7 * 3.6;
		
		assertEquals(14.72, myDoubleVector.plus(myDoubleVectorScaled).getX(), 0.0005);
		assertEquals(7.82, myDoubleVector.plus(myDoubleVectorScaled).getY(), 0.0005);
		
		assertEquals(13.13, myDoubleVector.dotProduct(myDoubleVector), 0.0005);
		
		assertEquals(Math.sqrt(13.13), myDoubleVector.getSize(), 0.0005);
		
		DoubleVector myDoubleVector2 = new DoubleVector(-3.2, -1.7);
		assertEquals(Math.sqrt(13.13), myDoubleVector2.getSize(), 0.0005);
		
		assertEquals(0, myDoubleVector.crossProduct(myDoubleVector), 0.0005);
		
		DoubleVector myDoubleVector3 = new DoubleVector(2.1, 3.4);
		assertEquals(7.31, myDoubleVector.crossProduct(myDoubleVector3), 0.0005);
		
		assertEquals(Math.atan2(1.7, 3.2), myDoubleVector.asAngle(), 0.0005);
		assertEquals(Math.atan2(-1.7, -3.2), myDoubleVector2.asAngle(), 0.0005);
		System.out.println(myDoubleVector2.asAngle());
		
		
	}

}

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
		
		assertEquals(Math.sqrt(3.2 * 3.2 + 1.7 * 1.7), myDoubleVector.getSize(), 0.0005);
		
		DoubleVector myDoubleVector2 = new DoubleVector(-3.2, -1.7);
		assertEquals(Math.sqrt(3.2 * 3.2 + 1.7 * 1.7), myDoubleVector2.getSize(), 0.0005);
		
		
	}

}

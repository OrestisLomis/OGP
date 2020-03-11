package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IntVectorTest {

	@Test
	void test() {
		IntVector myIntVector = new IntVector(2, 1);
		assert myIntVector.getX() == 2;
		assert myIntVector.getY() == 1;
		
		assert myIntVector.crossProduct(myIntVector) == 0;
		
		IntVector myIntVector2 = new IntVector(4, 1);
		assert myIntVector.crossProduct(myIntVector2) == -2;
		assert myIntVector.dotProduct(myIntVector2) == 9;
		
		assert myIntVector.isCollinearWith(myIntVector);
		assert !(myIntVector.isCollinearWith(myIntVector2));
		
		IntVector myIntVector3 = new IntVector(4, 2);
		assert myIntVector.isCollinearWith(myIntVector3);
		
		assert myIntVector.asDoubleVector().getX() == 2.0;
		assert myIntVector.asDoubleVector().getY() == 1.0;
	}

}

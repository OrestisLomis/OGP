package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoundedPolygonTest {

	@Test
	void test() {
		RoundedPolygon myRoundedPolygon = new RoundedPolygon();
		
		
		
		// assert myRoundedPolygon.getVertices() == myIntPoints;
		assert myRoundedPolygon.getRadius() == 10;
		
		System.out.println(myRoundedPolygon.getDrawingCommands());
	}

}


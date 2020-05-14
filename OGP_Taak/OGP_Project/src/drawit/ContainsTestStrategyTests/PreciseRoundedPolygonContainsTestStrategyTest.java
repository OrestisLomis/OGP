package drawit.ContainsTestStrategyTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.PreciseRoundedPolygonContainsTestStrategy;
import drawit.RoundedPolygon;

class PreciseRoundedPolygonContainsTestStrategyTest {

	@Test
	void test() {
		RoundedPolygon mypolygon = new RoundedPolygon();
		
		IntPoint point1 = new IntPoint(200,200);
		IntPoint point2 = new IntPoint(100, -100);
		IntPoint point3 = new IntPoint(-100,100);
		IntPoint point4 = new IntPoint(-50,-50);
		
		IntPoint[] points = new IntPoint[] {point1, point2, point4, point3};
		
		mypolygon.setVertices(points);
		
		PreciseRoundedPolygonContainsTestStrategy myPreciseRoundedPolygonContainsTestStrategy = new PreciseRoundedPolygonContainsTestStrategy();
		
		assert myPreciseRoundedPolygonContainsTestStrategy.contains(mypolygon, point1);
		assert myPreciseRoundedPolygonContainsTestStrategy.contains(mypolygon, new IntPoint(0,0));
		assert !(myPreciseRoundedPolygonContainsTestStrategy.contains(mypolygon, new IntPoint(150,0)));
		assert !(myPreciseRoundedPolygonContainsTestStrategy.contains(mypolygon, new IntPoint(201, 100)));
	}

}

package drawit.shapegroups1.exporter.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.ShapeGroup;
import drawit.shapegroups1.exporter.ShapeGroupExporter;
import drawit.shapegroups1.NonleafShapeGroup;

class ShapeGroupExporterTest {

	@Test
	void test() {
		RoundedPolygon myRoundedPolygon = new RoundedPolygon();
		RoundedPolygon myRoundedPolygon2 = new RoundedPolygon();
		myRoundedPolygon2.setColor(Color.red);
		myRoundedPolygon2.setRadius(20);
		myRoundedPolygon2.setVertices(new IntPoint[] {new IntPoint(0, 0), new IntPoint(0, 20), new IntPoint(20, 0)});
			
		LeafShapeGroup myLeafShapeGroup = new LeafShapeGroup(myRoundedPolygon);
		LeafShapeGroup myLeafShapeGroup2 = new LeafShapeGroup(myRoundedPolygon2);
		NonleafShapeGroup myNonleafShapeGroup = new NonleafShapeGroup(new ShapeGroup[] {myLeafShapeGroup, myLeafShapeGroup2});
		
		Object result = ShapeGroupExporter.toPlainData(myNonleafShapeGroup);
		Object expectedResult = Map.of(
				"originalExtent", Map.of("left", -100, "top", -100, "right", 100, "bottom", 100),
				"extent", Map.of("left", -100, "top", -100, "right", 100, "bottom", 100),
				"subgroups", List.of(
						Map.of(
								"originalExtent", Map.of("left", -100, "top", -100, "right", 100, "bottom", 100),
								"extent", Map.of("left", -100, "top", -100, "right", 100, "bottom", 100),
								"shape", Map.of(
										"vertices", List.of(
												Map.of("x", -100, "y", -100),
												Map.of("x", -100, "y", 100),
												Map.of("x", 100, "y", 100),
												Map.of("x", 100, "y", -100)),
										"radius", 10,
										"color", Map.of("red", 255, "green", 255, "blue", 0))),
						Map.of(
								"originalExtent", Map.of("left", 0, "top", 0, "right", 20, "bottom", 20),
								"extent", Map.of("left", 0, "top", 0, "right", 20, "bottom", 20),
								"shape", Map.of(
										"vertices", List.of(
												Map.of("x", 0, "y", 0),
												Map.of("x", 0, "y", 20),
												Map.of("x", 20, "y", 0)),
										"radius", 20,
										"color", Map.of("red", 255, "green", 0, "blue", 0)))));
		
		assertEquals(expectedResult, result);
	}

}

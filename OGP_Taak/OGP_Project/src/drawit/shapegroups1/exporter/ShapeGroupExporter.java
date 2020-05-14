package drawit.shapegroups1.exporter;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.lang.Integer;

public class ShapeGroupExporter {
	

	public static Object toPlainData(ShapeGroup shapeGroup) {
		Extent original = shapeGroup.getOriginalExtent();
		Extent extent = shapeGroup.getExtent();
		Object result = null;
		if (shapeGroup instanceof LeafShapeGroup) {
			LeafShapeGroup lsg = (LeafShapeGroup) shapeGroup;
			RoundedPolygon shape = lsg.getShape();
			IntPoint[] vertices = shape.getVertices();

			List<Map<String, Integer>> vertexCoords = new ArrayList<Map<String, Integer>>();
			Arrays.stream(vertices).forEach(vertex -> vertexCoords.add(Map.of("x", vertex.getX(), "y", vertex.getY())));
			
			// this stream could be done in parallel to make it faster, but the results are unpredictable and make the tests fail

			Color color = shape.getColor();
			result = Map.of("originalExtent",
					Map.of("left", original.getLeft(), "top", original.getTop(), "right", original.getRight(), "bottom",
							original.getBottom()),
					"extent",
					Map.of("left", extent.getLeft(), "top", extent.getTop(), "right", extent.getRight(), "bottom",
							extent.getBottom()),
					"shape", Map.of("vertices", vertexCoords, "radius", shape.getRadius(), "color",
							Map.of("red", color.getRed(), "green", color.getGreen(), "blue", color.getBlue())));
		}

		else {
			NonleafShapeGroup nlsg = (NonleafShapeGroup) shapeGroup;
			List<ShapeGroup> subgroups = nlsg.getSubgroups();

			List<Object> subgroupMaps = new ArrayList<Object>();
			subgroups.stream().forEach(subgroup -> subgroupMaps.add(ShapeGroupExporter.toPlainData(subgroup)));
			

			// this stream could be done in parallel to make it faster, but the results are unpredictable and make the tests fail

			result = Map.of("originalExtent",
					Map.of("left", original.getLeft(), "top", original.getTop(), "right", original.getRight(), "bottom",
							original.getBottom()),
					"extent", Map.of("left", extent.getLeft(), "top", extent.getTop(), "right", extent.getRight(),
							"bottom", extent.getBottom()),
					"subgroups", subgroupMaps);
		}

		return result;
	}

}

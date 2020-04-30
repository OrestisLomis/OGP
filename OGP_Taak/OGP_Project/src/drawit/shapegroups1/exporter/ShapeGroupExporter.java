package drawit.shapegroups1.exporter;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.lang.Integer;

public  class ShapeGroupExporter {
	
	public static Object toPlainData(ShapeGroup shapeGroup) {
		Extent original = shapeGroup.getOriginalExtent();
		Extent extent = shapeGroup.getExtent();
		if (shapeGroup instanceof LeafShapeGroup) {
			LeafShapeGroup lsg = (LeafShapeGroup)shapeGroup;
			RoundedPolygon shape = lsg.getShape();
			IntPoint[] vertices = shape.getVertices();
			
			List<Map<String, Integer>> vertexCoords = new ArrayList<Map<String, Integer>>();
			Arrays.stream(vertices).forEach(vertex -> vertexCoords.add(Map.of("x", vertex.getX(), "y", vertex.getY())));
			
			
			Map.of("originalExtent", Map.of("left", original.getLeft(), "top", original.getTop(), "right", original.getRight(), "bottom", original.getBottom()),
				"extent", Map.of("left", extent.getLeft(), "top", extent.getTop(), "right", extent.getRight(), "bottom", extent.getBottom()),
				"shape", Map.of("vertices", vertexCoords));
		}
		
		return shapeGroup;
	}

}

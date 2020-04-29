package drawit.shapegroups2;

import drawit.IntPoint;
import drawit.RoundedPolygon;

public class LeafShapeGroup extends ShapeGroup {
	
	private RoundedPolygon shape;
	
	/**
	 * Initializes this object to represent a leaf shape group that directly contains the given shape.
	 * @throws IllegalArgumentException if there is no shape given.
	 * 		| shape == null
	 * @post This shape groups shape equals the given shape
	 * 		| this.getShape() == shape
	 * @post This shape groups original extent equals the current one.
	 * 		| this.getExtent() == this.getOriginalExtent()
	 */
	public LeafShapeGroup(RoundedPolygon shape) {
		if (shape == null)
			throw new IllegalArgumentException("There is no shape given.");
		
		this.shape = shape;
		
		IntPoint[] vertices = shape.getVertices();
		IntPoint firstVertex = vertices[0];
		
		int mostLeft = firstVertex.getX();
		int mostRight = firstVertex.getX();
		int mostTop = firstVertex.getY();
		int mostBottom = firstVertex.getY();
		
		for (int i = 1; i < vertices.length; i++) {
			int currentX = vertices[i].getX();
			int currentY = vertices[i].getY();
			if (currentX < mostLeft) 
				mostLeft = currentX;
			if (currentX > mostRight) 
				mostRight = currentX;
			if (currentY < mostTop) 
				mostTop = currentY;
			if (currentY > mostBottom) 
				mostBottom = currentY;
		}
		
		this.extent = Extent.ofLeftTopRightBottom(mostLeft, mostTop, mostRight, mostBottom);
		this.originalExtent = extent;
	}
	
	/**
	 * Returns the shape directly contained by this shape group, or null if this is a non-leaf shape group.
	 */
	public RoundedPolygon getShape() {
		return shape;
	}

	@Override
	public String getDrawingCommands() {
		String commands = "";
		commands += "pushTranslate " + getHorizontalTranslate() + " " + getVerticalTranslate() + System.lineSeparator();
		commands += "pushScale " + getHorizontalScale() + " " + getVerticalScale() + System.lineSeparator();
		commands += getShape().getDrawingCommands();
		commands += "popTransform " + System.lineSeparator();
		commands += "popTransform " + System.lineSeparator();
		return commands.toString();
	}

}

package drawit.shapegroups1;

import drawit.RoundedPolygon;
import drawit.IntPoint;

public class LeafShapeGroup extends ShapeGroup {
	
	RoundedPolygon shape;
	
	/**
	 * Initializes this object to represent a leaf shape group that directly contains the given shape.
	 * @throws IllegalArgumentException if there is no shape given.
	 * 		| shape == null
	 * @post This shape groups shape equals the given shape
	 * 		| this.getShape() == shape
	 */
	public LeafShapeGroup(RoundedPolygon shape) {
		if (shape == null)
			throw new IllegalArgumentException("There is no shape given.");
		
		this.shape = shape;
		IntPoint[] vertices = shape.getVertices();
		IntPoint start = vertices[0];
		
		int left = start.getX();
		int right = start.getX();
		int top = start.getY();
		int bottom = start.getY();
		
		for (int i = 1; i < vertices.length; i++) {
			int currentX = vertices[i].getX();
			int currentY = vertices[i].getY();
			if (currentX < left)
				left = currentX;
			if (currentX > right)
				right = currentX;
			if (currentY < top)
				top = currentY;
			if (currentY > bottom)
				bottom = currentY;
		}
		
		this.extent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		this.originalExtent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		
	}
	
	/**
	 * Returns the shape directly contained by this shape group, or null if this is a non-leaf shape group.
	 */
	public RoundedPolygon getShape() {
		return shape;
	}
	
	@Override
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing the shapes contained directly or indirectly by this shape 
	 * group, expressed in this shape group's outer coordinate system. For the syntax of the drawing commands, see 
	 * RoundedPolygon.getDrawingCommands().
	 */
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

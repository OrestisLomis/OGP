package drawit.shapes1;

public interface Shape {

	boolean contains(drawit.IntPoint p);

	ControlPoint[] createControlPoints();

	String getDrawingCommands();

	drawit.shapegroups1.ShapeGroup getParent();

	drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p);

	drawit.IntPoint toShapeCoordinates(drawit.IntPoint p);

	drawit.IntVector toShapeCoordinates(drawit.IntVector v);

}
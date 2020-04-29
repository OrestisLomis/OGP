package drawit.shapes2;

public interface Shape {
	
	boolean contains(drawit.IntPoint p);
	
	ControlPoint[] createControlPoints();
	
	String getDrawingCommands();
	
	drawit.shapegroups2.ShapeGroup getParent();
	
	drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p);
	
	drawit.IntPoint toShapeCoordinates(drawit.IntPoint p);

}

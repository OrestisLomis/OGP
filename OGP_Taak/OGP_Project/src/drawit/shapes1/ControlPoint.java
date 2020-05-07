package drawit.shapes1;

public interface ControlPoint {
	
	drawit.IntPoint getLocation();
	
	void move(drawit.IntVector delta);
	
	void remove();

}
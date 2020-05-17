package drawit.shapes2;

public interface ControlPoint {

	drawit.IntPoint getLocation();

	void move(drawit.IntVector delta);

	void remove();

}

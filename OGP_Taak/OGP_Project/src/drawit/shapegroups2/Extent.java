package drawit.shapegroups2;

import drawit.IntPoint;

/**
	 * Each instance of this class represents a nonempty rectangular area in a 2D coordinate system, 
	 * whose edges are parallel to the coordinate axes. This class must deal with illegal arguments defensively.
	 */
public class Extent {
	
	private int left;
	private int top;
	private int width;
	private int height;

	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getRight() {
		return getLeft() + getWidth();
	}
	
	public int getBottom() {
		return getTop() - getHeight();
	}
	
	public IntPoint getTopLeft() {
		return new IntPoint(getLeft(), getTop());
	}
	
	public IntPoint getBottomRight() {
		return new IntPoint(getRight(), getBottom());
	}

	/**
	 * Returns whether this extent, considered as a closed set of points (i.e. including its edges and its vertices), contains the given point.
	 */
	public boolean contains(IntPoint point) {
		return point.getX() >= getLeft() && point.getX() <= getRight() && point.getY() <= getTop() && point.getY() >= getBottom();
	}
	
	public static Extent ofLeftTopWidhtHeight(int left, int top, int width, int height) {
		Extent myExtent = new Extent();
		myExtent.left = left;
		myExtent.top = top;
		myExtent.width = width;
		myExtent.height = height;
		return myExtent;
	}
	
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		Extent myExtent = new Extent();
		myExtent.left = left;
		myExtent.top = top;
		myExtent.width = right - left;
		myExtent.height = top - bottom;
		return myExtent;
	}
	
	public Extent withLeft(int newLeft) {
		this.left = newLeft;
		return this;
	}

}

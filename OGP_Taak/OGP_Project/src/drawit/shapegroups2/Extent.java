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
	
	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the smallest X coordinate.
	 */
	public int getLeft() {
		return left;
	}
	
	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the smallest Y coordinate.
	 */
	public int getTop() {
		return top;
	}

	/**
	 * Returns the distance between the edges that are parallel to the Y axis.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the distance between the edges that are parallel to the X axis.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the largest X coordinate.
	 */
	public int getRight() {
		return getLeft() + getWidth();
	}
	
	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the largest Y coordinate.
	 */
	public int getBottom() {
		return getTop() + getHeight();
	}
	
	/**
	 * Returns the point with the smallest X and Y coordinate.
	 */
	public IntPoint getTopLeft() {
		return new IntPoint(getLeft(), getTop());
	}
	
	/**
	 * Returns the point with the largest X and Y coordinate.
	 */
	public IntPoint getBottomRight() {
		return new IntPoint(getRight(), getBottom());
	}

	/**
	 * Returns whether this extent, considered as a closed set of points (i.e. including its edges and its vertices), contains the given point.
	 */
	public boolean contains(IntPoint point) {
		return point.getX() >= getLeft() && point.getX() <= getRight() && point.getY() >= getTop() && point.getY() <= getBottom();
	}
	
	/**
	 * Returns an extent with the given variables.
	 */
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		Extent myExtent = new Extent();
		myExtent.left = left;
		myExtent.top = top;
		myExtent.width = width;
		myExtent.height = height;
		return myExtent;
	}
	
	/**
	 * Returns an extent with the given variables.
	 */
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		Extent myExtent = new Extent();
		myExtent.left = left;
		myExtent.top = top;
		myExtent.width = right - left;
		myExtent.height = bottom - top;
		return myExtent;
	}
	
	/**
	 * Returns an object that has the given left coordinate and the same right, top, and bottom coordinate as this object.
	 */
	public Extent withLeft(int newLeft) {
		return ofLeftTopRightBottom(newLeft, getTop(), getRight(), getBottom());
	}
	
	/**
	 * Returns an object that has the given top coordinate and the same left, right, and bottom coordinate as this object.
	 */
	public Extent withTop(int newTop) {
		return ofLeftTopRightBottom(getLeft(), newTop, getRight(), getBottom());
	}
	
	/**
	 * Returns an object that has the given right coordinate and the same left, top, and bottom coordinate as this object.
	 */
	public Extent withRight(int newRight) {
		return ofLeftTopRightBottom(getLeft(), getTop(), newRight, getBottom());
	}

	/**
	 * Returns an object that has the given bottom coordinate and the same left, top, and right coordinate as this object.
	 */
	public Extent withBottom(int newBottom) {
		return ofLeftTopRightBottom(getLeft(), getTop(), getRight(), newBottom);
	}
	
	/**
	 * Returns an object that has the given width and the same left, top, and bottom coordinate as this object.
	 */
	public Extent withWidth(int newWidth) {
		return ofLeftTopWidthHeight(getLeft(), getTop(), newWidth, getHeight());
	}
	
	/**
	 * Returns an object that has the given height and the same left, top, and right coordinate as this object.
	 */
	public Extent withHeight(int newHeight) {
		return ofLeftTopWidthHeight(getLeft(), getTop(), getWidth(), newHeight);
	}
}

package drawit.shapegroups2;

import drawit.IntPoint;

/**
 * Each instance of this class represents a nonempty rectangular area in a 2D coordinate system, 
 * whose edges are parallel to the coordinate axes. This class must deal with illegal arguments defensively.
 * @immutable
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
	 * @post this extents right equals its left plus its width.
	 * 		| result == getLeft() + getWidth()
	 */
	public int getRight() {
		return getLeft() + getWidth();
	}
	
	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the largest Y coordinate.
	 * @post this extents bottom equals its top plus its height.
	 * 		| result == getTop() + getHeight()
	 */
	public int getBottom() {
		return getTop() + getHeight();
	}
	
	/**
	 * Returns the point with the smallest X and Y coordinate.
	 * @post this points X coordinate equals the left of its extent.
	 * 		| result.getX() == getLeft()
	 * @post this points Y coordinate equals the top of its extent.
	 * 		| result.getY() == getTop()
	 */
	public IntPoint getTopLeft() {
		return new IntPoint(getLeft(), getTop());
	}
	
	/**
	 * Returns the point with the largest X and Y coordinate.
	 * @post this points X coordinate equals the right of its extent.
	 * 		| result.getX() == getRight()
	 * @post this points Y coordinate equals the bottom of its extent.
	 * 		| result.getY() == getBottom()
	 */
	public IntPoint getBottomRight() {
		return new IntPoint(getRight(), getBottom());
	}

	/**
	 * Returns whether this extent, considered as a closed set of points (i.e. including its edges and its vertices), contains the given point.
	 * @throws IllegalArgumentException if there is no point given.
	 * 		| point == null
	 * @post the result equals true when the point is between the closed interval of the extent in each dimension.
	 * 		| result == (point.getX() >= getLeft() && point.getX() <= getRight() && point.getY() >= getTop() && point.getY() <= getBottom())
	 */
	public boolean contains(IntPoint point) {
		if (point == null)
			throw new IllegalArgumentException("There is no point given.");
		return point.getX() >= getLeft() && point.getX() <= getRight() && point.getY() >= getTop() && point.getY() <= getBottom();
	}
	
	/**
	 * Returns an extent with the given variables.
	 * @throws IllegalArgumentException if the given width is negative or zero.
	 * 		| width <= 0
	 * @throws IllegalArgumentException if the given height is negative or zero.
	 * 		| height <= 0
	 * @post the resulting left equals the one given.
	 * 		| result.getLeft() == left
	 * @post the resulting top equals the one given.
	 * 		| result.getTop() == top
	 * @post the resulting width equals the one given.
	 * 		| result.getWidth() == width
	 * @post the resulting height equals the one given.
	 * 		| result.getHeight() == height
	 * @post the resulting right equals the given left plus the given width.
	 * 		| result.getRight() == left + width
	 * @post the resulting bottom equals the given top plus the given height.
	 * 		| result.getBottom() == top + height
	 */
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException("The width and height must be positive.");
		Extent myExtent = new Extent();
		myExtent.left = left;
		myExtent.top = top;
		myExtent.width = width;
		myExtent.height = height;
		return myExtent;
	}
	
	/**
	 * Returns an extent with the given variables.
	 * @throws IllegalArgumentException if the given left is larger than or equal to the given right.
	 * 		| left >= right
	 * @throws IllegalArgumentException if the given top is larger than or equal to the given bottom.
	 * 		| top >= bottom
	 * @post the resulting left equals the one given.
	 * 		| result.getLeft() == left
	 * @post the resulting top equals the one given.
	 * 		| result.getTop() == top
	 * @post the resulting width equals the given right minus the given left.
	 * 		| result.getWidth() == right - left
	 * @post the resulting height equals the given bottom minus the given top.
	 * 		| result.getHeight() == bottom - top
	 * @post the resulting right equals the one given.
	 * 		| result.getRight() == right
	 * @post the resulting bottom equals the one given.
	 * 		| result.getBottom() == bottom
	 */
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		if (left > right || top > bottom)
			throw new IllegalArgumentException("The width and height must be positive.");
		Extent myExtent = new Extent();
		myExtent.left = left;
		myExtent.top = top;
		myExtent.width = right - left;
		myExtent.height = bottom - top;
		return myExtent;
	}
	
	/**
	 * Returns an object that has the given left coordinate and the same right, top, and bottom coordinate as this object.
	 * @throws IllegalArgumentException if the given left is larger than or equal to the right.
	 * 		| newLeft >= getRight()
	 * @post the resulting left equals the given one.
	 * 		| result.getLeft() == newLeft
	 * @post the resulting width is the right minus the given left.
	 * 		| result.getWidth() == this.getRight() - newLeft
	 * @post the right is not changed.
	 * 		| this.getRight() == result.getRight()
	 * @post the top is not changed.
	 * 		| this.getTop() == result.getTop()
	 * @post the bottom is not changed.
	 * 		| this.getBottom() == result.getBottom()
	 * @post the height is not changed.
	 * 		| this.getHeight() == result.getHeight()
	 */
	public Extent withLeft(int newLeft) {
		return ofLeftTopRightBottom(newLeft, getTop(), getRight(), getBottom());
	}
	
	/**
	 * Returns an object that has the given top coordinate and the same left, right, and bottom coordinate as this object.
	 * @throws IllegalArgumentException if the given top is larger than or equal to the bottom.
	 * 		| newTop >= getBottom()
	 * @post the left is not changed.
	 * 		| result.getLeft() == this.getLeft()
	 * @post the width is not changed.
	 * 		| result.getWidth() == this.getWidth()
	 * @post the right is not changed.
	 * 		| this.getRight() == result.getRight()
	 * @post the resulting top equals the one given.
	 * 		| result.getTop() == newTop
	 * @post the bottom is not changed.
	 * 		| this.getBottom() == result.getBottom()
	 * @post the resulting height equals the bottom minus the given top.
	 * 		| result.getHeight() == this.getBottom() - newTop
	 */
	public Extent withTop(int newTop) {
		return ofLeftTopRightBottom(getLeft(), newTop, getRight(), getBottom());
	}
	
	/**
	 * Returns an object that has the given right coordinate and the same left, top, and bottom coordinate as this object.
	 * @throws IllegalArgumentException if the left is larger than or equal to the given right.
	 * 		| getLeft() >= newRight
	 * @post the left is not changed.
	 * 		| result.getLeft() == getLeft()
	 * @post the resulting width is the right minus the given left.
	 * 		| result.getWidth() == newRight - getLeft()
	 * @post the resulting right equals the given one.
	 * 		| result.getRight() == newRight
	 * @post the top is not changed.
	 * 		| this.getTop() == result.getTop()
	 * @post the bottom is not changed.
	 * 		| this.getBottom() == result.getBottom()
	 * @post the height is not changed.
	 * 		| this.getHeight() == result.getHeight()
	 */
	public Extent withRight(int newRight) {
		return ofLeftTopRightBottom(getLeft(), getTop(), newRight, getBottom());
	}

	/**
	 * Returns an object that has the given bottom coordinate and the same left, top, and right coordinate as this object.
	 * @throws IllegalArgumentException if the top is larger than or equal to the given bottom.
	 * 		| getTop() >= newBottom
	 * @post the left is not changed.
	 * 		| result.getLeft() == this.getLeft()
	 * @post the width is not changed.
	 * 		| result.getWidth() == this.getWidth()
	 * @post the right is not changed.
	 * 		| this.getRight() == result.getRight()
	 * @post the top is not changed.
	 * 		| this.getTop() == result.getTop()
	 * @post the resulting bottom equals the one given.
	 * 		| result.getBottom() == newBottom
	 * @post the resulting height equals the given bottom minus the top.
	 * 		| result.getHeight() == newBottom - getTop()
	 */
	public Extent withBottom(int newBottom) {
		return ofLeftTopRightBottom(getLeft(), getTop(), getRight(), newBottom);
	}
	
	/**
	 * Returns an object that has the given width and the same left, top, and bottom coordinate as this object.
	 * @throws IllegalArgumentException if the given width is negative or zero.
	 * 		| newWidth <= 0
	 * @post the left is not changed.
	 * 		| result.getLeft() == this.getLeft()
	 * @post the resulting width equals the one given.
	 * 		| result.getWidth() == newWidth
	 * @post the resulting right equals the left plus the given width.
	 * 		| result.getRight() == this.getLeft() + newWidth
	 * @post the top is not changed.
	 * 		| this.getTop() == result.getTop()
	 * @post the bottom is not changed.
	 * 		| this.getBottom() == result.getBottom()
	 * @post the height is not changed.
	 * 		| this.getHeight() == result.getHeight()
	 */
	public Extent withWidth(int newWidth) {
		return ofLeftTopWidthHeight(getLeft(), getTop(), newWidth, getHeight());
	}
	
	/**
	 * Returns an object that has the given height and the same left, top, and right coordinate as this object.
	 * @throws IllegalArgumentException if the given height is negative or zero.
	 * 		| newHeight <= 0
	 * @post the left is not changed.
	 * 		| result.getLeft() == this.getLeft()
	 * @post the width is not changed.
	 * 		| result.getWidth() == this.getWidth()
	 * @post the right is not changed.
	 * 		| this.getRight() == result.getRight()
	 * @post the top is not changed.
	 * 		| this.getTop() == result.getTop()
	 * @post the resulting bottom equals the top plus the given height.
	 * 		| result.getBottom() == this.getTop() + newHeight
	 * @post the resulting height equals the one given.
	 * 		| result.getHeight() == newHeight
	 */
	public Extent withHeight(int newHeight) {
		return ofLeftTopWidthHeight(getLeft(), getTop(), getWidth(), newHeight);
	}
}

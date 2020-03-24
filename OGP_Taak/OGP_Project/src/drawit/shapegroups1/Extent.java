package drawit.shapegroups1;

import drawit.IntPoint;

public class Extent {
	
	private int left;
	private int top;
	private int right;
	private int bottom;
	
	public int getLeft() {
		return left;
	}
	
	public int getTop() {
		return top;
	}
	
	public int getRight() {
		return right;
	}
	
	public int getBottom() {
		return bottom;
	}
	
	public int getWidth() {
		return getRight() - getLeft();
	}
	
	public int getHeight() {
		return getTop() - getBottom();
	}
	
	public IntPoint getTopLeft() {
		return new IntPoint(getLeft(), getTop());
	}
	
	public IntPoint getBottomRight() {
		return new IntPoint(getRight(), getBottom());
	}
	
	public boolean contains(IntPoint point) {
		return (this.getLeft() <= point.getX() && point.getX() <= this.getRight() &&
				this.getBottom() <= point.getY() && point.getY() <= this.getTop());
	}
	
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		Extent myExtent = new Extent();
		myExtent.left = left;
		myExtent.top = top;
		myExtent.right = left + width;
		myExtent.bottom = top - height;
		return myExtent;		
	}
	
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		Extent myExtent = new Extent();
		myExtent.left = left;
		myExtent.top = top;
		myExtent.right = right;
		myExtent.bottom = bottom;
		return myExtent;
	}
	
	public Extent withLeft(int newLeft) {
		return ofLeftTopRightBottom(newLeft, this.getTop(), this.getRight(), this.getBottom());
	}
	
	public Extent withTop(int newTop) {
		return ofLeftTopRightBottom(this.getLeft(), newTop, this.getRight(), this.getBottom());
	}
	
	public Extent withRight(int newRight) {
		return ofLeftTopRightBottom(this.getLeft(), this.getTop(), newRight, this.getBottom());
	}
	
	public Extent withBottom(int newBottom) {
		return ofLeftTopRightBottom(this.getLeft(), this.getTop(), this.getRight(), newBottom);
	}
	
	public Extent withWidth(int newWidth) {
		return ofLeftTopRightBottom(this.getLeft(), this.getTop(), this.getLeft() + newWidth, this.getBottom());
	}
	
	public Extent withHeight(int newHeight) {
		return ofLeftTopRightBottom(this.getLeft(), this.getTop(), this.getRight(), this.getTop() + newHeight);
	}
		
}

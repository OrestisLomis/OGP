package drawit.shapegroups1;

import drawit.RoundedPolygon;
import drawit.DoublePoint;
import drawit.PointArrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import drawit.IntPoint;
import drawit.IntVector;

public class ShapeGroup {
	
	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	private Extent originalExtent;
	private Extent extent;
	private ShapeGroup parentgroup;
	
	/**
	 * Initializes this object to represent a leaf shape group that directly contains the given shape.
	 */
	public ShapeGroup(RoundedPolygon shape) {
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
			if (currentY > top)
				top = currentY;
			if (currentY < bottom);
		}
		
		this.extent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		this.originalExtent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		
	}
	
	/**
	 * Initializes this object to represent a non-leaf shape group that directly contains the given subgroups, 
	 * in the given order.
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subgroups = subgroups;
		ShapeGroup start = subgroups[0];
		start.parentgroup = this;
		Extent startExtent = start.getExtent();
		
		int left = startExtent.getLeft();
		int right = startExtent.getRight();
		int top = startExtent.getTop();
		int bottom = startExtent.getBottom();
		
		for (int i = 1; i < subgroups.length; i++) {
			subgroups[i].parentgroup = this;
			Extent current = subgroups[i].getExtent();
			
			if (current.getLeft() < left)
				left = current.getLeft();
			if (current.getRight() > right)
				right = current.getRight();
			if (current.getTop() > top)
				top = current.getTop();
			if (current.getBottom() < bottom)
				bottom = current.getBottom();
		}
		
		this.extent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		this.originalExtent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		
		
	}
	
	/**
	 * Returns the extent of this shape group, expressed in its outer coordinate system. 
	 * The extent of a shape group is the smallest axis-aligned rectangle that contained the shape group's shape 
	 * (if it is a leaf shape group) or its subgroups' extents (if it is a non-leaf shape group) 
	 * when the shape group was created. After the shape group is created, subsequent mutations of the shape or 
	 * subgroups contained by the shape group do not affect its extent. As a result, after mutating the shape or 
	 * subgroups contained by this shape group, this shape group's extent might no longer contain its shape or 
	 * its subgroups or might no longer be the smallest axis-aligned rectangle that does so.
	 */
	
	public Extent getExtent() {
		return extent;
	}
	
	/**
	 * Returns the extent of this shape group, expressed in its inner coordinate system. 
	 * This coincides with the extent expressed in outer coordinates at the time of creation of the shape group. 
	 * The shape transformation defined by this shape group is the one that transforms the original extent to the 
	 * current extent. This method returns an equal result throughout the lifetime of this object.
	 */
	
	public Extent getOriginalExtent() {
		return originalExtent;
	}
	
	/**
	 * Returns the shape group that directly contains this shape group, 
	 * or null if no shape group directly contains this shape group.
	 */
	
	public ShapeGroup getParentGroup() {
		return parentgroup;
	}
	
	public RoundedPolygon getShape() {
		return shape;
	}
	
	public ShapeGroup[] getSubgroups() {
		return subgroups;
	}
	
	public int getSubgroupCount() {
		return subgroups.length;
	}
	
	public ShapeGroup getSubgroup(int index) {
		return this.getSubgroups()[index];
	}
	
	public IntPoint toInnerCoordinates() {
		if (getParentGroup() != null)
			return getParentGroup().toInnerCoordinates(globalCoordinates);
		
		double newX = (innerCoordinates.getX() - getOriginalExtent().getLeft()) * getHorizontalScale() + getExtent().getLeft();
		double newY = (innerCoordinates.getY() - getOriginalExtent().getTop()) * getVerticalScale() + getExtent().getTop();
			
		DoublePoint result = new DoublePoint(newX, newY);
		
		return result.round();
		
	}
	
	public IntPoint toGlobalCoordinates() {
		
	}
	
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		
	}
	
	public void setExtent(Extent newExtent) {
		
	}
	
	

}

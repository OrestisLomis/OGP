package drawit.shapegroups1;

import drawit.RoundedPolygon;
import drawit.DoublePoint;
import drawit.PointArrays;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.DoubleVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	public List<ShapeGroup> getSubgroups() {
		return Arrays.asList(subgroups);
	}
	
	public int getSubgroupCount() {
		return subgroups.length;
	}
	
	public ShapeGroup getSubgroup(int index) {
		return this.getSubgroups().get(index);
	}
	
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		if (getParentGroup() != null)
			return getParentGroup().toInnerCoordinates(globalCoordinates);
		
		int newX = (int) (globalCoordinates.getX() - getExtent().getLeft()) / getHorizontalScale() + getOriginalExtent().getLeft();
		int newY = (int) (globalCoordinates.getY() - getExtent().getTop()) / getVerticalScale() + getOriginalExtent().getTop());
		
		DoublePoint doubleCoordinates = new DoublePoint(newX, newY);
		
		IntPoint innerCoordinates = doubleCoordinates.round();
		
		return new IntPoint(newX, newY);
	}
	
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		if (getParentGroup() != null)
			return getParentGroup().toGlobalCoordinates(innerCoordinates);
		
		int newX = (int) ((innerCoordinates.getX() - getOriginalExtent().getLeft()) * getHorizontalScale() + getExtent().getLeft());
		int newY = (int) ((innerCoordinates.getY() - getOriginalExtent().getTop()) * getVerticalScale() + getExtent().getTop());
			
		return new IntPoint(newX, newY);
	}
	
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		if (getParentGroup() != null)
			return getParentGroup().toInnerCoordinates(relativeGlobalCoordinates);
		
		int newX = (int) (relativeGlobalCoordinates.getX()/getHorizontalScale());
		int newY = (int) (relativeGlobalCoordinates.getY()/getVerticalScale());
		
		return new IntVector(newX, newY);
	}
		
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		for (int i = 0; i < this.getSubgroupCount(); i++) {
			Extent current = this.getSubgroup(i).getOriginalExtent();
			if (current.contains(innerCoordinates))
				return this.getSubgroup(i);
		}
		return null;
		
	}
	
	public void setExtent(Extent newExtent) {
		this.extent = newExtent;		
	}
	
	public void bringToFront() {
		ShapeGroup parent = getParentGroup();
		ShapeGroup[] newGroups = new ShapeGroup[parent.getSubgroupCount()];
		newGroups[0] = this;
		
		int index = parent.getSubgroups().indexOf(this);
		
		for (int i = 0; i < parent.getSubgroupCount(); i++) {
			if (i < index)
				newGroups[i + 1] = parent.getSubgroup(i);
			else if (i > index)
				newGroups[i] = parent.getSubgroup(i);
		}
		parent.subgroups = newGroups;	
	}
	
	public void sendToBack() {
		ShapeGroup parent = getParentGroup();
		ShapeGroup[] newGroups = new ShapeGroup[parent.getSubgroupCount()];
		newGroups[parent.getSubgroupCount() - 1] = this;
		
		int index = parent.getSubgroups().indexOf(this);
		
		for (int i = 0; i < parent.getSubgroupCount(); i++) {
			if (i < index)
				newGroups[i] = parent.getSubgroup(i);
			else if (i > index)
				newGroups[i - 1] = parent.getSubgroup(i);
		}
		parent.subgroups = newGroups;	
	}
	
	public String getDrawingCommands() {
		String commands = "";
		commands += "pushTranslate " + getHorizontalTranslate() + " " + getVerticalTranslate() + System.lineSeparator();
		commands += "pushScale " + getHorizontalScale() + " " + getVerticalScale() + System.lineSeparator();
		if (this.getShape() != null)
			commands += getShape().getDrawingCommands();
		else 
			for (int i = getSubgroupCount() - 1; i >= 0; i--)
				commands += getSubgroup(i).getDrawingCommands();
		commands += "popTransform " + System.lineSeparator();
		commands += "popTransform " + System.lineSeparator();
		
		return commands;
	}
	
	public double getHorizontalScale() {
		return getExtent().getHeight();
	}
	
	//Ik weet nog niet of ik ook getHorizontalScale enzo ga doen, heb dat nu even over genomen 

}

package drawit.shapegroups2;

import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ShapeGroup {
	
	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	private final Extent originalExtent;
	private Extent extent;
	private ShapeGroup parentgroup;
	
	/**
	 * Initializes this object to represent a leaf shape group that directly contains the given shape.
	 */
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
		
		int mostLeft = (Integer) null;
		int mostRight = (Integer) null;
		int mostTop = (Integer) null;
		int mostBottom = (Integer) null;
		IntPoint[] vertices = shape.getVertices();
		for (int i = 0; i < vertices.length; i++) {
			int currentX = vertices[i].getX();
			int currentY = vertices[i].getY();
			if (mostLeft == (Integer) null || currentX < mostLeft) 
				mostLeft = currentX;
			if (mostRight == (Integer) null || currentX > mostRight) 
				mostRight = currentX;
			if (mostTop == (Integer) null || currentY < mostTop) 
				mostTop = currentY;
			if (mostBottom == (Integer) null || currentY > mostBottom) 
				mostBottom = currentY;
		}
		
		this.extent = Extent.ofLeftTopRightBottom(mostLeft, mostTop, mostRight, mostBottom);
		this.originalExtent = extent;
	}
	
	/**
	 * Initializes this object to represent a non-leaf shape group that directly contains the given subgroups, in the given order.
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subgroups = subgroups;
		
		int mostLeft = (Integer) null;
		int mostRight = (Integer) null;
		int mostTop = (Integer) null;
		int mostBottom = (Integer) null;
		for (int i = 0; i < subgroups.length; i++) {
			subgroups[i].parentgroup = this;
			
			Extent currentExtent = subgroups[i].getExtent();
			int currentLeft = currentExtent.getLeft();
			int currentRight = currentExtent.getRight();
			int currentTop = currentExtent.getTop();
			int currentBottom = currentExtent.getBottom();
			if (mostLeft == (Integer) null || currentLeft < mostLeft)
				mostLeft = currentLeft;
			if (mostRight == (Integer) null || currentRight > mostRight)
				mostRight = currentRight;
			if (mostTop == (Integer) null || currentTop < mostTop)
				mostTop = currentTop;
			if (mostBottom == (Integer) null || currentBottom > mostBottom)
				mostBottom = currentBottom;
		}
		
		this.extent = Extent.ofLeftTopRightBottom(mostLeft, mostTop, mostRight, mostBottom);
		this.originalExtent = extent;
	}
	
	/**
	 * Returns the extent of this shape group, expressed in its outer coordinate system. The extent of a shape group is the smallest 
	 * axis-aligned rectangle that contained the shape group's shape (if it is a leaf shape group) or its subgroups' extents 
	 * (if it is a non-leaf shape group) when the shape group was created. After the shape group is created, subsequent 
	 * mutations of the shape or subgroups contained by the shape group do not affect its extent. As a result, after mutating the 
	 * shape or subgroups contained by this shape group, this shape group's extent might no longer contain its shape or its subgroups 
	 * or might no longer be the smallest axis-aligned rectangle that does so.
	 */
	public Extent getExtent() {
		return extent;
	}
	
	/**
	 * Returns the extent of this shape group, expressed in its inner coordinate system. This coincides with the extent expressed 
	 * in outer coordinates at the time of creation of the shape group. The shape transformation defined by this shape group is the 
	 * one that transforms the original extent to the current extent. This method returns an equal result throughout the lifetime of this object.
	 */
	public Extent getOriginalExtent() {
		return originalExtent;
	}
	
	/**
	 * Returns the shape group that directly contains this shape group, or null if no shape group directly contains this shape group.
	 */
	public ShapeGroup getParentGroup() {
		return parentgroup;
	}
	
	/**
	 * Returns the shape directly contained by this shape group, or null if this is a non-leaf shape group.
	 */
	public RoundedPolygon getShape() {
		return shape;
	}
	
	/**
	 * Returns the list of subgroups of this shape group, or null if this is a leaf shape group.
	 */
	public ShapeGroup[] getSubgroups() {
		return subgroups;
	}
	
	/**
	 * Returns the number of subgroups of this non-leaf shape group.
	 */
	public int getSubgroupCount() {
		return subgroups.length;
	}
	
	/**
	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape group's list of subgroups.
	 */
	public ShapeGroup getSubgroup(int index) {
		return subgroups[index];
	}
	
	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the point whose coordinates in the global coordinate system 
	 * are the given coordinates. The global coordinate system is the outer coordinate system of this shape group's root ancestor, i.e. the 
	 * ancestor that has no parent. This shape group's inner coordinate system is defined by the fact that the coordinates of its extent in 
	 * its inner coordinate system are constant and given by this.getOriginalExtent(). Its outer coordinate system is defined by the fact that 
	 * the coordinates of its extent in its outer coordinate system are as given by this.getExtent(). When a shape group is created, its inner 
	 * coordinate system coincides with its outer coordinate system (and with the global coordinate system). Subsequent calls of 
	 * this.setExtent() may cause the inner and outer coordinate systems to no longer coincide. The inner coordinate system of a non-leaf shape 
	 * group always coincides with the outer coordinate systems of its subgroups. Furthermore, the coordinates of the vertices of a shape 
	 * contained by a leaf shape group are interpreted in the inner coordinate system of the shape group.
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		Extent innerExtent = this.getOriginalExtent();
		Extent currentExtent = this.getExtent();
		
		/*
		int verticalTranslate = innerExtent.getLeft() - currentExtent.getLeft();
		int horizontalTranslate = innerExtent.getTop() - currentExtent.getTop();
		*/
		
		int horizontalOffset = globalCoordinates.getX() - currentExtent.getLeft();
		int verticalOffset = globalCoordinates.getY() - currentExtent.getTop();
		
		double horizontalScale = innerExtent.getWidth()/currentExtent.getWidth();
		double verticalScale = innerExtent.getHeight()/currentExtent.getHeight();
		
		double newHorizontalOffset = horizontalOffset * horizontalScale;
		double newVerticalOffset = verticalOffset * verticalScale;
		
		DoublePoint doubleCoordinates = new DoublePoint(innerExtent.getLeft() + newHorizontalOffset, innerExtent.getTop() + newVerticalOffset);
		
		IntPoint innerCoordinates = doubleCoordinates.round();
		
		return innerCoordinates;
	}
	
	/**
	 * Returns the coordinates in the global coordinate system of the point whose coordinates in this shape group's inner coordinate 
	 * system are the given coordinates.
	 */
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		return innerCoordinates;
	}
	
	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the vector whose coordinates in the global coordinate system are 
	 * the given coordinates. This transformation is affected only by mutations of the width or height of this shape group's extent, not by 
	 * mutations of this shape group's extent that preserve its width and height.
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		return relativeGlobalCoordinates;
	}
	
	/**
	 * Return the first subgroup in this non-leaf shape group's list of subgroups whose extent contains the given point, expressed in this 
	 * shape group's inner coordinate system.
	 */
	public ShapeGroup getSubGroupAt(IntPoint innerCoordinates) {
		boolean found = false;
		int i = 0;
		while (!(found) && i < this.getSubgroupCount()) {
			Extent currentExtent = this.getSubgroup(i).getOriginalExtent();
			if (currentExtent.contains(innerCoordinates))
				found = true;
			i++;
		}
	
		return this.getSubgroup(i-1);
	}
	
	/**
	 * Registers the given extent as this shape group's extent, expressed in this shape group's outer coordinate system. As a consequence, 
	 * by definition this shape group's inner coordinate system changes so that the new extent's coordinates in the new inner coordinate 
	 * system are equal to the old extent's coordinates in the old inner coordinate system. Note: this method does not mutate the coordinates 
	 * of the vertices stored by the shape or the extents stored by the subgroups contained by this shape group. However, since these are 
	 * interpreted in this shape group's inner coordinate system, this method effectively defines a transformation of this shape or these 
	 * subgroups.
	 */
	public void setExtent(Extent newExtent) {
		this.extent = newExtent;
	}
	
	/**
	 * Moves this shape group to the front of its parent's list of subgroups.
	 */
	public void BringToFront() {
		ShapeGroup parentgroup = this.getParentGroup();
		ShapeGroup[] newSubgroups = new ShapeGroup[parentgroup.getSubgroupCount()];
		boolean thisIndexPassed = false;
		newSubgroups[0] = this;
		for (int i = 1; i < parentgroup.getSubgroupCount(); i++) {
			if (parentgroup.getSubgroup(i).equals(this)) 
				thisIndexPassed = true;
			else if (thisIndexPassed)
				newSubgroups[i] = parentgroup.getSubgroup(i);
			else
				newSubgroups[i] = parentgroup.getSubgroup(i-1);
			parentgroup.subgroups = newSubgroups;
		}
	}
	
	/**
	 * Moves this shape group to the back of its parent's list of subgroups.
	 */
	public void SendToBack() {
		ShapeGroup parentgroup = this.getParentGroup();
		ShapeGroup[] newSubgroups = new ShapeGroup[parentgroup.getSubgroupCount()];
		boolean thisIndexPassed = false;
		newSubgroups[parentgroup.getSubgroupCount()-1] = this;
		for (int i = 0; i < parentgroup.getSubgroupCount() - 1; i++) {
			if (parentgroup.getSubgroup(i).equals(this)) 
				thisIndexPassed = true;
			else if (thisIndexPassed)
				newSubgroups[i] = parentgroup.getSubgroup(i+1);
			else
				newSubgroups[i] = parentgroup.getSubgroup(i);
			parentgroup.subgroups = newSubgroups;
		}
	}
	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing the shapes contained directly or indirectly by this shape 
	 * group, expressed in this shape group's outer coordinate system. For the syntax of the drawing commands, see 
	 * RoundedPolygon.getDrawingCommands().
	 */
	public String getDrawingCommands() {
		return null;
	}
	
}

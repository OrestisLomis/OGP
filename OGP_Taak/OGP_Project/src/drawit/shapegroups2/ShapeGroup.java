package drawit.shapegroups2;

import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;

abstract public class ShapeGroup {
	


	Extent originalExtent;
	Extent extent;
	
	/**
	 * @invar | true
	 * @invar The subgroup-parentgroup relation is always consistent, if this ShapeGroup object has a parent.
	 * 		| parentgroup == null || parentgroup.subgroups.contains(this)
	 * 
	 * @peerObject
	 */
	NonleafShapeGroup parentgroup;
	
	/**
	 * @peerObject
	 */
	ShapeGroup next;
	
	/**
	 * @peerObject
	 */
	ShapeGroup previous;
	
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
	 * 
	 * @peerObject
	 */
	public NonleafShapeGroup getParentGroup() {
		return parentgroup;
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
	 * @throws IllegalArgumentException if the given point is {@code null}.
	 * 		| globalCoordinates == null
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		if (globalCoordinates == null)
			throw new IllegalArgumentException("There is no point given.");
		if (getParentGroup() != null) 
			globalCoordinates = getParentGroup().toInnerCoordinates(globalCoordinates);
		
		double newXCoordinate = (globalCoordinates.getX() - getHorizontalTranslate()) / getHorizontalScale();
		double newYCoordinate = (globalCoordinates.getY() - getVerticalTranslate()) / getVerticalScale();
		
		DoublePoint doubleCoordinates = new DoublePoint(newXCoordinate, newYCoordinate);
		
		IntPoint innerCoordinates = doubleCoordinates.round();
		
		return innerCoordinates;
	}
	
	/**
	 * Returns the coordinates in the global coordinate system of the point whose coordinates in this shape group's inner coordinate 
	 * system are the given coordinates.
	 * @throws IllegalArgumentException if the given point is {@code null}.
	 * 		| innerCoordinates == null
	 */
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		if (innerCoordinates == null)
			throw new IllegalArgumentException("There is no point given.");
		double newXCoordinate = innerCoordinates.getX() * getHorizontalScale() + getHorizontalTranslate();
		double newYCoordinate = innerCoordinates.getY() * getVerticalScale() + getVerticalTranslate();
			
		DoublePoint doubleCoordinates = new DoublePoint(newXCoordinate, newYCoordinate);
		
		IntPoint globalCoordinates = doubleCoordinates.round();
		
		if (getParentGroup() != null)
			return getParentGroup().toGlobalCoordinates(globalCoordinates);
		
		return globalCoordinates;
	}
	
	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the vector whose coordinates in the global coordinate system are 
	 * the given coordinates. This transformation is affected only by mutations of the width or height of this shape group's extent, not by 
	 * mutations of this shape group's extent that preserve its width and height.
	 * @throws IllegalArgumentException if the given point is {@code null}.
	 * 		| relativeGlobalCoordinates == null
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		if (relativeGlobalCoordinates == null)
			throw new IllegalArgumentException("There is no point given.");
		if (getParentGroup() != null)
			relativeGlobalCoordinates = getParentGroup().toInnerCoordinates(relativeGlobalCoordinates);

		int newXCoordinate = (int) Math.round(relativeGlobalCoordinates.getX()/getHorizontalScale());
		int newYCoordinate = (int) Math.round(relativeGlobalCoordinates.getY()/getVerticalScale());
		
		IntVector innerCoordinates = new IntVector(newXCoordinate, newYCoordinate);
		
		return innerCoordinates;
	}
	
	
	/**
	 * Registers the given extent as this shape group's extent, expressed in this shape group's outer coordinate system. As a consequence, 
	 * by definition this shape group's inner coordinate system changes so that the new extent's coordinates in the new inner coordinate 
	 * system are equal to the old extent's coordinates in the old inner coordinate system. Note: this method does not mutate the coordinates 
	 * of the vertices stored by the shape or the extents stored by the subgroups contained by this shape group. However, since these are 
	 * interpreted in this shape group's inner coordinate system, this method effectively defines a transformation of this shape or these 
	 * subgroups.
	 * @throws IllegalArgumentException if the given extent is {@code null}.
	 * 		| newExtent == null
	 */
	public void setExtent(Extent newExtent) {
		if (newExtent == null)
			throw new IllegalArgumentException("There is no extent given.");
		this.extent = newExtent;
	}
	
	/**
	 * Moves this shape group to the front of its parent's list of subgroups.
	 * @throws IllegalArgumentException if the selected shape group has no parent.
	 * 		| this.getParentGroup() == null
	 * @post this shape is the first element of its parents subgroups.
	 * 		| this.getParentGroup().getSubgroup(0) == this
	 * @post the amount of subgroups remains the same.
	 * 		| this.getParentGroup().getSubgroupCount() == old(this.getParentGroup().getSubgroupCount())
	 * @mutates | this
	 * @mutates_properties | this.getParentGroup().getSubgroups()
	 */
	public void bringToFront() {
		NonleafShapeGroup parentgroup = this.getParentGroup();
		if(this.parentgroup == null)
			throw new IllegalArgumentException("This shape group has no parent.");

		if (!(this.equals(parentgroup.firstSubgroup))) {
			if (this.equals(parentgroup.lastSubgroup)) {
				parentgroup.lastSubgroup = this.previous;
				parentgroup.lastSubgroup.next = parentgroup.lastSubgroup;
				
			}
			else {
				this.next.previous = this.previous;
				this.previous.next = this.next;
			}
			this.next = parentgroup.firstSubgroup;
			parentgroup.firstSubgroup.previous = this;
			parentgroup.firstSubgroup = this;
			this.previous = this;
		}
	}
	
	/**
	 * Moves this shape group to the back of its parent's list of subgroups.
	 * @throws IllegalArgumentException if the selected shape group has no parent.
	 * 		| this.getParentGroup() == null
	 * @post this shape is the last element of its parent subgroups.
	 * 		| this.getParentGroup().getSubgroup(this.getParentGroup().getSubgroupCount() - 1) == this
	 * @post the amount of subgroups remains the same.
	 * 		| this.getParentGroup().getSubgroupCount() == old(this.getParentGroup().getSubgroupCount())
	 * @mutates | this
	 * @mutates_properties | this.getParentGroup().getSubgroups()
	 */
	public void sendToBack() {
		NonleafShapeGroup parentgroup = this.getParentGroup();
		if (this.getParentGroup() == null)
			throw new IllegalArgumentException("This shape group has no parent.");

		if (!(this.equals(parentgroup.lastSubgroup))) {
			if (this.equals(parentgroup.firstSubgroup)) {
				parentgroup.firstSubgroup = this.next;
				parentgroup.firstSubgroup.previous = parentgroup.firstSubgroup;
				
			}
			else {
				this.next.previous = this.previous;
				this.previous.next = this.next;
			}
			this.previous = parentgroup.lastSubgroup;
			parentgroup.lastSubgroup.next = this;
			parentgroup.lastSubgroup = this;
			this.next = this;
		}			
	}
	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing the shapes contained directly or indirectly by this shape 
	 * group, expressed in this shape group's outer coordinate system. For the syntax of the drawing commands, see 
	 * RoundedPolygon.getDrawingCommands().
	 */
	abstract public String getDrawingCommands();
	
	/**
	 * Returns the horizontal scale factor to go from inner to outer coordinates.
	 */
	double getHorizontalScale() {
		return getExtent().getWidth()/(double) getOriginalExtent().getWidth();
	}
	
	/**
	 * Returns the vertical scale factor to go from inner to outer coordinates.
	 */
	double getVerticalScale() {
		return getExtent().getHeight()/(double) getOriginalExtent().getHeight();
	}
	
	/**
	 * Returns the horizontal translate to go from inner to outer coordinates.
	 */
	double getHorizontalTranslate() {
		return getExtent().getLeft() - getOriginalExtent().getLeft() * getHorizontalScale(); 
	}
	
	/**
	 * Returns the vertical translate to go from inner to outer coordinates.
	 */
	double getVerticalTranslate() {
		return getExtent().getTop() - getOriginalExtent().getTop() * getVerticalScale();
	}
}

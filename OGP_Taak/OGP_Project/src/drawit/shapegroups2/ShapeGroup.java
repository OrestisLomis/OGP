package drawit.shapegroups2;

import drawit.DoublePoint;
import drawit.DoubleVector;
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
		
		IntPoint[] vertices = shape.getVertices();
		int mostLeft = vertices[0].getX();
		int mostRight = vertices[0].getX();
		int mostTop = vertices[0].getY();
		int mostBottom = vertices[0].getY();
		
		for (int i = 1; i < vertices.length; i++) {
			int currentX = vertices[i].getX();
			int currentY = vertices[i].getY();
			if (currentX < mostLeft) 
				mostLeft = currentX;
			if (currentX > mostRight) 
				mostRight = currentX;
			if (currentY < mostTop) 
				mostTop = currentY;
			if (currentY > mostBottom) 
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
		
		Extent firstExtent = getSubgroup(0).getExtent();
		
		int mostLeft = firstExtent.getLeft();
		int mostRight = firstExtent.getRight();
		int mostTop = firstExtent.getTop();
		int mostBottom = firstExtent.getBottom();
		getSubgroup(0).parentgroup = this;
		for (int i = 1; i < subgroups.length; i++) {
			getSubgroup(i).parentgroup = this;
			
			Extent currentExtent = getSubgroup(i).getExtent();
			int currentLeft = currentExtent.getLeft();
			int currentRight = currentExtent.getRight();
			int currentTop = currentExtent.getTop();
			int currentBottom = currentExtent.getBottom();
			if (currentLeft < mostLeft)
				mostLeft = currentLeft;
			if (currentRight > mostRight)
				mostRight = currentRight;
			if (currentTop < mostTop)
				mostTop = currentTop;
			if (currentBottom > mostBottom)
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
	 * @pre This is a non-leaf shape group.
	 * 		| this.getSubgroups() != null
	 */
	public int getSubgroupCount() {
		return subgroups.length;
	}
	
	/**
	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape group's list of subgroups.
	 * @pre This is a non-leaf shape group.
	 * 		| this.getSubgroups() != null
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
		double newXCoordinate = (globalCoordinates.getX() - getExtent().getLeft()) / getHorizontalScale() + getOriginalExtent().getLeft();
		double newYCoordinate = (globalCoordinates.getY() - getExtent().getTop()) / getVerticalScale() + getOriginalExtent().getTop();
		
		DoublePoint doubleCoordinates = new DoublePoint(newXCoordinate, newYCoordinate);
		
		IntPoint innerCoordinates = doubleCoordinates.round();
		
		if (getParentGroup() != null)
			return getParentGroup().toInnerCoordinates(innerCoordinates);
		
		return innerCoordinates;
	}
	
	/**
	 * Returns the coordinates in the global coordinate system of the point whose coordinates in this shape group's inner coordinate 
	 * system are the given coordinates.
	 */
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		double newXCoordinate = (innerCoordinates.getX() - getOriginalExtent().getLeft()) * getHorizontalScale() + getExtent().getLeft();
		double newYCoordinate = (innerCoordinates.getY() - getOriginalExtent().getTop()) * getVerticalScale() + getExtent().getTop();
			
		DoublePoint doubleCoordinates = new DoublePoint(newXCoordinate, newYCoordinate);
		
		IntPoint globalCoordinates = doubleCoordinates.round();
		
		if (getParentGroup() != null)
			return getParentGroup().toInnerCoordinates(globalCoordinates);
		
		return globalCoordinates;
	}
	
	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the vector whose coordinates in the global coordinate system are 
	 * the given coordinates. This transformation is affected only by mutations of the width or height of this shape group's extent, not by 
	 * mutations of this shape group's extent that preserve its width and height.
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		IntPoint origin = new IntPoint(0, 0);
		double newXCoordinate = relativeGlobalCoordinates.getX()/getHorizontalScale();
		double newYCoordinate = relativeGlobalCoordinates.getY()/getVerticalScale();
		
		DoublePoint doubleCoordinates = new DoublePoint(newXCoordinate, newYCoordinate);
		IntPoint intCoordinates = doubleCoordinates.round();
		
		IntVector innerCoordinates = intCoordinates.minus(origin);
		
		if (getParentGroup() != null)
			return getParentGroup().toInnerCoordinates(innerCoordinates);
		
		return innerCoordinates;
	}
	
	/**
	 * Return the first subgroup in this non-leaf shape group's list of subgroups whose extent contains the given point, expressed in this 
	 * shape group's inner coordinate system.
	 * @pre This is a non-leaf shape group.
	 * 		| this.getSubgroups() != null
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		for (int i = 0; i < this.getSubgroupCount(); i++) {
			Extent currentExtent = this.getSubgroup(i).getExtent();
			if (currentExtent.contains(innerCoordinates))
				return this.getSubgroup(i);
		}
	
		return null;
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
	public void bringToFront() {
		ShapeGroup parentgroup = this.getParentGroup();
		ShapeGroup[] newSubgroups = new ShapeGroup[parentgroup.getSubgroupCount()];
		boolean thisIndexPassed = false;
		newSubgroups[0] = this;
		for (int i = 1; i < parentgroup.getSubgroupCount(); i++) {
			if (parentgroup.getSubgroup(i).equals(this)) {
				thisIndexPassed = true;
				newSubgroups[i] = parentgroup.getSubgroup(i-1);
			}
			else if (thisIndexPassed)
				newSubgroups[i] = parentgroup.getSubgroup(i);
			else
				newSubgroups[i] = parentgroup.getSubgroup(i-1);
		}
		parentgroup.subgroups = newSubgroups;
	}
	
	/**
	 * Moves this shape group to the back of its parent's list of subgroups.
	 */
	public void sendToBack() {
		ShapeGroup parentgroup = this.getParentGroup();
		ShapeGroup[] newSubgroups = new ShapeGroup[parentgroup.getSubgroupCount()];
		boolean thisIndexPassed = false;
		newSubgroups[parentgroup.getSubgroupCount()-1] = this;
		for (int i = 0; i < parentgroup.getSubgroupCount() - 1; i++) {
			if (parentgroup.getSubgroup(i).equals(this)) {
				thisIndexPassed = true;
				newSubgroups[i] = parentgroup.getSubgroup(i+1);
			}
			else if (thisIndexPassed)
				newSubgroups[i] = parentgroup.getSubgroup(i+1);
			else
				newSubgroups[i] = parentgroup.getSubgroup(i);
		}
		parentgroup.subgroups = newSubgroups;
	}
	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing the shapes contained directly or indirectly by this shape 
	 * group, expressed in this shape group's outer coordinate system. For the syntax of the drawing commands, see 
	 * RoundedPolygon.getDrawingCommands().
	 */
	public String getDrawingCommands() {
		String commands = "";
		commands += "pushTranslate " + getHorizontalTranslate() + " " + getVerticalTranslate() + System.lineSeparator();
		commands += "pushScale " + getHorizontalScale() + " " + getVerticalScale() + System.lineSeparator();
		if (getShape() != null)
			commands += getShape().getDrawingCommands();
		else {
			for (int i = getSubgroupCount() - 1; i >= 0; i--) {
				ShapeGroup currentSubgroup = getSubgroup(i);
				commands += currentSubgroup.getDrawingCommands();	
			}
		}
		commands += "popTransform " + System.lineSeparator();
		commands += "popTransform " + System.lineSeparator();
		return commands.toString();
	}
	
	/**
	 * Returns the horizontal scale factor to go from inner to outer coordinates.
	 */
	public double getHorizontalScale() {
		return getExtent().getWidth()/(double) getOriginalExtent().getWidth();
	}
	
	/**
	 * Returns the vertical scale factor to go from inner to outer coordinates.
	 */
	public double getVerticalScale() {
		return getExtent().getHeight()/(double) getOriginalExtent().getHeight();
	}
	
	/**
	 * Returns the horizontal translate to go from inner to outer coordinates.
	 */
	public int getHorizontalTranslate() {
		return (int) (getExtent().getLeft() - getOriginalExtent().getLeft() * getHorizontalScale()); 
	}
	
	/**
	 * Returns the vertical translate to go from inner to outer coordinates.
	 */
	public int getVerticalTranslate() {
		return (int) (getExtent().getTop() - getOriginalExtent().getTop() * getVerticalScale());
	}
}

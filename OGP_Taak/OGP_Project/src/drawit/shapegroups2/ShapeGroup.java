package drawit.shapegroups2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;


public class ShapeGroup {
	
	private RoundedPolygon shape;
	private LinkedList<ShapeGroup> subgroups;
	private final Extent originalExtent;
	private Extent extent;
	private ShapeGroup parentgroup;
	private ShapeGroup next;
	private ShapeGroup previous;
	private ShapeGroup firstSubgroup;
	private ShapeGroup lastSubgroup;
	
	
	/**
	 * Initializes this object to represent a leaf shape group that directly contains the given shape.
	 * @throws IllegalArgumentException if there is no shape given.
	 * 		| shape == null
	 * @post This shape groups shape equals the given shape
	 * 		| this.getShape() == shape
	 */
	public ShapeGroup(RoundedPolygon shape) {
		if (shape == null)
			throw new IllegalArgumentException("There is no shape given.");
		
		this.shape = shape;
		
		IntPoint[] vertices = shape.getVertices();
		IntPoint firstVertex = vertices[0];
		
		int mostLeft = firstVertex.getX();
		int mostRight = firstVertex.getX();
		int mostTop = firstVertex.getY();
		int mostBottom = firstVertex.getY();
		
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
	 * @throws IllegalArgumentException if the given array is {@code null}.
	 * 		| subgroups == null
	 * @throws IllegalArgumentException if there is only 1 subgroup given.
	 * 		| subgroups.length <= 1
	 * @throws IllegalArgumentException if one of the given subgroups is {@code null}.
	 * 		| Arrays.stream(subgroups).anyMatch(subgroup -> subgroup == null)
	 * @throws IllegalArgumentException if any of the subgroups already have a parent.
	 * 		| Arrays.stream(subgroups).anyMatch(subgroup -> subgroup.getParentGroup() != null)
	 * @post This shape groups subgroups equal the given subgroups.
	 * 		| Arrays.equals(this.getSubgroups().toArray(), 0, this.getSubgroupCount() - 1, subgroups, 0, subgroups.length - 1)
	 * @post This shape group is the parent of all its subgroups
	 * 		| Arrays.stream(subgroups).allMatch(subgroup -> subgroup.getParentGroup() == this)
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		if (subgroups == null)
			throw new IllegalArgumentException("There are no subgroups given.");
		if (subgroups.length <= 1)
			throw new IllegalArgumentException("There is only 1 subgroup given.");
		for (ShapeGroup subgroup : subgroups) {
			if (subgroup == null)
				throw new IllegalArgumentException("One of the given subgroups does not exist.");
			if (subgroup.getParentGroup() != null)
				throw new IllegalArgumentException("One of the given subgroups already has a parent.");
		}
		
		this.subgroups = new LinkedList<ShapeGroup>();
		
		ShapeGroup firstSub = subgroups[0];
		Extent firstExtent = firstSub.getExtent();
		
		int mostLeft = firstExtent.getLeft();
		int mostRight = firstExtent.getRight();
		int mostTop = firstExtent.getTop();
		int mostBottom = firstExtent.getBottom();
		this.firstSubgroup = firstSub;
		this.firstSubgroup.previous = this.firstSubgroup;
		this.firstSubgroup.next = subgroups[1];
		firstSub.parentgroup = this;
		this.subgroups.add(firstSub);
		
		for (int i = 1; i < subgroups.length; i++) {
			ShapeGroup currentSub = subgroups[i];
			currentSub.parentgroup = this;
			if (i == subgroups.length - 1) {
				currentSub.next = currentSub;
				this.lastSubgroup = currentSub;
			}
			else
				currentSub.next = subgroups[i+1];
			currentSub.previous = subgroups[i-1];
			this.subgroups.add(currentSub);
				
			Extent currentExtent = currentSub.getExtent();
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
	 * Returns the list of subgroups of this shape group, or {@code null} if this is a leaf shape group.
	 */
	public List<ShapeGroup> getSubgroups() {
		LinkedList<ShapeGroup> subgroups = new LinkedList<ShapeGroup>();
		subgroups.add(firstSubgroup);
		ShapeGroup currentSub = firstSubgroup.next;
		while (!(currentSub.next.equals(currentSub))) {
			subgroups.add(currentSub);
			currentSub = currentSub.next;
		}
		return subgroups;
	}
	
	/**
	 * Returns the number of subgroups of this non-leaf shape group.
	 * @throws if this is a leaf shape group.
	 * 		| this.getSubgroups() == null
	 * @post the result is greater than or equal to 2.
	 * 		| result >= 2
	 */
	public int getSubgroupCount() {
		if (this.getSubgroups() != null)
			return subgroups.size();
		else
			throw new IllegalArgumentException("This shape group has no subgroups.");
	}
	
	/**
	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape group's list of subgroups.
	 * @throws if this is a leaf shape group.
	 * 		| this.getSubgroups() == null
	 */
	public ShapeGroup getSubgroup(int index) {
		if (this.getSubgroups() == null)
			throw new IllegalArgumentException("This shape group has no subgroups.");
		int numberSubgroups = getSubgroupCount();
		ShapeGroup currentSub = null;
		if (index > numberSubgroups/2) {
			currentSub = this.lastSubgroup;
			while (numberSubgroups > index + 1) {
				currentSub = currentSub.previous;
				numberSubgroups--;
			}
		}
		else {
			currentSub = this.firstSubgroup;
			for (int i = 0; i < index; i++) {
				currentSub = currentSub.next;
			}	
		}
		return currentSub;
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
	 * Return the first subgroup in this non-leaf shape group's list of subgroups whose extent contains the given point, expressed in this 
	 * shape group's inner coordinate system.
	 * @throws IllegalArgumentException if this is a non-leaf shape group.
	 * 		| this.getSubgroups() == null
	 * @post the result is {@code null} or the given point is contained by the extent of the returned subgroup.
	 * 		| (result == null) || (result.getExtent().contains(innerCoordinates))
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		if (this.getSubgroups() == null)
			throw new IllegalArgumentException("This shape group has no subgroups.");
		ShapeGroup currentSubgroup = this.firstSubgroup;
		for (int i = 0; i < this.getSubgroupCount(); i++) {
			Extent currentExtent = currentSubgroup.getExtent();
			if (currentExtent.contains(innerCoordinates))
				return this.getSubgroup(i);
			currentSubgroup = currentSubgroup.next;
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
	 * @mutates | this.getParentGroup()
	 */
	public void bringToFront() {
		ShapeGroup parentgroup = this.getParentGroup();
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
	 * @mutates | this.getParentGroup()
	 */
	public void sendToBack() {
		ShapeGroup parentgroup = this.getParentGroup();
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
	public String getDrawingCommands() {
		String commands = "";
		commands += "pushTranslate " + getHorizontalTranslate() + " " + getVerticalTranslate() + System.lineSeparator();
		commands += "pushScale " + getHorizontalScale() + " " + getVerticalScale() + System.lineSeparator();
		if (getShape() != null)
			commands += getShape().getDrawingCommands();
		else {
			ShapeGroup currentSubgroup = this.lastSubgroup;
			for (int i = getSubgroupCount() - 1; i >= 0; i--) {
				commands += currentSubgroup.getDrawingCommands();
				currentSubgroup = currentSubgroup.previous;
			}
		}
		commands += "popTransform " + System.lineSeparator();
		commands += "popTransform " + System.lineSeparator();
		return commands.toString();
	}
	
	/**
	 * Returns the horizontal scale factor to go from inner to outer coordinates.
	 */
	private double getHorizontalScale() {
		return getExtent().getWidth()/(double) getOriginalExtent().getWidth();
	}
	
	/**
	 * Returns the vertical scale factor to go from inner to outer coordinates.
	 */
	private double getVerticalScale() {
		return getExtent().getHeight()/(double) getOriginalExtent().getHeight();
	}
	
	/**
	 * Returns the horizontal translate to go from inner to outer coordinates.
	 */
	private int getHorizontalTranslate() {
		return (int) (getExtent().getLeft() - getOriginalExtent().getLeft() * getHorizontalScale()); 
	}
	
	/**
	 * Returns the vertical translate to go from inner to outer coordinates.
	 */
	private int getVerticalTranslate() {
		return (int) (getExtent().getTop() - getOriginalExtent().getTop() * getVerticalScale());
	}
}

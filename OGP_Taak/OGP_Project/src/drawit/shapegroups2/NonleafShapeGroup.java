package drawit.shapegroups2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import drawit.IntPoint;

public class NonleafShapeGroup extends ShapeGroup {
	
	private LinkedList<ShapeGroup> subgroups;
	ShapeGroup firstSubgroup;
	ShapeGroup lastSubgroup;
	
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
	 * @post This shape groups original extent equals the current one.
	 * 		| this.getExtent() == this.getOriginalExtent()
	 */
	public NonleafShapeGroup(ShapeGroup[] subgroups) {
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
		
		ShapeGroup firstSubgroup = subgroups[0];
		Extent firstExtent = firstSubgroup.getExtent();
		
		int mostLeft = firstExtent.getLeft();
		int mostRight = firstExtent.getRight();
		int mostTop = firstExtent.getTop();
		int mostBottom = firstExtent.getBottom();
		this.firstSubgroup = firstSubgroup;
		this.firstSubgroup.previous = this.firstSubgroup;
		this.firstSubgroup.next = subgroups[1];
		firstSubgroup.parentgroup = this;
		this.subgroups.add(firstSubgroup);
		
		for (int i = 1; i < subgroups.length; i++) {
			ShapeGroup currentSubgroup = subgroups[i];
			currentSubgroup.parentgroup = this;
			if (i == subgroups.length - 1) {
				currentSubgroup.next = currentSubgroup;
				this.lastSubgroup = currentSubgroup;
			}
			else
				currentSubgroup.next = subgroups[i+1];
			currentSubgroup.previous = subgroups[i-1];
			this.subgroups.add(currentSubgroup);
				
			Extent currentExtent = currentSubgroup.getExtent();
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
	 * Returns the list of subgroups of this shape group, or {@code null} if this is a leaf shape group.
	 */
	public List<ShapeGroup> getSubgroups() {
		LinkedList<ShapeGroup> subgroups = new LinkedList<ShapeGroup>();
		if (firstSubgroup != null) {
			ShapeGroup currentSubgroup = firstSubgroup;
			while (!(currentSubgroup.next.equals(currentSubgroup))) {
				subgroups.add(currentSubgroup);
				currentSubgroup = currentSubgroup.next;
			}
			subgroups.add(currentSubgroup);
			return subgroups;
		}
		else
			return null;
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
		ShapeGroup currentSubgroup = null;
		if (index > numberSubgroups/2) {
			currentSubgroup = this.lastSubgroup;
			while (numberSubgroups > index + 1) {
				currentSubgroup = currentSubgroup.previous;
				numberSubgroups--;
			}
		}
		else {
			currentSubgroup = this.firstSubgroup;
			for (int i = 0; i < index; i++) {
				currentSubgroup = currentSubgroup.next;
			}	
		}
		return currentSubgroup;
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

	@Override
	public String getDrawingCommands() {
		String commands = "";
		commands += "pushTranslate " + getHorizontalTranslate() + " " + getVerticalTranslate() + System.lineSeparator();
		commands += "pushScale " + getHorizontalScale() + " " + getVerticalScale() + System.lineSeparator();
		ShapeGroup currentSubgroup = this.lastSubgroup;
		for (int i = getSubgroupCount() - 1; i >= 0; i--) {
			commands += currentSubgroup.getDrawingCommands();
			currentSubgroup = currentSubgroup.previous;
		}
		commands += "popTransform " + System.lineSeparator();
		commands += "popTransform " + System.lineSeparator();
		return commands.toString();
	}

}

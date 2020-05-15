package drawit.shapegroups1;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import drawit.IntPoint;

public class NonleafShapeGroup extends ShapeGroup{
	
	LinkedList<ShapeGroup> subgroups;
	
	
	/**
	 * Initializes this object to represent a non-leaf shape group that directly contains the given subgroups, 
	 * in the given order.
	 * @throws IllegalArgumentException if the given array is {@code null}.
	 * 		| subgroups == null
	 * @throws IllegalArgumentException if any of the subgroups already have a parent.
	 * 		| Arrays.stream(subgroups).anyMatch(subgroup -> subgroup.getParentGroup() != null)
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
		ShapeGroup start = subgroups[0];
		this.subgroups.add(start);
		start.parentgroup = this;
		Extent startExtent = start.getExtent();
		
		int left = startExtent.getLeft();
		int right = startExtent.getRight();
		int top = startExtent.getTop();
		int bottom = startExtent.getBottom();
		
		for (int i = 1; i < subgroups.length; i++) {
			subgroups[i].parentgroup = this;
			this.subgroups.add(subgroups[i]);
			Extent current = subgroups[i].getExtent();
			
			if (current.getLeft() < left)
				left = current.getLeft();
			if (current.getRight() > right)
				right = current.getRight();
			if (current.getTop() < top)
				top = current.getTop();
			if (current.getBottom() > bottom)
				bottom = current.getBottom();
		}
		
		this.extent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		this.originalExtent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		
		
	}
	

	/**
	 * Returns the list of subgroups of this shape group
	 * 
	 * @peerObjects
	 */	
	public List<ShapeGroup> getSubgroups() {
		return List.copyOf(subgroups);
	}
	
	/**
	 * Returns the number of subgroups of this non-leaf shape group.
	 */
	public int getSubgroupCount() {
		return subgroups.size();
	}
	
	/**
	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape group's list of subgroups.
	 */
	public ShapeGroup getSubgroup(int index) {
		return subgroups.get(index);
	}
	
	/**
	 * Return the first subgroup in this non-leaf shape group's list of subgroups whose extent contains the given point, 
	 * expressed in this shape group's inner coordinate system.
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		for (int i = 0; i < this.getSubgroupCount(); i++) {
			Extent current = this.getSubgroup(i).getExtent();
			if (current.contains(innerCoordinates))
				return this.getSubgroup(i);
		}
		return null;
		
	}
	
	@Override
	public String getDrawingCommands() {
		String commands = "";
		commands += "pushTranslate " + getHorizontalTranslate() + " " + getVerticalTranslate() + System.lineSeparator();
		commands += "pushScale " + getHorizontalScale() + " " + getVerticalScale() + System.lineSeparator();
		for (int i = getSubgroupCount() - 1; i >= 0; i--) {
			ShapeGroup currentSubgroup = getSubgroup(i);
			commands += currentSubgroup.getDrawingCommands();	
		}
		commands += "popTransform " + System.lineSeparator();
		commands += "popTransform " + System.lineSeparator();
		return commands.toString();
	}

}

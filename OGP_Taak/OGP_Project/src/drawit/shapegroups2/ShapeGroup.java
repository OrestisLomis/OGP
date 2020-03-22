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
	
	public Extent getExtent() {
		return extent;
	}
	
	public Extent getOriginalExtent() {
		return originalExtent;
	}
	
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
		return subgroups[index];
	}
	
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
	
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		return innerCoordinates;
	}
	
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		return relativeGlobalCoordinates;
	}
	
	public ShapeGroup getSubGroupAt(IntPoint innerCoordinates) {
		return null;
	}
	
	public void setExtent(Extent newExtent) {
		this.extent = newExtent;
	}
	
	public void BringToFront() {
		ShapeGroup parentgroup = this.getParentGroup();
		ShapeGroup[] allSubgroups = parentgroup.getSubgroups();
		ShapeGroup[] newSubgroups = new ShapeGroup[allSubgroups.length];
		boolean thisIndexPassed = false;
		newSubgroups[0] = this;
		for (int i = 1; i < allSubgroups.length; i++) {
			if (allSubgroups[i].equals(this)) 
				thisIndexPassed = true;
			else if (thisIndexPassed)
				newSubgroups[i] = allSubgroups[i];
			else
				newSubgroups[i] = allSubgroups[i-1];
			parentgroup.subgroups = newSubgroups;
		}
	}
	
	public void SendToBack() {
		ShapeGroup parentgroup = this.getParentGroup();
		ShapeGroup[] allSubgroups = parentgroup.getSubgroups();
		ShapeGroup[] newSubgroups = new ShapeGroup[allSubgroups.length];
		boolean thisIndexPassed = false;
		newSubgroups[allSubgroups.length-1] = this;
		for (int i = 0; i < allSubgroups.length - 1; i++) {
			if (allSubgroups[i].equals(this)) 
				thisIndexPassed = true;
			else if (thisIndexPassed)
				newSubgroups[i] = allSubgroups[i+1];
			else
				newSubgroups[i] = allSubgroups[i];
			parentgroup.subgroups = newSubgroups;
		}
	}
	
	public String getDrawingCommands() {
		return null;
	}
	
}

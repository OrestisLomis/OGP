package drawit.shapegroups2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ShapeGroup {
	
	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	private Extent extent;

	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
	}
	
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subgroups = subgroups;
	}
	
	public Extent getExtent() {
		return extent;
	}
	
	public Extent getOriginalExtent() {
		return extent;
	}
	
	public ShapeGroup getParentGroup() {
		return this;
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
		return globalCoordinates;
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
		
	}
	
	public void SendToBack() {
		
	}
	
	public String getDrawingCommands() {
		return null;
	}
	
}

package drawit.shapes1.tests;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.ShapeGroup;
import drawit.shapes1.ControlPoint;
import drawit.shapes1.ShapeGroupShape;

class ShapeGroupShapeTest {

	@Test
	void test() {
		
		RoundedPolygon myPolygon1 = new RoundedPolygon();
		RoundedPolygon myPolygon2 = new RoundedPolygon();		
		IntPoint[] myVertices = {new IntPoint(0,0), new IntPoint(0,200), new IntPoint(200,200), new IntPoint(200,0)};
		myPolygon2.setVertices(myVertices);

		LeafShapeGroup SubGroup1 = new LeafShapeGroup(myPolygon1);
		LeafShapeGroup SubGroup2 = new LeafShapeGroup(myPolygon2);
		
		ShapeGroup[] subgroups = new ShapeGroup[] {SubGroup1, SubGroup2};
		ShapeGroup mygroup = new NonleafShapeGroup(subgroups);
		
		ShapeGroupShape myshape = new ShapeGroupShape(mygroup);
		
		assert myshape.getShapeGroup() == mygroup;
		assert myshape.getDrawingCommands().equals(mygroup.getDrawingCommands());
		assert myshape.getParent() == null;
		
		assert myshape.contains(new IntPoint(0,200));
		assert myshape.contains(new IntPoint(100,100));
		assert myshape.contains(new IntPoint(100,200));
		
		assert myshape.toGlobalCoordinates(new IntPoint(-100,-100)).equals(new IntPoint(-100,-100));
		assert myshape.toGlobalCoordinates(new IntPoint(200, 200)).equals(new IntPoint(200, 200));
		assert myshape.toGlobalCoordinates(new IntPoint(50, 50)).equals(new IntPoint(50, 50));
		assert myshape.toShapeCoordinates(new IntPoint(100,200)).equals(new IntPoint(100,200));
		assert myshape.toShapeCoordinates(new IntPoint(100,100)).equals(new IntPoint(100,100));
		assert myshape.toShapeCoordinates(new IntVector(1,3)).getX() == 1;
		assert myshape.toShapeCoordinates(new IntVector(1,3)).getY() == 3;
		
		LeafShapeGroup SubGroup3 = new LeafShapeGroup(new RoundedPolygon());
		ShapeGroup[] subgroups2 = new ShapeGroup[] {SubGroup3,mygroup};
		ShapeGroup mygroup2 = new NonleafShapeGroup(subgroups2);
		Extent newExtent = Extent.ofLeftTopRightBottom(0,100,100,200);
		mygroup2.setExtent(newExtent);
		
		ShapeGroupShape myshape2 = new ShapeGroupShape(mygroup2);
		
		assert myshape2.getShapeGroup() == mygroup2;
		assert myshape2.getParent() == null;
		assert myshape2.getDrawingCommands().equals(mygroup2.getDrawingCommands());
		
		assert myshape2.toGlobalCoordinates(new IntPoint(-100,-100)).equals(new IntPoint(0, 100));
		assert myshape2.toGlobalCoordinates(new IntPoint(200, 200)).equals(new IntPoint(100, 200));
		assert myshape2.toGlobalCoordinates(new IntPoint(50, 50)).equals(new IntPoint(50, 150));
		assert myshape.toShapeCoordinates(new IntPoint(100,200)).equals(new IntPoint(200,200));
		assert myshape.toShapeCoordinates(new IntPoint(100,100)).equals(new IntPoint(200,-100));
		assert myshape.toShapeCoordinates(new IntVector(1,3)).getX() == 3;
		assert myshape.toShapeCoordinates(new IntVector(1,3)).getY() == 9;
		
		ControlPoint[] controlpoints = myshape.createControlPoints();
		
		assert controlpoints.length == 2;
		assert controlpoints[0].getLocation().equals(myshape.getShapeGroup().getExtent().getTopLeft());
		assert controlpoints[1].getLocation().equals(myshape.getShapeGroup().getExtent().getBottomRight());
	}

}

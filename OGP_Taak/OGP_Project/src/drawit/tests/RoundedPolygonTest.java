package drawit.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;

class RoundedPolygonTest {

	@Test
	void test() {
		RoundedPolygon myRoundedPolygon = new RoundedPolygon();
		IntPoint myIntPoint = new IntPoint(-100, -100);
		IntPoint myIntPoint2 = new IntPoint(-100, 100);
		IntPoint myIntPoint3 = new IntPoint(100, 100);
		IntPoint myIntPoint4 = new IntPoint(100, -100);
		
		IntPoint[] myIntPoints = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint4};
		IntPoint[] myIntPoints2 = {myIntPoint, myIntPoint2};
		
		myRoundedPolygon.setVertices(myIntPoints);
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.setVertices(myIntPoints2));
		
		assert myRoundedPolygon.getRadius() == 10;
		
		myRoundedPolygon.setRadius(20);
		assert myRoundedPolygon.getRadius() == 20;
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.setRadius(-20));
		
		IntPoint myIntPoint5 = new IntPoint(0, -100);
		IntPoint[] myIntPoints3 = {myIntPoint, myIntPoint2, myIntPoint3, myIntPoint4, myIntPoint5};
		
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.insert(-2, myIntPoint5));
		myRoundedPolygon.insert(4, myIntPoint5);
		for (int i = 0; i < myIntPoints3.length; i++) {
			assert myRoundedPolygon.getVertices()[i] == myIntPoints3[i];
		}
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.insert(0, myIntPoint5));
		
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.remove(-2));
		myRoundedPolygon.remove(4);
		for (int i = 0; i < myIntPoints.length; i++) {
			assert myRoundedPolygon.getVertices()[i] == myIntPoints[i];
		}
		
		IntPoint[] myIntPoints4 = {myIntPoint, myIntPoint2, myIntPoint3};
		RoundedPolygon myRoundedPolygon2 = new RoundedPolygon();
		myRoundedPolygon2.setVertices(myIntPoints4);
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon2.remove(0));
		
		IntPoint myIntPoint6 = new IntPoint(200, 200);
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon2.update(-2, myIntPoint6));
		assert !(myRoundedPolygon.contains(myIntPoint6));
		IntPoint[] myIntPoints5 = {myIntPoint, myIntPoint2, myIntPoint6, myIntPoint4};
		myRoundedPolygon.update(2, myIntPoint6);
		for (int i = 0; i < myIntPoints5.length ; i++) {
			assert myRoundedPolygon.getVertices()[i] == myIntPoints5[i];
		}
		assertThrows(IllegalArgumentException.class, () -> myRoundedPolygon.update(0, myIntPoint6));
		
		IntPoint origin = new IntPoint(0, 0);
		assert myRoundedPolygon.contains(origin);
		
		assert myRoundedPolygon.contains(myIntPoint5);
		assert myRoundedPolygon.contains(myIntPoint);
		
		
		IntPoint myIntPoint7 = new IntPoint(-200, -100);
		IntPoint myIntPoint8 = new IntPoint(-200, 100);
		IntPoint myIntPoint9 = new IntPoint(-200, 150);
		
		assert !(myRoundedPolygon.contains(myIntPoint7));
		assert !(myRoundedPolygon.contains(myIntPoint8));
		assert !(myRoundedPolygon.contains(myIntPoint9));
		
		
		IntPoint myIntPoint10 = new IntPoint(-10, -10);
		IntPoint myIntPoint11 = new IntPoint(0, -10);
		IntPoint myIntPoint12 = new IntPoint(10, -10);
		IntPoint myIntPoint13 = new IntPoint(20, 20);
		IntPoint myIntPoint14 = new IntPoint(0, 0);
		IntPoint myIntPoint15 = new IntPoint(-10, 20);
		IntPoint myIntPoint16 = new IntPoint(-20, 20);
		IntPoint myIntPoint17 = new IntPoint(0, 10);
		IntPoint[] myIntPoints6 = {myIntPoint16, myIntPoint10, myIntPoint11, myIntPoint12, myIntPoint13, myIntPoint14, myIntPoint15};
		RoundedPolygon myRoundedPolygon3 = new RoundedPolygon();
		myRoundedPolygon3.setVertices(myIntPoints6);
		assert !(myRoundedPolygon3.contains(myIntPoint17));
		
		assertEquals("line -100.0 0.0 -100.0 85.58481559887747\r\n" + 
				"arc -80.0 85.58481559887747 20.0 3.141592653589793 -1.2490457723982544\r\n" + 
				"line -86.32455532033676 104.55848155988775 50.0 150.0\r\n" + 
				"line 50.0 150.0 162.05266807797946 187.3508893593265\r\n" + 
				"arc 168.3772233983162 168.3772233983162 20.0 1.8925468811915387 -2.214297435588181\r\n" + 
				"line 187.3508893593265 162.05266807797946 150.0 50.0\r\n" + 
				"line 150.0 50.0 104.55848155988774 -86.32455532033677\r\n" + 
				"arc 85.58481559887747 -80.0 20.0 -0.32175055439664213 -1.2490457723982544\r\n" + 
				"line 85.58481559887747 -100.0 0.0 -100.0\r\n" + 
				"line 0.0 -100.0 -80.0 -100.0\r\n" + 
				"arc -80.0 -80.0 20.0 -1.5707963267948966 -1.5707963267948966\r\n" + 
				"line -100.0 -80.0 -100.0 0.0\r\n", myRoundedPolygon.getDrawingCommands());
		
		assertEquals("line -15.0 5.0 -10.92450813543146 -7.226475593705624\r\n" + 
				"arc -7.076448576681175 -5.943789074122196 4.056210925877804 -2.819842099193151 1.2490457723982544\r\n" + 
				"line -7.076448576681176 -10.0 -5.0 -10.0\r\n" + 
				"line -5.0 -10.0 0.0 -10.0\r\n" + 
				"line 0.0 -10.0 5.0 -10.0\r\n" + 
				"line 5.0 -10.0 7.076448576681175 -10.0\r\n" + 
				"arc 7.076448576681175 -5.943789074122196 4.056210925877804 -1.5707963267948966 1.2490457723982544\r\n" + 
				"line 10.92450813543146 -7.226475593705624 15.0 5.0\r\n" + 
				"line 15.0 5.0 15.647498201034358 6.942494603103071\r\n" + 
				"arc 12.56503931079631 7.9699808998490855 3.2491969623290626 -0.32175055439664213 2.677945044588987\r\n" + 
				"line 10.2675101053227 10.2675101053227 10.0 10.0\r\n" + 
				"line 10.0 10.0 6.413432597917138 6.413432597917138\r\n" + 
				"arc 1.790891920759845 11.035973275074435 6.537259718257157 -0.7853981633974483 -1.8925468811915387\r\n" + 
				"line -4.056210925877804 8.112421851755608 -5.0 10.0\r\n" + 
				"line -5.0 10.0 -8.824429495415053 17.648858990830107\r\n" + 
				"arc -12.628655560595668 15.7467459582398 4.2532540417602 0.46364760900080615 1.1071487177940904\r\n" + 
				"line -12.628655560595668 20.0 -15.0 20.0\r\n" + 
				"line -15.0 20.0 -15.943789074122195 20.0\r\n" + 
				"arc -15.943789074122195 17.076448576681177 2.9235514233188242 -4.71238898038469 1.8925468811915387\r\n" + 
				"line -18.71731348041657 16.151940441249717 -15.0 5.0\r\n" + 
				"", myRoundedPolygon3.getDrawingCommands());
	}

}

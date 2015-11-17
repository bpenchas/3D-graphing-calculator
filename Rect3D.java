import java.awt.Color;

import acm.graphics.GPoint;
import acm.graphics.GPolygon;


public class Rect3D {
	Point3D cornerOne;
	Point3D cornerTwo;
	Point3D cornerThree;
	Point3D cornerFour;
	
	public Rect3D(Point3D cornerOne, Point3D cornerTwo, Point3D cornerThree, Point3D cornerFour) {
		this.cornerOne = cornerOne;
		this.cornerTwo = cornerTwo;
		this.cornerThree = cornerThree;
		this.cornerFour = cornerFour;
		
	}
	
	public GPolygon to2D() {
		GPoint tempCornerOne = this.cornerOne.to2D();
		GPoint tempCornerTwo = this.cornerTwo.to2D();
		GPoint tempCornerThree = this.cornerThree.to2D();
		GPoint tempCornerFour = this.cornerFour.to2D();
		if (tempCornerOne == null || tempCornerTwo == null || tempCornerThree == null || tempCornerFour == null) {
			return null;
		}
		GPolygon tempPolygon = new GPolygon(new GPoint[] {tempCornerOne, tempCornerTwo, tempCornerThree, tempCornerFour});
		tempPolygon.setFilled(true);
		tempPolygon.setFillColor(Color.RED);
		//tempPolygon.setFillColor(new Color(255 / (int) (cornerOne.getZ() + 1), 255 / (int) (cornerOne.getZ() + 1), 255 / (int) (cornerOne.getZ() + 1)));
		return tempPolygon;
		
	}
	
	public Rect3D rotate(double theta, double phi){
		return new Rect3D(cornerOne.rotate(theta, phi), cornerTwo.rotate(theta, phi), cornerThree.rotate(theta, phi), cornerFour.rotate(theta, phi));
	}

}

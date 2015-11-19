import java.awt.Color;

import acm.graphics.GPoint;
import acm.graphics.GPolygon;


public class Rect3D {
	Point3D cornerOne;
	Point3D cornerTwo;
	Point3D cornerThree;
	Point3D cornerFour;
	
	GPoint cornerOne2D;
	GPoint cornerTwo2D;
	GPoint cornerThree2D;
	GPoint cornerFour2D;
	
	public Rect3D(Point3D cornerOne, Point3D cornerTwo, Point3D cornerThree, Point3D cornerFour) {
		this.cornerOne = cornerOne;
		this.cornerTwo = cornerTwo;
		this.cornerThree = cornerThree;
		this.cornerFour = cornerFour;
		
	}
	
	public GPolygon to2D() {
//		GPoint tempCornerOne = this.cornerOne.to2D();
//		GPoint tempCornerTwo = this.cornerTwo.to2D();
//		GPoint tempCornerThree = this.cornerThree.to2D();
//		GPoint tempCornerFour = this.cornerFour.to2D();
//		if (tempCornerOne == null || tempCornerTwo == null || tempCornerThree == null || tempCornerFour == null) {
//			return null;
//		}
		return new GPolygon(new GPoint[] {cornerOne2D, cornerTwo2D, cornerThree2D, cornerFour2D});
//		tempPolygon.setFilled(true);
//		tempPolygon.setFillColor(Color.RED);
		//tempPolygon.setFillColor(new Color(255 / (int) (cornerOne.getZ() + 1), 255 / (int) (cornerOne.getZ() + 1), 255 / (int) (cornerOne.getZ() + 1)));
		
		
	}
	
	public void rotate(double theta, double phi){
		cornerOne2D.setLocation(cornerOne.rotate(theta, phi).to2D());
		cornerTwo2D.setLocation(cornerTwo.rotate(theta, phi).to2D());
		cornerThree2D.setLocation(cornerThree.rotate(theta, phi).to2D());
		cornerFour2D.setLocation(cornerFour.rotate(theta, phi).to2D());
	}

}

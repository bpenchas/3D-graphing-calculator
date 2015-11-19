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
	GPolygon polygon;
	
	public Rect3D(Point3D cornerOne, Point3D cornerTwo, Point3D cornerThree, Point3D cornerFour) {
		this.cornerOne = cornerOne;
		this.cornerTwo = cornerTwo;
		this.cornerThree = cornerThree;
		this.cornerFour = cornerFour;
		this.cornerOne2D = cornerOne.to2D();
		this.cornerTwo2D = cornerTwo.to2D();
		this.cornerThree2D = cornerThree.to2D();
		this.cornerFour2D = cornerFour.to2D();
		this.polygon = new GPolygon(new GPoint[] {cornerOne2D, cornerTwo2D, cornerThree2D, cornerFour2D});
	}
	
	public GPolygon to2D() {
//		GPoint tempCornerOne = this.cornerOne.to2D();
//		GPoint tempCornerTwo = this.cornerTwo.to2D();
//		GPoint tempCornerThree = this.cornerThree.to2D();
//		GPoint tempCornerFour = this.cornerFour.to2D();
//		if (tempCornerOne == null || tempCornerTwo == null || tempCornerThree == null || tempCornerFour == null) {
//			return null;
//		}
//		GPolygon temp = new GPolygon(new GPoint[] {cornerOne2D, cornerTwo2D, cornerThree2D, cornerFour2D});
//		
//		tempPolygon.setFilled(true);
//		tempPolygon.setFillColor(Color.RED);
		//tempPolygon.setFillColor(new Color(255 / (int) (cornerOne.getZ() + 1), 255 / (int) (cornerOne.getZ() + 1), 255 / (int) (cornerOne.getZ() + 1)));
		return this.polygon;
		
	}
	
	public void rotate(double theta, double phi){
		//cornerOne2D.setLocation(cornerOne.rotate(theta, phi).to2D());
	
		cornerOne2D.setLocation(0, 0);
		cornerTwo2D.setLocation(cornerTwo.rotate(theta, phi).to2D());
		cornerThree2D.setLocation(cornerThree.rotate(theta, phi).to2D());
		cornerFour2D.setLocation(cornerFour.rotate(theta, phi).to2D());
	}

}

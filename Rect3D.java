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
	
	private GPolygon to2D() {
		return new GPolygon(new GPoint[] {this.cornerOne.to2D(), this.cornerTwo.to2D(), this.cornerThree.to2D(), this.cornerFour.to2D()});
	}
	
	public Rect3D rotate(double theta, double phi){
		return new Rect3D(cornerOne.rotate(theta, phi), cornerTwo.rotate(theta, phi), cornerThree.rotate(theta, phi), cornerFour.rotate(theta, phi));
	}

}

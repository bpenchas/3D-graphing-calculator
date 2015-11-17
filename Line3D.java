
import acm.graphics.GLine;
import acm.graphics.GPoint;


public class Line3D {
		private Point3D first;
		private Point3D second;
		private GLine lineFromPoints(GPoint first, GPoint second) {
			return new GLine(first.getX(), first.getY(), second.getX(), second.getY());
		}

		public GLine to2D() {
			return lineFromPoints(first.to2D(), second.to2D());
		}
		
		public Line3D(Point3D first, Point3D second) {
			this.first = first;
			this.second = second;
		}
		
		public Line3D rotate(double theta, double phi){
			return new Line3D(first.rotate(theta, phi), second.rotate(theta, phi));
		}
	}
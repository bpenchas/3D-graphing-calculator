/*
 * File: GraphicsContest.java

 * --------------------------
 */

import acm.program.*;

import acm.graphics.*;

public class GraphicsContest extends GraphicsProgram {

	private class Point3D {
		private double x;
		private double y;
		private double z;
		
		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public double getZ() {
			return z;
		}

		public void setZ(double z) {
			this.z = z;
		}

		public void setX(double x) {
			this.x = x;
		}
		
		public Point3D(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	public void run() {
		
		
		Point3D myPoint = new Point3D(5, 5, 5);
		Point3D secondPoint = new Point3D(10, 10, 2);
		GPoint first = projection(myPoint);
		GPoint second = projection(secondPoint);
		GLine tobinLine = lineFromPoints(first, second);
		add(tobinLine);
		
	}
	
	private GPoint projection(Point3D pt) {
		GPoint point = new GPoint(pt.x * Math.exp(pt.z), pt.y * Math.exp(pt.z));
		return point;
	}
	
	private GLine lineFromPoints(GPoint first, GPoint second) {
		return new GLine(first.getX(), first.getY(), second.getX(), second.getY());
	}

}

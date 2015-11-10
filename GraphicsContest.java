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
		
		
		Point3D myPoint = new Point3D(5, 5, 0);
		Point3D secondPoint = new Point3D(0, 0, 0);
		GPoint first = toPixel(projection3Dto2D(myPoint));
		GPoint second = toPixel(projection3Dto2D(secondPoint));
		GLine tobinLine = lineFromPoints(first, second);
		add(tobinLine);
		
	}
	
	private GPoint projection3Dto2D(Point3D pt) {
		GPoint point = new GPoint(pt.x * Math.exp(pt.z), pt.y * Math.exp(pt.z));
		return point;
	}
	
	private GLine lineFromPoints(GPoint first, GPoint second) {
		return new GLine(first.getX(), first.getY(), second.getX(), second.getY());
	}
	
	private GPoint toPixel(GPoint pt) {
		return new GPoint((pt.getX() + 10) / 20 * getWidth(), (10 - pt.getY()) / 20 * getHeight());
	}

}

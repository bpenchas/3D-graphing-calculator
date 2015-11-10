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
		
		public GPoint to2D() {
			return new GPoint(x * Math.exp(z), y * Math.exp(z));
		}
	}
	
	private class Line3D {
		private Point3D first;
		private Point3D second;

		private GLine to2D() {
			return lineFromPoints(first.to2D(), second.to2D());
		}
		
		public Line3D(Point3D first, Point3D second) {
			this.first = first;
			this.second = second;
		}
	}
	
	public void run() {
		Point3D first = new Point3D(5, 5, 0);
		Point3D second = new Point3D(0, 0, 0);
		Line3D tobinLine = new Line3D(first, second);
		
		while(true) {
			removeAll();
			add(tobinLine.to2D());
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	private GLine lineFromPoints(GPoint first, GPoint second) {
		return new GLine(first.getX(), first.getY(), second.getX(), second.getY());
	}
	
	private GPoint toPixel(GPoint pt) {
		return new GPoint((pt.getX() + 10) / 20 * getWidth(), (10 - pt.getY()) / 20 * getHeight());
	}
	

}

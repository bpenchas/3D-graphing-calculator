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
		
		public Point3D rotate(double theta) {
			return new Point3D(x * Math.cos(theta) + 0 - z * Math.sin(theta), y, x * Math.sin(theta) + 0 + z * Math.cos(theta));
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
		
		public Line3D rotate(double theta){
			return new Line3D(first.rotate(theta), second.rotate(theta));
		}
	}
	
	public void run() {
		Point3D first = new Point3D(5, 5, 0);
		Point3D second = new Point3D(0, 0, 0);
		Line3D tobinLine = new Line3D(first, second);
		
		Point3D z = new Point3D(0, 0, 10);
		Point3D y = new Point3D(0, 10, 0);
		Point3D x = new Point3D(10, 0, 0);
		Point3D origin = new Point3D(0, 0, 0);
		
		Line3D xAxis = new Line3D(origin, x);
		Line3D yAxis = new Line3D(origin, y);
		Line3D zAxis = new Line3D(origin, z);
		
		double theta = 0;
		
		while(true) {
			removeAll();
			add(toPixel(tobinLine.rotate(theta).to2D()));
			theta += 0.01;
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
	
	private GLine toPixel(GLine line) {
		return lineFromPoints(toPixel(line.getStartPoint()), toPixel(line.getEndPoint()));
	}
	

}

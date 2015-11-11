/*
 * File: GraphicsContest.java

 * --------------------------
 */

import acm.program.*;
import acm.graphics.*;

public class GraphicsContest extends GraphicsProgram {
	
	public double theta = 0;
	public double phi = 0;
	private double costheta = Math.cos(theta);
	private double sintheta = Math.sin(theta);
	private double cosphi = Math.cos(phi);
	private double sinphi = Math.sin(phi);
	
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
			return new GPoint(x * Math.pow(1.1, z), y * Math.pow(1.1, z));
		}
		
		public Point3D rotate() {
			return new Point3D(x * costheta - z * sintheta, x * -sintheta * sinphi + y * cosphi - z* sinphi * costheta, x* sintheta * cosphi + y * sinphi + z * costheta * cosphi);
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
		
		public Line3D rotate(){
			return new Line3D(first.rotate(), second.rotate());
		}
	}
	
	public void run() {
		Point3D z = new Point3D(0, 0, 10);
		Point3D y = new Point3D(0, 10, 0);
		Point3D x = new Point3D(10, 0, 0);
		Point3D origin = new Point3D(0, 0, 0);
		
		Line3D xAxis = new Line3D(origin, x);
		Line3D yAxis = new Line3D(origin, y);
		Line3D zAxis = new Line3D(origin, z);
		
		
		
		while(true) {
			removeAll();
			add(toPixel(xAxis.rotate().to2D()));
			add(toPixel(yAxis.rotate().to2D()));
			add(toPixel(zAxis.rotate().to2D()));
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

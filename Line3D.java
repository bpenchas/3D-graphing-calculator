
import java.awt.Color;

import acm.graphics.GLine;
import acm.graphics.GPoint;


public class Line3D {
		private Point3D first;
		private Point3D second;
		private GLine lineFromPoints(GPoint first, GPoint second) {
			return new GLine(first.getX(), first.getY(), second.getX(), second.getY());
		}
		private GLine line2D;

		public GLine to2D() {
			return line2D;
		}
		
		
		public Line3D(Point3D first, Point3D second) {
			int colorCounter = GraphicsContest.mainGraphicsContest.colorCounter;
			this.first = first;
			this.second = second;
			this.line2D = lineFromPoints(first.to2D(), second.to2D());
			Color current = new Color(colorCounter, colorCounter, colorCounter);
			this.line2D.setColor(current);
			System.out.println(colorCounter);
		}
		
		public void rotate(double theta, double phi) {
			GPoint firstPt = first.rotate(theta, phi).to2D();
			GPoint secondPt = second.rotate(theta, phi).to2D();
			if (firstPt == null || secondPt == null) {
				return;
			}
			line2D.setStartPoint(firstPt.getX(), firstPt.getY());
			line2D.setEndPoint(secondPt.getX(), secondPt.getY());
		}
	}
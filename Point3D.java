
import acm.graphics.GPoint;



public class Point3D {
		private double x;
		private double y;
		private double z;
		
		
		private double costheta;
		private double sintheta;
		private double cosphi;
		private double sinphi;

		public static double cameraToPlane = 45;
		public static double cameraToOrigin = 50;
		
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
			return new GPoint((x * cameraToPlane / (cameraToOrigin - z)  + 10) / 20 *GraphicsContest.APPLICATION_WIDTH, (10 - y * cameraToPlane / (cameraToOrigin - z)) / 20 * GraphicsContest.APPLICATION_HEIGHT );
		}
		
		public Point3D rotate(double theta, double phi) {
			costheta = Math.cos(theta);
			sintheta = Math.sin(theta);
			cosphi = Math.cos(phi);
			sinphi = Math.sin(phi);
			return new Point3D(x * costheta - z * sintheta, x * -sintheta * sinphi + y * cosphi - z* sinphi * costheta, x* sintheta * cosphi + y * sinphi + z * costheta * cosphi);
		}
	}

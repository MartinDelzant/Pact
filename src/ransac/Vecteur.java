package ransac;


public class Vecteur {

	
		
		//private Point3D a, b;
		private double x,y,z;
		
		public Vecteur(double x, double y, double z){
			
			this.x = x;
			this.y = y;
			this.z = z;		
		}
		
		public Vecteur(Point3D a, Point3D b){
			
			this.x = a.getX() - b.getX();
			this.y = a.getY() - b.getY();
			this.z = a.getZ() - b.getZ();		
		}
		

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getZ() {
			return z;
		}
		
		public void setX(Point3D a, Point3D b) {
			this.x = a.getX() - b.getX();
		}

		public void setY(Point3D a, Point3D b) {
			this.y = a.getY() - b.getY();
		}

		public void setZ(Point3D a,Point3D b) {
			this.x = a.getZ() - b.getZ();
		}
		public void setX(double x) {
			this.x = x;
		}

		public void setY(double y) {
			this.y = y;
		}
		public void setZ(double z) {
			this.z = z;
		}
	}



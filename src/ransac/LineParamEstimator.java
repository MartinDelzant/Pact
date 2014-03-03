package ransac;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 * This is a estimator for 2D lines based on the equation:
 *     n_x*(x-a_x)+n_y*(y-a_y) +n_z*(z-a_z)=0
 * We use 4 parameters (n_x, n_y, a_x, a_y) to represent a line,
 * in which
 * (n_x, n_y,n_z) is the normal vector of the line, and
 * (n_x*n_x+n_y*n_y)=1.
 * (a_x, a_y,a_z) is a point one the line.
 * </pre>
 * 
 * @author tiger
 */
public class LineParamEstimator implements ParameterEstimator<Point3D, Double> {
        private double deltaSquared;

        public LineParamEstimator(double delta) {
                this.deltaSquared = delta * delta;
        }
        // calculer le produit vectoriel de deux vecteurs
        public Vecteur vectoriel(Vecteur v1, Vecteur v2){
    		
    		Vecteur v3 = null;
    		
    		
    			v3.setX((v1.getY()*v2.getZ())-(v1.getZ()*v2.getY()));
    			v3.setY((v1.getZ()*v2.getX())-(v1.getX()*v2.getZ()));
    			v3.setZ((v1.getX()*v2.getY())-(v1.getY()*v2.getX()));
    					
    			return v3;
    	}	
        //tester l'alignement de 3 points
        public boolean testAlignement(Point3D a,Point3D b,Point3D c){
        	Vecteur v1=null,v2=null,v3;
        	v1.setX(a, b);
        	v1.setY(a, b);
        	v1.setZ(a, b);
        	
        	v2.setX(a, c);
        	v2.setY(a, c);
        	v2.setZ(a, c);
        	
        	v3=vectoriel(v1,v2);
        	if(v3==null)
        	return true;
        	else
        	return false;
        	
        }
        
        
        //*****************************dire si un point est à conserver dans l'estimation du plan ou non**************
        public boolean agree(List<Double> params, Point3D data) {
              //ce sont les valeurs caractéristiques du plan (nX,nY,nZ) vecteur normal et(aX,aY,aZ) point du plan  
        		double nX = params.get(0);
                double nY = params.get(1);
                double nZ = params.get(2);
                double aX = params.get(3);
                double aY = params.get(4);
                double aZ = params.get(5);
               
                double pX = data.getX();
                double pY = data.getY();
                double pZ = data.getZ();
                double norme=nX * nX + nY * nY + nZ * nZ;
                double d = (nX * (pX - aX) + nY * (pY - aY)+nZ * (pZ - aZ))/Math.pow(norme,1/2);
                return ((d * d) < deltaSquared);
        }

        
        //***************************************************************************************************//
         //Calculer à partir une liste de points non alignés le plan correspandant
         
        public List<Double> estimerUnPlan(List<Point3D> data) {
                if (data.size() < 3) {
                        return null;
                }
               
           
         Vecteur v1 = null,v2 = null,normal;
         //les 3 premieres points donnés supposés non alignés 
       
       v1.setX(data.get(0),data.get(1)); 
       v2.setX(data.get(0), data.get(2));
       
       v1.setY(data.get(0),data.get(1)); 
       v2.setY(data.get(0), data.get(2));
       
       v1.setZ(data.get(0),data.get(1)); 
       v2.setZ(data.get(0), data.get(2));
       
       normal=vectoriel(v1,v2);
       double norm = Math.sqrt(normal.getX()*normal.getX() + normal.getY() * normal.getY() + normal.getZ() * normal.getZ() );
       List<Double> params = new ArrayList<Double>();
       params.add(normal.getX() / norm);
       params.add(normal.getY() / norm);
       params.add(normal.getZ() / norm);
       params.add(data.get(0).getX());
       params.add(data.get(0).getY());
       params.add(data.get(0).getZ());
       return params;
         
        }
        //résoudre AI=B ou I est le vecteur inconnu
        //A=[somme(xi^2), somme(xi*yi), somme(xi)     ]
        //	[somme(xi*yi), somme(yi^2), somme(yi)     ]
        //	[somme( xi ), somme( yi  ), nbe de données]
        
        //B=[somme(xi*zi)]
        //	[somme(yi*zi)]
        //	[somme(  zi )]
        public List<Double> FonctionMoindreCarre(List<Point3D> data) {
                int dataSize = data.size();
                if (dataSize < 3) {
                        return null;
                }
                if (dataSize == 3) {
                        return estimerUnPlan(data);
                }
                double nX, nY, nZ , norm;
                double pointX = 0.0;
                double pointY = 0.0;
                double pointZ = 0.0;
                // Les valeurs de la matrices
                
                double M11 = 0.0;
                double M12 = 0.0;
                double M13 = 0.0;
                double M21 = 0.0;
                double M22 = 0.0;
                double M23 = 0.0;
                double M31 = 0.0;
                double M32 = 0.0;
                double M33 = 0.0;
                //les valeurs de B
                
                double B11 = 0.0;
                double B12 = 0.0;
                double B13 = 0.0;
                
                
                for (int i = 0; i < dataSize; i++) {
                        pointX += data.get(i).getX();
                        pointY += data.get(i).getY();
                        pointZ += data.get(i).getZ();
                        //sommes des xi^2
                        M11 = M11+data.get(i).getX() * data.get(i).getX();
                        //sommes des yi*xi
                        M12 = M12+data.get(i).getX() * data.get(i).getY();
                        //sommes des xi
                        M13 = M13+data.get(i).getX();
                        //sommes des yi^2
                        M22 = M22+data.get(i).getY() * data.get(i).getY();
                        //sommes des yi
                        M23 =M23+ data.get(i).getY() ;
                        
                        //sommes des zi*xi
                        B11 = B11+data.get(i).getX() * data.get(i).getZ();
                        //sommes des yi*zi
                        B12 = B12+data.get(i).getZ() * data.get(i).getY();
                        //sommes des z
                        B13 =B13+ data.get(i).getY() ;
                }

                pointX /= dataSize;
                pointY /= dataSize;
                pointZ /= dataSize;
               
                
                M21 = M12;
                M31 = M13;
                M32 = M23;
                
                
                
                double[][] M={{M11,M21,M31},{M12,M22,M32},{M13,M23,M33}};
                double[] B={B11,B12,B13};
             
                double[][] invM=inverse(M); 
                double[] m1=multiplyMatVec(invM,B);
                
                nX=m1[0];
                nY=m1[1];
                nZ=m1[2];
                norm = Math.sqrt(nX * nX + nY * nY +nZ * nZ);
                nX /= norm;
                nY /= norm;	
                nZ /= norm;
                
                ArrayList<Double> parameters = new ArrayList<Double>();
                parameters.add(nX);
                parameters.add(nY);
                parameters.add(nZ);
                parameters.add(pointX);
                parameters.add(pointY);
                parameters.add(pointZ);
                return parameters;
        }
      
    //*****************************************************************************************************************
        
		//multiplier une matrice par un vecteur
		public double[] multiplyMatVec(double[][] invM, double[] b) 
		{					
			double[] r = new double[3];
			for(int i = 0 ; i < r.length; ++i) 
	        	for(int k = 0; k < b.length; ++k) 
	                r[i] += invM[i][k]*b[k];
			return r;
		}
		
		
		//inverser une matrice 3*3
		private double[][] inverse(double[][] a) {
		
			double[][] invM=new double[3][3];
			double det=(a[0][0] * (a[1][1] * a[2][2] - a[2][1] * a[1][2])-a[1][0] * (a[0][1] * a[2][2] - a[2][1] * a[0][2]) +a[2][0] * (a[0][1] * a[1][2] - a[1][1] * a[0][2]));
			
			invM[0][0]= (1/det)*(a[1][1] * a[2][2] - a[2][1] * a[1][2]);
			invM[0][1]= (1/det)*(a[1][2] * a[2][1] - a[2][2] * a[1][0]);
			invM[0][2]= (1/det)*(a[1][0] * a[2][1] - a[2][0] * a[1][1]);
			invM[1][0]= (1/det)*(a[0][2] * a[2][1] - a[2][2] * a[0][1]);
			invM[1][1]= (1/det)*(a[0][0] * a[2][2] - a[2][0] * a[0][2]);
			invM[1][2]= (1/det)*(a[0][1] * a[2][0] - a[2][1] * a[0][0]);
			invM[2][0]= (1/det)*(a[0][1] * a[1][2] - a[1][1] * a[0][2]);
			invM[2][1]= (1/det)*(a[0][2] * a[1][0] - a[1][2] * a[0][0]);
			invM[2][2]= (1/det)*(a[0][0] * a[1][1] - a[1][0] * a[0][1]);
			
			return invM;
					
		}

}


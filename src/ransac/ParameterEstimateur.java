package ransac;

import java.util.List;
import java.util.List;

	
	public interface ParameterEstimateur {
	       
		/*les donnees exact d'estimation data*/
		/*Ce vecteur est vidé puis rempli avec les parametres calculés*/
	public  List<Double> estimerUnPlan(Point3D a,Point3D b,Point3D c);

	        /*Les parametres de FonctionMoindreCarre
	         data 
	          parameters
	         */
	public List<Double> FonctionMoindreCarre(List<Point3D> data);

	        /*
	         parameters:
	         Les parametres du model estimé.
	         data:
	         Les données pour tester.
	         */
	public boolean agree(List<Double> parameters, Point3D data);
	
	
	}



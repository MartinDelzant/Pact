package ransac;

import java.util.List;
import java.util.List;

	
	public interface ParameterEstimateur {
	       
		/*les donnees exact d'estimation data*/
		/*Ce vecteur est vid� puis rempli avec les parametres calcul�s*/
	public  List<Double> estimerUnPlan(Point3D a,Point3D b,Point3D c);

	        /*Les parametres de FonctionMoindreCarre
	         data 
	          parameters
	         */
	public List<Double> FonctionMoindreCarre(List<Point3D> data);

	        /*
	         parameters:
	         Les parametres du model estim�.
	         data:
	         Les donn�es pour tester.
	         */
	public boolean agree(List<Double> parameters, Point3D data);
	
	
	}



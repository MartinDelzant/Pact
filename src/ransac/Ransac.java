package ransac;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;



 
public class Ransac 
{
	private List<Point3D> parametres;
	private ParameterEstimateur paramEstimator;
	private boolean[] MeilleurVotes;
	private int minDonneesEstime;
	private double MaxAberrantesPourcentage;

	
	public boolean[] getMeilleurVotes() {
		return MeilleurVotes;
}
	
	
	// @return La  Meilleure liste du modelèle souhaité.
	 
	public List<Point3D> getParameters() {
		return parametres;
	}
	
	/**
	 *Le constructeur de l'objet RANSAC
	 * @param MaxAberrantesPourcentage
	 *            le max pourcentage des points aberrantes.
	 */
	public Ransac( double MaxAberrantesPourcentage,ParameterEstimateur paramEstimator) {
		this.paramEstimator = paramEstimator;
		this.MaxAberrantesPourcentage = MaxAberrantesPourcentage;
		
	}
	
	/**
	 * Cette methode est utilisée pour exécuter RANSAC.
	 * 
	 * @param data
	 *            les données utilisées.
	 * @param probaDeLEstimation
	 *            La probabilité utile pour l'estimation.
	 * @return Les parametres du MeilleurPlan
	 */
	
	//*************************************************************************************************************
		
	    public Resultat Algo(List<Point3D> data, double probaDeLEstimation) {
		
	    	int dataSize = data.size();
		
	    	if (dataSize < minDonneesEstime || probaDeLEstimation >= 1.0) {
			return null;
	    	}
		
		List<Point3D> DataExact = new ArrayList<Point3D>();
		List<Point3D> MoindreCarreData;
		List<Double> PlanPossible,PlanChoisi ;
		
		int MeilleurSize, SizeActuel, NbeEssai,i1,i2,i3;
		MeilleurVotes = new boolean[dataSize];
		boolean[] VotesActuel = new boolean[dataSize];
		double numerateur = Math.log(1.0 - probaDeLEstimation);
		double denominateur = Math.log(1 - Math.pow(1 - MaxAberrantesPourcentage, 3));
	   
		//vider la liste des paramètres si elle existe
				
			    PlanChoisi = new ArrayList<Double>();
				MeilleurSize = -1;
				Random random = new Random(new Date().getTime());
				
				NbeEssai = (int) Math.round(numerateur / denominateur);
	    
			for (int i = 0; i < NbeEssai; i++) {	
						
						// initializer l'itérateur
						
						DataExact.clear();
						
						// sélection aléatoire des données
					do{		
						 i1 =random.nextInt(dataSize );
						 //System.out.print(i1);
						 i2 =random.nextInt(dataSize-1);
						// System.out.print(i2);
					  	 i3 =random.nextInt(dataSize-2);
					  	//System.out.print(i3);
					  }
					while(PlanEstimateur.testAlignement(data.get(i1), data.get(i2), data.get(i3)));		
					  
		 
					
					//Estimer le plan passant par ces 3 points
					PlanPossible = paramEstimator.estimerUnPlan(data.get(i1),data.get(i2),data.get(i3));
					
					//parcourir l'image pour voir si les points sont à une distance delta 
					DataExact.clear();
					SizeActuel = 0;
					for (int j = 0; j < dataSize; j++) {
						VotesActuel[j] = false;
					}
					for (int j = 0; j < dataSize; j++) {
						if (paramEstimator.agree(PlanPossible, data.get(j))) {
							VotesActuel[j] = true;
							SizeActuel++;
							DataExact.add(data.get(j));
						}
					}
					
					//actualiser le tableau de Meilleur vote
					if (SizeActuel > MeilleurSize) {
						MeilleurSize = SizeActuel;
						System.arraycopy(VotesActuel, 0, MeilleurVotes, 0, dataSize);
					}
	
					
		}
			// Calculer le meilleur plan avec la méthode des moindres carrés
			MoindreCarreData = new ArrayList<Point3D>();
			for (int i = 0; i < dataSize; i++) {
				if (MeilleurVotes[i]) {
					MoindreCarreData.add(data.get(i));
				}
	    //Estimer le meilleur plan
			}
				PlanChoisi = paramEstimator.FonctionMoindreCarre(MoindreCarreData);
	    
				Resultat r=new Resultat(null,null);
				r.setData(MoindreCarreData);
				r.setParam(PlanChoisi);
				return r;
			
			
	    
	    }
	    

}




	    
	    
		
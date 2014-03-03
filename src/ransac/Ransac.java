package ransac;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @param <T>
 *            Class des donnees
 * @param <S>
 *            Class des parametres 
 */
public class Ransac<T, S> {
	
	private List<S> parameters = null;
	private ParameterEstimator<T, S> paramEstimator;
	private boolean[] MeilleurVotes;
	private int numPourEstimer;
	private double MaxAberrantesPourcentage;

	
	public boolean[] getMeilleurVotes() {
		return MeilleurVotes;
	}

	/**
	 * @return The Meilleur list des model.
	 */
	public List<S> getParameters() {
		return parameters;
	}

	/**
	 * This is the constructor of the new ransac object.
	 * 
	 * @param paramEstimator
	 *            The parameter estimator to be use.
	 * @param numForEstimate
	 *            The minimal data record number needed to estimate model
	 *            parameters.
	 * @param maximalOutlierPercentage
	 *            The maximal outlier percentage.
	 */
	public Ransac(ParameterEstimator<T, S> paramEstimator, int numForEstimate,
			double maximalOutlierPercentage) {
		this.paramEstimator = paramEstimator;
		this.numPourEstimer = numForEstimate;
		this.MaxAberrantesPourcentage = MaxAberrantesPourcentage;
	}

	/**
	 * This method is used to run the RANSAC process.
	 * 
	 * @param data
	 *            The data sample.
	 * @param desiredProbabilityForNoOutliers
	 *            The probability needed in the estimation.
	 * @return The percentage of data used to estimate the best result.
	 */
	
	//*************************************************************************************************************
		
	    public double compute(List<T> data, double desiredProbabilityForNoOutliers) {
		int dataSize = data.size();
		
		if (dataSize < numPourEstimer || MaxAberrantesPourcentage >= 1.0) {
			return 0.0;
		}
		
		List<T> DataExact = new ArrayList<T>();
		List<T> MoindreCarreData;
		List<S> exactedParams;
		
		int MeilleurSize, SizeActuel, NbeEssai;
		MeilleurVotes = new boolean[dataSize];
		boolean[] VotesActuel = new boolean[dataSize];
		boolean[] nonChoisi = new boolean[dataSize];
		Set<int[]>EnsembleChoisi = new HashSet<int[]>();
		int[] curSubSetIndexes;
		double outlierPercentage = MaxAberrantesPourcentage;
		double numerateur = Math.log(1.0 - desiredProbabilityForNoOutliers);
		double denominateur = Math.log(1 - Math.pow(1 - MaxAberrantesPourcentage, numPourEstimer));
		
		if (parameters != null) {
			parameters.clear();
		} else {
			parameters = new ArrayList<S>();
		}
		MeilleurSize = -1;
		Random random = new Random(new Date().getTime());
		NbeEssai = (int) Math.round(numerateur / denominateur);
		for (int i = 0; i < NbeEssai; i++) {
			// initiate a new iterator
			for (int j = 0; j < nonChoisi.length; j++) {
				nonChoisi[j] = true;
			}
			curSubSetIndexes = new int[numPourEstimer];
			DataExact.clear();
			// randomly select data
			for (int j = 0; j < numPourEstimer; j++) {
				int selectedIndex = random.nextInt(dataSize - j);
				int k, l;
				for (k = 0, l = -1; k < dataSize && l < selectedIndex; k++) {
					if (nonChoisi[k]) {
						l++;
					}
				}
				k--;
				DataExact.add(data.get(k));
				nonChoisi[k] = false;
			}
			for (int j = 0, k = 0; j < dataSize; j++) {
				if (!nonChoisi[j]) {
					curSubSetIndexes[k] = j;
					k++;
				}
			}
			// If the subset is not selected, test it.
			if (EnsembleChoisi.add(curSubSetIndexes)) {
				exactedParams = paramEstimator.estimerUnPlan(DataExact);
				// see how many agree on this estimate
				SizeActuel = 0;
				for (int j = 0; j < nonChoisi.length; j++) {
					VotesActuel[j] = false;
				}
				for (int j = 0; j < dataSize; j++) {
					if (paramEstimator.agree(exactedParams, data.get(j))) {
						VotesActuel[j] = true;
						SizeActuel++;
					}
				}
				if (SizeActuel > MeilleurSize) {
					MeilleurSize = SizeActuel;
					System.arraycopy(VotesActuel, 0, MeilleurVotes, 0, dataSize);
				}
				// update the estimate of outliers and the number of iterations
				// we need
				outlierPercentage = 1.0 - (double) SizeActuel / (double) dataSize;
				if (outlierPercentage < MaxAberrantesPourcentage) {
					MaxAberrantesPourcentage = outlierPercentage;
					denominateur = Math.log(1 - Math.pow(
							1 - MaxAberrantesPourcentage, numPourEstimer));
					NbeEssai = (int) Math.round(numerateur / denominateur);
				}
			} else {
				i--;
			}
		}
		EnsembleChoisi.clear();
		// compute the least squares estimate using the best subset
		MoindreCarreData = new ArrayList<T>();
		for (int i = 0; i < dataSize; i++) {
			if (MeilleurVotes[i]) {
				MoindreCarreData.add(data.get(i));
			}
		}
		parameters = paramEstimator.FonctionMoindreCarre(MoindreCarreData);

		return (double) MeilleurSize / (double) dataSize;
	}
}
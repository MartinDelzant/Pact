package ransac;

	//La classe LinearEquation qui s'occupe de modeliser une equation lineaire (coefs + valeur)

public class LinearEquation {
 
	/**
	 * Les coefficients de l'equation
	 */
	public double[] coefficents;
 
 
	/**
	 * Le résidu (= le résultat) de l'equation 
	 */
	public double residual;
 
	/**
	 * Constructeur
	 * 
	 * @param unknownsCount Le nombre d'inconnues
	 */
	public LinearEquation(int unknownsCount) {
		this.coefficents = new double[unknownsCount];
	}
 
	/**
	 * Constructeur
	 * 
	 * @param coefficents Les coefficients de l'equation
	 * @param residual Le résidu (= le résultat) de l'equation 
	 */
	public LinearEquation(double[] coefficents, double residual) {
		this.coefficents = coefficents;
		this.residual = residual;
	}
 
}

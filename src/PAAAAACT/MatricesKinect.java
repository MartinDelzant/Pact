package PAAAAACT;

public class MatricesKinect {
	private int[][] matrice_profondeur;
	private float[][] matrice_Lum;
	private float[][] oldMatrice_Lum;
	
	public MatricesKinect(int[][] matrice_profondeur, float[][] matrice_Lum, float[][] oldMatrice_Lum){
		this.matrice_profondeur=matrice_profondeur;
		this.matrice_Lum=matrice_Lum;
		this.oldMatrice_Lum=oldMatrice_Lum;
		
	} 
			
	public int[][] getMatrice_pronfondeur() {
		return matrice_profondeur;
	}
	public void setMatrice_pronfondeur(int[][] matrice_pronfondeur) {
		this.matrice_profondeur = matrice_pronfondeur;
	}
	public float[][] getMatrice_Lum() {
		return matrice_Lum;
	}
	public void setMatrice_Lum(float[][] matrice_Lum) {
		this.matrice_Lum = matrice_Lum;
	}
	public float[][] getOldMatrice_Lum() {
		return oldMatrice_Lum;
	}
	public void setOldMatrice_Lum(float[][] oldMatrice_Lum) {
		this.oldMatrice_Lum = oldMatrice_Lum;
	}

}

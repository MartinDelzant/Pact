package PAAAAACT;
import java.util.List;
import java.util.ArrayList;

import ransac.*;

public class ResultatKinect {
	private double[][] matrice_profondeur;
	private List<Point3D> liste;
	private float[][] matrice_Lum;
	private float[][] oldMatrice_Lum;
	
	public ResultatKinect(double[][] matrice_profondeur, float[][] matrice_Lum, float[][] oldMatrice_Lum ,List<Point3D> liste){
		this.matrice_profondeur=matrice_profondeur;
		this.matrice_Lum=matrice_Lum;
		this.oldMatrice_Lum=oldMatrice_Lum;
		this.liste=liste;
		
	} 
			
	public double[][] getMatrice_pronfondeur() {
		return matrice_profondeur;
	}
	public void setMatrice_pronfondeur(double[][] matrice_pronfondeur) {
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
	
	public List<Point3D> getListe(){
		return this.liste;	}
}

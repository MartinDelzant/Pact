package PAAAAACT;


public class TestKinect {
	public static void main(String[] args) throws Exception{
		ResultatKinect result = Kiki.kiki();
		
		ResultatKinect resul = KikiforTestKinect.kiki();//test de la kinect avec une seule image: on détermine la distance au mur
		double[][] matrice = resul.getMatrice_pronfondeur();
		double metre =matrice[320][240];
		if (metre ==0) System.out.println("le mur est à moins de 50cm");
		else {System.out.println("le mur est à " + matrice[320][240]/1000+"m");
		}
	}

}

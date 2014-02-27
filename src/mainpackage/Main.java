package mainpackage;
import PAAAAACT.*;
import estim.mouvement.*;

public class Main {

	public static void main(String[] args) throws Exception {
		MatricesKinect test = Kiki.kiki();
		float[][] matriceLum = test.getMatrice_Lum();
		float[][] oldMatriceLum = test.getOldMatrice_Lum() ;
		
		MainProgramme.BlockMatching(oldMatriceLum, matriceLum);

	}

}

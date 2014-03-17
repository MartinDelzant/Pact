package estim.mouvement;
public class MainProgramme {

	/**
	 * @param args
	 */
	public static String[][] BlockMatching(float[][] matrice1, float[][] matrice2) {

		// Essai sur de 500*500 par exemple :

		int hauteurMatrice = 640;
		int largeurMatrice = 480;
//		float[][] matrice1 = new float[hauteurMatrice][largeurMatrice];
//		float[][] matrice2 = new float[hauteurMatrice][largeurMatrice];
		
		String[][] matriceResultat = new String[hauteurMatrice][largeurMatrice];

		
		// Matrice 1 -> gaussienne centrée en 250 250
		// Matrice 2 -> gaussienne centrée en 260 260

//		// Centrage de la gaussienne dans l'image 1 :
//		int xImage1 = hauteurMatrice / 2;
//		int yImage1 = largeurMatrice / 2;
//
//		// Dans l'image 2 :
//		int xImage2 = xImage1 + 10;
//		int yImage2 = yImage1 + 10;

//		// Initialisation des images
//		for (int i = 0; i < hauteurMatrice; i++) {
//			for (int j = 0; j < largeurMatrice; j++) {
//				matrice1[i][j] = fonction(i - xImage1, j - yImage1);
//				matrice2[i][j] = fonction(i - xImage2, j - yImage2);
//			}
//		}

		// Taille des blocks :
		int hauteurBlock = 8;
		int largeurBlock = 8;

		double time = System.currentTimeMillis();
		
		// Taille de la fenetre de recherche :
		int hauteurRange = 100;
		int largeurRange = 100;

//		for (int xDepart = Math.abs(xImage1 - xImage2); xDepart <
//		hauteurMatrice
//		- hauteurBlock - Math.abs(xImage2 - xImage1); xDepart++) {
//		for (int yDepart = Math.abs(yImage2 - yImage1); yDepart <
//		largeurMatrice
//		- largeurBlock - Math.abs(yImage2 - yImage1); yDepart++) {
		int xxDepart = 320;
		int yyDepart = 240;
		 
		for (int xDepart = xxDepart - (hauteurRange / 2); xDepart <= xxDepart
				+ (hauteurRange / 2); xDepart++) {
			for (int yDepart = yyDepart - (largeurRange / 2); yDepart <= yyDepart
					+ (largeurRange / 2); yDepart++) {

		// on choisit un block de l'image de départ :
		Block Test1 = new Block(matrice1, hauteurBlock, largeurBlock, xDepart,
				yDepart);

		// On parcourt l'image d'arrivée:
		float min = Float.MAX_VALUE;
		int xMin = -1;
		int yMin = -1;

		// Recherche :

		// FullSearch :
		// for (int i = 0; i < hauteurMatrice - hauteurBlock; i++) {
		// for (int j = 0; j < largeurMatrice - largeurBlock; j++) {

		for (int i = xDepart - (hauteurRange / 2); i <= xDepart
				+ (hauteurRange / 2); i++) {
			for (int j = yDepart - (largeurRange / 2); j <= yDepart
					+ (largeurRange / 2); j++) {
				Block Test2 = new Block(matrice2, hauteurBlock, largeurBlock,
						i, j);
				float mad = Test1.MAD(Test2);
				if (mad < min) {
					min = mad;
					xMin = i;
					yMin = j;
				}
			}
		}
				matriceResultat[xDepart][yDepart] = (xMin - xDepart) + " "
						+ (yMin - yDepart);
			System.out.print(matriceResultat[xDepart][yDepart]);
			}
		System.out.println();
		}
		System.out.println(" Temps = " + (System.currentTimeMillis() - time));
		return matriceResultat ;
	}

	public static float fonction(double i, double j) {
		float res = (float) (100000 * Math.exp(-((i * i) + (j * j)) / 100));
		return res;
	}

}

package pact.cconnexe;
import java.util.Arrays;
import java.util.Random;


public class Main {

	public static void main(String[] args) {
		int compte = 0 ;
		//long moyenneTime = 0;
		//while(++compte<100){
		double m = 1000.0 ;
		double[][] matrice = new double[640][480];
		Random alea = new Random();
		for(int i = 0; i< matrice.length ; i++){
			for(int j = 0; j< matrice[0].length ; j++){
				double res = 20000* alea.nextDouble();
				if(res>10000 && res <18000){
					matrice[i][j] = res;
				}
			}
		}
		
		
		long time = System.currentTimeMillis();
		CConnexe test = new CConnexe(matrice);
//		System.out.println("Matrice de départ : ");
//		for(int i = 0; i< matrice.length ;i++){
//			for(int j = 0; j< matrice[0].length ; j++){
//				if(matrice[i][j] != 0){
//					System.out.print(1+ " ");
//				}
//				else{
//					System.out.print(0+ " ");
//				}
//			}
//			System.out.println();
//		}
//		
//		System.out.println("\nMatrice après premier passage");
		//test.setCompoLabel();
		//System.out.println(test.toString() + "\n");
		
		test.setTableCorr();
		//System.out.println(test.toString() + "\n") ;
		System.out.println("Temps : " + (System.currentTimeMillis()-time));
		//moyenneTime += (System.currentTimeMillis()-time);
		//System.out.println("Temps : " + moyenneTime );
		//}
		//System.out.println("Temps moyen : " + (moyenneTime/(compte+1)));
	}
}

package connexe.amélioré;

import java.util.Arrays;
import java.util.Random;


public class Main {

	public static void main(String[] args) {
		//int compte = 0 ;
	
		//long moyenneTime = 0;
		//while(++compte<100){
		double m = 1000.0 ;
		double[][] matrice = new double[10][10];
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
		System.out.println("Matrice de dï¿½part : ");
		for(int i = 0; i< matrice.length ;i++){
			for(int j = 0; j< matrice[0].length ; j++){
				if(matrice[i][j] != 0){
					System.out.print(1+ " ");
				}
				else{
					System.out.print(0+ " ");
				}
			}
			System.out.println();
		}
//		
		
		System.out.println("\nMatrice aprï¿½s premier passage");
		//test.setCompoLabel();
		System.out.println(test.toString() + "\n");
		
		test.setTableCorr();
		System.out.println(test.toString() + "\n") ;
		
		Obstacle[] liste = test.getObstacle(matrice);
		for (int i = 0; i< liste.length ; i++){
			if(liste[i] != null){
				//System.out.println("obstacle " + i+ " =  "+ liste[i].toString());
				System.out.println("obstacle "+i+" = "+liste[i].getProfondeurMoy());
			}
		}
		
		//System.out.println("Temps : " + (System.currentTimeMillis()-time));
		//moyenneTime += (System.currentTimeMillis()-time);
		//System.out.println("Temps : " + moyenneTime );
		//}
		//System.out.println("Temps moyen : " + (((double)moyenneTime)/(compte+1)));
	}
}

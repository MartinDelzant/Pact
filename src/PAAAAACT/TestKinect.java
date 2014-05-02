package PAAAAACT;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import ransac.Point3D;
import wavreading.AlarmeDroite;
import wavreading.AlarmeGauche;


public class TestKinect {
	public static void main(String[] args) throws Exception{
//		Thread t1 = new Thread (new KikiforTestKinect());
//		Thread t2 = new Thread (new AlarmeDroite());
//		Thread t3 = new Thread (new AlarmeGauche());
//		
//		t1.start();
//		while(true){
//			Random random = new Random();
//			double alea = random.nextDouble();
//			if(alea>0.5){
//				t2.run();
//				Thread.sleep(500);
//			}
//		}
		
		ResultatKinect result = Kiki.kiki();
		
		
//		List<Point3D> liste= result.getListe();
//		
//		PrintWriter printwriter = new PrintWriter("data/listepoint.txt");
//		PrintWriter printwriter2 = new PrintWriter("data/liste.txt");
//		
//		for(int i = 0 ; i<liste.size() ; i++){
//			if(i<liste.size()/2){
//				printwriter.write("Point3D point"+i+" = new Point3D("+ liste.get(i).getX()+"," +liste.get(i).getY()+ ", "+ liste.get(i).getZ()+" );");
//				printwriter.println();
//			}
//			else{
//				printwriter2.write("Point3D point"+i+" = new Point3D("+ liste.get(i).getX()+"," +liste.get(i).getY()+ ", "+ liste.get(i).getZ()+" );");
//				printwriter2.println();
//			}
//			
//		}
//		printwriter2.close();
//		printwriter.close();
//		System.out.println("Fini !");
		
//		ResultatKinect resul = KikiforTestKinect.kiki();//test de la kinect avec une seule image: on détermine la distance au mur
//		double[][] matrice = resul.getMatrice_pronfondeur();
//		double metre =matrice[320][240];
//		if (metre ==0) System.out.println("le mur est à moins de 50cm");
//		else {System.out.println("le mur est à " + matrice[320][240]/1000+"m");
//		}
	}

}

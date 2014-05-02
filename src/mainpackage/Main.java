package mainpackage;
import ransac.*;
import wavreading.AlarmeDroite;
import wavreading.AlarmeGauche;
import wavreading.AlarmeMilieu;
import wavreading.Son;
import PAAAAACT.*;
import estim.mouvement.*;
import detectplan.*;

public class Main {
	public static void main(String args[]) throws InterruptedException{
		
//		for(int i = 0; i<=1 ; i++){
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Mi.wav", 500);
//		Son.lireSon("data/Mi.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Mi.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Silence.wav", 500);
//		
//		}
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/La.wav", 500);
//		Son.lireSon("data/La.wav", 500);
//		Son.lireSon("data/La.wav", 500);
//		Son.lireSon("data/Silence.wav", 250);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Si.wav", 500);
//		Son.lireSon("data/La.wav", 500);
//		Son.lireSon("data/Sol.wav", 500);
//		
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Mi.wav", 500);
//		Son.lireSon("data/Mi.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Mi.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Re.wav", 500);
//		Son.lireSon("data/Do.wav", 500);
//		Son.lireSon("data/Silence.wav", 500);
//		
		new Thread(new AlarmeDroite()).start();
	}
}

package wavreading;

public class AlarmeGauche implements Runnable{
	
	@Override
	public void run(){
		Son.lireSon("data/AlarmGauche.wav", 500);
	}
}

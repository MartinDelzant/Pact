package wavreading;

public class AlarmeDroite implements Runnable{
	@Override
	public void run() {
		Son.lireSon("data/AlarmDroite.wav", 500);
		Son.lireSon("data/AlarmMilieu.wav", 500);
		Son.lireSon("data/AlarmGauche.wav", 500);
		
		
	}
}

package wavreading;

public class AlarmeMilieu implements Runnable{

	@Override
	public void run() {
		Son.lireSon("data/AlarmMilieu.wav", 500);
		
	}
	
}

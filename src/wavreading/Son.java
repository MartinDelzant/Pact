package wavreading;


import java.io.*;

import javax.sound.sampled.*;

public class Son {

	/*
	 * @param Alarm01 pour lancer l'alarme 1 etc ... 
	 * @param entier = longueur en secondes de l'alarme
	 * @return void : joue l'alarme en question
	 */
	public static void lireSon(String sonFile, long time) {

		
		try {
			AudioInputStream monFichierAudio = AudioSystem.getAudioInputStream(new File(sonFile));
			AudioFormat monFormat = monFichierAudio.getFormat();
			int taille = (int) (monFormat.getFrameSize() * monFichierAudio.getFrameLength());
			byte[] monSon = new byte[taille];
			DataLine.Info mesInfos = new DataLine.Info(Clip.class, monFormat, taille);
			monFichierAudio.read(monSon,0,taille);
			
			Clip monClip = (Clip) AudioSystem.getLine(mesInfos);
			monClip.open(monFormat,monSon,0,taille);
			monClip.start();
			Thread.sleep(time);
			//Boucle qui arrete le programme au bout de n secondes
//			int n = Integer.parseInt(args[1]);
//			System.out.println(n);
//			for (int i = n ; i<n+1 ; i++){
//			Thread.sleep(n*1000);
//			}	
			
			//Waveform.main(args);
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}

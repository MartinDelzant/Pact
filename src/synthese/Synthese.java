package synthese;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.wavfile.io.WavFile;
import org.wavfile.io.WavFileException;

import wavreading.*;

public class Synthese {

	public static void main(String[] args) throws IOException, WavFileException {
		
		
		float[] frequences = new float[2];
		frequences[0] = (float) 0;
		frequences[1] = (float) 0;
		String file = "data/Silence.wav" ;
		WavFile.createWaveFile( file , 2, 44100, 0.5 , frequences);
		Son.lireSon(file, 500);
		
	}

}

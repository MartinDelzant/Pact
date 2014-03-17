package wavreading;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/*
 * Classe qui fait le lien entre texte et fichier son 
 */

public class Link {
	
	private Hashtable<String, String> hash ;
	
	public Link(){
		this.hash = new Hashtable<String,String>();
		hash.put("Alarm01", "data/hallelujah.wav");
		hash.put("Alarm02", "data/Sinusoide.wav");
	}

	//Constructeur qui initialise à partir d'un fichier texte adéquat :
	public Link(String fileName) throws FileNotFoundException {
		this.hash = new Hashtable<String,String>();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String s = br.readLine();
			while(s != null){
				String alarmName = s.substring(0, 7);
				String wavName = s.substring(8, s.length());
				this.hash.put(alarmName, wavName);
				s = br.readLine();
			}
			br.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Existe Pas");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public final Hashtable<String, String> getHash() {
		return hash;
	}
	
}

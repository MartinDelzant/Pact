package detectplan;

import java.util.HashSet;
import java.util.Hashtable;


public class CConnexe {
	
	private int[][] Label;
	private int width,height;
	private double [][] profondeur;
	private Hashtable<Integer, Integer> tableCorr = new Hashtable<Integer, Integer>();
 

	
	public Hashtable<Integer, Integer> getTableCorr() {
		return tableCorr;
	}

	public CConnexe ( int width, int height, double[][] profondeur) {
		Label= new int[580][420];
		for (int y=0; y<height;y++){
			for (int x=0; x<width; x++) { 
				this.Label[x][y]=0;
				}
			}			
		this.width = width;
		this.height = height;
		this.profondeur=profondeur;
		
	}
	
	public int[][] getLabel(){
		return this.Label;
	}
	
	public void setComposanteLabel() {
		
		int nLabel=1;  // Nombre d'etiquettes
				
		for (int j=0;j<height;j++){    // height = 480
    		for (int i=0; i<width;i++){   // width = 640
    			
	    			double pixel = profondeur[i][j];
	    		if (pixel > 1500) {profondeur[i][j] = 0; pixel =0;}//tous les points plus loin que 1,5m et plus près que 50cm sont fixés à zéro
	    			
	    			
	    			if (pixel != 0 && i!=0 && j!=0){
	    				if (0 == profondeur[i][j-1] && profondeur[i-1][j] == 0){
	    					Label[i][j] = nLabel;
	    					nLabel++;
	    				}
	    				
//	    				else if (Math.abs(profondeur[i-1][j] - profondeur[i][j-1])< valeur && profondeur[i-1][j] != 0 && profondeur[i][j-1] !=0){
//	    					if (Math.abs(pixel - profondeur [i-1][j])<30)
//	    						{Label[i][j] = Label[i-1][j];}
//	    					else Label[i][j] = nLabel;
//	    					     nLabel++;
//	    				}
	    			
	    				else {
	    					int min = 0 ;
	    					int max = 0 ;
	    					
	    					if(Label[i-1][j]>Label[i][j-1]){
	    						min = Label[i][j-1];
	    						max = Label[i-1][j];
	    					}
	    					else{
	    						min = Label[i-1][j];
	    						max = Label[i][j-1];
	    					}
	    				
	    					if(min == 0){
	    						Label[i][j] = max;
	    					}
	    					else{
	    						Label[i][j] = min ;
	    					}
	    					if(min !=0){
	    					System.out.println(max + " -> " + min);
	    					tableCorr.put(max, min);
	    					}
	    				}
	    					
	    			}
	    			
	    			else if (pixel != 0 && i==0 && j==0) {
	    				Label[i][j] = nLabel;
	    				nLabel++;
	    			}
    			
	    			else if (pixel != 0 && i==0 && j!=0){
		    				if (Label[i][j-1] == 0){
		    					Label[i][j] = nLabel;
		    					nLabel++;
		    				}
		    				
		    				
		    				else if (Label[i][j-1] != 0){
		    					//if (Math.abs(pixel-profondeur[i][j-1])<valeur){
		    						Label[i][j] = Label[i][j-1];
		    						//}
		    					//else {Label[i][j] = nLabel;
		    						nLabel++;
		    					//}
		    				}
		    				
	    			}
    			
	    			else if (pixel != 0 && i!=0 && j==0){
	    				if (Label[i-1][j] == 0){
	    					Label[i][j] = nLabel;
	    					nLabel++;
	    				}
	    				
	    				else if (Label[i-1][j] != 0){
	    					//if (Math.abs(pixel-profondeur[i-1][j])<valeur){
	    						Label[i][j] = Label[i-1][j];
	    						//}
	    					//else {Label[i][j] = nLabel;
	    						nLabel++;
	    					//}
	    				}
	    			}
	    			
	    			
    		}
    		
    	}
		
//    	for (int k=1; k<=nLabel ; k++){
//    		
//    		int j = tableCorr.get(k);
//    		
//    		if (k==j){
//    			int newLabel =0;
//    			newLabel = newLabel+1;
//    			tableCorr.get(k) = newLabel;
//    		}
//    		else tableCorr.get(k) = tableCorr[j];
//    	}
//    	
//
//    	for (int i=0;i<480;i++){
//    		for (int j=0; j<640;j++){
//    			
//	    			int n = Label[j][i];
//	    			
//	    			if (n!=0){
//	    					for(int k=1; k<=nLabel; k++){		
//	    						if (n == k) {
//	    							Label[j][i] = tableCorr.get(k); 
//	    							k = nLabel+1;
//	    						}
//	    					}
//	    			}
//	    			
//    		}
//    		
//    	} 
		
	 //return Label;	
	}
	
	public void setTableCorr(){
		
		int nLabel = tableCorr.size();
		for(int i = 1; i<= nLabel ; i++){
			tableCorr.put(i, i);
		}
		for (int k=2; k<= nLabel ; k++){
    		
    		int j = tableCorr.get(k);
    		
    		if (k==j){
    			int newLabel =1;
    			tableCorr.put(k ,newLabel);
    			newLabel = newLabel+1;

    		}
    		else tableCorr.put(k, tableCorr.get(j));
    	}
    	

    	for (int i=0;i<this.height;i++){
    		for (int j=0; j<this.width;j++){
    			
	    			int n = Label[j][i];
	    			
	    			if (n!=0){
	    					for(int k=1; k<=nLabel; k++){		
	    						if (n == k) {
	    							Label[j][i] = tableCorr.get(k); 
	    							k = nLabel+1;
	    						}
	    					}
	    			}
	    			
    		}
    		
    	} 
		
	}
	

}

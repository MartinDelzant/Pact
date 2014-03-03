package detectplan;


public class CConnexe {
	
	private int[][] Label;
	private int width,height;
 
 
	
	public CConnexe (int[][] Label, int width, int height) {
		this.Label = Label;
		this.width = width;
		this.height = height;
		
	}
	
	public int[][] detectComp() {
		
		int[] tableCorr = new int[1000];  /*Table de correspondence*/
		
		int nLabel=1;  // Nombre d'etiquettes
		int valeur= 30; // nombre de mm d'écart max entre 2pixels d'un même objet
		
		for (int j=0;j<height;j++){    // height = 480
    		for (int i=0; i<width;i++){   // width = 640
    			
	    			int pixel = Label[i][j];
	    		if (pixel > 17500) pixel = 0; //tous les points plus loin que 1,75m et plus près que 1m sont fixé à zéro
	    			
	    			
	    			if (pixel != 0 && i!=0 && j!=0){
	    				if (Label[i-1][j] == Label[i][j-1] && Label[i-1][j] == 0){
	    					Label[i][j] = nLabel;
	    					nLabel++;
	    				}
	    				
	    				else if (Label[i-1][j] - Label[i][j-1] < valeur && Label[i-1][j] != 0){
	    					Label[i][j] = Label[i-1][j];
	    				}
	    			
	    				else {
	    					int min = Label[i-1][j];
	    					if ((min !=0 && Label[i][j-1]<min && Label[i][j-1]!=0) || min == 0){
	    						min = Label[i][j-1];
	    					}
	    					
	    					Label[i][j]=min;
	    					
	    					int max = Label[i-1][j];
	    					if (max == 0 || Label[i][j-1]>max){
	    						max = Label[i][j-1];
	    					}
	    					
	    					tableCorr[max] = min;
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
		    					Label[i][j] = Label[i][j-1];
		    				}
		    				
	    			}
    			
	    			else if (pixel != 0 && i!=0 && j==0){
	    				if (Label[i-1][j] == 0){
	    					Label[i][j] = nLabel;
	    					nLabel++;
	    				}
	    				
	    				else if (Label[i-1][j] != 0){
	    					Label[i][j] = Label[i-1][j];
	    				}
	    			}
	    			
	    			
    		}
    		
    	}
    	
    	for (int k=1; k<=nLabel ; k++){
    		
    		int j = tableCorr[k];
    		
    		if (k==j){
    			int newLabel =0;
    			newLabel = newLabel+1;
    			tableCorr[k] = newLabel;
    		}
    		else tableCorr[k] = tableCorr[j];
    	}
    	

    	for (int i=0;i<480;i++){
    		for (int j=0; j<640;j++){
    			
	    			int n = Label[j][i];
	    			
	    			if (n!=0){
	    					for(int k=1; k<=nLabel; k++){		
	    						if (n == k) {
	    							Label[j][i] = tableCorr[k]; 
	    							k = nLabel+1;
	    						}
	    					}
	    			}
	    			
    		}
    		
    	} 
	 return Label;	
	}
	

}

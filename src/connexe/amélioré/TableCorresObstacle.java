package connexe.amélioré;

import java.util.Hashtable;


public class TableCorresObstacle {
	
	//TODO : Essayer de le declarer final ?
	private Hashtable<Integer, Obstacle> hashtable ;
	double[][] profondeur ;

	public TableCorresObstacle(Hashtable<Integer, Obstacle> hashtable) {
		super();
		this.hashtable = hashtable;
	}
	
	public TableCorresObstacle() {
		this.hashtable = new Hashtable<Integer, Obstacle>();
	}

	public TableCorresObstacle(double[][] profondeur) {
		this.hashtable = new Hashtable<Integer, Obstacle>();
		this.profondeur = profondeur ;
	}
	
	public Hashtable<Integer, Obstacle> getHashtable() {
		return hashtable;
	}
	
	//Des qu'on ajoute un ï¿½lï¿½ment, on le fait correspondre ï¿½ son objet directement: 
	public void add(Integer key, Obstacle obstacle){
		Obstacle newObstacle = obstacle ;
        while(hashtable.containsKey(newObstacle.value)){
                newObstacle = hashtable.get(newObstacle.value);
        }
        hashtable.put(key, newObstacle);
	}
	
	//Si deux objets on la mï¿½me "value", on les fusionne. 
	public void setMatrix(int[][] matrix){
		int xLength = matrix.length ;
		int yLength = matrix[0].length;
		for(int i = 0 ; i< xLength ; i++){
			for(int j = 0; j<yLength ; j++){
				if(hashtable.containsKey(matrix[i][j])){
					Obstacle obstacle = hashtable.get(matrix[i][j]);
					obstacle.ajoute(i, j, (float)profondeur[i][j]) ;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "TableCorrespondance [hashtable=" + hashtable.toString() + "]";
	}
	


}

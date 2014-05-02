package connexe.amélioré;

import java.util.Hashtable;



public class TableCorrespondance {
	
	//TODO : Essayez de le declarer final
	private Hashtable<Integer, Integer> hashtable ;

	public TableCorrespondance(Hashtable<Integer, Integer> hashtable) {
		super();
		this.hashtable = hashtable;
	}
	
	public TableCorrespondance() {
		this.hashtable = new Hashtable<Integer, Integer>();
	}

	public Hashtable<Integer, Integer> getHashtable() {
		return hashtable;
	}
	
	//Des qu'on ajoute un élément, on le fait correspondre à son objet directement: 
	public void add(Integer key, Integer value){
		Integer newValue = value ;
        while(hashtable.containsKey(newValue)){
                newValue = hashtable.get(newValue);
        }
        hashtable.put(key, newValue);
	}
	
	public void setMatrix(int[][] matrix){
		int xLength = matrix.length ;
		int yLength = matrix[0].length;
		for(int i = 0 ; i< xLength ; i++){
			for(int j = 0; j<yLength ; j++){
				if(hashtable.containsKey(matrix[i][j])){
					matrix[i][j] = hashtable.get(matrix[i][j]);
				}
			}
		}
	}

	@Override
	public String toString() {
		return "TableCorrespondance [hashtable=" + hashtable.toString() + "]";
	}
	
}

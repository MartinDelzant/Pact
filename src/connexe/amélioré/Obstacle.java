package connexe.amélioré;



public class Obstacle {
	
	public int value ;
	
	public int nbPoints ;
	public double profondeurSomme ;
	public int xMin ;
	public int yMin ;
	public int xMax ;
	public int yMax ;
	
	public Obstacle() {
		super();
	}

	public Obstacle(int nbPoints, double profondeurSomme, int xMin, int yMin,
			int xMax, int yMax) {
		super();
		this.nbPoints = nbPoints;
		this.profondeurSomme = profondeurSomme;
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	public void fusionne(Obstacle obstacle){
		//Value reste la mï¿½me normalement !
		this.nbPoints = nbPoints + obstacle.nbPoints ;
		this.profondeurSomme += obstacle.profondeurSomme;
		this.xMin = Math.min(this.xMin, obstacle.xMin);
		this.xMax = Math.max(xMax, obstacle.xMax);
		this.yMin = Math.min(yMin, obstacle.yMin);
		this.yMax = Math.max(yMax, obstacle.yMax);
		
	}
	
	public void ajoute(int i, int j, double profondeur){
		nbPoints++;
		profondeurSomme += profondeur ;
		this.xMin = Math.min(i, xMin);
		this.xMax = Math.max(xMax, i);
		this.yMin = Math.min(yMin, j);
		this.yMax = Math.max(yMax, j);
		
	}

	@Override
	public String toString() {
		return "Obstacle [nbPoints=" + nbPoints + ", profondeurSomme="
				+ profondeurSomme + ", xMin=" + xMin + ", yMin=" + yMin
				+ ", xMax=" + xMax + ", yMax=" + yMax + "]";
	}
	
	public double getProfondeurMoy(){
		return (profondeurSomme/nbPoints);
	}
	
	
	
	
}

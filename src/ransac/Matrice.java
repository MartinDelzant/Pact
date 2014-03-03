package ransac;

public class Matrice  {
	
	// les données de la matrice ordonnnée en [x=colonne][y=ligne]
		public double value[][];
	 
		// les dimensions de la matrice
		public final int ligne, colonne;
	 
		// constructeur de matrice quelconque (vide)
		public Matrice(int cols, int rows) {
			this.colonne = cols;
			this.ligne = rows;
			this.value = new double[this.colonne][this.ligne];
		}
	 
		// constructeur de matrice carré (pré-remplies)
		public Matrice(int size, double[][] squarearray) {
			this.colonne = size;
			this.ligne = size;
			this.value = squarearray;
		}

		public void setValue(double[][] value) {
			this.value = value;
		}

		public double getValue(int i ,int j) {
			return value[i][j];
		}
		
	 
		

	
}
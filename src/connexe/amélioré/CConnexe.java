package connexe.amélioré;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class CConnexe {

	private int[][] Label;
	private int xLength, yLength;
	private int nombreLabel ;
	//private double[][] profondeur;
	private TableCorrespondance tableCorrespondance = new TableCorrespondance();
	private TableCorresObstacle tableCorresObstacle ;

	public TableCorrespondance getTableCorrespondance() {
		return tableCorrespondance;
	}
	
	

	/*
	 * Constructeur :
	 */
//	public CConnexe(int xLength, int yLength, double[][] profondeur) {
//		Label = new int[xLength][yLength];
//		/*
//		 * Inutile ...
//		 */
//		// for (int y=0; y<yLength;y++){
//		// for (int x=0; x<xLength; x++) {
//		// this.Label[x][y]=0;
//		// }
//		// }
//		this.xLength = xLength;
//		this.yLength = yLength;
//		this.profondeur = profondeur;
//
//	}
	
	public final TableCorresObstacle getTableCorresObstacle() {
		return tableCorresObstacle;
	}



	public CConnexe(double[][] profondeur){
		this.xLength = profondeur.length ;
		this.yLength = profondeur[0].length ;
		this.Label = new int[xLength][yLength];
		setCompoLabel(profondeur);
		this.tableCorresObstacle = new TableCorresObstacle(profondeur);
		//this.profondeur = profondeur ;
	}

	public int[][] getLabel() {
		return this.Label;
	}

	
	public void setCompoLabel(double[][] profondeur){
		int nLabel = 1 ;
		double pixel; 
		int labelPixelGauche, labelPixelHaut ;
		
		for(int i=0 ; i< xLength ; i++){
			for(int j=0 ; j< yLength ; j++){
				pixel = profondeur[i][j];
				//On ne s'interesse qu'au pixel non nul 
				if(pixel != 0){
					if(i==0){
						if(j==0 || Label[i][j-1] == 0){
							Label[i][j] = nLabel ;
							nLabel++;
						}
						else{
							Label[i][j] = Label[i][j-1];
						}
					}
					else {
						labelPixelHaut = Label[i-1][j];
						if(j==0){
							if(labelPixelHaut == 0){
								Label[i][j] = nLabel;
								nLabel++;
							}
							else{
								Label[i][j] = labelPixelHaut ;
							}
						}
						else{
							labelPixelGauche = Label[i][j-1];
							if(labelPixelGauche == 0 && labelPixelHaut == 0){
								Label[i][j] = nLabel;
								nLabel ++;
							}
							else if( labelPixelGauche == 0 || labelPixelHaut == 0){
								Label[i][j] = labelPixelGauche + labelPixelHaut ;
							}
							else{
								if(labelPixelGauche<labelPixelHaut){
									Label[i][j] = labelPixelGauche ;
									tableCorrespondance.add(labelPixelHaut, labelPixelGauche);
								}
								else{
									Label[i][j] = labelPixelHaut;
									if(labelPixelHaut<labelPixelGauche){
										tableCorrespondance.add(labelPixelGauche, labelPixelHaut);
									}
								}
							}
							
						}
					}
				}
			}
		}
		nombreLabel = nLabel;
	}
	
//	public void setComposanteLabel() {
//
//		int nLabel = 1; // Nombre d'etiquettes
//		int valeur = 30; // nombre de mm d'ï¿½cart max entre 2pixels d'un mï¿½me
//							// objet
//
//		for (int j = 0; j < yLength; j++) { // yLength = 480
//			for (int i = 0; i < xLength; i++) { // xLength = 640
//
//				double pixel = profondeur[i][j];
//				if (pixel > 17500) {
//					profondeur[i][j] = 0;
//					pixel = 0;
//				}// tous les
//					// points plus loin que 1,75m et plus prï¿½s que 1m sont fixï¿½
//					// ï¿½ zï¿½ro
//
//				//Si le pixel contient un objet pas sur le bord : 
//				if (pixel != 0 && i != 0 && j != 0) {
//					if (0 == profondeur[i][j - 1] && profondeur[i - 1][j] == 0) {
//						Label[i][j] = nLabel;
//						nLabel++;
//					}
//
//					// else if (Math.abs(profondeur[i-1][j] -
//					// profondeur[i][j-1])<
//					// valeur && profondeur[i-1][j] != 0 && profondeur[i][j-1]
//					// !=0){
//					// if (Math.abs(pixel - profondeur [i-1][j])<30)
//					// {Label[i][j] = Label[i-1][j];}
//					// else Label[i][j] = nLabel;
//					// nLabel++;
//					// }
//
//					else {
//						int min = 0;
//						int max = 0;
//
//						if (Label[i - 1][j] > Label[i][j - 1]) {
//							min = Label[i][j - 1];
//							max = Label[i - 1][j];
//						} else {
//							min = Label[i - 1][j];
//							max = Label[i][j - 1];
//						}
//
//						if (min == 0) {
//							Label[i][j] = max;
//						} else {
//							Label[i][j] = min;
//						}
//						if (min != 0) {
//							System.out.println(max + " -> " + min);
//							tableCorrespondance.add(max, min);
//						}
//					}
//
//				}
//
//				else if (pixel != 0 && i == 0 && j == 0) {
//					Label[i][j] = nLabel;
//					nLabel++;
//				}
//
//				else if (pixel != 0 && i == 0 && j != 0) {
//					if (Label[i][j - 1] == 0) {
//						Label[i][j] = nLabel;
//						nLabel++;
//					}
//
//					else if (Label[i][j - 1] != 0) {
//						Label[i][j] = Label[i][j - 1];
//						nLabel++;
//					}
//
//				}
//
//				else if (pixel != 0 && i != 0 && j == 0) {
//					if (Label[i - 1][j] == 0) {
//						Label[i][j] = nLabel;
//						nLabel++;
//					}
//
//					else if (Label[i - 1][j] != 0) {
//						Label[i][j] = Label[i - 1][j];
//						nLabel++;
//					}
//				}
//
//			}
//
//		}
//
//		
//	}

	public void setTableCorr() {
		tableCorrespondance.setMatrix(Label);
	}
	
	public void setTableCorrObstacle(){
		tableCorresObstacle.setMatrix(Label);
	}
	
	public Obstacle[] getObstacle(double[][] profondeur){
		Obstacle[] liste = new Obstacle[nombreLabel];
		for (int i = 0; i< Label.length ; i++){
			for(int j = 0; j< Label[0].length ; j++){
				int label = Label[i][j];
				if(label!=0){
					if(liste[label] == null){
						liste[label] = new Obstacle(1, profondeur[i][j], i, j, i, j);
					}
					else{
						liste[label].ajoute(i, j, profondeur[i][j]);
					}
				}
			}
		}
		return liste;
		
	}

	public String toStringLabel(){
		String res = "";
		for (int i = 0; i < xLength; i++) {
			for (int j = 0; j < yLength; j++) {
				res = res + Label[i][j] + " ";
			}
			res = res + "\n";
		}
		return res;
	}
	
	
	
	@Override
	public String toString() {
		String labelString = toStringLabel() ;
		return "CConnexe [Label=" + "\n" + labelString + ",\n xLength=" + xLength + ",\n yLength="
				+ yLength + ",\n tableCorrespondance=" + tableCorrespondance + "]";
	}

}
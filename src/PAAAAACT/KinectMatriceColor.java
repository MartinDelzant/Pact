package PAAAAACT;

import java.nio.ByteBuffer;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class KinectMatriceColor {
	private IplImage rgb_image;
//	private int[][]  matrice_r;
//	private int[][]  matrice_b;
//	private int[][]  matrice_g;
	private float[][] matrice_lum;
	private static int height=480;
	private static int width=640;
	
	
	public float[][] getMatriceLum() {
		return matrice_lum;
	}

	public KinectMatriceColor(IplImage rgb_image){
//		this.matrice_r=new int[width][height];
//		this.matrice_b=new int[width][height];
//		this.matrice_g=new int[width][height];
		this.matrice_lum=new float[width][height];
		this.rgb_image=rgb_image;
		
		
	}
	
	public void setImage(IplImage rgb_image){
		this.rgb_image=rgb_image;
	}
	
	
//	public int[][] getMatricer(){
//		return this.matrice_r;
//	}
//	
//	public int[][] getMatriceb(){
//		return this.matrice_b;
//	}
//	
//	public int[][] getMatriceg(){
//		return this.matrice_g;
//	}

	public void initializeMatrice(){
	  for (int y=0; y<height;y++){
			for (int x=0; x<width; x++) {  
				int pixel_index = 3*x + 3*width*y;
				ByteBuffer rgb_data = rgb_image.getByteBuffer();
				
      
      /* lire la composante bleu de notre pixel*/
     float blue_value = (float) rgb_data.get(pixel_index);
     if (blue_value < 0 ) blue_value = 255 + blue_value;
     //this.matrice_b[x][y]=blue_value;
     
     float green_value = (float) rgb_data.get(pixel_index + 1);
     if (green_value < 0 ) green_value = 255 + green_value;
     //this.matrice_g[x][y]=green_value;
     
     float red_value = (float) rgb_data.get(pixel_index +2);
     if (red_value < 0 ) red_value = 255 + red_value;
     //this.matrice_r[x][y]=red_value;
     this.matrice_lum[x][y]=(float) (0.2126*red_value+0.7152*green_value+0.0722*blue_value);
			}
     }
}
}

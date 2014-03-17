package PAAAAACT;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import ransac.*;

import java.util.List;

import ransac.Point3D;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class KinectMatrice {//classe pour le module detection de plan
	private IplImage depth_image;
	private double[][] matrice;//matrice de profondeur
	public double[][] matriceGauche;//matrice pour les composantes connexes
	public double[][] matriceDroite;
	public double[][] matriceMilieu;
	private List<Point3D> liste;//entrée pour ransac
	private static int height = 480;
	private static int width = 640;
	private boolean is_playback;
	private int depth_bytes_per_pixels;

	public KinectMatrice(IplImage depth_image, boolean is_playback,
			int depth_bytes_per_pixels) {
		this.matrice = new double[width][height];
		this.matriceGauche = new double[(width-60)/(5)*2][height-60];
		this.matriceDroite = new double[(width-60)/(5)*2][height-60];
		this.matriceMilieu = new double [(width-60)/(5)][height-60];
		this.depth_image = depth_image;
		this.is_playback = is_playback;
		this.depth_bytes_per_pixels = depth_bytes_per_pixels;
		this.liste =new ArrayList<Point3D>();

	}

	public void setImage(IplImage depth_image) {
		this.depth_image = depth_image;
	}

	public void setPlay(boolean is_playback) {
		this.is_playback = is_playback;
	}

	public void setDep(int depth_bytes_per_pixels) {
		this.depth_bytes_per_pixels = depth_bytes_per_pixels;
	}

	public double[][] getMatrice() {
		return this.matrice;
	}

	 public List<Point3D> getList(){
	 return this.liste;
	 }

	 public void setMatriceZero(List<Point3D> liste){
		 for(int i = 0; i<liste.size() ; i++){
			 Point3D p = liste.get(i);
			 matrice[(int) Math.floor(p.getX())][(int) Math.floor(p.getY())] = 0 ;
		 }
	 }
	public void initializeListetMatrice() {

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {//parcours de tous les pixels
				ByteBuffer depth_data = depth_image.getByteBuffer();
				ByteBuffer rescale_depth_data = null;
				int idx_depth = depth_bytes_per_pixels * (x + y * width);

				int resu;
				double fin;

				if (is_playback) {

					resu = depth_data.get(idx_depth);
					if (resu < 0)
						resu = 255 + resu;
				} else {

					int b1 = depth_data.get(idx_depth);
					int b2 = depth_data.get(idx_depth + 1);
					if (b1 < 0)
						b1 = 255 + b1;
					if (b2 < 0)
						b2 = 255 + b2;

					resu = (b1 << 8) | b2;
				}

				if (resu <= 835) {//passage de la profondeur en mètre avec approximation en deux droites
					fin = (1000 * (resu - 209.2) / 507.5);
				} else {
					fin = (1000 * (resu - 793.8) / 57.46);
				}
				if(fin <0){
					fin =0;
					}
				liste.add(new Point3D(x,y,fin));
				matrice[x][y] = fin;
				
				if (30<x && x<262 && y>30 && y < height - 30)//les matrices sont remplies en découpant les bords, pour ne pas prendre en compte les murs
				{matriceGauche[x - 30][y - 30] = fin;
				}
				if (x>262 && x<378 && y>30 && y<height - 30)
				{ matriceMilieu[x-262][y-30] =fin;
				}
				if (x>378 && x<610 && y>30 && y<height - 30){
					matriceDroite[x-378][y-30]=fin;
				}			
			}
		}

	}
}

package PAAAAACT;
import java.nio.ByteBuffer;



import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class KinectMatrice {
	private IplImage depth_image;
	private int[][]  matrice;
	private static int height=480;
	private static int width=640;
	private boolean is_playback;
	private int depth_bytes_per_pixels;
	
	
	public  KinectMatrice(IplImage depth_image, boolean is_playback, int depth_bytes_per_pixels){
		this.matrice=new int[width][height];
		this.depth_image=depth_image;
		this.is_playback=is_playback;
		this.depth_bytes_per_pixels=depth_bytes_per_pixels;
	}
	
	public void setImage(IplImage depth_image){
		this.depth_image=depth_image;
	}
	public void setPlay(boolean is_playback){
		this.is_playback=is_playback;
	}
	
	public void setDep(int depth_bytes_per_pixels){
		this.depth_bytes_per_pixels=depth_bytes_per_pixels;
	}
	public int[][] getMatrice1(){
		return this.matrice;
	}

	public void initializeMatrice(){
		
		this.matrice=new int[width][height];
		for (int y=0; y<height;y++){
			for (int x=0; x<width; x++) { 
         	   ByteBuffer depth_data = depth_image.getByteBuffer();
               ByteBuffer rescale_depth_data = null;
               int idx_depth = depth_bytes_per_pixels * (x + y*width);

             int resu;
             double fin;
            
             if (is_playback) {
              
               resu = depth_data.get(idx_depth);
               if (resu<0) resu = 255+resu;
             } else {
              
               int b1 = depth_data.get(idx_depth);
               int b2 = depth_data.get(idx_depth+1);
               if (b1<0) b1=255+b1;
               if (b2<0) b2=255+b2;

               resu = (b1<<8) | b2;
             }
             
             fin = (1000*(resu-746.2)/73.85);
             if ((resu==17600)||resu<0) resu=0;
             matrice[x][y]=resu;
	}
	
}
}
		
	public int[][] getMatrice(){
			return matrice;
		}
}


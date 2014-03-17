package PAAAAACT;
import java.nio.*;
import java.util.List;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.*;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.OpenKinectFrameGrabber;
import com.googlecode.javacv.cpp.freenect; 
import com.googlecode.javacv.cpp.avcodec.ReSampleContext;
import com.googlecode.javacv.cpp.freenect.*; 
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import pact.cconnexe.CConnexe;
import ransac.PlanEstimateur;
import ransac.Point3D;
import ransac.Ransac;
import ransac.Resultat;
import wavreading.Son;

import java.awt.GridLayout;

public class KikiRansac {
	public static double[][] affiche (double[][] mat){
		return mat;
	     }
	
    public static void PrintUsage() {
      System.out.println("KinectGrabber [OPTIONS]");
      System.out.println("where options can be");
      System.out.println("\t-min: minimum depth value (default 0). If depth values are below this value, depth is set to 0");
      System.out.println("\t-max: maximum depth value (default 2048==MAX KINECT DEPTH). If depth values are over this value, depth is set to 0");
      System.out.println("\t-rescale: rescale depth values to 0-255");
      System.out.println("\t-show-color: show color frame clamped with depth values - NO CALIBRATION YET");
      System.out.println("\t-show-orig: show original version of depth and/or color before clamping");
      System.out.println("\t-rec-depth AVIFILE: records depth video to AVIFILE.");
      System.out.println("\t-rec-orig-depth AVIFILE: records depth video before clamping to AVIFILE.");
      System.out.println("\t-rec-color AVIFILE: records color video to AVIFILE.");
      System.out.println("\t-rec-orig-color AVIFILE: records color video before clamping to AVIFILE.");
      System.out.println("\t-codec 4CC: sets codec to 4CC (e.g., XVID, DIVX, ...). Default record format is uncompressed video");
      System.out.println("\t-depth-in AVIFILE: uses AVIFILE as depth source.");
      System.out.println("\t-color-in AVIFILE: uses AVIFILE as RGB source.");
      System.out.println("\t-h or -help: prints this screen.");
    }

    /*helper function to create a frame recorder*/
    public static FrameRecorder CreateRecorder(String fileName, int width, int height, int codecID) throws Exception {
      FrameRecorder recorder = new OpenCVFrameRecorder(fileName, width, height);
      recorder.setCodecID(codecID);
//      recorder.setVideoCodec(codecID);
      recorder.setFrameRate(10.0);
      recorder.start();
      return recorder;
    }

    public static ResultatKinect kiki(/*String[] args*/) throws Exception {
       /*default values*/
       int depth_min = 0;
       int depth_max = 2048;
       boolean is_playback = false;
       int codecID = CV_FOURCC('D','I','B',' ');       
       boolean show_orig = false;
       boolean show_color = false;
       boolean record_depth = false;
       String depth_file = "";
       String in_depth_file = "";
       boolean record_orig_depth = false;
       String orig_depth_file = "";
       boolean record_color = false;
       String color_file = "";
       String in_color_file = "";
       boolean record_orig_color = false;
       String orig_color_file = "";
       boolean rescale = false;
       
       float[][] matriceLum = new float[640][480]; 
       float[][] oldMatriceLum;
       ResultatKinect retour =null;
       
       double time;
       double time2=0;
       double time3=0;
       
       show_color = true ;
        
        
      try{ 
        boolean clamp_rgb = false;
        boolean clamp_depth = true;
        int rescale_denum = 2048;
        /*create frame*/
        JFrame mainframe = new JFrame();
        int rows, cols;
        rows = cols = 1;
        if (show_orig && show_color) rows = cols = 2;
        else if (show_color || show_orig) cols = 2;
        mainframe.setLayout(new GridLayout(rows, cols));
        mainframe.setVisible(true);

        /*playback mode, turn off recording of source*/
        if (is_playback == true) {
          record_orig_depth = false;
          record_orig_color = false;
          if (in_depth_file.length()==0) {
           System.out.println("Missing input file name for depth file");
           System.exit(1);
          }
          if (show_color && (in_color_file.length()==0)) {
           System.out.println("Missing input file name for RGB file");
           System.exit(1);
          }
        }
        
        /*1- create canvas - we use OpenCV canvas as they are hardware-accelerated, hide them (hide the window) and add their underlying canvas to the main frame*/

        /*source depth canvas*/
        CanvasFrame depth_orig_frame = null;
        if (show_orig) {
          depth_orig_frame = new CanvasFrame("Source Depth");
          depth_orig_frame.setVisible(false);
        }
        
        /*source RGB canvas*/
        CanvasFrame rgb_orig_frame = null;
        if (show_orig && show_color) {
          rgb_orig_frame = new CanvasFrame("Source RGB");
          rgb_orig_frame.setVisible(false);
          System.out.println("ok");
        }

        /*depth canvas*/
        CanvasFrame depth_frame = new CanvasFrame("Depth");        
        depth_frame.setVisible(false);

        /*RGB canvas*/
        CanvasFrame rgb_frame = null;
        if (show_color) {
          rgb_frame = new CanvasFrame("RGB");
          rgb_frame.setVisible(false);
        }
        
        mainframe.getContentPane().add(depth_frame.getCanvas() );
        if (show_orig) mainframe.getContentPane().add(depth_orig_frame.getCanvas() );
        if (show_color) mainframe.getContentPane().add(rgb_frame.getCanvas() );
        if (show_orig && show_color) mainframe.getContentPane().add(rgb_orig_frame.getCanvas() );

        /*2- create depth grabber (whether Kinect or file)*/
        FrameGrabber depth_grabber = null;
        FrameGrabber rgb_grabber = null;        
        IplImage rgb_image = null;
        IplImage depth_image = null;

        if (is_playback) {
          depth_grabber = new OpenCVFrameGrabber(in_depth_file);
        } else {
          depth_grabber = new OpenKinectFrameGrabber(0);
          depth_grabber.setFormat("depth");
        }
        depth_grabber.start();


        /*3 - adjust windows size, and prepare some state vars for our program*/
        depth_image = depth_grabber.grab();
        int width  = depth_image.width();
        int height = depth_image.height();
        mainframe.setSize( cols*width, rows*height);

        /*4- create recorders and RGB grabber if needed*/
        FrameRecorder recorder_depth = null;
        FrameRecorder recorder_orig_depth = null;
        FrameRecorder recorder_color = null;
        FrameRecorder recorder_orig_color = null;
        if (record_depth) recorder_depth = CreateRecorder(depth_file, width, height, codecID);
        if (record_orig_depth) recorder_orig_depth = CreateRecorder(orig_depth_file, width, height, codecID);
        if (record_color) recorder_color = CreateRecorder(color_file, width, height, codecID);
        if (record_orig_color) recorder_orig_color = CreateRecorder(orig_color_file, width, height, codecID);

        if (show_color || (recorder_color!=null) || (recorder_orig_color!=null)) {
          if (is_playback) {
            rgb_grabber = new OpenCVFrameGrabber(in_color_file);
          } else {
            rgb_grabber = new OpenKinectFrameGrabber(0);
            rgb_grabber.setFormat("rgb");
          }
          rgb_grabber.start();
        }


        if (show_color || record_color){ 
        	clamp_rgb = true;
        }
        if ((depth_min == 0) && (depth_max==2048)) clamp_depth = false;
        /*warning: depth bytes per pixel is 2 (11 bits raw data frm kinect -> 16 bits), but the JavaCV grabber gives
        us a weird format with 3 components, 16 bits each*/
        int depth_bytes_per_pixels = depth_image.widthStep() / width;

        System.out.println((is_playback ? "Playback" : "Capture" ) + " mode");
        System.out.println("Depth image: width "+depth_image.width() + " height " + depth_image.height() + " stride " + depth_image.widthStep() + " - bytes per pixel " + depth_bytes_per_pixels);
        System.out.println("Clamping " + (clamp_depth ? "Enabled" : "Disabled") + " - depth min value " + depth_min + " - max value " + depth_max);
        System.out.println("RGB enabled: " + ((rgb_grabber==null) ? "no" : "yes"));
        
        
        rescale_denum = (depth_max - depth_min);
        IplImage rescale_depth_image = null;
        if (rescale) {
          rescale_depth_image = IplImage.create(depth_image.width(), depth_image.height(), IPL_DEPTH_8U, 3);
        }
        
        
        byte blank = 0;
        byte blank_depth = -1;
        
        // Objects allocated with a create*() or clone() factory method are automatically released
        // by the garbage collector, but may still be explicitly released by calling release().
        CvMemStorage storage = CvMemStorage.create();
        int [][] matrice= new int [depth_image.width()][depth_image.height()];
        
        
        /*5- main loop:
          1- grab depth/rgb, display/record if needed
          2- clamp depth and RGB values based on depth value
          3- display/record modified frames
        */   
        long tempsDebutSon = 0 ;
       while (mainframe.isVisible() ) {
        
       // for(int i = 0 ; i<=10 ; i++) {
        //	time = System.currentTimeMillis();
        	
           if (rgb_grabber != null) {
            rgb_image = rgb_grabber.grab();
            if ((rgb_image != null) && (recorder_orig_color != null)) recorder_orig_color.record(rgb_image);
            }

           depth_image = depth_grabber.grab();
           //if (depth_image == null) continue;

           if (recorder_orig_depth != null) recorder_orig_depth.record(depth_image);

           if (show_orig) {
            depth_orig_frame.showImage(depth_image);
            if (show_color) rgb_orig_frame.showImage(rgb_image);
           }
         
         KinectMatrice test = new KinectMatrice(depth_image, is_playback,depth_bytes_per_pixels);
       //création et initialisation de profondeur pour le module détection de plan
        test.initializeListetMatrice();
       	PlanEstimateur p=new PlanEstimateur(0.01) ;  //creation du plan pour ransac     
		Ransac ransac= new Ransac(0.1, p);
		
		System.out.println("Je suis avant Ransac ") ;
 		Resultat resultatransac1 = ransac.Algo(test.getList(), 0.99);//premiere application de ransac pour trouver un plan
		List<Point3D> inter = resultatransac1.getNewList(resultatransac1.getData(),test.getList());
 		Resultat resultatransac2= ransac.Algo(inter, 0.99);
 		Resultat resultatransac3= ransac.Algo(resultatransac2.getNewList(resultatransac2.getData(),inter), 0.99);
 		List<Double> MeilleurPlan=resultatransac1.getParam();
// 		for(int i=0;i< MeilleurPlan.size();i++){
// 			System.out.println(MeilleurPlan.get(i));
// 		}
 		test.setMatriceZero(resultatransac1.getData());
 		
 		CConnexe fin = new CConnexe(test.getMatrice());
 		 if(fin.contientObjet){
       	 Son.lireSon("data/AlarmMilieu.wav", 500);
         }
 
         
           
           if (clamp_depth || rescale) {
             ByteBuffer depth_data = depth_image.getByteBuffer();
             ByteBuffer rescale_depth_data = null;
             ByteBuffer rgb_data = null;

             if ((rgb_image!=null) && clamp_rgb) rgb_data = rgb_image.getByteBuffer();
             if (rescale) rescale_depth_data = rescale_depth_image.getByteBuffer();
   
             for (int y=0; y<height;y++) {
               for (int x=0; x<width; x++) {
                int idx_depth = depth_bytes_per_pixels * (x + y*width);
  
                int res;
                /*warning when getting pixel values: pixels components are unsigned values, however Java does not know unsigned number. 
                we therefore have to reverse negative numbers*/
                if (is_playback) {
                  /*recorded files are on 8 bits per pixel*/
                  res = depth_data.get(idx_depth);
                  if (res<0) res = 255+res;
                } else {
                  /*captured files are on 16 bits per pixel*/
                  int b1 = depth_data.get(idx_depth);
                  int b2 = depth_data.get(idx_depth+1);
                  if (b1<0) b1=255+b1;
                  if (b2<0) b2=255+b2;

                  res = (b1<<8) | b2;
                }
 
                if ((res > depth_max) || (res < depth_min)) {
                  /*clamp depth to 0*/
                  if (res > depth_max) {
                    res = depth_max;
                    for (int k=0; k<depth_bytes_per_pixels; k++) {
                     depth_data.put(idx_depth+k, blank_depth);
                    }
                  } else {
                    res = depth_min;
                    for (int k=0; k<depth_bytes_per_pixels; k++) {
                     depth_data.put(idx_depth+k, blank);
                    }
                  }
                               
                  /*clamp color to black (0,0,0)*/
                  if ((rgb_image != null) && clamp_rgb) {
                    int rgb_idx = 3*(x+y*width);
                    rgb_data.put(rgb_idx, blank);
                    rgb_data.put(rgb_idx+1, blank);
                    rgb_data.put(rgb_idx+2, blank);
                  }
                }
                
                if (rescale) {
                  res -= depth_min;
                  res = (res * 255) / rescale_denum;
                
                  int rescale_idx = 3* (x + y*width);
                  rescale_depth_data.put(rescale_idx+2, (byte) (res & 0xFF) );
                  rescale_depth_data.put(rescale_idx+1, (byte) (((res>>8) & 0xFF)*30) );
                  rescale_depth_data.put(rescale_idx, (byte) (0) );                
                }
                  
              }
             }
           }
           if (rescale) {
             depth_frame.showImage(rescale_depth_image );
             if (recorder_depth != null) recorder_depth.record(rescale_depth_image);
           } else {
             depth_frame.showImage(depth_image );
             if (recorder_depth != null) recorder_depth.record(depth_image);
           }
           
           if (rgb_image != null) {
            if (show_color) rgb_frame.showImage(rgb_image);
            if (recorder_color != null) recorder_color.record(rgb_image);
           }
           cvClearMemStorage(storage);
           
           if (is_playback) Thread.sleep(100);
           
        
//           time2 = System.currentTimeMillis();
//           time3 = time2 - time ;
          // System.out.println("Temps : boucle "+ i + ", " + time3);
        }

        /*6- as it is stated, cleanup*/
        System.out.println("Cleaning up kinect");
        
        depth_frame.dispose();
        if (show_color) rgb_frame.dispose();
        if (show_orig) {
         depth_orig_frame.dispose();
         if (show_color) rgb_orig_frame.dispose();
        }

        if (recorder_depth != null) recorder_depth.stop();
        if (recorder_orig_depth != null) recorder_orig_depth.stop();
        if (recorder_color != null) recorder_color.stop();
        if (recorder_orig_color != null) recorder_orig_color.stop();

        depth_grabber.stop();

        if (rgb_grabber != null) rgb_grabber.stop();
         
        System.out.println("Done");
        return retour;
      }catch(Exception e){
    	  e.printStackTrace();
        System.out.println(e.getMessage());
      }         
      System.exit(0);
      return retour;
    }
    
    public double[][] matriceGauche(double[][] matrice){
    	double[][] res = new double[matrice.length][matrice[0].length];
    	
    	return res ;
    }
}



package PAAAAACT;
import java.nio.*;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.*;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.OpenKinectFrameGrabber;
import com.googlecode.javacv.cpp.freenect; 
import com.googlecode.javacv.cpp.freenect.*; 
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import pact.cconnexe.CConnexe;
import wavreading.AlarmeDroite;
import wavreading.Son;

import java.awt.GridLayout;

public class Kiki {
	
	

    /*helper function to create a frame recorder*/
    public static FrameRecorder CreateRecorder(String fileName, int width, int height, int codecID) throws Exception {
      FrameRecorder recorder = new OpenCVFrameRecorder(fileName, width, height);
      recorder.setCodecID(codecID);
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
       show_color = true ;
       
       float[][] oldMatriceLum = new float[640][480] ;
       float[][] MatriceLum = new float[640][480] ;
       
       ResultatKinect retour =null;
       
        
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
      
        
        
        /*5- main loop:
          1- grab depth/rgb, display/record if needed
          2- clamp depth and RGB values based on depth value
          3- display/record modified frames
         
        */
        
       
       while (mainframe.isVisible() ) {
        
        	
           if (rgb_grabber != null) {
            rgb_image = rgb_grabber.grab();
            if ((rgb_image != null) && (recorder_orig_color != null)) recorder_orig_color.record(rgb_image);
            }

           depth_image = depth_grabber.grab();
           if (depth_image == null) continue;

           if (recorder_orig_depth != null) recorder_orig_depth.record(depth_image);

           if (show_orig) {
            depth_orig_frame.showImage(depth_image);
            if (show_color) rgb_orig_frame.showImage(rgb_image);
           }
         
          KinectMatrice test = new KinectMatrice(depth_image, is_playback,depth_bytes_per_pixels);
           //création et initialisation de profondeur pour le module détection de plan
         test.initializeListetMatrice();
        
        
        
        CConnexe testGauche = new CConnexe(test.matriceGauche);//application de la méthode pour détecter les composantes connexes sur la matrice de profondeur divisée en trois parties
        CConnexe testDroite = new CConnexe(test.matriceDroite);
        CConnexe testMilieu= new CConnexe(test.matriceMilieu);
        
        
        if (testGauche.contientObjet){//déclenchement des alarmes gauche, droite et milieu suivant les cas
        	if (testMilieu.contientObjet){
        			Son.lireSon("data/AlarmMilieu.wav", 500);
        	} else {
        		if (! testDroite.contientObjet){
        			Son.lireSon("data/AlarmGauche.wav", 500);
        		}
        	}
        }else {
        	if (testMilieu.contientObjet){
        		Son.lireSon("data/AlarmMilieu.wav", 500);
        	}else{
        		if (testDroite.contientObjet){
        			Son.lireSon("data/AlarmDroite.wav", 500);
        		}
        		
        	}
        }
          
         KinectMatriceColor test2 = new KinectMatriceColor(rgb_image);//création et initialisation de la matrice de luminance pour l'estimation de mouvement
         oldMatriceLum = MatriceLum ;
         test2.initializeMatrice();
         MatriceLum = test2.getMatriceLum();
           
         retour = new ResultatKinect(test.getMatrice(),MatriceLum, oldMatriceLum,test.getList());
           
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
	
}


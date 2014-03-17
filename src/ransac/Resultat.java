package ransac;
import java.util.ArrayList;
import java.util.List;


public class Resultat {

	List<Point3D> Data = new ArrayList<Point3D>();
	List<Double> Param = new ArrayList<Double>();
	
	public Resultat(List<Point3D> Data,List<Double>  param){
		this.Data=Data;
		this.Param=Param;
	}

	public void setData(List<Point3D> data) {
		Data = data;
	}

	public void setParam(List<Double> param) {
		Param = param;
	}

	public List<Point3D> getData() {
		return Data;
	}

	public List<Double> getParam() {
		return Param;
	}

	public double[][] getMatriceCConnexe(double[][] matrice){
		for (Point3D point:this.Data){
			matrice[(int)point.getX()][(int)point.getY()]=0;
			}
		return matrice;
	}
	public List<Point3D> getNewList(List<Point3D> Data,List<Point3D> Kinect){
		for (Point3D p: Data){
			Kinect.remove(p);
			}
		return Kinect;
		}
		
	}
	



package ransac;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method s

		PlanEstimateur p = new PlanEstimateur(0.01);
		Ransac ransac = new Ransac(0.99, p);

		List<Double> ParamPlan = new ArrayList<Double>();
		List<Point3D> data = new ArrayList<Point3D>();
		List<Point3D> MeilleurPlan = new ArrayList<Point3D>();

//		data.add(new Point3D(-8, 7, -2));
//		data.add(new Point3D(10, -1, 4));
//		data.add(new Point3D(-8.01, 7.96, 0));
//		data.add(new Point3D(-2.03, -1.94, 1));
//		data.add(new Point3D(-6.01, 5.56, 1));
//		data.add(new Point3D(8.03, -7.34, 1));
//		data.add(new Point3D(-3.01, 3.96, 1));
//		data.add(new Point3D(5.03, -3.34, 1));
//		data.add(new Point3D(-7.01, 7.96, 1));
//		data.add(new Point3D(-3.03, 2.94, 1));
//		data.add(new Point3D(6.71, -6.56, 1));
//		data.add(new Point3D(-8.23, 7.44, 1));
//		data.add(new Point3D(3.31, 4.96, 1));
//		data.add(new Point3D(-4.33, -3.44, 1));
//		data.add(new Point3D(-7.01, 7.96, 1));
//		data.add(new Point3D(-3.03, 2.94, 1));
//		data.add(new Point3D(5.71, -6.56, 1));
//		data.add(new Point3D(-6.23, 7.44, 1));
//		data.add(new Point3D(7.31, 4.96, 1));
//		data.add(new Point3D(-8.33, -3.44, 1));

		for(int i = 0; i< 640*480 ; i++){
			Random alea = new Random();
			data.add(new Point3D(alea.nextDouble()*100, alea.nextDouble()*100, 1));
		}
		ParamPlan = ransac.Algo(data, 0.9999).getParam();
		MeilleurPlan = ransac.Algo(data, 0.9999).getData();

		/*
		 * for(int i=0;i< MeilleurPlan.size();i++){
		 * System.out.println(MeilleurPlan.get(i)); }
		 */
		
		for (int j = 0; j < ParamPlan.size(); j++) {
			System.out.println(ParamPlan.get(j));
		}

		// tester estimerUn Plan
		/*
		 * List<Double> e =
		 * p.estimerUnPlan(data.get(0),data.get(1),data.get(2)); for (int
		 * i=0;i<e.size();i++){ System.out.print(e.get(i)); } for (int i = 0; i
		 * < 3; i++) {
		 */

		// initializer l'itérateur

		// sélection aléatoire des données
		// do{
		/*
		 * Random random=new Random(100000); int i1 =random.nextInt(3 );
		 * System.out.print(i1); int i2 =random.nextInt(2);
		 * System.out.print(i2); int i3 =random.nextInt(1);
		 * System.out.print(i3); }
		 */
	}

}
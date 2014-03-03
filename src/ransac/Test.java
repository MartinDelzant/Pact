package ransac;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Test extends JFrame {

        /**
         * Unique ID
         */
        private static final long serialVersionUID = 6538463951464863248L;

        public Test() {
                super("Test");
                setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                setSize(new Dimension(600, 600));
                setVisible(true);
                setResizable(false);
        }

        public void paint(Graphics g, int[][] matrice) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0xffffff));
                g2.fillRect(0, 0, 600, 600);
                g2.translate(300, 300);
                g2.setColor(new Color(0xcccccc));
                for (int i = -9; i < 10; i++) {
                        g2.drawLine(-300, i * 30, 300, i * 30);
                        g2.drawLine(i * 30, -300, i * 30, 300);
                }
                g2.setStroke(new BasicStroke(2));
                List<Point3D> data = new ArrayList<Point3D>();
                List<Double> params;
                LineParamEstimator lpe = new LineParamEstimator(0.5);

//              System.out.println("params:[n_x,n_y,a_x,a_y]");
//              for (int i = 0; i < 4; i++) {
//                      System.out.print("\t" + params.get(i));
//              }
//              System.out.println();

                for (int y=0; y<480;y++){
        			for (int x=0; x<640; x++) {  
        		data.add(new Point3D(x,y,matrice[x][y]));
        			}
        		}
                
                params = lpe.FonctionMoindreCarre(data);
//              System.out.println("params:[n_x,n_y,a_x,a_y]");
//              for (int i = 0; i < 4; i++) {
//                      System.out.print("\t" + params.get(i));
//              }
//              System.out.println();
                Point3D p = new Point3D(2.02, 0.69,5);
//              System.out.println("Is point " + p + " near line?");
                if (lpe.agree(params, p)) {
//                      System.out.println("Yes");
                        drawPlot(g2, p.getX(), p.getY(), 4, 0x66aa66, true);
                } else {
//                      System.out.println("No");
                        drawPlot(g2, p.getX(), p.getY(), 4, 0xaa3333, true);
                }
                for (int i = 0; i < data.size(); i++) {
//                      int x = (int) Math.round(data.get(i).getX() * 30.0);
//                      int y = (int) Math.round(data.get(i).getX() * 30.0);
                        drawPlot(g2, data.get(i).getX(), data.get(i).getY(), 4, 0xaaff00, true);
                }
                drawLine(g2, params.get(0), params.get(1), params.get(2),
                                params.get(3), 1, 0x3366aa);
                drawLine(g2, params.get(0), params.get(1), params.get(2)-params.get(0)*0.5,
                                params.get(3)-params.get(1)*0.5, 1, 0x3366aa);
                drawLine(g2, params.get(0), params.get(1), params.get(2)+params.get(0)*0.5,
                                params.get(3)+params.get(1)*0.5, 1, 0x3366aa);
                // g2.fillRect(0, 0, 600, 600);
                // g2.translate(300, 300);
                // g2.setColor(new Color(0x000000));
                // g2.drawLine(100, 40, -80, 300);
                // g2.setColor(new Color(0xff6600));
                // g2.drawRect(10, 50, 100, 100);
                // drawPlot(g2,10,204,5,0x557700,false);
                // drawPlot(g2,30,204,5,0x3366aa,true);
                // drawPlot(g2,150,-204,2,0x3366aa,true);
        }

        private void drawPlot(Graphics2D g, double x, double y, int size, int rgb,
                        boolean isFill) {
                int pX = (int) Math.round(x * 30.0);
                int pY = (int) Math.round(y * 30.0);
                Color color = g.getColor();
                g.setColor(new Color(rgb));
                if (isFill) {
                        g.fillRect(pX - size, pY - size, size * 2, size * 2);
                } else {
                        g.drawOval(pX - size, pY - size, size * 2, size * 2);
                }
                g.setColor(color);
        }

        private void drawLine(Graphics2D g, double nX, double nY, double aX,
                        double aY, int size, int rgb) {
                int w = 30;
                int y1 = (int) Math.round((nX * (aX + 10.0) / nY + aY) * w);
                int y2 = (int) Math.round((nX * (aX - 10.0) / nY + aY) * w);
                Color color = g.getColor();
                Stroke stroke = g.getStroke();
                g.setColor(new Color(rgb));
                g.setStroke(new BasicStroke(size));
                g.drawLine(-300, y1, 300, y2);
//              g.drawString("-300, "+y1+", 300, "+y2, -280, -250);
                g.setColor(color);
                g.setStroke(stroke);
        }

        /**
         * @param args
         */
        public static void main(String[] args) {
                new Test();
                //g.finalize();
        }
}
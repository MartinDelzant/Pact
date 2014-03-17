package PAAAAACT;

import java.nio.ByteBuffer;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class KinectMatriceColor {// classe pour le module estimation de
									// mouvement
	private IplImage rgb_image;
	private float[][] matrice_lum;
	private static int height = 480;
	private static int width = 640;

	public float[][] getMatriceLum() {
		return matrice_lum;
	}

	public KinectMatriceColor(IplImage rgb_image) {
		this.matrice_lum = new float[width][height];
		this.rgb_image = rgb_image;
	}

	public void setImage(IplImage rgb_image) {
		this.rgb_image = rgb_image;
	}

	public void initializeMatrice() {// initialisation de la matrice de
										// luminance à partir des composantes
										// rouge verte et bleue du pixel
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel_index = 3 * x + 3 * width * y;
				ByteBuffer rgb_data = rgb_image.getByteBuffer();

				// lire la composante bleu de notre pixel
				float blue_value = (float) rgb_data.get(pixel_index);
				if (blue_value < 0)
					blue_value = 255 + blue_value;

				// lire la composante verte de notre pixel
				float green_value = (float) rgb_data.get(pixel_index + 1);
				if (green_value < 0)
					green_value = 255 + green_value;

				// lire la composante rouge de notre pixel
				float red_value = (float) rgb_data.get(pixel_index + 2);
				if (red_value < 0)
					red_value = 255 + red_value;

				this.matrice_lum[x][y] = (float) (0.2126 * red_value + 0.7152
						* green_value + 0.0722 * blue_value);
			}
		}
	}
}

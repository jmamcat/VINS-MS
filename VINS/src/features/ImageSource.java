package features;

import org.opencv.core.Mat;

public interface ImageSource {
	public static final int GRAYSCALE = 0;
	public static final int RGBA = 1;
	
	public Mat getCurrentFrame(int type);
}

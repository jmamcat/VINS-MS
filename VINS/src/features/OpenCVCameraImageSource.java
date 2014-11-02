package features;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Mat;

import android.util.Log;

public class OpenCVCameraImageSource implements ImageSource, CvCameraViewListener2 {
	public static final String TAG = "OpenCV Camera"; 
	private static CvCameraViewFrame currentFrame;
	
	public OpenCVCameraImageSource(){
		Log.i(TAG, "Trying to load OpenCV library");
	}

	@Override
	public Mat onCameraFrame(CvCameraViewFrame arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCameraViewStarted(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mat getCurrentFrame(int type) {
		// TODO Auto-generated method stub
		return null;
	}
}
		/*
		this.listener = listener;
		initLoader(caller);

		cameraView = (CameraBridgeViewBase) caller.findViewById(R.id.surface_view);
		// http://stackoverflow.com/a/17872107
		// cameraView.setMaxFrameSize(720, 1280); // sets to 720 x 480
		cameraView.setMaxFrameSize(400, 1280); // sets to 320 x 240
		cameraView.setVisibility(SurfaceView.VISIBLE);

		cameraView.setCvCameraViewListener(this);

		if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, caller, loaderCallback)) {
			Log.e(TAG, "Cannot connect to OpenCV Manager");
		}
	}
	
	private void initLoader(Activity caller) {
		loaderCallback = new BaseLoaderCallback(caller) {
			@Override
			public void onManagerConnected(int status) {
				switch (status) {
				case LoaderCallbackInterface.SUCCESS: {
					Log.i(TAG, "OpenCV loaded successfully");
					opticalFlow = new OpticalFlow();
					triangulation = new Triangulation();
					cameraView.enableView();
					checkpointFeatures = new MatOfPoint2f();
					checkpointImage = new Mat();
					images = new ArrayList<>();
					listener.initDone();
				} break;
				default: {
					super.onManagerConnected(status);
				} break;
				}
			}
		};
	}
	
	public Mat getCurrentFrame(int type){
		switch(type){
			case GRAYSCALE:	
				return currentFrame.gray();
			case RGBA:
				return currentFrame.rgba();
		}
		return null;
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		// TODO Auto-generated method stub
		return null;
	}
}
*/
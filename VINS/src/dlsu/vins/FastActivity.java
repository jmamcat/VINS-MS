package dlsu.vins;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.video.Video;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

public class FastActivity extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "Fast Activity";
    private FeatureDetector detector;
    
    private CameraBridgeViewBase mOpenCvCameraView;
    private TextView textBox;
    private int totalUpdates = 0;
    
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    prevFeatures = new MatOfPoint2f();
                    prevImage = new Mat();
                    detector = FeatureDetector.create(FeatureDetector.FAST);
                } break;
                default: {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public FastActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.fastlayout);

        Log.i(TAG, "Trying to load OpenCV library");

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.surface_view);
        textBox = (TextView) findViewById(R.id.counter);
        
        // http://stackoverflow.com/a/17872107
        //mOpenCvCameraView.setMaxFrameSize(720, 1280); // sets to 720 x 480
        mOpenCvCameraView.setMaxFrameSize(400, 1280); // sets to 320 x 240
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        
        mOpenCvCameraView.setCvCameraViewListener(this);
    }
    
    // http://stackoverflow.com/a/7433510
    
    private void updateFPS() {
        textBox.setText("FPS: " + totalUpdates);
        Log.d("FPS printing", ""+totalUpdates);
        totalUpdates = 0;
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_2, this, mLoaderCallback))
        {
          Log.e(TAG, "Cannot connect to OpenCV Manager");
        }   
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    
    private Size imageSize;
    private Mat cameraMatrix, distCoeffs, Rot, T;
    private Mat R1,R2,P1,P2,Q;
    private Mat points4D;

    public void onCameraViewStarted(int width, int height) {
    	 
        drawMasks = new ArrayList<Mat>();
    	
    	// CAMERA CALIBRATION START
        
        // INPUT VARIABLES
        
        cameraMatrix = Mat.zeros(3, 3, CvType.CV_64F);
        distCoeffs =  Mat.zeros(5, 1, CvType.CV_64F);
        imageSize = new Size(width, height);
        Rot = Mat.zeros(3, 3, CvType.CV_64F);
        T = Mat.ones(3, 1, CvType.CV_64F);
        
        cameraMatrix.put(0, 0, 1768.104971372035, 0, 959.5);
        cameraMatrix.put(1, 0, 0, 1768.104971372035, 539.5);
        cameraMatrix.put(2, 0, 0, 0, 1);
        
        distCoeffs.put(0, 0, 0.1880897270445046);
        distCoeffs.put(1, 0, -0.7348187497379466);
        distCoeffs.put(2, 0, 0);
        distCoeffs.put(3, 0, 0);
        distCoeffs.put(4, 0, 0.6936210153459164);
        
        Rot.put(0, 0, 1, 0, 0);
        Rot.put(1, 0, 0, 1, 0);
        Rot.put(2, 0, 0, 0, 1);
        
        // OUTPUT VARIABLES
        
        R1 = Mat.zeros(3, 3, CvType.CV_64F);
        R2 = Mat.zeros(3, 3, CvType.CV_64F);
        P1 = Mat.zeros(3, 4, CvType.CV_64F);
        P2 = Mat.zeros(3, 4, CvType.CV_64F);
        Q = Mat.zeros(4, 4, CvType.CV_64F);
        
        Calib3d.stereoRectify(cameraMatrix, distCoeffs, cameraMatrix.clone(), distCoeffs.clone(), imageSize, Rot, T, R1, R2, P1, P2, Q);
        
        // CAMERA CALIBRATION END
    }

    public void onCameraViewStopped() { }
    
    private MatOfPoint2f prevFeatures;
    private Mat prevImage;
    private Mat drawMask;
    
    private List<Mat> drawMasks;
    
    private final Scalar RED = new Scalar(255,0,0);
    private final Scalar GREEN = new Scalar(0,255,0);
    private final Scalar BLACK = new Scalar(0);
    private final Scalar WHITE = new Scalar(255);
    
    private int frames = 0;
    private int detectInterval = 5;
    
    // NOTE: just a tempory variable
    private boolean first = true;
    
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Log.d("VINS", "onCameraFrame");
        
        Mat image = inputFrame.gray();
        
        Mat modifiedImage = image.clone();
        Mat detectMask = image.clone();
        Mat drawMask = image.clone();
        detectMask.setTo(WHITE);
        
        if (prevFeatures.size().height > 0) {
            MatOfByte status = new MatOfByte();
            MatOfFloat err = new MatOfFloat();
            MatOfPoint2f nextFeatures = new MatOfPoint2f();
            Video.calcOpticalFlowPyrLK(prevImage, image, prevFeatures, nextFeatures, status, err);
            
            List<Point> oldPoints = prevFeatures.toList();
            List<Point> newPoints = nextFeatures.toList();
            List<Point> goodOldList = new ArrayList<>();
            List<Point> goodNewList = new ArrayList<>();
            List<Integer> badPointsIndex = new ArrayList<>();
            int i = 0;
            Mat imageLines = image.clone();
            for (Byte item : status.toList()) {
                if (item.intValue() == 1) {
                    goodOldList.add(oldPoints.get(i));
                    goodNewList.add(newPoints.get(i));
                    Core.circle(detectMask, newPoints.get(i), 10, BLACK, -1); // mask out during detection        
                    Core.line(drawMask, oldPoints.get(i), newPoints.get(i), WHITE, 1);
                    Core.circle(imageLines, newPoints.get(i), 2, RED, -1);
                }
                else {
                    badPointsIndex.add(Integer.valueOf(i));
                }
                i++;
            }
            Core.add(imageLines, drawMask, modifiedImage);
            MatOfPoint2f goodOld = new MatOfPoint2f();
            MatOfPoint2f goodNew = new MatOfPoint2f();
            goodOld.fromList(goodOldList);
            goodNew.fromList(goodNewList);
            
            goodNew.copyTo(prevFeatures);

            // TRIANGULATION ???
            
            // TODO: might want to initialize points4D with a large Nx4 Array
            //		 so that both memory and time will be saved (instead of reallocation each time)
            
            points4D = Mat.zeros(1, 4, CvType.CV_64F);
            
            if(!goodOld.empty() && !goodOld.empty())
            	Calib3d.triangulatePoints(P1, P2, goodOld, goodNew, points4D);
            
            // Only dumps the first triangulation result for now
            if(first){
                Log.i("Array Sizes", goodOld.size() + " " + goodNew.size() + " " + points4D.size());
                Log.i("Old Points", goodOld.dump());
                Log.i("New Points", goodOld.dump());
            	Log.i("Triangulated Points", points4D.dump());
            	first = !first;
            }
        }
        
        if (frames % detectInterval == 0) {
            MatOfKeyPoint featureMat = new MatOfKeyPoint();
            detector.detect(image, featureMat, detectMask);
            if (featureMat.size().height > 0) {
                MatOfPoint2f newFeatures = convert(featureMat);
                prevFeatures.push_back(newFeatures);
            }
        }
        
        // fades lines (purely a visual thing really)
        
        drawMasks.add(drawMask.clone());
        
        if(drawMasks.size() > (detectInterval * 5)){
        	Mat old = drawMasks.get(0);
        	Mat filtered = drawMask;
        	filtered.setTo(BLACK, old);
        	drawMasks.remove(0);
        	drawMask = filtered;
        }
        
//        // resets the tracks after a while, else screen will be all white
//        if (frames % (detectInterval * 5) == 0) {
//            drawMask = image.clone();
//            drawMask.setTo(BLACK);
//        }
        
        frames++;
        image.copyTo(prevImage);
        Log.d("Next", prevFeatures.size() + "");
        return modifiedImage;
    }
    
    private MatOfPoint2f convert(MatOfKeyPoint keyPoints) {
        KeyPoint[] keyPointsArray = keyPoints.toArray();
        Point[] pointsArray = new Point[keyPointsArray.length];
        
        for (int i = 0; i < keyPointsArray.length; i++) {
            pointsArray[i] = (Point) keyPointsArray[i].pt;
        }
        
        return new MatOfPoint2f(pointsArray);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
   
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}

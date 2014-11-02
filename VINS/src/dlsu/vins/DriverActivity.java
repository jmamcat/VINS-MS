package dlsu.vins;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import motionestimation.DevicePose;
import motionestimation.IntegrateMotionEstimation;
import motionestimation.MotionEstimation;
import motionestimation.SensorEntry;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import ekf.EKF;
import ekf.PointDouble;
import features.FeatureManager;
import features.FeatureManagerListener;
import features.FeatureUpdate;

public class DriverActivity extends Activity implements SensorEventListener, FeatureManagerListener {

	private static String TAG = "Driver Activity";
	private SensorEntry nextSensorEntryToAdd;
	private EKF ekf;
	private FeatureManager featureManager;
	MotionEstimation motionEstimator;

	// TODO: maybe separate all motion estimation things to motion estimator
	private SensorManager sensorManager;
	private Sensor senAccelerometer, senGyroscope, senOrientation, senGravity, senMagnetField;

	private float mGrav[], mMag[];

	private boolean isFeaturesReady = false;

	private long timeLastRecorded;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_driver);
		setContentView(R.layout.fastlayout);

		featureManager = new FeatureManager(this, this);
		ekf = new EKF();
		Log.i("EKF", ekf.getCurrDevicePose().toString());
		motionEstimator = new IntegrateMotionEstimation();
		nextSensorEntryToAdd = new SensorEntry();

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);// Sensor.TYPE_ACCELEROMETER);
		senGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		senOrientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

		senGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		senMagnetField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this, senGyroscope, SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this, senOrientation, SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this, senGravity, SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this, senMagnetField, SensorManager.SENSOR_DELAY_FASTEST);

		startDriver();
		timeLastRecorded = System.currentTimeMillis();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	private void startDriver() {
		Timer recordTimer = new Timer();
		recordTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOneCycle();
			}
		}, 0, 333);
	}

	private void runOneCycle() {
		
		// const VW::ImageMono<unsigned char>*, double delta_t, bool currently_mapping_flag
		
		
		
		/*
		 * // Nullify image selection robot->nullify_image_selection();
		 * 
		 * init_feature_search_region_defined_flag = false;
		 * 
		 * 
		 * // Control vector of accelerations VNL::Vector<double> u(3);
		 * u.Fill(0.0);
		 * 
		 * sim_or_rob->set_control(u, delta_t);
		 * 
		 * // Record the current position so that I can estimate velocity // (We
		 * can guarantee that the state vector has position; we can't //
		 * guarantee that it has velocity.) VNL::Vector<double> xv =
		 * scene->get_xv(); scene->get_motion_model()->func_xp(xv);
		 * VNL::VectorFixed<3, double> prev_xp_pos =
		 * scene->get_motion_model()->get_xpRES().Extract(3);
		 * 
		 * // Prediction step kalman->predict_filter_fast(scene, u, delta_t);
		 * 
		 * number_of_visible_features =
		 * scene->auto_select_n_features(NUMBER_OF_FEATURES_TO_SELECT);
		 * 
		 * if(Scene_Single::STATUSDUMP) scene->print_selected_features();
		 * 
		 * if (scene->get_no_selected() != 0) { //
		 * scene->predict_measurements(); if (DEBUGDUMP) cout <<
		 * "Time after predicting measurements " << timer1 << endl;
		 * 
		 * // Calls function in control_general.cc number_of_matched_features =
		 * make_measurements(scene, sim_or_rob);
		 * 
		 * if (DEBUGDUMP) cout << "Time after making measurements " << timer1 <<
		 * endl;
		 * 
		 * if (scene->get_successful_measurement_vector_size() != 0) {
		 * kalman->total_update_filter_slow(scene);
		 * 
		 * if (DEBUGDUMP) cout << "Time after total_update_filter " << timer1 <<
		 * endl;
		 * 
		 * scene->normalise_state();
		 * 
		 * if (DEBUGDUMP) cout << "Time after normalise_state " << timer1 <<
		 * endl; } }
		 * 
		 * scene->delete_bad_features();
		 * 
		 * // Let's enforce symmetry of covariance matrix... // Add to transpose
		 * and divide by 2 VNL::Matrix<double>
		 * Pxx(scene->get_total_state_size(), scene->get_total_state_size());
		 * scene->construct_total_covariance(Pxx); VNL::Matrix<double> PxxT =
		 * Pxx.Transpose();
		 * 
		 * Pxx.Update(Pxx * 0.5 + PxxT * 0.5); scene->fill_covariances(Pxx);
		 * 
		 * // Look at camera speed estimate // Get the current position and
		 * estimate the speed from it xv = scene->get_xv();
		 * scene->get_motion_model()->func_xp(xv); VNL::VectorFixed<3, double>
		 * xp_pos = scene->get_motion_model()->get_xpRES().Extract(3); velocity
		 * = (xp_pos - prev_xp_pos) / delta_t; double speed =
		 * sqrt(velocity.SquaredMagnitude()); if (DEBUGDUMP) cout <<
		 * "Camera speed " << speed << " ms^-1 " << endl;
		 * 
		 * if (speed > 0.2 && currently_mapping_flag) { if
		 * (number_of_visible_features < NUMBER_OF_FEATURES_TO_KEEP_VISIBLE &&
		 * scene->get_feature_init_info_vector().size() < (unsigned
		 * int)(MAX_FEATURES_TO_INIT_AT_ONCE)) { AutoInitialiseFeature(u,
		 * delta_t); } }
		 * 
		 * if (DEBUGDUMP) cout << "Time after matching point features: " <<
		 * timer1 << endl;
		 * 
		 * MatchPartiallyInitialisedFeatures();
		 * 
		 * if (DEBUGDUMP) cout <<
		 * "Time after matching partially init. feature: " << timer1 << endl;
		 */

		if (!isFeaturesReady)
			return;

		try {
			StringBuilder timeStringBuilder = new StringBuilder();
			/* TRIGGER MOTION ESTIMATION */
			DevicePose devicePose = motionEstimator.getHeadingAndDisplacement();

			StringBuilder logString = new StringBuilder();
			logString.append("Device Pose(ME): " + devicePose.toString() + "\n");

			/* PASS DISTANCE & HEADING TO EKF.insUpdate() */
			ekf.predictFromINS(devicePose.getXYDistance(), Math.toRadians(devicePose.getHeading()));

			logString.append("Device Pose(After EKF Predict): " + ekf.getCurrDevicePose().toString() + "\n");

			long start = System.currentTimeMillis();

			/* TRIGGER TRIANGULATION AND GET OLD, RE-OBSERVED, AND NEW FEATURES */
			FeatureUpdate update = featureManager.getFeatureUpdate(devicePose);

			logString.append("Features to Delete: " + update.getBadPointsIndex().size() + "\nFeatures to Update: "
					+ update.getCurrentPoints().size() + "\nFeatures to Add: " + update.getNewPoints().size() + "\n");

			timeStringBuilder.append("Triangulate took: " + (System.currentTimeMillis() - start) + "ms\n");

			start = System.currentTimeMillis();
			/* LOOP THROUGH THE RETURNED FEATURES */

			/* IF OLD FEATURE TYPE, CALL EKF.removeFeature(featureIndex) */
			Collections.reverse(update.getBadPointsIndex());
			for (Integer index : update.getBadPointsIndex())
				ekf.deleteFeature(index);

			/*
			 * IF RE-OBSERVED FEATURE, CALL
			 * EKF.updateReobservedFeature(featureIndex, observedDistance,
			 * observedHeading)
			 */
			int i = 0;
			for (PointDouble featpos : update.getCurrentPoints())
				ekf.updateFromReobservedFeatureCoords(i++, featpos.getX(), featpos.getY());

			/* IF NEW FEATURE, CALL EKF.addFeature(x, y) */
			for (PointDouble featpos : update.getNewPoints())
				ekf.addFeature(featpos.getX(), featpos.getY());

			timeStringBuilder.append("EKF took: " + (System.currentTimeMillis() - start) + "ms\n");
			timeStringBuilder.append("Features to Delete: " + update.getBadPointsIndex().size() + "\nFeatures to Update: "
					+ update.getCurrentPoints().size() + "\nFeatures to Add: " + update.getNewPoints().size() + "\n");

			devicePose = ekf.getCurrDevicePose();
			logString.append("Device Pose(EKF): " + devicePose.toString() + "\n");

			final String timeString = timeStringBuilder.toString();
			final DevicePose dev = devicePose;

			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					TextView tv = (TextView) findViewById(R.id.debugTextView);
					tv.setText(dev.toString() + "\n" + timeString.toString() + "\n" + tv.getText());
					// tv.setText(dev.toString() + "\n" + tv.getText());
					// tv.setText("world");
				}

			});

			Log.i(TAG, logString.toString());

		} catch (Exception e) {
			// if anything goes wrong we cry

			Log.e(TAG, e.toString(), e);
		}
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {

		nextSensorEntryToAdd.status = SensorEntry.WRITING;
		Sensor mySensor = sensorEvent.sensor;

		long currTime = System.currentTimeMillis();
		if (currTime - timeLastRecorded >= 10) {
			motionEstimator.inputData(nextSensorEntryToAdd);
			nextSensorEntryToAdd = new SensorEntry();
			timeLastRecorded = currTime;
		}

		if (mySensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

			// add to the sensor entry batch if time to add

			float x = sensorEvent.values[0];
			float y = sensorEvent.values[1];
			float z = sensorEvent.values[2];

			nextSensorEntryToAdd.setAcc_x(x);
			nextSensorEntryToAdd.setAcc_y(y);
			nextSensorEntryToAdd.setAcc_z(z);

		} else if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {

			// add to the sensor entry batch if time to add

			float x = sensorEvent.values[0];
			float y = sensorEvent.values[1];
			float z = sensorEvent.values[2];

			nextSensorEntryToAdd.setGyro_x(x);
			nextSensorEntryToAdd.setGyro_y(y);
			nextSensorEntryToAdd.setGyro_z(z);
		} else if (mySensor.getType() == Sensor.TYPE_ORIENTATION) {

			// add to the sensor entry batch if time to add

			float x = sensorEvent.values[0];
			float y = sensorEvent.values[1];
			float z = sensorEvent.values[2];
			//
			// nextSensorEntryToAdd.setOrient_x(x);
			// nextSensorEntryToAdd.setOrient_y(y);
			// nextSensorEntryToAdd.setOrient_z(z);
		} else if (mySensor.getType() == Sensor.TYPE_GRAVITY) {
			mGrav = lowpass(sensorEvent.values.clone(), mGrav);
		} else if (mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			mMag = lowpass(sensorEvent.values.clone(), mMag);
		}

		if (mGrav != null && mMag != null) {

			float[] rotMat = new float[9];
			if (SensorManager.getRotationMatrix(rotMat, null, mGrav, mMag)) {
				float[] remap = new float[9];

				SensorManager.remapCoordinateSystem(rotMat, SensorManager.AXIS_X, SensorManager.AXIS_Z, remap);

				float[] orient = new float[3];

				SensorManager.getOrientation(rotMat, orient);

				float heading = 0;
				if (heading < 0) {
					heading += 360;
				}

				for (int i = 0; i < orient.length; ++i) {
					orient[i] = (float) Math.toDegrees(orient[i]);
					if (orient[i] < 0)
						orient[i] += 360;
				}

				nextSensorEntryToAdd.setOrient_x(orient[0]); // rotation by z
				nextSensorEntryToAdd.setOrient_y(orient[1]);
				nextSensorEntryToAdd.setOrient_z(orient[2]);

				// Log.i(TAG, orient[0] + "," + orient[1] + "," + orient[2]);
			}
		}

		nextSensorEntryToAdd.status = SensorEntry.FULL;
	}

	private float[] lowpass(float[] curr, float[] prev) {
		// if(prev != null)
		// for(int i=0; i < curr.length; ++i)
		// curr[i] += ALPHA * (curr[i]-prev[i]);

		return curr;
	}

	@Override
	public void initDone() {
		isFeaturesReady = true;
	}
}

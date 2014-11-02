package monoslam;

import org.opencv.core.Mat;

/**
 * 
 * Ported from: MonoSLAM/monoslam
 * 
 */
public class MonoSLAM {
	// TODO: Implement each Model

	// PartiallyInitialisedFeatureMeasurementModel default_feature_type_for_initialisation;
	// SimOrRob sim_or_rob;
	// Robot robot;
	// Scene_Single scene;
	// Kalman kalman;

	public static long CAMERA_WIDTH;
	public static long CAMERA_HEIGHT;

	// Parameters for map management
	public static long NUMBER_OF_FEATURES_TO_SELECT;
	public static long NUMBER_OF_FEATURES_TO_KEEP_VISIBLE;
	public static long MAX_FEATURES_TO_INIT_AT_ONCE;

	// Parameters for feature initialisation
	// Maximum and minimum feature depth
	public static double MIN_LAMBDA;
	public static double MAX_LAMBDA;
	public static long NUMBER_OF_PARTICLES;
	// Initialise as popublic static int when standard deviation / depth is less than this
	public static double STANDARD_DEVIATION_DEPTH_RATIO;
	// Give up on particles if number drops below this
	public static long MIN_NUMBER_OF_PARTICLES;
	// Prune particle if probability/(number of particles) falls below this 
	public static double PRUNE_PROBABILITY_THRESHOLD;
	public static long ERASE_PARTIALLY_INIT_FEATURE_AFTER_THIS_MANY_ATTEMPTS;

	// Corners of box we search for new features: save so we can display it
	public static int init_feature_search_ustart;
	public static int init_feature_search_vstart;
	public static int init_feature_search_ufinish;
	public static int init_feature_search_vfinish;
	public static boolean init_feature_search_region_defined_flag;

	// TODO: Implement this
	// Timing information for performance analysis
	// Global timer reset at program start
	//VW::Timer timer0;
	// Frame timer reset as each new frame is acquired
	//VW::Timer timer1;

	// Remember this so that the user can query it
	public static long number_of_visible_features;
	public static long number_of_matched_features;

	// TODO: Implement this
	//VNL::VectorFixed<3, public static double> velocity;

	public MonoSLAM() {
		// TODO: Implement this
		// Create the Settings class by reading from the initialisation file
		//		  ifstream stream(initialisation_file.c_str());
		//		  if(!stream)
		//		  {
		//		    ostringstream os;
		//		    os << "Unable to open initialisation file \""
		//		       << initialisation_file
		//		       << "\"." << endl;
		//		    throw Scene::InitialisationError(os.str());
		//		  }
		//
		//		
		//		Settings settings(stream);
		//

		//		  // Create the Scene class. This also constructs the motion model and 
		//		  // internal measurement models and sets the initial state
		//		  scene = new Scene_Single(settings, mm_creator, imm_creator);
		//

		//		  // Now sort out the feature types
		//		  string feature_init_type = settings.get_entry(
		//		    "Models", "NewFeatureMeasurementModel");
		//		  Feature_Measurement_Model* fm_model =
		//		    fmm_creator->create_model(feature_init_type, scene->get_motion_model());
		//
		//		  if(fm_model == NULL)
		//		  {
		//		    ostringstream os;
		//		    os << "Unable to create a feature measurement motion model of type \"" 
		//		       << feature_init_type 
		//		       << "\" as requested in initalisation file \"" << initial_state_file 
		//		       << "\". " << endl;
		//		    throw Scene::InitialisationError(os.str());
		//		  }
		//

		//		  // Initialise this motion model
		//		  fm_model->read_parameters(settings);
		//		  
		//		  // Check that this is a partially-initialised feature type
		//		  if(fm_model->fully_initialised_flag)
		//		  {
		//		    ostringstream os;
		//		    os << "Feature measurement motion model \"" << feature_init_type 
		//		       << " as requested in initalisation file \"" << initial_state_file 
		//		       << "\" is not a partially-initialised feature type. " << endl;
		//		    throw Scene::InitialisationError(os.str());
		//		  }
		//
		//		  default_feature_type_for_initialisation =
		//		    dynamic_cast<Partially_Initialised_Feature_Measurement_Model*>(fm_model);

		//
		//		  // We hope that features are viewed through a camera! If so,
		//		  // the feature measurement class should derive from
		//		  // Camera_Feature_Measurement_Model
		//		  const Camera_Feature_Measurement_Model* cfmm =
		//		    dynamic_cast<const Camera_Feature_Measurement_Model*>(fm_model);
		//
		//		  if(cfmm == 0)
		//		  {
		//		    // Oops - the feature measurement model is not derived from
		//		    // Camera_Feature_Measurement_Model!
		//		    ostringstream os;
		//		    os << "The default feature measurement motion model \""
		//		       << fm_model->feature_type
		//		       << "\" is not derived from Camera_Feature_Measurement_Model!" << endl;
		//		    throw Scene::InitialisationError(os.str());
		//		  }
		//
		//		  CAMERA_WIDTH = cfmm->get_camera().ImageWidth();
		//		  CAMERA_HEIGHT = cfmm->get_camera().ImageHeight();
		//

		//		  kalman = new Kalman;
		//		  robot = new Robot();
		//		  sim_or_rob = dynamic_cast<Sim_Or_Rob*>(robot);
		//

		//		  // Initialise any known features
		//		  initialise_known_features(settings, fmm_creator, sim_or_rob, scene);

		//
		//		  // Save the time the program was started
		//		  timer0.Start();
		//

		//		  // Various flags
		//		  init_feature_search_region_defined_flag = false;

	}

	/**
	 * Step the MonoSLAM application on by one frame. This should be called every time a new frame is captured (and care should be taken to
	 * avoid skipping frames). Before calling this function, Scene_Single::load_new_image() should be called (e.g. using
	 * <code>monoslaminterface.GetRobotNoConst()->load_new_image()</code>), since the first parameter to GoOneStep() is currently ignored.
	 * 
	 * @param delta_t
	 *            The time between frames
	 * @param currently_mapping_flag
	 *            Set to be true if new features should be detected and added to the map.
	 * 
	 *            GoOneStep() performs the following processing steps: - Kalman filter prediction step (by calling
	 *            Kalman::predict_filter_fast(), with a zero control vector) - Select a set of features to make measurements from (set by
	 *            SetNumberOfFeaturesToSelect()) - Predict the locations and and make measurements of those features - Kalman filter update
	 *            step - Delete any bad features (those that have repeatedly failed to be matched) - If we are not currently initialising a
	 *            enough new features, and the camera is translating, and <code>currently_mapping_flag</code> is set, initialise a new
	 *            feature somewhere sensible - Update the partially-initialised features
	 */

	public boolean GoOneStep(Mat new_image, double delta_t, boolean currently_mapping_flag) {

		//  robot->nullify_image_selection();

		init_feature_search_region_defined_flag = false;

		//  VNL::Vector<double> u(3);
		//  u.Fill(0.0);

		//  sim_or_rob->set_control(u, delta_t);

		//	VNL::Vector<double> xv = scene->get_xv();
		//	scene->get_motion_model()->func_xp(xv);
		//	VNL::VectorFixed<3, double> prev_xp_pos = 
		//	scene->get_motion_model()->get_xpRES().Extract(3);

		//  kalman->predict_filter_fast(scene, u, delta_t);
		
		//  number_of_visible_features = scene->auto_select_n_features(NUMBER_OF_FEATURES_TO_SELECT);
		
		//		if(Scene_Single::STATUSDUMP) scene->print_selected_features();
		//
		//		  if (scene->get_no_selected() != 0)
		//		  {
		//		    // Calls function in control_general.cc
		//		    number_of_matched_features = make_measurements(scene, sim_or_rob);
		//
		//		    if (scene->get_successful_measurement_vector_size() != 0) {
		//		      kalman->total_update_filter_slow(scene);
		//
		//		      scene->normalise_state();
		//
		//		    }
		//		  }
		
		//  scene->delete_bad_features();
		
		//	VNL::Matrix<double> Pxx(scene->get_total_state_size(), scene->get_total_state_size());
		//  scene->construct_total_covariance(Pxx);
		//  VNL::Matrix<double> PxxT = Pxx.Transpose();
		//
		//  Pxx.Update(Pxx * 0.5 + PxxT * 0.5);
		//  scene->fill_covariances(Pxx);
		
		//		xv = scene->get_xv();
		//		  scene->get_motion_model()->func_xp(xv);
		//		  VNL::VectorFixed<3, double> xp_pos = 
		//		    scene->get_motion_model()->get_xpRES().Extract(3);
		//		  velocity = (xp_pos - prev_xp_pos) / delta_t;
		//		  double speed = sqrt(velocity.SquaredMagnitude());
		//		  if (DEBUGDUMP) cout << "Camera speed " << speed << " ms^-1 " << endl;
		//
		//		  if (speed > 0.2 &&
		//		    currently_mapping_flag) {
		//		    if (number_of_visible_features < NUMBER_OF_FEATURES_TO_KEEP_VISIBLE && 
		//		        scene->get_feature_init_info_vector().size() <
		//		          (unsigned int)(MAX_FEATURES_TO_INIT_AT_ONCE)) {
		//		      AutoInitialiseFeature(u, delta_t);
		//		    }
		//		  }
		
		return true;
	}

}

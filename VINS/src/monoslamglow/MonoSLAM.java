package monoslamglow;

/**
 * Ported from: monoslam monoslamglow
 */
public class MonoSLAM {
	public static final double delta_t = 1.0 / 3.0; // CHANGED NAME
	public static final int NUMBER_OF_FEATURES_TO_SELECT = 10;
	public static final int NUMBER_OF_FEATURES_TO_KEEP_VISIBLE = 12;
	public static final int MAX_FEATURES_TO_INIT_AT_ONCE = 1;
	public static final double MIN_LAMBDA = 0.5;
	public static final double MAX_LAMBDA = 5.0;
	public static final int NUMBER_OF_PARTICLES = 100;
	public static final double STANDARD_DEVIATION_DEPTH_RATIO = 0.3;
	public static final int MIN_NUMBER_OF_PARTICLES = 20;
	public static final double PRUNE_PROBABILITY_THRESHOLD = 0.05;
	public static final int ERASE_PARTIALLY_INIT_FEATURE_AFTER_THIS_MANY_ATTEMPTS = 10;

	public static long output_tracked_image_number;
	public static long output_raw_image_number;

	public MonoSLAM() {
		setupMonoSLAM();

		output_tracked_image_number = 0;
		output_raw_image_number = 0;

		// SetUpButtons();
		//
		// SetUp3DDisplays();
		//
		// SetUpImageGrabber();
		
	}

	private void setupMonoSLAM() {
		// TODO Implement this
		
//		  MonoSLAM_Motion_Model_Creator mm_creator;
//		  MonoSLAM_Feature_Measurement_Model_Creator fmm_creator;
//
//		  monoslaminterface = 
//		    new MonoSLAMInterface("monoslam_state.ini",
//					  &mm_creator,
//					  &fmm_creator,
//					  NULL, // no internal measurement models used here
//					  NUMBER_OF_FEATURES_TO_SELECT,
//					  NUMBER_OF_FEATURES_TO_KEEP_VISIBLE,
//					  MAX_FEATURES_TO_INIT_AT_ONCE,
//					  MIN_LAMBDA,
//					  MAX_LAMBDA,
//					  NUMBER_OF_PARTICLES,
//					  STANDARD_DEVIATION_DEPTH_RATIO,
//					  MIN_NUMBER_OF_PARTICLES,
//					  PRUNE_PROBABILITY_THRESHOLD,
//				  	  ERASE_PARTIALLY_INIT_FEATURE_AFTER_THIS_MANY_ATTEMPTS);
		
	}
}

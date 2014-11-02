package scene;

import java.util.ArrayList;

import scene_models.Internal_Measurement;

/**
 * 
 * Ported from: Scene/scene_single
 *
 */
public class Scene_Single {
	// Motion_Model *motion_model;

	/*
	 * The robot state \f$x_v\f$, as used by the motion model. The complete system state (robot and features) is not stored explicitely by
	 * Scene, but instead is constructed as required by construct_total_state_and_covariance().
	 */
	private ArrayList<Double> xv;

	//  The robot state covariance  \f$P_{xx}\f$, as used by the motion model.
	private ArrayList<ArrayList<Double>> Pxx;

	// Lists of pointers to features
	// feature_list contains all features
	private ArrayList<Feature> feature_list;

	// selected_feature_list just lists those currently selected for measurement
	private ArrayList<Feature> selected_feature_list;

	// A separate list here of partially-initialised features with additional
	// probabilistic information. These features are also in the main 
	// feature_list. 
	private ArrayList<FeatureInitInfo> feature_init_info_vector;

	// The size of the total state vector including robot and features
	private long total_state_size;
	// The size of the most recent vector of measurements
	private long successful_measurement_vector_size;

	// Unique labels to be given to new features
	private long next_free_label;

	// Marked a feature: for deleting, steering round, etc.
	int marked_feature_label; /*
							 * The label of the last feature selected with the mouse. Signed because -1 means no marked feature
							 */

	// A vector of numbered internal measurement models
	private ArrayList<Internal_Measurement> internal_measurement_vector;

	// A counter for state output to text file
	long output_counter;

	// TODO: implement more methods
}

package scene;

import java.util.ArrayList;

/**
 * 
 * Ported from: Scene/init_feature
 *
 */
public class Particle {
	
	/// The value(s) of the free parameters represented by this particle
	protected ArrayList<Double> lambda;
	/// The probability of this particle
	protected double probability;
	/// Cumulative probability of all particles up to and including this one
	protected double cumulative_probability;

	// Measurement information used for updating particle
	/**
	 * The measurement of the feature state \f$ z_i \f$. (e.g. the image location)
	 */
	protected ArrayList<Double> m_z;
	/**
	 * The current predicted measurement state \f$ h_i \f$ for this particle. (e.g. image location)
	 */
	protected ArrayList<Double> m_h;
	/**
	 * The current inverse innovation covariance \f$ S_i^{-1} \f$ for this particle.
	 */
	protected ArrayList<Double> m_SInv;
	/** The determinant of the current innovation covariance \f$ |S_i| \f$ */
	protected double m_detS;
	/** Was the last measurement of this particle successful? */
	protected boolean m_successful_measurement_flag;
	
	 protected Particle(ArrayList<Double> l, double p, long MEASUREMENT_SIZE){
		 
	 }

	  /** Set the current measurement position \f$ h_i \f$ for this particle. Called
	  from Scene_Single::predict_partially_initialised_feature_measurements() */
	  protected void set_h(ArrayList<Double> h) {
		  //TODO: m_h.Update(h);
		  }
	  protected void set_S(ArrayList<ArrayList<Double>> Si){}
}

package scene_models;

import Jama.Matrix;

/**
 * 
 * Ported from: Scene/models_base
 *
 */
public abstract class Feature_Measurement_Model {
	
	protected Matrix PyiyiGraphicsRES;
	protected Matrix RiRES;
	protected Matrix SiRES;
	
	/** Get the value of the feature measurement covariance Ri calculated earlier (e.g. by func_Ri()). */
	final public Matrix getRiRES() {
		return RiRES;
	}
}

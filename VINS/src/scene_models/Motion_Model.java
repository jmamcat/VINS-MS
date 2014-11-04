package scene_models;

import java.util.List;

import Jama.Matrix;

/**
 * 
 * Ported from: Scene/models_base
 *
 */
public abstract class Motion_Model {
	// Where results will be stored after function calls
	List<Double> fvRES;
	List<Double> xpRES;
	List<Double> fvNoisyRES;
	
	Matrix dxpByDxvRES;
	
	/** Extract the position and orientation from the state vector. **/
	public abstract void funcXp(List<Double> xv);
	
	/** Calculate Jacobian for funcXp. (This is just the identity in this case.) **/
	public abstract void funcDxpByDxv(List<Double> xv);
	
	final public List<Double> getXpRES() {return xpRES;}
	final public Matrix getDxpByDxvRES() {return dxpByDxvRES;}

	
	
}

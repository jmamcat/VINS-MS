package scene_models;

import java.util.List;

import Jama.Matrix;

/**
 * 
 * Ported from: Scene/models_base
 *
 */

/** 
Class for a feature that isn't fully initialised after just one
measurement, and so needs further measurements. Typically, a
partially-initalised feature has some known parameters (with Gaussian
uncertainty), and some free parameters represented by other means (such as
particles). This class does not store any permanent state - it just provides
helper functions to convert between different state vectors.

The various state vectors and covariances are:
<table border=0>
<tr><td>\f$ y_{pi} \f$</td><td>The partial feature state (e.g. the parameters
of a 3D line).</td><td>
<tr><td>\f$ h_{pi} \f$</td><td>The measurement state of the
partially-initialised feature (e.g. the location of the feature in the
image.)</td><td>
<tr><td>\f$ h_i \f$</td><td>An inital measurement of the feature, used to
initialise the partially-initialised feature.</td><td>
<tr><td>\f$ R_i \f$</td><td>The covariance of the measurement \f$ h_i
\f$.</td><td>
<tr><td>\f$ \lambda \f$</td><td>A vector containing values for the free
parameters.</td><td>
<tr><td>\f$ y_{fi} \f$</td><td>The fully-initalised feature state (which can be
constructed given \f$ y_{pi} \f$ and \f$ \lambda \f$).</td><td>
<tr><td>\f$ x_p \f$</td><td>The robot position state. (e.g. its 3D position and
orientation).</td><td>
</table>
@ingroup gScene
*/

public abstract class Partially_Initialised_Feature_Measurement_Model extends Feature_Measurement_Model {
	// Where results will be stored after calls to functions
	protected List<Double> ypiRES;
	protected Matrix dypiByDxpRES;
	protected Matrix dypiByDhiRES;
	
	
	/** Initialise a new partial feature ypi as a function of
	a measurement hi and the robot position state xp. 
	Also calculate its Jacobians \f$ \frac{\partial
	y_{pi}}{\partial x_p} \f$ and \f$ \frac{\partial
	y_{pi}}{\partial y_i} \f$ and the measurement uncertainty \f$ Ri \f$.
	 */
	abstract public void funcYpiAndDypiByDxpAndDypiByDhiAndRi(final List<Double> hi, final List<Double> xp);

	final public List<Double> getYpiRES() {
		return ypiRES;
	}
	
	
	/** Get the value of dy<SUB>pi</SUB>/dxp calculated earlier. */
	public Matrix getDypiByDxpRES() {
		return dypiByDxpRES;
	}

	
	/** Get the value of dy<SUB>pi</SUB>/dhi calculated earlier. */
	public Matrix getDypiByDhiRES() {
		return dypiByDhiRES;
	}
	
}

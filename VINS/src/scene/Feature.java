package scene;

import java.util.List;

import Jama.Matrix;

/**
 * 
 * Ported from: Scene/feature
 *
 */
public class Feature {
	// private Identifier identifier;
	private int label;
	
	// private FeatureMeasurementModel featureMeasurementModel;
	// private PartiallyInitialisedFeatureMeasurementModel partiallyInitialisedFeatureMeasurementModel; // null if not relevant
	
	// private Fully_Initialised_Feature_Measurement_Model fully_initialised_feature_measurement_model; // null if not relevant
	
	/** The estimate of the feature state. (e.g. the feature's 3D position) */
	private List<Double> y;
	
	/** The covariance of this feature \f$ */
	private Matrix Pyy;
	
	/** The covariance between the robot state and this feature's state. */
	private Matrix Pxy;
	  
	/** The robot position state from which we initialised this feature. */
	private List<Double> xp_orig;
	
	/** The covariances between this feature and others. */
	private List<Matrix> matrix_block_list;
	
	/** The predicted feature measurement state. */
	private List<Double> h;
	
	/** The actual feature measurement state. */
	private List<Double> z;
	
	/** The feature innovation. */
	private List<Double> nu;
	
	/** The Jacobian between the predicted measurement and the vehicle state. */
	private Matrix dhBydxv;
	
	/** The Jacobian between the feature measurement and its state. */
	private Matrix dhBydy;
	
	/** The innovation covariance. */
	private Matrix R;
	
	/** The feature measurement covariance. */
	private Matrix S;
	
	private int positionInList;
	
	/** With general feature state sizes, need this to identify the starting
	position of this feature in total state vector. */
	private int positionInTotalStateVector;
	
	// Keep track of the attempted and successful measurements made of a 
	// feature over time 
	private boolean successfulMeasurementFlag = false;
	
	/** The number of times that a measurement of this feature has been
	attempted. Used together with successful_measurements_of_feature to
	determine whether the feature is a bad one that should be deleted. */
	private int attemptedMeasurements = 0;
	
	/** The number of times that this feature has been successfully measured. Used
  	together with attempted_measurements_of_feature to determine whether the
	feature is a bad one that should be deleted. */
	private int successfulMeasurements = 0;
	
	private int knownFeatureLabel;

	
	/*****************************Access functions******************************/
	
	public List<Double> getY() {
		
		return y;
	}

	public void setY(List<Double> y) {
		this.y = y;
	}

	public Matrix getPyy() {
		return Pyy;
	}

	public void setPyy(Matrix pyy) {
		Pyy = pyy;
	}

	public Matrix getPxy() {
		return Pxy;
	}

	public void setPxy(Matrix pxy) {
		Pxy = pxy;
	}

	public List<Double> getXp_orig() {
		return xp_orig;
	}

	public void setXp_orig(List<Double> xp_orig) {
		this.xp_orig = xp_orig;
	}

	public List<Matrix> getMatrix_block_list() {
		return matrix_block_list;
	}

	public void setMatrix_block_list(List<Matrix> matrix_block_list) {
		this.matrix_block_list = matrix_block_list;
	}

	public List<Double> getH() {
		return h;
	}

	public void setH(List<Double> h) {
		this.h = h;
	}

	public List<Double> getZ() {
		return z;
	}

	public void setZ(List<Double> z) {
		this.z = z;
	}

	public List<Double> getNu() {
		return nu;
	}

	public void setNu(List<Double> nu) {
		this.nu = nu;
	}

	public Matrix getDhBydxv() {
		return dhBydxv;
	}

	public void setDhBydxv(Matrix dhBydxv) {
		this.dhBydxv = dhBydxv;
	}

	public Matrix getDhBydy() {
		return dhBydy;
	}

	public void setDhBydy(Matrix dhBydy) {
		this.dhBydy = dhBydy;
	}

	public Matrix getR() {
		return R;
	}

	public void setR(Matrix r) {
		R = r;
	}

	public Matrix getS() {
		return S;
	}

	public void setS(Matrix s) {
		S = s;
	}

	public int getPositionInList() {
		return positionInList;
	}

	public void setPositionInList(int positionInList) {
		this.positionInList = positionInList;
	}

	public int getPositionInTotalStateVector() {
		return positionInTotalStateVector;
	}

	public void setPositionInTotalStateVector(int positionInTotalStateVector) {
		this.positionInTotalStateVector = positionInTotalStateVector;
	}

	public boolean isSuccessfulMeasurementFlag() {
		return successfulMeasurementFlag;
	}

	public void setSuccessfulMeasurementFlag(boolean successfulMeasurementFlag) {
		this.successfulMeasurementFlag = successfulMeasurementFlag;
	}

	public int getAttemptedMeasurements() {
		return attemptedMeasurements;
	}

	public void setAttemptedMeasurements(int attemptedMeasurements) {
		this.attemptedMeasurements = attemptedMeasurements;
	}

	public int getSuccessfulMeasurements() {
		return successfulMeasurements;
	}

	public void setSuccessfulMeasurements(int successfulMeasurements) {
		this.successfulMeasurements = successfulMeasurements;
	}

	public int getKnownFeatureLabel() {
		return knownFeatureLabel;
	}

	public void setKnownFeatureLabel(int knownFeatureLabel) {
		this.knownFeatureLabel = knownFeatureLabel;
	}
	
	
	public Feature(int label, int positionInList, final List<Double> h) {
		this.label = label;
		this.positionInList = positionInList;
		
		
	}
	
	  
}

package v1.app.com.codenutrient.accelerometer;

import android.hardware.SensorEvent;
import android.util.Log;

/**
 * Computing and processing accelerometer data.
 */
public class AccelerometerProcessing implements OnThresholdChangeListener {

    private static final String TAG = AccelerometerProcessing.class.getSimpleName();

    private static AccelerometerProcessing instance = null;

    public static AccelerometerProcessing getInstance() {

        if (instance == null)
            return new AccelerometerProcessing();
        return instance;
    }

    private static final int INACTIVE_PERIODS = 12;
    public static final float THRESH_INIT_VALUE = 12.72f;

    // dynamic variables
    private int mInactiveCounter = 0;
    public boolean isActiveCounter = true;

    private static double mThresholdValue = THRESH_INIT_VALUE;
    private double[] mAccelValues = new double[AccelerometerSignals.count];

    private SensorEvent mEvent;

    private double[] linear_acceleration = new double[3];

    public void setEvent(SensorEvent e) {
        mEvent = e;
    }

    /**
     * Vector Magnitude |V| = sqrt(x^2 + y^2 + z^2)
     * @param i signal identifier.
     * @return vector.
     */
    public double calcMagnitudeVector(int i) {
        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = mEvent.values[0];
        linear_acceleration[1] = mEvent.values[1];
        linear_acceleration[2] = mEvent.values[2];

        mAccelValues[i] = Math.sqrt(
                linear_acceleration[0] * linear_acceleration[0] +
                linear_acceleration[1] * linear_acceleration[1] +
                linear_acceleration[2] * linear_acceleration[2]);
        return mAccelValues[i];
    }

    /**
     * My step detection algorithm.
     * When the value is over the threshold, the step is found and the algorithm sleeps for
     * the specified distance which is {@link #INACTIVE_PERIODS this }.
     * @param i signal identifier.
     * @return step found / not found
     */
    public boolean stepDetected(int i) {
        if (mInactiveCounter == INACTIVE_PERIODS) {
            mInactiveCounter = 0;
            if (!isActiveCounter)
                isActiveCounter = true;
        }
        if (mAccelValues[i] > mThresholdValue) {
            if (isActiveCounter) {
                mInactiveCounter = 0;
                isActiveCounter = false;
                return true;
            }
        }
        ++mInactiveCounter;
        return false;
    }

    @Override
    public void onThresholdChange(double value) {
        Log.d(TAG, "Current Threshold is: " + value);
        mThresholdValue = value;
    }
}

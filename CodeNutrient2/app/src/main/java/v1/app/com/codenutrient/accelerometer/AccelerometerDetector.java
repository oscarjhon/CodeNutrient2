package v1.app.com.codenutrient.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Date;

/**
 * Configuring accelerometer and handling its results.
 */
public class AccelerometerDetector implements SensorEventListener {

    private static final String TAG = AccelerometerDetector.class.getSimpleName();
    private  Long now = new Date().getTime();
    /**
     * Suggested periods:
     * DELAY_UI: T ~= 60ms => f = 16,6Hz
     * DELAY_GAME: T ~= 20ms => f = 50Hz
     */
    public static final int CONFIG_SENSOR = SensorManager.SENSOR_DELAY_GAME;

    private AccelerometerProcessing mAccelProcessing = AccelerometerProcessing.getInstance();

    private SensorManager mSensorManager;
    private Sensor mAccel;

    private OnStepCountChangeListener mStepListener;

    /**
     * Listener setting for Step Detected event
     *
     * @param listener a listener.
     */
    public void setStepCountChangeListener(OnStepCountChangeListener listener) {
        mStepListener = listener;
    }

    public AccelerometerDetector(SensorManager sensorManager) {
        mStepListener = null;
        mSensorManager = sensorManager;
        if (hasAccelerometer())
            mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public boolean hasAccelerometer(){
        return mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null;
    }

    public void startDetector() {
        // just starts just the accelerometer. It doesn't update the UI.
        if (!mSensorManager.registerListener(this, mAccel, CONFIG_SENSOR)) {
            Log.d(TAG,"The sensor is not supported and unsuccessfully enabled.");
        }
    }

    public void stopDetector() {
        mSensorManager.unregisterListener(this, mAccel);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Procesar datos del acelerometro
        mAccelProcessing.setEvent(event);
        final double value = mAccelProcessing.calcMagnitudeVector(1);

        // Deteccion de pasos
        if (mAccelProcessing.stepDetected(1)) {
            // ¿Paso!

            // Notificar al listener
            if (mStepListener != null) {
                final long eventMsecTime = new Date().getTime() - now;
                now = new Date().getTime();
                mStepListener.onStepCountChange(eventMsecTime, value);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

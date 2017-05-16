package v1.app.com.codenutrient.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import v1.app.com.codenutrient.POJO.StepCounters;

/**
 * Created by Arturo on 26/04/2017.
 * This code was made for the project CodeNutrient.
 */
public class SensorRestarterBroadcastReciver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent sensor = new Intent(context, Pedometer.class);
        //sensor.putExtra("conter", intent.getSerializableExtra("counter"));
        context.startService(sensor);
    }
}

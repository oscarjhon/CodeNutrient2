package v1.app.com.codenutrient.Services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.StepCounters;
import v1.app.com.codenutrient.accelerometer.AccelerometerDetector;
import v1.app.com.codenutrient.accelerometer.OnStepCountChangeListener;

/**
 * Created by Arturo on 26/04/2017.
 * This code was made for the project CodeNutrient.
 */
public class Pedometer  extends Service {

    public boolean conected;
    public AppUser user;
    StepCounters counters;
    private Timer hourtimerm, m5timer;
    private TimerTask hourtask, m5taskt;
    private int user_id;

    private AccelerometerDetector detector;

    public Pedometer(){
        super();
        user = new AppUser();
        conected = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseReadWrite();
            Cursor cursor = helper.fetchConectedUser();
            if (cursor != null && cursor.moveToFirst()){
                conected = true;
                user.setUid(cursor.getString(cursor.getColumnIndex("uid")));
                user.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
                user.setToken(cursor.getString(cursor.getColumnIndex("token")));
                user_id = cursor.getInt(cursor.getColumnIndex("id"));
                helper.close();
                SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                detector = new AccelerometerDetector(manager);
                detector.setStepCountChangeListener(listener);
                if (intent.hasExtra("counter")){
                    counters = (StepCounters) intent.getSerializableExtra("counter");
                }else{
                    counters = new StepCounters();
                }
                //Comenzar  contadores de tiempo y lectores de pasos etc.
                startM5Task();
            }else{
                conected = false;
                stopSelf();
            }
        } catch (SQLException e) {
            conected = false;
            stopSelf();
        }
        return START_STICKY;
    }

    private OnStepCountChangeListener listener = new OnStepCountChangeListener() {
        @Override
        public void onStepCountChange(long eventMsecTime, double value) {
            if (value < 15){
                //paso normal
                if (eventMsecTime > 350 && eventMsecTime <1000){
                    counters.walking ++;
                    counters.w_time += value;
                }
            }
            if(value < 17){
                //paso trotando
                if(eventMsecTime > 350 && eventMsecTime < 700){
                    counters.jogging ++;
                    counters.j_time += value;
                }
            }
            if (value < 19){
                //Correr
                if(eventMsecTime > 350 && eventMsecTime < 500){
                    counters.running ++;
                    counters.r_time += value;
                }
            }
        }
    };

    private void startHourTask(){
        hourtimerm = new Timer();
        InitializeHourTask();
        hourtimerm.schedule(hourtask, 3600000);
    }

    private void startM5Task(){
        m5timer = new Timer();
        Initialize5MTimerTask();
        m5timer.schedule(m5taskt, 0, 300000);
    }

    private void Initialize5MTimerTask(){
        m5taskt = new TimerTask() {
            @Override
            public void run() {
                if (isAppOnForeground(getApplicationContext())){
                    StartPedometer();
                }else{
                    StopPedometer();
                }
            }
        };
    }

    private void InitializeHourTask(){
        hourtask = new TimerTask() {
            @Override
            public void run() {
                //Guardar la infomeación en la base de datos
                SaveDatabase(getApplicationContext());
            }
        };
    }

    private void SaveDatabase(Context ctx){
        DataBaseHelper helper = new DataBaseHelper(ctx);
        try {
            float caminar = 3.3f;
            float trotar = 5.0f;
            float correr = 9.0f;
            helper.openDataBaseReadWrite();
            Cursor cursor = helper.fetchMETS(user.getProvider(), user.getUid());
            if (cursor != null && cursor.moveToFirst()){
                //Obtener valores
                caminar = cursor.getFloat(cursor.getColumnIndex("caminar"));
                trotar = cursor.getFloat(cursor.getColumnIndex("trotar"));
                correr = cursor.getFloat(cursor.getColumnIndex("correr"));
            }
            if (counters.walking != 0){
                double minutes = counters.w_time/6000;
                int steps = counters.walking;
                float calories = (float) (caminar * minutes);
                helper.insertCalorieHistory(user_id,  calories);
                helper.insertSteps(user_id, steps);
            }
            if (counters.jogging != 0){
                double minutes = counters.j_time/6000;
                int steps = counters.jogging;
                float calories = (float) (trotar * minutes);
                helper.insertCalorieHistory(user_id,  calories);
                helper.insertSteps(user_id, steps);
            }
            if (counters.running != 0){
                double minutes = counters.r_time/6000;
                int steps = counters.running;
                float calories = (float) (correr * minutes);
                helper.insertCalorieHistory(user_id,  calories);
                helper.insertSteps(user_id, steps);
            }
        } catch (SQLException ignored) {}
    }

    private void StartPedometer(){
        detector.startDetector();
        startHourTask();
    }

    private void StopM5Task(){
        if(m5timer != null){
            m5timer.cancel();
            m5timer = null;
        }
    }

    private void StopHourTask(){
        if(hourtimerm != null){
            hourtimerm.cancel();
            hourtimerm = null;
        }
    }

    private void StopPedometer(){
        detector.stopDetector();
    }

    private boolean isAppOnForeground(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        if (appProcessInfos == null){
            return false;
        }
        final String packageName = context.getPackageName();
        for(ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos){
            if(appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcessInfo.processName.equals(packageName))
                return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (conected){
            //reconectar
            Intent broadcastIntent = new Intent("android.intent.action.BOOT_COMPLETED");
            broadcastIntent.putExtra("counter", counters);
            sendBroadcast(broadcastIntent);
            //Detener procesos;
            StopM5Task();
            StopHourTask();
            StopPedometer();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

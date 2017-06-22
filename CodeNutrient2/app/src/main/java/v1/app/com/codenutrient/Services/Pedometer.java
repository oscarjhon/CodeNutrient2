package v1.app.com.codenutrient.Services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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

    public boolean conected = false;
    public AppUser user;
    private Timer hourtimerm, m5timer;
    private TimerTask hourtask, m5taskt;
    private int user_id;
    private String Tag = "PEDOMETER";

    private AccelerometerDetector detector;

    public Pedometer(){
        super();
        user = new AppUser();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseRead();
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
                /**if (intent != null && intent.hasExtra("counter")){
                    counters = (StepCounters) intent.getSerializableExtra("counter");
                }else{
                    counters = new StepCounters();
                }*/
                //Comenzar  contadores de tiempo y lectores de pasos etc.
                Log.i(Tag, "Iniciado");
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
            if ((value > 18 && value <= 34) && (eventMsecTime > 250 && eventMsecTime <= 400)){
                //Correr
                StepCounters.running ++;
                StepCounters.r_time += eventMsecTime;
            }else if ((value > 14 && value <= 18) && (eventMsecTime > 250 && eventMsecTime <= 500)){
                //paso trotando
                StepCounters.jogging ++;
                StepCounters.j_time += eventMsecTime;
            }else if ((value <= 14) && (eventMsecTime > 300 && eventMsecTime <= 800)){
                //caminando
                StepCounters.walking ++;
                StepCounters.w_time += eventMsecTime;
            }
            Log.i(Tag, "Normal: " +StepCounters.walking + " Trotando: " + StepCounters.jogging + " Corriendo: " +StepCounters.running);
        }
    };

    private void startHourTask(){
        hourtimerm = new Timer();
        InitializeHourTask();
        //hourtimerm.schedule(hourtask, 3600000);
        hourtimerm.schedule(hourtask, 60000);
    }

    private void startM5Task(){
        m5timer = new Timer();
        Initialize5MTimerTask();
        m5timer.schedule(m5taskt, 60000);
        //m5timer.schedule(m5taskt, 0, 5000);
    }

    private boolean already = false;

    private void Initialize5MTimerTask(){

        m5taskt = new TimerTask() {
            @Override
            public void run() {
                if (!isAppOnForeground(getApplicationContext())){
                    if(!already) {
                        already = true;
                        StartPedometer();
                    }
                }else{
                    already = false;
                    StopPedometer();
                }
                startM5Task();
            }
        };
    }

    private void InitializeHourTask(){
        hourtask = new TimerTask() {
            @Override
            public void run() {
                //Log.i("PEdometer","2");
                //Guardar la información en la base de datos
                SaveDatabase(getApplicationContext());
                //Log.i("PEdometer","Inicio hourtask");
                startHourTask();
            }
        };
    }

    private void SaveDatabase(Context ctx){
        DataBaseHelper helper = new DataBaseHelper(ctx);
        Log.i("PEDOMETER", "GUARDANDO en base de datos");
        Log.i("PEDOMETER", "value" + StepCounters.j_time + "");
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
            if (StepCounters.walking != 0){
                double minutes = StepCounters.w_time/60000;
                int steps = StepCounters.walking;
                float calories = (float) (caminar * minutes);
                if (minutes > 1) {
                    helper.insertCalorieHistory(user_id, calories);
                    helper.insertSteps(user_id, steps, 1);
                    StepCounters.w_time = 0;
                    StepCounters.walking = 0;
                }
            }
            if (StepCounters.jogging != 0){
                double minutes = StepCounters.j_time/60000;
                int steps = StepCounters.jogging;
                float calories = (float) (trotar * minutes);
                Log.i("PEDOMETER", "calorías: "+ calories+ " minutos " + minutes);
                if (minutes >= 1){
                    Log.i("PEDOMETER", "entro" + user_id);
                    helper.insertCalorieHistory(user_id, calories);
                    long i = helper.insertSteps(user_id, steps, 2);
                    Log.i("PEDOMETER", "registro"+i);
                    StepCounters.j_time = 0;
                    StepCounters.jogging = 0;
                }
            }
            if (StepCounters.running != 0){
                double minutes = StepCounters.r_time/60000;
                int steps = StepCounters.running;
                float calories = (float) (correr * minutes);
                if (minutes > 1) {
                    helper.insertCalorieHistory(user_id, calories);
                    helper.insertSteps(user_id, steps, 3);
                    StepCounters.r_time = 0;
                    StepCounters.running = 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void StartPedometer(){
        detector.setStepCountChangeListener(listener);
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
        detector.setStepCountChangeListener(null);
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
            if(appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcessInfo.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(Tag, "Finish");
        if (conected){
            Log.i(Tag, "Terminado");
            //reconectar
            Intent broadcastIntent = new Intent("android.intent.action.BOOT_COMPLETED");
            sendBroadcast(broadcastIntent);
            //Detener procesos;
            StopM5Task();
            StopHourTask();
            StopPedometer();
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(Tag, "Finish");
        if (conected){
            Log.i(Tag, "Terminado");
            //reconectar
            Intent broadcastIntent = new Intent("android.intent.action.BOOT_COMPLETED");
            sendBroadcast(broadcastIntent);
            //Detener procesos;
            StopM5Task();
            StopHourTask();
            StopPedometer();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

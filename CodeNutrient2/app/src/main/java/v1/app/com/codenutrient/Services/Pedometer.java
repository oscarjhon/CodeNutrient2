package v1.app.com.codenutrient.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Arturo on 26/04/2017.
 * This code was made for the project CodeNutrient.
 */
public class Pedometer  extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

package v1.app.com.codenutrient.Helpers;

import android.os.AsyncTask;

import v1.app.com.codenutrient.Activities.MainActivity;
import v1.app.com.codenutrient.POJO.BNV_response;
import v1.app.com.codenutrient.Requests.Values;

/**
 * Created by Arturo on 14/03/2017.
 * This code was made for the project CodeNutrient.
 */
public class BNVRequest extends AsyncTask<BNV_response, String, BNV_response>{

    @Override
    protected BNV_response doInBackground(BNV_response... params) {
        Values values = new Values();
        return values.ExecutePost(params[0], MainActivity.appUser);
    }
}

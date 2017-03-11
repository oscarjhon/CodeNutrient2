package v1.app.com.codenutrient.Helpers;

import android.os.AsyncTask;
import v1.app.com.codenutrient.POJO.AppUser;

public class UserRequests extends AsyncTask<AppUser, String, AppUser> {
    public AppUser doInBackground(AppUser... appUsers) {
        AppUser appUser = new v1.app.com.codenutrient.Requests.AppUser().ExecutePost("Google", appUsers[0].getEmail(), appUsers[0].getUid(), appUsers[0].getName());
        appUser.setPhoto(appUsers[0].getPhoto());
        return appUser;
    }
}

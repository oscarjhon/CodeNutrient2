package v1.app.com.codenutrient.Helpers;


import android.content.Context;
import android.database.Cursor;

import java.sql.SQLException;

import v1.app.com.codenutrient.Activities.MainActivity;
import v1.app.com.codenutrient.POJO.AppUser;

public class ReloadUser {

    private AppUser FirstOrCreate(Context context, String provider, String uid, String name, String token, String email) {
        AppUser appUser = getDatabaseUser(provider, uid, context);
        DataBaseHelper helper = new DataBaseHelper(context);
        try {
            helper.openDataBaseReadWrite();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (appUser != null) {
            helper.updateUsers(token, uid, "Google", email, name);
            helper.close();
            return getDatabaseUser(provider, uid, context);
        }
        if (helper.insertUsers(email, "Google", token, uid, name) == -1) {
            return null;
        }
        helper.updateUsers(token, uid, "Google", email, name);
        helper.close();
        return getDatabaseUser(provider, uid, context);
    }

    private AppUser PassCursorAppUser(Cursor cursor) {
        AppUser appUser = new AppUser();
        appUser.setUid(cursor.getString(cursor.getColumnIndex("uid")));
        appUser.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
        appUser.setName(cursor.getString(cursor.getColumnIndex("name")));
        appUser.setToken(cursor.getString(cursor.getColumnIndex("token")));
        appUser.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        return appUser;
    }

    private AppUser getDatabaseUser(String provider, String uid, Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        try {
            dataBaseHelper.openDataBaseRead();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = dataBaseHelper.fetchUser(uid, provider);
        if (cursor == null) {
            dataBaseHelper.close();
            return null;
        } else if (cursor.moveToFirst()) {
            dataBaseHelper.close();
            return PassCursorAppUser(cursor);
        } else {
            dataBaseHelper.close();
            return null;
        }
    }

    public boolean postExecute(AppUser appUser, Context context) {
        if (appUser.getCode() == 200) {
            appUser = FirstOrCreate(context, appUser.getProvider(), appUser.getUid(), appUser.getName(), appUser.getToken(), appUser.getEmail());
            if (appUser == null) {
                return false;
            }
            appUser.setPhoto(appUser.getPhoto());
            MainActivity.appUser = appUser;
            return true;
        } else if (appUser.getCode() == 0) {
            return false;
        } else {
            return false;
        }
    }
}

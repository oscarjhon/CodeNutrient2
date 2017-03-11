package v1.app.com.codenutrient.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import com.google.android.gms.common.Scopes;
import v1.app.com.codenutrient.Activity.MainActivity;
import v1.app.com.codenutrient.POJO.AppUser;

public class ReloadUser {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    ReloadUser(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case -487443680:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Helpers/ReloadUser"}));
        }
    }

    private AppUser FirstOrCreate(Context context, String provider, String uid, String name, String token, String email) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (AppUser) incrementalChange.access$dispatch("FirstOrCreate.(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lv1/app/com/codenutrient/POJO/AppUser;", this, context, provider, uid, name, token, email);
        }
        AppUser appUser = getDatabaseUser(provider, uid, context);
        DataBaseHelper helper = new DataBaseHelper(context);
        helper.openDataBaseReadWrite();
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
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (AppUser) incrementalChange.access$dispatch("PassCursorAppUser.(Landroid/database/Cursor;)Lv1/app/com/codenutrient/POJO/AppUser;", this, cursor);
        }
        AppUser appUser = new AppUser();
        appUser.setUid(cursor.getString(cursor.getColumnIndex("uid")));
        appUser.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
        appUser.setName(cursor.getString(cursor.getColumnIndex("name")));
        appUser.setToken(cursor.getString(cursor.getColumnIndex("token")));
        appUser.setEmail(cursor.getString(cursor.getColumnIndex(Scopes.EMAIL)));
        return appUser;
    }

    public static /* synthetic */ Object access$super(ReloadUser reloadUser, String str, Object... objArr) {
        switch (str.hashCode()) {
            case -2128160755:
                return super.toString();
            case -1600833221:
                super.wait(((Number) objArr[0]).longValue(), ((Number) objArr[1]).intValue());
                return null;
            case -1554832987:
                super.finalize();
                return null;
            case -1166127280:
                super.notify();
                return null;
            case -1021472056:
                super.wait(((Number) objArr[0]).longValue());
                return null;
            case -712101345:
                super.notifyAll();
                return null;
            case 201261558:
                return super.getClass();
            case 244142972:
                super.wait();
                return null;
            case 1403628309:
                return new Integer(super.hashCode());
            case 1814730534:
                return new Boolean(super.equals(objArr[0]));
            case 2025021518:
                return super.clone();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Helpers/ReloadUser"}));
        }
    }

    private AppUser getDatabaseUser(String provider, String uid, Context context) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (AppUser) incrementalChange.access$dispatch("getDatabaseUser.(Ljava/lang/String;Ljava/lang/String;Landroid/content/Context;)Lv1/app/com/codenutrient/POJO/AppUser;", this, provider, uid, context);
        }
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        dataBaseHelper.openDataBaseRead();
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
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Boolean) incrementalChange.access$dispatch("postExecute.(Lv1/app/com/codenutrient/POJO/AppUser;Landroid/content/Context;)Z", this, appUser, context)).booleanValue();
        } else if (appUser.getCode() == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
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

    public ReloadUser() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Helpers/ReloadUser;)V", this);
        }
    }
}

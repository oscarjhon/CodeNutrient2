package v1.app.com.codenutrient.Helpers;

import android.os.AsyncTask;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import v1.app.com.codenutrient.POJO.AppUser;

public class UserRequests extends AsyncTask<AppUser, String, AppUser> {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    UserRequests(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -823583733:
                this();
            case 696849597:
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Helpers/UserRequests"}));
        }
    }

    public static /* synthetic */ Object access$super(UserRequests userRequests, String str, Object... objArr) {
        switch (str.hashCode()) {
            case -2128160755:
                return super.toString();
            case -2026216808:
                super.onPreExecute();
                return null;
            case -1619161865:
                return new Boolean(super.cancel(((Boolean) objArr[0]).booleanValue()));
            case -1600833221:
                super.wait(((Number) objArr[0]).longValue(), ((Number) objArr[1]).intValue());
                return null;
            case -1554832987:
                super.finalize();
                return null;
            case -1553130374:
                return super.execute((Object[]) objArr[0]);
            case -1325021319:
                super.onPostExecute(objArr[0]);
                return null;
            case -1166127280:
                super.notify();
                return null;
            case -1021472056:
                super.wait(((Number) objArr[0]).longValue());
                return null;
            case -886319787:
                super.onCancelled(objArr[0]);
                return null;
            case -712101345:
                super.notifyAll();
                return null;
            case -12658394:
                return super.executeOnExecutor((Executor) objArr[0], (Object[]) objArr[1]);
            case 201261558:
                return super.getClass();
            case 244142972:
                super.wait();
                return null;
            case 304939393:
                return super.get(((Number) objArr[0]).longValue(), (TimeUnit) objArr[1]);
            case 356493166:
                super.publishProgress((Object[]) objArr[0]);
                return null;
            case 566591929:
                super.onCancelled();
                return null;
            case 1050423957:
                super.onProgressUpdate((Object[]) objArr[0]);
                return null;
            case 1403628309:
                return new Integer(super.hashCode());
            case 1666372597:
                return super.get();
            case 1814730534:
                return new Boolean(super.equals(objArr[0]));
            case 1836909792:
                return super.getStatus();
            case 1878389842:
                return new Boolean(super.isCancelled());
            case 2025021518:
                return super.clone();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Helpers/UserRequests"}));
        }
    }

    public AppUser doInBackground(AppUser... appUsers) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (AppUser) incrementalChange.access$dispatch("doInBackground.([Lv1/app/com/codenutrient/POJO/AppUser;)Lv1/app/com/codenutrient/POJO/AppUser;", this, appUsers);
        }
        AppUser appUser = new v1.app.com.codenutrient.Requests.AppUser().ExecutePost("Google", appUsers[0].getEmail(), appUsers[0].getUid(), appUsers[0].getName());
        appUser.setPhoto(appUsers[0].getPhoto());
        return appUser;
    }

    public UserRequests() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Helpers/UserRequests;)V", this);
        }
    }
}

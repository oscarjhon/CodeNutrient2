package v1.app.com.codenutrient.Requests;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;

public class AppUser {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    AppUser(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case -1773550701:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Requests/AppUser"}));
        }
    }

    public static /* synthetic */ Object access$super(AppUser appUser, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Requests/AppUser"}));
        }
    }

    public v1.app.com.codenutrient.POJO.AppUser ExecutePost(String provider, String email, String uid, String name) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (v1.app.com.codenutrient.POJO.AppUser) incrementalChange.access$dispatch("ExecutePost.(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lv1/app/com/codenutrient/POJO/AppUser;", this, provider, email, uid, name);
        }
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setUri("http://172.16.10.11:80/api/v1/app_users");
        requestPackage.setParams("auth[provider]", provider);
        requestPackage.setParams("auth[email]", email);
        requestPackage.setParams("auth[uid]", uid);
        requestPackage.setParams("auth[name]", name);
        Response res = new HttpManager().getData(requestPackage);
        if (res.code == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            return new v1.app.com.codenutrient.Parser.AppUser().parser(res);
        }
        v1.app.com.codenutrient.POJO.AppUser app = new v1.app.com.codenutrient.POJO.AppUser();
        app.setCode(res.code);
        return app;
    }

    public AppUser() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Requests/AppUser;)V", this);
        }
    }
}

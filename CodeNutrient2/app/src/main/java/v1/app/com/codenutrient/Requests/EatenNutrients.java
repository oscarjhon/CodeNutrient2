package v1.app.com.codenutrient.Requests;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import v1.app.com.codenutrient.BuildConfig;
import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.AppUser;

public class EatenNutrients {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    EatenNutrients(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 1605915634:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Requests/EatenNutrients"}));
        }
    }

    public static /* synthetic */ Object access$super(EatenNutrients eatenNutrients, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Requests/EatenNutrients"}));
        }
    }

    public v1.app.com.codenutrient.POJO.EatenNutrients ExecuteGET(AppUser appUser) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (v1.app.com.codenutrient.POJO.EatenNutrients) incrementalChange.access$dispatch("ExecuteGET.(Lv1/app/com/codenutrient/POJO/AppUser;)Lv1/app/com/codenutrient/POJO/EatenNutrients;", this, appUser);
        }
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri("http://172.16.10.11:80/api/v1/eaten_nutrients");
        requestPackage.setParams("uid", appUser.getUid() + BuildConfig.FLAVOR);
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            return new v1.app.com.codenutrient.Parser.EatenNutrients().parser(response);
        }
        v1.app.com.codenutrient.POJO.EatenNutrients eatenNutrients = new v1.app.com.codenutrient.POJO.EatenNutrients();
        eatenNutrients.setCode(response.code);
        return eatenNutrients;
    }

    public v1.app.com.codenutrient.POJO.EatenNutrients ExecuteGET(AppUser appUser, Date fecha) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (v1.app.com.codenutrient.POJO.EatenNutrients) incrementalChange.access$dispatch("ExecuteGET.(Lv1/app/com/codenutrient/POJO/AppUser;Ljava/util/Date;)Lv1/app/com/codenutrient/POJO/EatenNutrients;", this, appUser, fecha);
        }
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri("http://172.16.10.11:80/api/v1/eaten_nutrients");
        requestPackage.setParams("uid", appUser.getUid() + BuildConfig.FLAVOR);
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        requestPackage.setParams("fecha", formatter.format(fecha));
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            return new v1.app.com.codenutrient.Parser.EatenNutrients().parser(response);
        }
        v1.app.com.codenutrient.POJO.EatenNutrients eatenNutrients = new v1.app.com.codenutrient.POJO.EatenNutrients();
        eatenNutrients.setCode(response.code);
        return eatenNutrients;
    }

    public EatenNutrients() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Requests/EatenNutrients;)V", this);
        }
    }
}

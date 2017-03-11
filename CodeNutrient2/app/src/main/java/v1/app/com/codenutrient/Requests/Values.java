package v1.app.com.codenutrient.Requests;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.util.ArrayList;
import v1.app.com.codenutrient.BuildConfig;
import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.BNV_response;

public class Values {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    Values(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case -368358103:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Requests/Values"}));
        }
    }

    public static /* synthetic */ Object access$super(Values values, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Requests/Values"}));
        }
    }

    public BNV_response ExecuteGET(AppUser appUser) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (BNV_response) incrementalChange.access$dispatch("ExecuteGET.(Lv1/app/com/codenutrient/POJO/AppUser;)Lv1/app/com/codenutrient/POJO/BNV_response;", this, appUser);
        }
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri("http://172.16.10.11:80/api/v1/best_nutrient_values");
        requestPackage.setParams("uid", appUser.getUid() + BuildConfig.FLAVOR);
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        Response response = new HttpManager().getData(requestPackage);
        if (response.code != Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            BNV_response bnv_response = new BNV_response();
            bnv_response.setCode(response.code);
            return bnv_response;
        }
        new v1.app.com.codenutrient.Parser.Values().parser(response);
        return null;
    }

    public BNV_response ExecutePost(BNV_response values, AppUser appUser) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (BNV_response) incrementalChange.access$dispatch("ExecutePost.(Lv1/app/com/codenutrient/POJO/BNV_response;Lv1/app/com/codenutrient/POJO/AppUser;)Lv1/app/com/codenutrient/POJO/BNV_response;", this, values, appUser);
        }
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setUri("http://172.16.10.11:80/api/v1/best_nutrient_values");
        ArrayList aux_values = values.getValues();
        requestPackage.setParams("uid", appUser.getUid() + BuildConfig.FLAVOR);
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        for (int i = 0; i < aux_values.size(); i++) {
            v1.app.com.codenutrient.POJO.Values value = (v1.app.com.codenutrient.POJO.Values) aux_values.get(i);
            requestPackage.setParams("best[_" + value.getNutrient_id() + "][value]", value.getValue() + BuildConfig.FLAVOR);
        }
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            return new v1.app.com.codenutrient.Parser.Values().parser(response);
        }
        BNV_response bnv_response = new BNV_response();
        bnv_response.setCode(response.code);
        return bnv_response;
    }

    public Values() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Requests/Values;)V", this);
        }
    }
}

package v1.app.com.codenutrient.Parser;

import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import com.google.android.gms.common.Scopes;
import org.json.JSONException;
import org.json.JSONObject;
import v1.app.com.codenutrient.HTTP.Response;

public class AppUser {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    AppUser(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case -1470149074:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Parser/AppUser"}));
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Parser/AppUser"}));
        }
    }

    public v1.app.com.codenutrient.POJO.AppUser parser(Response response) {
        v1.app.com.codenutrient.POJO.AppUser appUser;
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (v1.app.com.codenutrient.POJO.AppUser) incrementalChange.access$dispatch("parser.(Lv1/app/com/codenutrient/HTTP/Response;)Lv1/app/com/codenutrient/POJO/AppUser;", this, response);
        }
        try {
            appUser = new v1.app.com.codenutrient.POJO.AppUser();
            appUser.setCode(response.code);
            JSONObject data = new JSONObject(response.response).getJSONObject("data");
            JSONObject attributes = data.getJSONObject("attributes");
            appUser.setEmail(attributes.getString(Scopes.EMAIL));
            appUser.setProvider(attributes.getString("provider"));
            appUser.setName(attributes.getString("name"));
            appUser.setUid(attributes.getString("uid"));
            appUser.setToken(data.getString("token"));
            return appUser;
        } catch (JSONException e) {
            e.printStackTrace();
            appUser = new v1.app.com.codenutrient.POJO.AppUser();
            appUser.setCode(0);
            return appUser;
        }
    }

    public AppUser() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Parser/AppUser;)V", this);
        }
    }
}

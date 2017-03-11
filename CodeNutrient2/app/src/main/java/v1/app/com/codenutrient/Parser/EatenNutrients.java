package v1.app.com.codenutrient.Parser;

import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.Nutrient;

public class EatenNutrients {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    EatenNutrients(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 59356407:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Parser/EatenNutrients"}));
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Parser/EatenNutrients"}));
        }
    }

    public v1.app.com.codenutrient.POJO.EatenNutrients parser(Response response) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (v1.app.com.codenutrient.POJO.EatenNutrients) incrementalChange.access$dispatch("parser.(Lv1/app/com/codenutrient/HTTP/Response;)Lv1/app/com/codenutrient/POJO/EatenNutrients;", this, response);
        }
        try {
            v1.app.com.codenutrient.POJO.EatenNutrients E_nutrients = new v1.app.com.codenutrient.POJO.EatenNutrients();
            ArrayList<Nutrient> nutrients = new ArrayList();
            E_nutrients.setCode(response.code);
            JSONArray data = new JSONObject(response.response).getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject attributes = data.getJSONObject(i).getJSONObject("attributes");
                Nutrient nutrient = new Nutrient();
                nutrient.setNutrient_id(attributes.getInt("nutrient_id"));
                nutrient.setCantidad((float) attributes.getDouble("cantidad"));
                nutrients.add(nutrient);
            }
            E_nutrients.setNutrients(nutrients);
            return E_nutrients;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public EatenNutrients() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Parser/EatenNutrients;)V", this);
        }
    }
}

package v1.app.com.codenutrient.Parser;

import android.content.ContentValues;
import android.support.transition.Fade;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import com.google.android.gms.common.ConnectionResult;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import v1.app.com.codenutrient.HTTP.Response;

public class InfoAppUser {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    InfoAppUser(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 760541984:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Parser/InfoAppUser"}));
        }
    }

    public static /* synthetic */ Object access$super(InfoAppUser infoAppUser, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Parser/InfoAppUser"}));
        }
    }

    public v1.app.com.codenutrient.POJO.InfoAppUser parser(Response response) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (v1.app.com.codenutrient.POJO.InfoAppUser) incrementalChange.access$dispatch("parser.(Lv1/app/com/codenutrient/HTTP/Response;)Lv1/app/com/codenutrient/POJO/InfoAppUser;", this, response);
        }
        v1.app.com.codenutrient.POJO.InfoAppUser infoAppUser;
        try {
            int[] diseases;
            infoAppUser = new v1.app.com.codenutrient.POJO.InfoAppUser();
            infoAppUser.setCode(response.code);
            String str = "data";
            JSONObject data = new JSONObject(response.response).getJSONObject(r17);
            JSONObject attributes = data.getJSONObject("attributes");
            JSONObject relations = data.getJSONObject("relations");
            infoAppUser.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(attributes.getString("fecha_nacimiento")));
            infoAppUser.setPeso((float) attributes.getDouble("peso"));
            infoAppUser.setEstatura((float) attributes.getDouble("estatura"));
            infoAppUser.setSexo(attributes.getBoolean("sexo"));
            infoAppUser.setMax_calorias((float) attributes.getDouble("max_calorias"));
            infoAppUser.setMin_calorias((float) attributes.getDouble("min_calorias"));
            infoAppUser.setEmbarazo(attributes.getBoolean("embarazo"));
            infoAppUser.setLactancia(attributes.getBoolean("lactancia"));
            JSONArray has_disease = relations.getJSONArray("has_diseases");
            if (has_disease.length() == 0) {
                diseases = null;
            } else {
                int[] aux = new int[has_disease.length()];
                for (int i = 0; i < has_disease.length(); i++) {
                    str = "disease_id";
                    aux[i] = has_disease.getJSONObject(i).getJSONObject("attributes").getInt(r17);
                }
                diseases = aux;
            }
            infoAppUser.setDiseases(diseases);
            return infoAppUser;
        } catch (JSONException e) {
            e.printStackTrace();
            infoAppUser = new v1.app.com.codenutrient.POJO.InfoAppUser();
            infoAppUser.setCode(0);
            return infoAppUser;
        } catch (ParseException e2) {
            e2.printStackTrace();
            infoAppUser = new v1.app.com.codenutrient.POJO.InfoAppUser();
            infoAppUser.setCode(0);
            return infoAppUser;
        }
    }

    public v1.app.com.codenutrient.POJO.InfoAppUser parserError(Response response) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (v1.app.com.codenutrient.POJO.InfoAppUser) incrementalChange.access$dispatch("parserError.(Lv1/app/com/codenutrient/HTTP/Response;)Lv1/app/com/codenutrient/POJO/InfoAppUser;", this, response);
        }
        String[] cases = new String[]{"Fecha", "Peso", "Estatura", "Max", "Min"};
        v1.app.com.codenutrient.POJO.InfoAppUser infoAppUser;
        try {
            infoAppUser = new v1.app.com.codenutrient.POJO.InfoAppUser();
            infoAppUser.setCode(response.code);
            JSONArray errors = new JSONObject(response.response).getJSONArray("errors");
            ContentValues values = new ContentValues();
            int i = 0;
            while (i < errors.length()) {
                String error = errors.getString(i);
                for (int j = 0; j < cases.length && !error.contains(cases[i]); j++) {
                }
                switch (i) {
                    case ConnectionResult.SUCCESS /*0*/:
                        values.put("Fecha", error);
                        break;
                    case Fade.IN /*1*/:
                        values.put("Peso", error);
                        break;
                    case Fade.OUT /*2*/:
                        values.put("Estatura", error);
                        break;
                    case ConnectionResult.SERVICE_DISABLED /*3*/:
                        values.put("Max", error);
                        break;
                    case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                        values.put("Min", error);
                        break;
                    default:
                        values.put("default", error);
                        break;
                }
                i++;
            }
            infoAppUser.setErrors(values);
            return infoAppUser;
        } catch (JSONException e) {
            e.printStackTrace();
            infoAppUser = new v1.app.com.codenutrient.POJO.InfoAppUser();
            infoAppUser.setCode(0);
            return infoAppUser;
        }
    }

    public InfoAppUser() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Parser/InfoAppUser;)V", this);
        }
    }
}

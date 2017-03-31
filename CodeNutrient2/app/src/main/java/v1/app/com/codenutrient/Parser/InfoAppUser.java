package v1.app.com.codenutrient.Parser;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import v1.app.com.codenutrient.HTTP.Response;

public class InfoAppUser{

    public v1.app.com.codenutrient.POJO.InfoAppUser parser(Response response) {
        v1.app.com.codenutrient.POJO.InfoAppUser infoAppUser;
        try {
            int[] diseases;
            infoAppUser = new v1.app.com.codenutrient.POJO.InfoAppUser();
            infoAppUser.setCode(response.code);
            JSONObject data = new JSONObject(response.response).getJSONObject("data");
            JSONObject attributes = data.getJSONObject("attributes");
            infoAppUser.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(attributes.getString("fecha_nacimiento")));
            infoAppUser.setPeso((float) attributes.getDouble("peso"));
            infoAppUser.setEstatura((float) attributes.getDouble("estatura"));
            infoAppUser.CalculateIMC();
            infoAppUser.setSexo(attributes.getBoolean("sexo"));
            infoAppUser.setMax_calorias((float) attributes.getDouble("max_calorias"));
            infoAppUser.setMin_calorias((float) attributes.getDouble("min_calorias"));
            infoAppUser.setEmbarazo(attributes.getBoolean("embarazo"));
            infoAppUser.setLactancia(attributes.getBoolean("lactancia"));
           if (data.has("relations")) {
               JSONObject relations = data.getJSONObject("relations");
               JSONArray has_disease = relations.getJSONArray("has_diseases");
               if (has_disease.length() == 0) {
                   diseases = null;
               } else {
                   int[] aux = new int[has_disease.length()];
                   for (int i = 0; i < has_disease.length(); i++) {
                       aux[i] = has_disease.getJSONObject(i).getJSONObject("attributes").getInt("disease_id");
                   }
                   diseases = aux;
               }
               infoAppUser.setDiseases(diseases);
           }else{
               diseases = null;
               infoAppUser.setDiseases(diseases);
           }
            return infoAppUser;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            infoAppUser = new v1.app.com.codenutrient.POJO.InfoAppUser();
            infoAppUser.setCode(0);
            return infoAppUser;
        }
    }

    public v1.app.com.codenutrient.POJO.InfoAppUser parserError(Response response) {
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
                    case 0:
                        values.put("Fecha", error);
                        break;
                    case 1:
                        values.put("Peso", error);
                        break;
                    case 2:
                        values.put("Estatura", error);
                        break;
                    case 3:
                        values.put("Max", error);
                        break;
                    case 4:
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

}

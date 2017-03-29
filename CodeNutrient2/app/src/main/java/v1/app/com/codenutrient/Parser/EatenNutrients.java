package v1.app.com.codenutrient.Parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.Nutrient;

public class EatenNutrients {

    public v1.app.com.codenutrient.POJO.EatenNutrients parser(Response response) {
        v1.app.com.codenutrient.POJO.EatenNutrients E_nutrients = new v1.app.com.codenutrient.POJO.EatenNutrients();
        try {
            ArrayList<Nutrient> nutrients = new ArrayList();
            E_nutrients.setCode(response.code);
            JSONObject json = new JSONObject(response.response);
            if (json.has("data")) {
                JSONArray data = json.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject attributes = data.getJSONObject(i).getJSONObject("attributes");
                    Nutrient nutrient = new Nutrient();
                    nutrient.setNutrient_id(attributes.getInt("nutrient_id"));
                    nutrient.setCantidad((float) attributes.getDouble("cantidad"));
                    nutrients.add(nutrient);
                }
            }
            E_nutrients.setNutrients(nutrients);
            return E_nutrients;
        } catch (JSONException e) {
            e.printStackTrace();
            E_nutrients.setCode(0);
            return E_nutrients;
        }
    }
}

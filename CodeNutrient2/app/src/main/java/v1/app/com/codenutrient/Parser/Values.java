package v1.app.com.codenutrient.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.BNV_response;

public class Values {

    public BNV_response parser(Response response) {
        BNV_response BNV_response = new BNV_response();
        try {
            ArrayList<v1.app.com.codenutrient.POJO.Values> Values = new ArrayList();
            BNV_response.setCode(response.code);
            JSONObject data = new JSONObject(response.response).getJSONObject("data");
            JSONObject relations = data.getJSONObject("relations");
            JSONArray bnv = relations.getJSONArray("best_nutrient_values");
            for (int i = 0; i < bnv.length(); i++) {
                JSONObject v_attributes = bnv.getJSONObject(i).getJSONObject("attributes");
                v1.app.com.codenutrient.POJO.Values values = new v1.app.com.codenutrient.POJO.Values();
                values.setNutrient_id(v_attributes.getInt("nutrient_id"));
                values.setValue((float) v_attributes.getDouble("value"));
                Values.add(values);
            }
            BNV_response.setValues(Values);
            return BNV_response;
        } catch (JSONException e) {
            e.printStackTrace();
            BNV_response.setCode(0);
            return BNV_response;
        }
    }
}

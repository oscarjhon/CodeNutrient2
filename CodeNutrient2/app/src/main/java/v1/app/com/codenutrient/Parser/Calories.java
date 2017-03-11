package v1.app.com.codenutrient.Parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import v1.app.com.codenutrient.HTTP.Response;

public class Calories {

    public v1.app.com.codenutrient.POJO.Calories parser(Response response) {
        v1.app.com.codenutrient.POJO.Calories calories = new v1.app.com.codenutrient.POJO.Calories();
        try {
            calories.setCode(response.code);
            JSONObject jsonObject = new JSONObject(response.response);
            JSONArray data = jsonObject.getJSONArray("data");
            JSONObject calorie = data.getJSONObject(0);
            JSONObject attributes = calorie.getJSONObject("attributes");
            calories.setGasto((float) attributes.getDouble("gasto"));
            return calories;
        } catch (JSONException e) {
            e.printStackTrace();
            calories.setCode(0);
            return calories;
        }
    }
}

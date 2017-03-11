package v1.app.com.codenutrient.Parser;

import org.json.JSONException;
import org.json.JSONObject;
import v1.app.com.codenutrient.HTTP.Response;

public class AppUser {

    public v1.app.com.codenutrient.POJO.AppUser parser(Response response) {
        v1.app.com.codenutrient.POJO.AppUser appUser;
        try {
            appUser = new v1.app.com.codenutrient.POJO.AppUser();
            appUser.setCode(response.code);
            JSONObject data = new JSONObject(response.response).getJSONObject("data");
            JSONObject attributes = data.getJSONObject("attributes");
            appUser.setEmail(attributes.getString("email"));
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
}

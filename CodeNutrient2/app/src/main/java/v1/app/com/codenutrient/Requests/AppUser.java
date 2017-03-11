package v1.app.com.codenutrient.Requests;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.Constants;

public class AppUser {

    public v1.app.com.codenutrient.POJO.AppUser ExecutePost(String provider, String email, String uid, String name) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.app_users);
        requestPackage.setParams("auth[provider]", provider);
        requestPackage.setParams("auth[email]", email);
        requestPackage.setParams("auth[uid]", uid);
        requestPackage.setParams("auth[name]", name);
        Response res = new HttpManager().getData(requestPackage);
        if (res.code == 200) {
            return new v1.app.com.codenutrient.Parser.AppUser().parser(res);
        }
        v1.app.com.codenutrient.POJO.AppUser app = new v1.app.com.codenutrient.POJO.AppUser();
        app.setCode(res.code);
        return app;
    }
}

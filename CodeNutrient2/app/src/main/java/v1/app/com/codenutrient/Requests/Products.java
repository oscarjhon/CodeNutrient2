package v1.app.com.codenutrient.Requests;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.*;

public class Products {

    public Product ExecuteGET(String code, v1.app.com.codenutrient.POJO.AppUser appUser) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.products + code);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        Response res = new HttpManager().getData(requestPackage);
        if (res.code == 200) {
            return new v1.app.com.codenutrient.Parser.Product().parser(res);
        }
        Product product = new Product();
        product.setHttpcode(res.code);
        return product;
    }
}

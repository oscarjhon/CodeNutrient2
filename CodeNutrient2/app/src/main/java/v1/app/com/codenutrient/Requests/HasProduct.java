package v1.app.com.codenutrient.Requests;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.Constants;
import v1.app.com.codenutrient.POJO.Nutrient;
import v1.app.com.codenutrient.POJO.Product;

public class HasProduct {

    public v1.app.com.codenutrient.POJO.HasProduct ExecuteGET(AppUser appUser) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.has_products);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == 200) {
            return new v1.app.com.codenutrient.Parser.HasProduct().parser(response);
        }
        v1.app.com.codenutrient.POJO.HasProduct hasProduct = new v1.app.com.codenutrient.POJO.HasProduct();
        hasProduct.setCode(response.code);
        return hasProduct;
    }

    public v1.app.com.codenutrient.POJO.HasProduct ExecuteGET(AppUser appUser, Date fecha) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.has_products);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        requestPackage.setParams("fecha", formatter.format(fecha));
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == 200) {
            return new v1.app.com.codenutrient.Parser.HasProduct().parser(response);
        }
        v1.app.com.codenutrient.POJO.HasProduct hasProduct = new v1.app.com.codenutrient.POJO.HasProduct();
        hasProduct.setCode(response.code);
        return hasProduct;
    }

    public int ExecutePost(AppUser appUser, Product product) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.has_products);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        requestPackage.setParams("has[porciones]", product.getPorcion() + "");
        requestPackage.setParams("has[cantidad]", product.getCantidad() + "");
        requestPackage.setParams("has[calories]", product.getCalorias() + "");
        requestPackage.setParams("has[Product]", product.getCode() + "");
        Iterator it = product.getNutrients().iterator();
        while (it.hasNext()) {
            Nutrient nutrient = (Nutrient) it.next();
            requestPackage.setParams("nutrients[_" + nutrient.getNutrient_id() + "]", nutrient.getCantidad() + "");
        }
        return new HttpManager().getData(requestPackage).code;
    }
}

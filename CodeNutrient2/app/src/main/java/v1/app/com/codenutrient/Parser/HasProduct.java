package v1.app.com.codenutrient.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.Constants;

public class HasProduct {

    public v1.app.com.codenutrient.POJO.HasProduct parser(Response response) {
        v1.app.com.codenutrient.POJO.HasProduct hasProduct = new v1.app.com.codenutrient.POJO.HasProduct();
        try {
            ArrayList<v1.app.com.codenutrient.POJO.Product> products = new ArrayList();
            hasProduct.setCode(response.code);
            JSONObject json = new JSONObject(response.response);
            if (json.has("data")){
                JSONArray data = json.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject has = data.getJSONObject(i);
                    JSONObject has_attributes = has.getJSONObject("attributes");
                    JSONObject j_product = has.getJSONObject("relations").getJSONObject("product");
                    JSONObject j_product_attributes = j_product.getJSONObject("attributes");
                    v1.app.com.codenutrient.POJO.Product product = new v1.app.com.codenutrient.POJO.Product();
                    product.setNombre(j_product_attributes.getString("nombre"));
                    product.setPorcion((float) has_attributes.getDouble("porciones"));
                    product.setCantidad((float) has_attributes.getDouble("cantidad"));
                    product.setCalorias((float) (has_attributes.getDouble("porciones") * j_product_attributes.getDouble("calorias")));
                    product.setCode(j_product_attributes.getString("codigo"));
                    product.setDate(j_product_attributes.getString("created_at"));
                    product.setMeasure_id(has_attributes.getInt("measure_id"));
                    product.setImageURL("http:" + j_product.getString("imageURL"));
                    //product.setImageURL(Constants.ip_addr + j_product.getString("imageURL"));
                    products.add(product);
                }
            }
            hasProduct.setProducts(products);
            return hasProduct;
        } catch (JSONException e) {
            e.printStackTrace();
            hasProduct.setCode(0);
            return hasProduct;
        }
    }
}

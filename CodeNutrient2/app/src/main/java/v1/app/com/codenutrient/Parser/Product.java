package v1.app.com.codenutrient.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.Constants;
import v1.app.com.codenutrient.POJO.Nutrient;

public class Product {

    public v1.app.com.codenutrient.POJO.Product parser(Response response) {
        v1.app.com.codenutrient.POJO.Product product;
        try {
            product = new v1.app.com.codenutrient.POJO.Product();
            ArrayList<Nutrient> nutrients = new ArrayList();
            product.setHttpcode(response.code);
            JSONObject data = new JSONObject(response.response).getJSONObject("data");
            JSONObject attributes = data.getJSONObject("attributes");
            product.setNombre(attributes.getString("nombre"));
            product.setCode(attributes.getString("codigo"));
            product.setCantidad((float) attributes.getDouble("cantidad"));
            product.setCalorias((float) attributes.getDouble("calorias"));
            product.setMeasure_id(attributes.getInt("measure_id"));
            product.setImageURL("http:" + data.getString("imageURL"));
            //product.setImageURL(Constants.ip_addr + data.getString("imageURL"));
            //product.setImageURL("http://foodapiapp.s3-us-west-1.amazonaws.com/products/images/000/000/001/mobile/LADL.png?1490501508");
            JSONObject relations = data.getJSONObject("relations");
            JSONArray has_nutrients = relations.getJSONArray("has_nutrients");
            JSONObject portion_attr = relations.getJSONObject("portion").getJSONObject("attributes");
            product.setPorcion((float) portion_attr.getDouble("porcion"));
            product.setP_cantidad((float) portion_attr.getDouble("cantidad"));
            product.setEquivalencia(portion_attr.getString("equivalencia"));
            for (int i = 0; i < has_nutrients.length(); i++) {
                JSONObject n_attributes = has_nutrients.getJSONObject(i).getJSONObject("attributes");
                Nutrient n = new Nutrient();
                n.setNutrient_id(n_attributes.getInt("nutrient_id"));
                n.setCantidad((float) n_attributes.getDouble("cantidad"));
                nutrients.add(n);
            }
            product.setNutrients(nutrients.size() != 0 ? nutrients : null);
            return product;
        } catch (JSONException e) {
            e.printStackTrace();
            product = new v1.app.com.codenutrient.POJO.Product();
            product.setHttpcode(0);
            return product;
        }
    }
}

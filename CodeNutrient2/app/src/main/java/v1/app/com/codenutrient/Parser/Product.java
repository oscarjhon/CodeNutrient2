package v1.app.com.codenutrient.Parser;

import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.Nutrient;

public class Product {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    Product(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 851759121:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Parser/Product"}));
        }
    }

    public static /* synthetic */ Object access$super(Product product, String str, Object... objArr) {
        switch (str.hashCode()) {
            case -2128160755:
                return super.toString();
            case -1600833221:
                super.wait(((Number) objArr[0]).longValue(), ((Number) objArr[1]).intValue());
                return null;
            case -1554832987:
                super.finalize();
                return null;
            case -1166127280:
                super.notify();
                return null;
            case -1021472056:
                super.wait(((Number) objArr[0]).longValue());
                return null;
            case -712101345:
                super.notifyAll();
                return null;
            case 201261558:
                return super.getClass();
            case 244142972:
                super.wait();
                return null;
            case 1403628309:
                return new Integer(super.hashCode());
            case 1814730534:
                return new Boolean(super.equals(objArr[0]));
            case 2025021518:
                return super.clone();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Parser/Product"}));
        }
    }

    public v1.app.com.codenutrient.POJO.Product parser(Response response) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (v1.app.com.codenutrient.POJO.Product) incrementalChange.access$dispatch("parser.(Lv1/app/com/codenutrient/HTTP/Response;)Lv1/app/com/codenutrient/POJO/Product;", this, response);
        }
        v1.app.com.codenutrient.POJO.Product product;
        try {
            product = new v1.app.com.codenutrient.POJO.Product();
            ArrayList<Nutrient> nutrients = new ArrayList();
            product.setCode(response.code);
            String str = "data";
            JSONObject data = new JSONObject(response.response).getJSONObject(r16);
            JSONObject attributes = data.getJSONObject("attributes");
            product.setNombre(attributes.getString("nombre"));
            product.setCantidad((float) attributes.getDouble("cantidad"));
            product.setCalorias((float) attributes.getDouble("calorias"));
            product.setMeasure_id(attributes.getInt("measure_id"));
            product.setImageURL(data.getString("imageURL"));
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
            product.setNutrients(nutrients);
            return product;
        } catch (JSONException e) {
            e.printStackTrace();
            product = new v1.app.com.codenutrient.POJO.Product();
            product.setCode(0);
            return product;
        }
    }

    public Product() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Parser/Product;)V", this);
        }
    }
}

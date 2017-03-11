package v1.app.com.codenutrient.Parser;

import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.Product;

public class HasProduct {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    HasProduct(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 1232440065:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Parser/HasProduct"}));
        }
    }

    public static /* synthetic */ Object access$super(HasProduct hasProduct, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Parser/HasProduct"}));
        }
    }

    public v1.app.com.codenutrient.POJO.HasProduct parser(Response response) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (v1.app.com.codenutrient.POJO.HasProduct) incrementalChange.access$dispatch("parser.(Lv1/app/com/codenutrient/HTTP/Response;)Lv1/app/com/codenutrient/POJO/HasProduct;", this, response);
        }
        try {
            v1.app.com.codenutrient.POJO.HasProduct hasProduct = new v1.app.com.codenutrient.POJO.HasProduct();
            ArrayList<Product> products = new ArrayList();
            hasProduct.setCode(response.code);
            JSONArray data = new JSONObject(response.response).getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject has = data.getJSONObject(i);
                JSONObject has_attributes = has.getJSONObject("attributes");
                JSONObject j_product = has.getJSONObject("relations").getJSONObject("product");
                JSONObject j_product_attributes = j_product.getJSONObject("attributes");
                Product product = new Product();
                product.setNombre(j_product_attributes.getString("nombre"));
                product.setPorcion((float) has_attributes.getDouble("porciones"));
                product.setCantidad((float) has_attributes.getDouble("cantidad"));
                product.setCalorias((float) (has_attributes.getDouble("porciones") * j_product_attributes.getDouble("calorias")));
                product.setCode(j_product_attributes.getInt("codigo"));
                product.setImageURL(j_product.getString("imageURL"));
                products.add(product);
            }
            hasProduct.setProducts(products);
            return hasProduct;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HasProduct() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Parser/HasProduct;)V", this);
        }
    }
}

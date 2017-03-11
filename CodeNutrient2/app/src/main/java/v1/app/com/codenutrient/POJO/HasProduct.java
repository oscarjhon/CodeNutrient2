package v1.app.com.codenutrient.POJO;

import java.util.ArrayList;

public class HasProduct {
    private int code;
    private ArrayList<Product> products = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}

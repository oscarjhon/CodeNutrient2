package v1.app.com.codenutrient.POJO;

import java.util.ArrayList;

public class Product {
    private float calorias;
    private float cantidad;
    private String code;
    private int httpcode;
    private String equivalencia;
    private String imageURL;
    private int measure_id;
    private String nombre;
    private ArrayList<Nutrient> nutrients;
    private float p_cantidad;
    private float porcion;

    public Product(){
        nutrients = null;
    }

    public float getCalorias() {
        return calorias;
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getHttpcode() {
        return httpcode;
    }

    public void setHttpcode(int httpcode) {
        this.httpcode = httpcode;
    }

    public String getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(String equivalencia) {
        this.equivalencia = equivalencia;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getMeasure_id() {
        return measure_id;
    }

    public void setMeasure_id(int measure_id) {
        this.measure_id = measure_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(ArrayList<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public float getP_cantidad() {
        return p_cantidad;
    }

    public void setP_cantidad(float p_cantidad) {
        this.p_cantidad = p_cantidad;
    }

    public float getPorcion() {
        return porcion;
    }

    public void setPorcion(float porcion) {
        this.porcion = porcion;
    }
}

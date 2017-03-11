package v1.app.com.codenutrient.POJO;

import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.util.ArrayList;

public class Product {
    public static volatile transient /* synthetic */ IncrementalChange $change;
    private float calorias;
    private float cantidad;
    private int code;
    private String equivalencia;
    private String imageURL;
    private int measure_id;
    private String nombre;
    private ArrayList<Nutrient> nutrients;
    private float p_cantidad;
    private float porcion;

    Product(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 614530710:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/POJO/Product"}));
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/POJO/Product"}));
        }
    }

    public float getCalorias() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.calorias;
        }
        return ((Number) incrementalChange.access$dispatch("getCalorias.()F", this)).floatValue();
    }

    public float getCantidad() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.cantidad;
        }
        return ((Number) incrementalChange.access$dispatch("getCantidad.()F", this)).floatValue();
    }

    public int getCode() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.code;
        }
        return ((Number) incrementalChange.access$dispatch("getCode.()I", this)).intValue();
    }

    public String getEquivalencia() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.equivalencia;
        }
        return (String) incrementalChange.access$dispatch("getEquivalencia.()Ljava/lang/String;", this);
    }

    public String getImageURL() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.imageURL;
        }
        return (String) incrementalChange.access$dispatch("getImageURL.()Ljava/lang/String;", this);
    }

    public int getMeasure_id() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.measure_id;
        }
        return ((Number) incrementalChange.access$dispatch("getMeasure_id.()I", this)).intValue();
    }

    public String getNombre() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.nombre;
        }
        return (String) incrementalChange.access$dispatch("getNombre.()Ljava/lang/String;", this);
    }

    public ArrayList<Nutrient> getNutrients() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.nutrients;
        }
        return (ArrayList) incrementalChange.access$dispatch("getNutrients.()Ljava/util/ArrayList;", this);
    }

    public float getP_cantidad() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.p_cantidad;
        }
        return ((Number) incrementalChange.access$dispatch("getP_cantidad.()F", this)).floatValue();
    }

    public float getPorcion() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.porcion;
        }
        return ((Number) incrementalChange.access$dispatch("getPorcion.()F", this)).floatValue();
    }

    public void setCalorias(float calorias) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setCalorias.(F)V", this, new Float(calorias));
            return;
        }
        this.calorias = calorias;
    }

    public void setCantidad(float cantidad) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setCantidad.(F)V", this, new Float(cantidad));
            return;
        }
        this.cantidad = cantidad;
    }

    public void setCode(int code) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setCode.(I)V", this, new Integer(code));
            return;
        }
        this.code = code;
    }

    public void setEquivalencia(String equivalencia) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setEquivalencia.(Ljava/lang/String;)V", this, equivalencia);
            return;
        }
        this.equivalencia = equivalencia;
    }

    public void setImageURL(String imageURL) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setImageURL.(Ljava/lang/String;)V", this, imageURL);
            return;
        }
        this.imageURL = imageURL;
    }

    public void setMeasure_id(int measure_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setMeasure_id.(I)V", this, new Integer(measure_id));
            return;
        }
        this.measure_id = measure_id;
    }

    public void setNombre(String nombre) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setNombre.(Ljava/lang/String;)V", this, nombre);
            return;
        }
        this.nombre = nombre;
    }

    public void setNutrients(ArrayList<Nutrient> nutrients) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setNutrients.(Ljava/util/ArrayList;)V", this, nutrients);
            return;
        }
        this.nutrients = nutrients;
    }

    public void setP_cantidad(float p_cantidad) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setP_cantidad.(F)V", this, new Float(p_cantidad));
            return;
        }
        this.p_cantidad = p_cantidad;
    }

    public void setPorcion(float porcion) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setPorcion.(F)V", this, new Float(porcion));
            return;
        }
        this.porcion = porcion;
    }

    public Product() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/POJO/Product;)V", this);
        }
    }
}

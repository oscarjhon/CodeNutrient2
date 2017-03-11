package v1.app.com.codenutrient.POJO;

import android.content.ContentValues;

import java.util.Date;

public class InfoAppUser {
    private ContentValues Errors;
    private Date FechaNacimiento;
    private int code;
    private int[] diseases;
    private boolean embarazo;
    private float estatura;
    private boolean lactancia;
    private float max_calorias;
    private float min_calorias;
    private float peso;
    private boolean sexo;

    public InfoAppUser() {
        diseases = null;
        Errors = null;
    }

    public ContentValues getErrors() {
        return Errors;
    }

    public void setErrors(ContentValues errors) {
        Errors = errors;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int[] getDiseases() {
        return diseases;
    }

    public void setDiseases(int[] diseases) {
        this.diseases = diseases;
    }

    public boolean isEmbarazo() {
        return embarazo;
    }

    public void setEmbarazo(boolean embarazo) {
        this.embarazo = embarazo;
    }

    public float getEstatura() {
        return estatura;
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura;
    }

    public boolean isLactancia() {
        return lactancia;
    }

    public void setLactancia(boolean lactancia) {
        this.lactancia = lactancia;
    }

    public float getMax_calorias() {
        return max_calorias;
    }

    public void setMax_calorias(float max_calorias) {
        this.max_calorias = max_calorias;
    }

    public float getMin_calorias() {
        return min_calorias;
    }

    public void setMin_calorias(float min_calorias) {
        this.min_calorias = min_calorias;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }
}

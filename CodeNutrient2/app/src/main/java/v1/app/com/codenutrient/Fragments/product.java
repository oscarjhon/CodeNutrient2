package v1.app.com.codenutrient.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import v1.app.com.codenutrient.POJO.Nutrient;
import v1.app.com.codenutrient.R;

/**
 * Created by Arturo on 16/03/2017.
 * This code was made for the project CodeNutrient.
 */
public class Product extends Fragment {

    private View rootView;
    private RadioButton porciones, full;
    private Button send;
    private Spinner spinner;
    private LinearLayout linear;
    private TextView tittle, portions;
    private v1.app.com.codenutrient.POJO.Product product;

    private TextInputLayout til_op_portions;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_product, container, false);

        porciones = (RadioButton) rootView.findViewById(R.id.porciones);
        full = (RadioButton) rootView.findViewById(R.id.full);
        send = (Button) rootView.findViewById(R.id.send_product);
        spinner = (Spinner) rootView.findViewById(R.id.portion_spiner);

        portions = (TextView) rootView.findViewById(R.id.portions_question);
        tittle = (TextView) rootView.findViewById(R.id.register_tittle);

        linear = (LinearLayout) rootView.findViewById(R.id.linear_portions);

        til_op_portions = (TextInputLayout) rootView.findViewById(R.id.til_op_portions);
        porciones.setOnClickListener(listener);
        full.setOnClickListener(listener);
        send.setOnClickListener(listener);

        return rootView;
    }

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.porciones:
                    linear.setVisibility(View.VISIBLE);
                    break;
                case R.id.full:
                    linear.setVisibility(View.GONE);
                    break;
                case R.id.send_product:
                    if (full.isChecked()) {
                        til_op_portions.setError(null);
                        PrepareProduct(true);
                    }else if (porciones.isChecked()){
                        til_op_portions.setError(null);
                        PrepareProduct(false);
                    }else{
                        til_op_portions.setError("Selecciona una opción.");
                        Snackbar.make(rootView.findViewById(R.id.productCoordinator), "Selecciona una opción.", Snackbar.LENGTH_SHORT);
                    }
                    break;
            }
        }
    };

    public void PrepareProduct(boolean full){
        float porcion = this.product.getPorcion();
        v1.app.com.codenutrient.POJO.Product send_product= new v1.app.com.codenutrient.POJO.Product();
        send_product.setCode(product.getCode());
        if (!full){
            try {
                porcion = Float.parseFloat(spinner.getSelectedItem().toString());
            }catch (NumberFormatException e){
                Snackbar.make(rootView.findViewById(R.id.productCoordinator), "No se ha podido enviar la información. Intentalo de nuevo más tarde.", Snackbar.LENGTH_SHORT);
                return;
            }
        }
        send_product.setCalorias(product.getCalorias() * porcion);
        send_product.setCantidad((product.getCantidad() / product.getPorcion()) * porcion);
        send_product.setPorcion(porcion);
        if (product.getNutrients() != null){
            ArrayList<Nutrient> nutrients = product.getNutrients();
            ArrayList<Nutrient> send_nutrients = new ArrayList<>();
            for (Nutrient nutrient: nutrients) {
                nutrient.setCantidad(nutrient.getCantidad() * porcion);
                send_nutrients.add(nutrient);
            }
            send_product.setNutrients(send_nutrients);
        }else{
            Snackbar.make(rootView.findViewById(R.id.productCoordinator), "No se ha podido enviar la información. Intentalo de nuevo más tarde.", Snackbar.LENGTH_SHORT);
        }
    }

    public void setData(v1.app.com.codenutrient.POJO.Product product){
        this.product = product;
        if (product.getPorcion() != 1){
            ArrayList<String> portions = new ArrayList<>();
            for (int i = 1; i < product.getPorcion(); i++){
                portions.add("" + i);
            }
            String [] aux_portions = new String[portions.size()];
            aux_portions = portions.toArray(aux_portions);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, aux_portions);
            spinner.setAdapter(adapter);
        }else{
            porciones.setEnabled(false);
        }
        tittle.setVisibility(View.VISIBLE);
        portions.setVisibility(View.VISIBLE);
        porciones.setVisibility(View.VISIBLE);
        full.setVisibility(View.VISIBLE);
        send.setVisibility(View.VISIBLE);
    }

    private class MyTask extends AsyncTask<v1.app.com.codenutrient.POJO.Product, String, v1.app.com.codenutrient.POJO.Product> {

        @Override
        protected v1.app.com.codenutrient.POJO.Product doInBackground(v1.app.com.codenutrient.POJO.Product[] params) {
            return null;
        }
    }

}

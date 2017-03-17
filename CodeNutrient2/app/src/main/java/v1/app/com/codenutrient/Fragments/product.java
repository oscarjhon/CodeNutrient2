package v1.app.com.codenutrient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_product, container, false);

        porciones = (RadioButton) rootView.findViewById(R.id.porciones);
        full = (RadioButton) rootView.findViewById(R.id.full);
        send = (Button) rootView.findViewById(R.id.send_product);
        spinner = (Spinner) rootView.findViewById(R.id.portion_spiner);

        linear = (LinearLayout) rootView.findViewById(R.id.linear_portions);

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
                    //check portions and calculate the values
                    break;
            }
        }
    };

    public void setData(v1.app.com.codenutrient.POJO.Product product){
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
    }



}

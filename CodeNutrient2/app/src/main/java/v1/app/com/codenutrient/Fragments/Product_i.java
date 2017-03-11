package v1.app.com.codenutrient.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.Constants;
import v1.app.com.codenutrient.POJO.Product;
import v1.app.com.codenutrient.R;

public class Product_i extends Fragment {
    public Button evaluar;
    public ImageView imageView;
    public TextView presentacion;
    public TextView product_calories;
    public TextView product_name;
    public TextView product_portions;
    public ProgressBar progressBar;
    public Button registrar;
    public View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.product_i, container, false);
        this.product_name = (TextView) this.rootView.findViewById(R.id.product_name);
        this.product_calories = (TextView) this.rootView.findViewById(R.id.product_calories);
        this.product_portions = (TextView) this.rootView.findViewById(R.id.product_portions);
        this.presentacion = (TextView) this.rootView.findViewById(R.id.presentation);
        this.imageView = (ImageView) this.rootView.findViewById(R.id.product_image);
        this.progressBar = (ProgressBar) this.rootView.findViewById(R.id.product_progress);
        this.evaluar = (Button) this.rootView.findViewById(R.id.Evaluate);
        this.registrar = (Button) this.rootView.findViewById(R.id.register);
        this.evaluar.setOnClickListener(listener);
        this.registrar.setOnClickListener(listener);
        return this.rootView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Evaluate:
                    Toast.makeText(getActivity().getApplicationContext(), "Te recomendamos consumir este producto dado su alto contenido de calcio", Toast.LENGTH_SHORT).show();
                case R.id.register:
                    Toast.makeText(getActivity().getApplicationContext(), "Se ha registrado el producto como consumido", Toast.LENGTH_SHORT).show();
                default:
            }
        }
    };

    public void setData(Product product, Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        dataBaseHelper.openDataBaseReadWrite();
        Cursor cursor = dataBaseHelper.fetchMeasure(product.getMeasure_id());
        if (cursor != null && cursor.moveToFirst()) {
            presentacion.setText("Presentación: " + product.getCantidad() + cursor.getString(cursor.getColumnIndex("abreviacion")));
        }
        product_name.setText(product.getNombre());
        product_calories.setText("Calorias: " + product.getCalorias());
        product_portions.setText("Porciones: " + product.getPorcion());
        Picasso.with(context).load(Constants.ip_addr + product.getImageURL()).into(this.imageView);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.INVISIBLE);
        product_name.setVisibility(View.VISIBLE);
        product_calories.setVisibility(View.VISIBLE);
        product_portions.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        presentacion.setVisibility(View.VISIBLE);
        evaluar.setVisibility(View.VISIBLE);
        registrar.setVisibility(View.VISIBLE);
    }
}

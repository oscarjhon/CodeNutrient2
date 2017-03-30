package v1.app.com.codenutrient.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import v1.app.com.codenutrient.POJO.Product;
import v1.app.com.codenutrient.R;


/**
 * Created by Arturo on 30/03/2017.
 * This code was made for the project CodeNutrient.
 */
public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ProductViewHolder> {

    private ArrayList<Product> my_data;
    private Context context;

    public MyProductAdapter(ArrayList<Product> my_data, Context context) {
        this.my_data = my_data;
        this.context = context;
    }

    @Override
    public MyProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return  new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyProductAdapter.ProductViewHolder holder, int position) {
        holder.product_name.setText(my_data.get(position).getNombre());
        holder.product_portions.setText("Porciones consumidas: " + my_data.get(position).getPorcion());
        holder.product_calories.setText("Calorias consumidas: " + my_data.get(position).getCalorias());
        holder.product_content.setText("Contenido: " + my_data.get(position).getCantidad());
        holder.product_date.setText("Consumido el:" + my_data.get(position).getDate());
        Picasso.with(context)
                .load(my_data.get(position).getImageURL())
                .into(holder.product_image);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView product_name, product_portions, product_calories, product_content, product_date;
        public ImageView product_image;
        public ProductViewHolder(View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.item_product_name);
            product_portions = (TextView) itemView.findViewById(R.id.portions_item_product);
            product_calories = (TextView) itemView.findViewById(R.id.calories_item_product);
            product_content = (TextView) itemView.findViewById(R.id.quantity_item_product);
            product_date = (TextView) itemView.findViewById(R.id.date_item_product);

            product_image = (ImageView) itemView.findViewById(R.id.item_product_image);

        }
    }
}

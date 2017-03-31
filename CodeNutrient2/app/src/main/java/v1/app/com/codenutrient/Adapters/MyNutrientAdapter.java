package v1.app.com.codenutrient.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import v1.app.com.codenutrient.POJO.Item_nutrient;
import v1.app.com.codenutrient.R;

/**
 * Created by Arturo on 30/03/2017.
 * This code was made for the project CodeNutrient.
 */
public class MyNutrientAdapter extends RecyclerView.Adapter<MyNutrientAdapter.NutrientViewHolder> {

    private ArrayList<Item_nutrient> my_data;

    public MyNutrientAdapter (ArrayList<Item_nutrient> my_data){
        this.my_data = my_data;
    }

    @Override
    public NutrientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nutrient, parent, false);
        return new NutrientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NutrientViewHolder holder, int position) {
        holder.nutrient_name.setText(my_data.get(position).getNutrient_name());
        holder.nutrient_consumed.setText("Cantidad consumida: " + my_data.get(position).getNutrient_consumed() + my_data.get(position).getNutrient_measure_name());
        holder.nutrient_recomended.setText("Cantidad recomendada: " + my_data.get(position).getNutrient_recomended() + my_data.get(position).getNutrient_measure_name());
        holder.nutrient_resume.setText(my_data.get(position).getNutrient_resume());
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public static class NutrientViewHolder extends RecyclerView.ViewHolder{
        public TextView nutrient_name, nutrient_consumed, nutrient_recomended, nutrient_resume;
        public NutrientViewHolder(View itemView) {
            super(itemView);
            nutrient_name = (TextView) itemView.findViewById(R.id.item_nutrient_name);
            nutrient_consumed = (TextView) itemView.findViewById(R.id.quantity_item_nutrient_consumed);
            nutrient_recomended = (TextView) itemView.findViewById(R.id.quantity_item_nutrient_recomended);
            nutrient_resume = (TextView) itemView.findViewById(R.id.item_nutrient_resume);

        }
    }
}

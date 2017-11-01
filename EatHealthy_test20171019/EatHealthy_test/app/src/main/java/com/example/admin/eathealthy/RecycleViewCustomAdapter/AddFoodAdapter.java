package com.example.admin.eathealthy.RecycleViewCustomAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.eathealthy.CustomItem.Ntrition;
import com.example.admin.eathealthy.R;

import java.util.ArrayList;

/**
 * Created by shackoon on 2017/7/20.
 */

public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.ViewHolder> {
    private ArrayList<Ntrition> arraylist = new ArrayList<>();

    public AddFoodAdapter(ArrayList<Ntrition> data) {
        this.arraylist = data;
    }

    @Override
    public AddFoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemn_nutrition, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ntrition ntrition = arraylist.get(position);
        holder.nutrition_name.setText(ntrition.getName());
        //holder.quantity.setText(Double.toString(ntrition.getQuantity()));
        holder.quantity.setText(ntrition.getFoodQuantityUnit());
        holder.food_copy.setText(Double.toString(ntrition.getQuantity()) + " 份");

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nutrition_name, quantity, food_copy;

        public ViewHolder(View v) {
            super(v);
            nutrition_name = (TextView) v.findViewById(R.id.textView_nutrition);
            quantity = (TextView) v.findViewById(R.id.textView_quantity);
            food_copy = (TextView) v.findViewById(R.id.textView_copy);


        }

    }
}

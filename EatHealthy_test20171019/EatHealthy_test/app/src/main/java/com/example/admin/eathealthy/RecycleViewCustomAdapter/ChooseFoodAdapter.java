package com.example.admin.eathealthy.RecycleViewCustomAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.eathealthy.CustomItem.Ntrition;
import com.example.admin.eathealthy.CustomItem.Ntrition_choose;
import com.example.admin.eathealthy.R;

import java.util.ArrayList;

/**
 * Created by shackoon on 2017/7/20.
 */

public class ChooseFoodAdapter extends RecyclerView.Adapter<ChooseFoodAdapter.ViewHolder> {
    private ArrayList<Ntrition_choose> arraylist = new ArrayList<>();

    public ChooseFoodAdapter(ArrayList<Ntrition_choose> data) {
        this.arraylist = data;
    }

    @Override
    public ChooseFoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemn_choose_food, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ntrition_choose ntrition = arraylist.get(position);
        holder.nutrition_name.setText(ntrition.getName());
        holder.food_copy.setText(ntrition.getFoodQuantityUnit());
        holder.k_cal.setText(ntrition.getK_cal());

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nutrition_name, k_cal, food_copy;

        public ViewHolder(View v) {
            super(v);
            nutrition_name = (TextView) v.findViewById(R.id.textView_nutrition);
            k_cal = (TextView) v.findViewById(R.id.textView_quantity);
            food_copy = (TextView) v.findViewById(R.id.textView_copy);


        }

    }
}

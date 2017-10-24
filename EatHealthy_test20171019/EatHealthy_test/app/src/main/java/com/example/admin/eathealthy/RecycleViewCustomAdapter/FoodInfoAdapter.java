package com.example.admin.eathealthy.RecycleViewCustomAdapter;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.eathealthy.CustomItem.FoodInfo;
import com.example.admin.eathealthy.R;

import java.util.ArrayList;

/**
 * Created by shackoon on 2017/7/20.
 */

public class FoodInfoAdapter extends RecyclerView.Adapter<FoodInfoAdapter.ViewHolder> {
    private ArrayList<FoodInfo> arraylist = new ArrayList<>();


    public FoodInfoAdapter(ArrayList<FoodInfo> data) {
        this.arraylist = data;
    }

    @Override
    public FoodInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_info, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodInfo foodInfo = arraylist.get(position);
        switch (position) {
            case 0:
                holder.column_name.setSingleLine();
                holder.column_name.setTextSize(18);
                holder.linearLayout.setClickable(false);
                break;
            case 1:
            case 2:
                //改顏色
                holder.value.setTextColor(ContextCompat.getColor(holder.value.getContext(), R.color.dodgerblue));
                break;
            case 3:
                holder.column_name.setTextSize(15);
                holder.linearLayout.setClickable(false);
                break;
            default:
                break;
        }
        holder.column_name.setText(foodInfo.getColumnName());
        holder.value.setText(foodInfo.getValue());

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView column_name, value;
        private ConstraintLayout linearLayout;

        public ViewHolder(View v) {
            super(v);
            column_name = (TextView) v.findViewById(R.id.tv_foodinfo_name);
            value = (TextView) v.findViewById(R.id.tv_foodinfo_value);
            linearLayout = v.findViewById(R.id.linear_test);
        }

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

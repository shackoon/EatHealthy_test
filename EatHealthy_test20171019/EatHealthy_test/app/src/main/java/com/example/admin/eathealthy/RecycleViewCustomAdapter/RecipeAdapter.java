package com.example.admin.eathealthy.RecycleViewCustomAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.eathealthy.CustomItem.RecipeItem;
import com.example.admin.eathealthy.News.CardViewItem;
import com.example.admin.eathealthy.R;

import java.util.ArrayList;

/**
 * Created by BinLine on 2017/10/2.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<RecipeItem> arraylist = new ArrayList<>();

    public RecipeAdapter(ArrayList<RecipeItem> data) {
        this.arraylist = data;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_food;
        public ImageView img_food;
        public ImageView img_yn;

        public ViewHolder(View v) {
            super(v);
            tv_food = (TextView) v.findViewById(R.id.tv_recipe_name);
            img_food = (ImageView) v.findViewById(R.id.img_recipe_food);
            img_yn = (ImageView) v.findViewById(R.id.img_recipe_yn);

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecipeItem recipeItem = arraylist.get(position);
        holder.tv_food.setText(recipeItem.getFoodname());
        holder.img_food.setImageResource(recipeItem.getPicfood());
        holder.img_yn.setImageResource(recipeItem.getPicyn());

    }


    @Override
    public int getItemCount() {
        return arraylist.size();
    }
}
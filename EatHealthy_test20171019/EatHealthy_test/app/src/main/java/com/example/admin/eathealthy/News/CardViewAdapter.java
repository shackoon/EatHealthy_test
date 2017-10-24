package com.example.admin.eathealthy.News;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.eathealthy.R;

import java.util.ArrayList;

/**
 * Created by BinLine on 2017/10/2.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    private ArrayList<CardViewItem> arraylist = new ArrayList<>();

    public CardViewAdapter(ArrayList<CardViewItem> data) {
        this.arraylist = data;
    }

    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
            imageView = (ImageView) v.findViewById(R.id.img);


        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardViewItem cardViewItem = arraylist.get(position);
        holder.mTextView.setText(cardViewItem.GetWebname());
        holder.imageView.setImageResource(cardViewItem.GetPicture());

    }


    @Override
    public int getItemCount() {
        return arraylist.size();
    }
}
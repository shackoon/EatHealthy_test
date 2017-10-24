package com.example.admin.eathealthy.RecycleViewCustomAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.eathealthy.CustomItem.PersonInfo;
import com.example.admin.eathealthy.R;

import java.util.ArrayList;

/**
 * Created by shackoon on 2017/7/20.
 */

public class PersonInfoAdapter extends RecyclerView.Adapter<PersonInfoAdapter.ViewHolder> {
    private ArrayList<PersonInfo> arraylist = new ArrayList<>();


    public PersonInfoAdapter(ArrayList<PersonInfo> data) {
        this.arraylist = data;
    }

    @Override
    public PersonInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_info, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PersonInfo personInfo = arraylist.get(position);
        holder.column_name.setText(personInfo.getColumnName());
        holder.value.setText(personInfo.getValue());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView column_name, value;

        public ViewHolder(View v) {
            super(v);
            column_name = v.findViewById(R.id.tv_column_name);
            value = v.findViewById(R.id.tv_value);
        }

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

package com.example.admin.eathealthy.RecycleViewCustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.eathealthy.Camera.Trans;
import com.example.admin.eathealthy.CustomItem.PersonInfo;
import com.example.admin.eathealthy.CustomItem.UserPicture;
import com.example.admin.eathealthy.R;

import java.util.ArrayList;

import static com.example.admin.eathealthy.R.id.imageView;

/**
 * Created by shackoon on 2017/7/20.
 */

public class UserPictureAdapter extends RecyclerView.Adapter<UserPictureAdapter.ViewHolder> {
    private ArrayList<UserPicture> arraylist = new ArrayList<>();


    public UserPictureAdapter(ArrayList<UserPicture> data) {
        this.arraylist = data;
    }

    @Override
    public UserPictureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        UserPicture userPicture = arraylist.get(position);

        byte[] foodImage = userPicture.getImage();

        Glide.with(holder.imageView.getContext()).load(foodImage).centerCrop().into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != 0) {
                    Intent intent = new Intent((Activity) holder.imageView.getContext(), Trans.class);
                    intent.putExtra("Account", arraylist.get(position).getAccount());
                    intent.putExtra("pic_id", arraylist.get(position).getKey_id());
                    intent.putExtra("diningTime", arraylist.get(position).getDiningTime());
                    intent.putExtra("date", arraylist.get(position).getDate());
                    intent.putExtra("position", position - 1);//因為前面多了一張新增圖示，所以需-1才是正確位置
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) holder.imageView.getContext(), holder.imageView, "trnas_image");
                    holder.imageView.getContext().startActivity(intent, options.toBundle());
                    //Toast.makeText(holder.imageView.getContext(), "pic", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.user_picture);
        }

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

package com.example.admin.eathealthy.Camera;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.admin.eathealthy.CustomItem.UserPicture;
import com.example.admin.eathealthy.Data_table.User_Picture_Data;
import com.example.admin.eathealthy.R;

import java.util.ArrayList;

/**
 * Created by User on 2017/10/24.
 */

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<UserPicture> arrayPic = new ArrayList<>();
    private Context mContext;

    public MyPagerAdapter(ArrayList<UserPicture> arrayPic, Context context) {
        this.arrayPic = arrayPic;
        mContext = context;
    }

    @Override
    public int getCount() {
        return arrayPic.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.content_trans, container, false);
        ImageView imageView = view.findViewById(R.id.img_for_trans);
//        TextView tv_page = view.findViewById(R.id.tv_page);
//        tv_page.setText(position + 1 + "/" + arrayPic.size());
        Glide.with(imageView.getContext()).load(arrayPic.get(position).getImage()).fitCenter().into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                //图片加载完成的回调中，启动过渡动画
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

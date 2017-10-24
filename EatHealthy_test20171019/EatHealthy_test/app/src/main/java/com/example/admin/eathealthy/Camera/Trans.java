package com.example.admin.eathealthy.Camera;

import android.accounts.Account;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.admin.eathealthy.CustomItem.UserPicture;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.Data_table.User_Picture_Data;
import com.example.admin.eathealthy.R;

import java.util.ArrayList;

public class Trans extends AppCompatActivity {
    private Bundle bundle;
    private byte[] img_byte;
    private String account;
    private String pic_id;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private ArrayList<UserPicture> userPictures = new ArrayList<>();
    private UserPicture picData;

    private String diningTime;
    private String date;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // 延迟共享动画的执行
        postponeEnterTransition();
        //ImageView imageView = (ImageView) findViewById(R.id.img_for_trans);
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();
        //img_byte = getIntent().getByteArrayExtra("pic_byte");
        account = getIntent().getStringExtra("Account");
        pic_id = getIntent().getStringExtra("pic_id");
        diningTime = getIntent().getStringExtra("diningTime");
        date = getIntent().getStringExtra("date");
        position = getIntent().getIntExtra("position", 0);
        initViews();

    }


    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        userPictures.clear();
        cursor = db.query(User_Picture_Data.TABLE_NAME + account, null, User_Picture_Data.DINING_TIME_COLUMN + "=?" + " AND " + User_Food_Data.DATE_COLUMN + "=?", new String[]{diningTime, date}, null, null, null);
        while (cursor.moveToNext()) {
            byte[] pic = cursor.getBlob(cursor.getColumnIndex(User_Picture_Data.PICTURE_COLUMN));
            String key_id = cursor.getString(cursor.getColumnIndex(User_Picture_Data.KEY_ID));
            picData = new UserPicture(pic, key_id, account, diningTime, date);
            userPictures.add(picData);
        }
        mPagerAdapter = new MyPagerAdapter(userPictures, this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(position);
        supportStartPostponedEnterTransition();
    }


    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

}

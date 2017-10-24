package com.example.admin.eathealthy.Camera;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Picture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.eathealthy.CustomItem.Ntrition;
import com.example.admin.eathealthy.CustomItem.UserPicture;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.Data_table.User_Picture_Data;
import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.RecycleViewCustomAdapter.AddFoodAdapter;
import com.example.admin.eathealthy.RecycleViewCustomAdapter.UserPictureAdapter;
import com.example.admin.eathealthy.recyevent.EmptyRecyclerView;

import java.util.ArrayList;

public class PictureList extends AppCompatActivity {

    private static UserPictureAdapter myAdapter;
    protected static ArrayList<UserPicture> Dataset = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();
        cursor = db.query(User_Picture_Data.TABLE_NAME + "wowo", null, null, null, null, null, null);
        Dataset.clear();
        while (cursor.moveToNext()) {
            Dataset.add(new UserPicture(cursor.getBlob(cursor.getColumnIndex(User_Picture_Data.PICTURE_COLUMN)), cursor.getString(cursor.getColumnIndex(User_Picture_Data.KEY_ID)), "wowo","",""));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_picture);
        myAdapter = new UserPictureAdapter(Dataset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //設定此 layoutManager 為 linearlayout (類似ListView)
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); //設定此 layoutManager 為垂直堆疊
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);

    }
}

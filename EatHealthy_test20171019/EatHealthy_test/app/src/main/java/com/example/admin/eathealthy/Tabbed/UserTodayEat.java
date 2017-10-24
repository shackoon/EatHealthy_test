package com.example.admin.eathealthy.Tabbed;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.admin.eathealthy.CustomItem.Ntrition;
import com.example.admin.eathealthy.CustomItem.UserPicture;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.Data_table.User_Picture_Data;
import com.example.admin.eathealthy.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by User on 2017/10/22.
 */

public class UserTodayEat {
    private String diningTime;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private String account;
    private Context context;

    public UserTodayEat(String account, String diningTime, Context context) {
        this.diningTime = diningTime;
        this.account = account;
        this.context = context;
        helper = new MyDBHelper(context);
        db = helper.getWritableDatabase();
    }

    public ArrayList<Ntrition> getFoodListData(String date) {
        Ntrition Data;
        Cursor cursor;
        ArrayList<Ntrition> arrayDateset = new ArrayList<>();
        cursor = db.query(User_Food_Data.TABLE_NAME + account, null, "DiningTime=?" + "And Date=?", new String[]{diningTime, date}, null, null, null);

        while (cursor.moveToNext()) {
            String food_info = String.valueOf(cursor.getDouble(cursor.getColumnIndex(User_Food_Data.FOOD_QUANTITY_COLUMN)) * cursor.getDouble(cursor.getColumnIndex(User_Food_Data.PERUNIT_COLUMN))) + cursor.getString(cursor.getColumnIndex(User_Food_Data.UNIT_COLUMN));
            String key_id = cursor.getString(cursor.getColumnIndex(User_Food_Data.KEY_ID));
            Data = new Ntrition(cursor.getString(cursor.getColumnIndex(User_Food_Data.FOOD_NAME_COLUMN)), cursor.getDouble(cursor.getColumnIndex(User_Food_Data.FOOD_QUANTITY_COLUMN)), food_info, key_id);
            arrayDateset.add(Data);
        }
        return arrayDateset;
    }


    public ArrayList<UserPicture> getPictureData(String date) {
        UserPicture picData;
        Cursor cursor;
        ArrayList<UserPicture> arrayDateset = new ArrayList<>();
        cursor = db.query(User_Picture_Data.TABLE_NAME + account, null, User_Picture_Data.DINING_TIME_COLUMN + "=?" + " AND " + User_Food_Data.DATE_COLUMN + "=?", new String[]{diningTime, date}, null, null, null);
        arrayDateset.add(new UserPicture(image_add_toByte(), "add", account, "add", "add"));
        while (cursor.moveToNext()) {
            byte[] pic = cursor.getBlob(cursor.getColumnIndex(User_Picture_Data.PICTURE_COLUMN));
            String key_id = cursor.getString(cursor.getColumnIndex(User_Picture_Data.KEY_ID));
            picData = new UserPicture(pic, key_id, account, diningTime, date);
            arrayDateset.add(picData);
        }
        return arrayDateset;
    }

    private byte[] image_add_toByte() {
        Resources res = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.add_image);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }





}

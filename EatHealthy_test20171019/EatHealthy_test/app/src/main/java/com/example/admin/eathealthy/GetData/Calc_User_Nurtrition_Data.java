package com.example.admin.eathealthy.GetData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Nutritional_Goals;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 2017/10/2.
 */

public class Calc_User_Nurtrition_Data {
    private Cursor cursor;
    private String account;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Context context;
    private SimpleDateFormat dateFormat;
    private Date curDate;
    private String nowTime;
    private boolean isEmpty;
    NumberFormat numberFormat = new DecimalFormat("#0");

    public Calc_User_Nurtrition_Data(String account, String nowTime, Context context) {
        this.account = account;
        this.context = context;
        this.nowTime = nowTime;
        helper = new MyDBHelper(context);
        db = helper.getWritableDatabase();
        //dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        //nowTime = dateFormat.format(curDate);
        cursor = db.query(User_Nutritional_Goals.TABLE_NAME + account, null, User_Nutritional_Goals.DATE_COLUMN + "=?", new String[]{nowTime}, null, null, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            isEmpty = false;
        } else {
            isEmpty = true;
        }

    }

    public String get_HeatPercent() {
        return String.valueOf(numberFormat.format(get_Now_Heat() / get_T_Heat() * 100));
    }

    public String get_ProteinPercent() {
        return (!isEmpty  ? numberFormat.format((double) get_Now_Protein() / (double) get_T_Protein() * 100):"0");
    }

    public String get_FatPercent() {
        return (!isEmpty ?  numberFormat.format((double) get_Now_Fat() / (double) get_T_Fat() * 100):"0");
    }

    public String get_CarbohydratePercent() {
        return (!isEmpty ? numberFormat.format((double) get_Now_Carbohydrate() / (double) get_T_Carbohydrate() * 100):"0");
    }

    public String get_NaPercent() {
        return (!isEmpty ?  numberFormat.format((double) get_Now_Na() / (double) get_T_Na() * 100):"0");
    }

    public double get_T_Heat() {
        return (!isEmpty ? cursor.getDouble(cursor.getColumnIndex(User_Nutritional_Goals.TARGET_HEAT_COLUMN)) : 0.00000001);
    }

    public int get_T_Protein() {
        return (!isEmpty ? cursor.getInt(cursor.getColumnIndex(User_Nutritional_Goals.TARGET_PROTEIN_COLUMN)) : 0);
    }

    public int get_T_Fat() {
        return (!isEmpty ? cursor.getInt(cursor.getColumnIndex(User_Nutritional_Goals.TARGET_FAT_COLUMN)) : 0);
    }

    public int get_T_Carbohydrate() {
        return (!isEmpty ? cursor.getInt(cursor.getColumnIndex(User_Nutritional_Goals.TARGET_CARBOHYDRATE_COLUMN)) : 0);
    }

    public int get_T_Na() {
        return (!isEmpty ? cursor.getInt(cursor.getColumnIndex(User_Nutritional_Goals.TARGET_Na_COLUMN)) : 0);
    }


    public double get_Now_Heat() {
        return (isEmpty == false ? cursor.getDouble(cursor.getColumnIndex(User_Nutritional_Goals.NOW_HEAT_COLUMN)) : 0);
    }

    public int get_Now_Protein() {
        return (!isEmpty ? cursor.getInt(cursor.getColumnIndex(User_Nutritional_Goals.NOW_PROTEIN_COLUMN)) : 0);
    }

    public int get_Now_Fat() {
        return (!isEmpty ? cursor.getInt(cursor.getColumnIndex(User_Nutritional_Goals.NOW_FAT_COLUMN)) : 0);
    }

    public int get_Now_Carbohydrate() {
        return (!isEmpty ? cursor.getInt(cursor.getColumnIndex(User_Nutritional_Goals.NOW_CARBOHYDRATE_COLUMN)) : 0);
    }

    public int get_Now_Na() {
        return (!isEmpty? cursor.getInt(cursor.getColumnIndex(User_Nutritional_Goals.NOW_NA_COLUMN)) : 0);
    }

}

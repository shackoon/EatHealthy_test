package com.example.admin.eathealthy.Update_Data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.eathealthy.GetData.GetNutrition;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.Item_User_Data;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.Data_table.User_Nutritional_Goals;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Update_Today_Nutrition {
    private String userName;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Context context;
    private String select_time;
    private Cursor userfood;
    private Cursor foodtable;

    //更新"今日已達成"的營養目標
    public Update_Today_Nutrition(String userName, String select_time, Context context) {
        this.userName = userName;
        this.select_time = select_time;
        this.context = context;
        helper = new MyDBHelper(context);
        db = helper.getWritableDatabase();
    }
    //更新"今日目標"營養值
    public Update_Today_Nutrition(String userName, Context context) {
        this.userName = userName;
        this.context = context;
        helper = new MyDBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void update() {
        double total_Heat = 0.0;
        double total_Protein = 0.0;
        double total_Fat = 0.0;
        double total_Carbohydrate = 0.0;
        double total_Na = 0.0;

        userfood = db.query(User_Food_Data.TABLE_NAME + userName, null, User_Food_Data.DATE_COLUMN + "=?", new String[]{select_time}, null, null, null);
        userfood.moveToFirst();
        if (userfood.getCount() != 0) {
            do {
                foodtable = db.query("food", null, "Name=?", new String[]{userfood.getString(userfood.getColumnIndex(User_Food_Data.FOOD_NAME_COLUMN))}, null, null, null);
                foodtable.moveToFirst();
                total_Heat += foodtable.getDouble(foodtable.getColumnIndex("Heat")) * userfood.getDouble(userfood.getColumnIndex(User_Food_Data.FOOD_QUANTITY_COLUMN));
                total_Protein += foodtable.getDouble(foodtable.getColumnIndex("Protein")) * userfood.getDouble(userfood.getColumnIndex(User_Food_Data.FOOD_QUANTITY_COLUMN));
                total_Fat += foodtable.getDouble(foodtable.getColumnIndex("Fat")) * userfood.getDouble(userfood.getColumnIndex(User_Food_Data.FOOD_QUANTITY_COLUMN));
                total_Carbohydrate += foodtable.getDouble(foodtable.getColumnIndex("Carbohydrates")) * userfood.getDouble(userfood.getColumnIndex(User_Food_Data.FOOD_QUANTITY_COLUMN));
                total_Na += foodtable.getDouble(foodtable.getColumnIndex("Na")) * userfood.getDouble(userfood.getColumnIndex(User_Food_Data.FOOD_QUANTITY_COLUMN));
                //Toast.makeText(context, "總熱量=" + String.valueOf(total_Heat), Toast.LENGTH_SHORT).show();
                userfood.moveToNext();
            } while (userfood.isAfterLast() != true);

        }


        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(User_Nutritional_Goals.NOW_HEAT_COLUMN, total_Heat);
        cv.put(User_Nutritional_Goals.NOW_PROTEIN_COLUMN, total_Protein);
        cv.put(User_Nutritional_Goals.NOW_FAT_COLUMN, total_Fat);
        cv.put(User_Nutritional_Goals.NOW_CARBOHYDRATE_COLUMN, total_Carbohydrate);
        cv.put(User_Nutritional_Goals.NOW_NA_COLUMN, total_Na);
        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        db.update(User_Nutritional_Goals.TABLE_NAME + userName, cv, User_Nutritional_Goals.DATE_COLUMN + "=?", new String[]{select_time});


    }

    public void change_Target_Nurtition() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        String nowTime = formatter.format(curDate);
        Cursor cursor;
        GetNutrition getNutrition;
        cursor = db.query(Item_User_Data.TABLE_NAME, null, "Account=?", new String[]{userName}, null, null, null);

        //將cursor丟給GetNutrition處理，並輸出今日目標熱量、蛋白質、只防、碳水化合物、鈉。
        getNutrition = new GetNutrition(cursor);

        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(User_Nutritional_Goals.TARGET_HEAT_COLUMN, getNutrition.getHeat());
        cv.put(User_Nutritional_Goals.TARGET_PROTEIN_COLUMN, getNutrition.getProtein());
        cv.put(User_Nutritional_Goals.TARGET_FAT_COLUMN, getNutrition.getFat());
        cv.put(User_Nutritional_Goals.TARGET_CARBOHYDRATE_COLUMN, getNutrition.getCarbohydrate());
        cv.put(User_Nutritional_Goals.TARGET_Na_COLUMN, getNutrition.getNa());
        db.update(User_Nutritional_Goals.TABLE_NAME + userName, cv, User_Nutritional_Goals.DATE_COLUMN + "=?", new String[]{nowTime});
        //Toast.makeText(context, "更改...", Toast.LENGTH_SHORT).show();

    }


}

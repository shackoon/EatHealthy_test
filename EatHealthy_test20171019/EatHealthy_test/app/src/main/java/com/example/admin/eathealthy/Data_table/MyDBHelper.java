package com.example.admin.eathealthy.Data_table;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper{

    // 資料庫名稱
    public static final String DATABASE_NAME = "database.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;


    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME,null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Item_User_Data.User_Basic_Data_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 刪除原有的表格
        //db.execSQL("DROP TABLE IF EXISTS " + Item_User_Data.TABLE_NAME);
        // 呼叫onCreate建立新版的表格
        if (newVersion > oldVersion) {
            db.beginTransaction();//建立交易

            boolean success = false;//判斷參數

            //由之前不用的版本，可做不同的動作
            switch (oldVersion) {
                case 1:
                    db.execSQL("ALTER TABLE "+Item_User_Data.TABLE_NAME+" ADD COLUMN Login TEXT DEFAULT n");
                    oldVersion++;
                    success = true;
                    break;
            }

            if (success) {
                db.setTransactionSuccessful();//正確交易才成功
            }
            db.endTransaction();
        }
        else {
            onCreate(db);
        }
    }


}

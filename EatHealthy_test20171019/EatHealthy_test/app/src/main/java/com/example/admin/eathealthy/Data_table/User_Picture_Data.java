package com.example.admin.eathealthy.Data_table;

/**
 * Created by User on 2017/10/19.
 */

public class User_Picture_Data {
    // 表格名稱
    public static final String TABLE_NAME = "User_Picture_";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String ACCOUNT_COLUMN = "Account";
    public static final String PICTURE_COLUMN = "Picture";
    public static final String TITLE_COLUMN = "Title";
    public static final String DESCRIBE_COLUMN = "Describe";
    public static final String DINING_TIME_COLUMN = "DiningTime";
    public static final String DATE_COLUMN = "Date";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String User_Picture_TABLE = " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ACCOUNT_COLUMN + " TEXT NOT NULL, " +
            PICTURE_COLUMN + " BLOB NOT NULL, " +
            TITLE_COLUMN + " TEXT NOT NULL, " +
            DESCRIBE_COLUMN + " TEXT NOT NULL, " +
            DINING_TIME_COLUMN + " TEXT NOT NULL, " +
            DATE_COLUMN + " TIME NOT NULL)";
}

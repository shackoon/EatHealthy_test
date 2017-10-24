package com.example.admin.eathealthy.Data_table;


public class User_Food_Data {
    // 表格名稱
    public static final String TABLE_NAME = "User_Food_Data_Table_";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String USER_NAME_COLUMN = "UserName";
    public static final String FOOD_NAME_COLUMN = "Food_Name";
    public static final String FOOD_QUANTITY_COLUMN = "Quantity";
    public static final String PERUNIT_COLUMN = "Perunit";
    public static final String UNIT_COLUMN = "Unit";
    public static final String DINING_TIME_COLUMN = "DiningTime";
    public static final String DATE_COLUMN = "Date";


    // 使用上面宣告的變數建立表格的SQL指令
    public static final String User_Food_Data_TABLE = " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME_COLUMN + " TEXT NOT NULL, " +
            FOOD_NAME_COLUMN + " TEXT NOT NULL, " +
            FOOD_QUANTITY_COLUMN + " REAL NOT NULL, " +
            PERUNIT_COLUMN + " REAL NOT NULL, " +
            UNIT_COLUMN + " TEXT NOT NULL, " +
            DINING_TIME_COLUMN + " TEXT NOT NULL, " +
            DATE_COLUMN + " TIME NOT NULL)";



}

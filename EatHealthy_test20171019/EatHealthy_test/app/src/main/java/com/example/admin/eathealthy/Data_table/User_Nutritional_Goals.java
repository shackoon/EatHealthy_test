package com.example.admin.eathealthy.Data_table;


public class User_Nutritional_Goals {
    // 表格名稱
    public static final String TABLE_NAME = "Nutritional_Goals_";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String ACCOUNT_COLUMN = "Account";
    public static final String TARGET_HEAT_COLUMN = "T_Heat";
    public static final String TARGET_PROTEIN_COLUMN = "T_Protein";
    public static final String TARGET_FAT_COLUMN = "T_Fat";
    public static final String TARGET_CARBOHYDRATE_COLUMN = "T_Carbohydrate";
    public static final String TARGET_Na_COLUMN = "T_Na";
    public static final String NOW_HEAT_COLUMN = "Now_Heat";
    public static final String NOW_PROTEIN_COLUMN = "Now_Protein";
    public static final String NOW_FAT_COLUMN = "Now_Fat";
    public static final String NOW_CARBOHYDRATE_COLUMN = "Now_Carbohydrate";
    public static final String NOW_NA_COLUMN = "Now_Na";
    public static final String DATE_COLUMN = "Date";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String User_Nutritional_Goals_TABLE = " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ACCOUNT_COLUMN + " TEXT NOT NULL, " +
            TARGET_HEAT_COLUMN + " REAL NOT NULL, " +
            TARGET_PROTEIN_COLUMN + " REAL NOT NULL, " +
            TARGET_FAT_COLUMN + " REAL NOT NULL, " +
            TARGET_CARBOHYDRATE_COLUMN + " REAL NOT NULL, " +
            TARGET_Na_COLUMN + " REAL NOT NULL, " +
            NOW_HEAT_COLUMN + " REAL NOT NULL, " +
            NOW_PROTEIN_COLUMN + " REAL NOT NULL, " +
            NOW_FAT_COLUMN + " REAL NOT NULL," +
            NOW_CARBOHYDRATE_COLUMN + " REAL  NOT NULL," +
            NOW_NA_COLUMN + " REAL NOT NULL," +
            DATE_COLUMN + " TIME NOT NULL)";
}

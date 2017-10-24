package com.example.admin.eathealthy.Data_table;



public class Item_User_Data {
    // 表格名稱
    public static final String TABLE_NAME = "User_Basic_Data_Table";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String ACCOUNT_COLUM = "Account";
    public static final String PASSWORD_COLUM = "Password";
    public static final String NAME_COLUMN = "Name";
    public static final String SEX_COLUMN = "Sex";
    public static final String HEIGHT_COLUMN = "Height";
    public static final String WEIGHT_COLUMN = "Weight";
    public static final String AGE_COLUMN = "Age";
    public static final String BMI_COLUMN = "BMI";
    public static final String OTHER_COLUMN = "Other";
    public static final String LOGINSTATE_COLUMN ="Login";
    public static final String FACTOR_COLUMN ="Factor";
    public static final String PROTEIN_RATIO_COLUMN ="Protein_ratio";
    public static final String FAT_RATIO_COLUMN ="Fat_ratio";
    public static final String CARBOHYDRATE_RATIO_COLUMN ="Carbohydrate_ratio";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String User_Basic_Data_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ACCOUNT_COLUM + " TEXT NOT NULL, " +
                    PASSWORD_COLUM + " TEXT NOT NULL, " +
                    NAME_COLUMN + " TEXT NOT NULL, " +
                    SEX_COLUMN + " TEXT NOT NULL, " +
                    HEIGHT_COLUMN + " REAL NOT NULL, " +
                    WEIGHT_COLUMN + " REAL NOT NULL, " +
                    AGE_COLUMN + " INTEGER NOT NULL, " +
                    BMI_COLUMN + " REAL NOT NULL, " +
                    OTHER_COLUMN + " TEXT," +
                    LOGINSTATE_COLUMN+" TEXT  NOT NULL,"+
                    FACTOR_COLUMN+" REAL NOT NULL,"+
                    PROTEIN_RATIO_COLUMN+" REAL NOT NULL,"+
                    FAT_RATIO_COLUMN+" REAL NOT NULL,"+
                    CARBOHYDRATE_RATIO_COLUMN+" REAL NOT NULL)";


}

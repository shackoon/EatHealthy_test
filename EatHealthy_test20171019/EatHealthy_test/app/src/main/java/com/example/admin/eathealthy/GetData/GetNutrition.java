package com.example.admin.eathealthy.GetData;

import android.database.Cursor;

import com.example.admin.eathealthy.Data_table.Item_User_Data;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by User on 2017/10/2.
 */

public class GetNutrition {
    private Cursor cursor;
    private double height;
    private double weight;
    private int age;
    private String sex;
    private double factor;
    private double protein_ratio;
    private double fat_ratio;
    private double carbohydrate_ratio;
    private double T_Heat;
    private double T_Protein;
    private double T_Fat;
    private double T_Carbohydrate;
    private double T_Na;
    NumberFormat formatter = new DecimalFormat("#0.0");

    public GetNutrition(Cursor userdata) {
        this.cursor = userdata;
        cursor.moveToFirst();
        this.height = cursor.getDouble(cursor.getColumnIndex(Item_User_Data.HEIGHT_COLUMN));
        this.weight = cursor.getDouble(cursor.getColumnIndex(Item_User_Data.WEIGHT_COLUMN));
        this.age = cursor.getInt(cursor.getColumnIndex(Item_User_Data.AGE_COLUMN));
        this.sex = cursor.getString(cursor.getColumnIndex(Item_User_Data.SEX_COLUMN));
        this.factor = cursor.getDouble(cursor.getColumnIndex(Item_User_Data.FACTOR_COLUMN));
        this.protein_ratio = cursor.getDouble(cursor.getColumnIndex(Item_User_Data.PROTEIN_RATIO_COLUMN));
        this.fat_ratio = cursor.getDouble(cursor.getColumnIndex(Item_User_Data.FAT_RATIO_COLUMN));
        this.carbohydrate_ratio = cursor.getDouble(cursor.getColumnIndex(Item_User_Data.CARBOHYDRATE_RATIO_COLUMN));
        setHeat();
        setNutrition();
    }

    public void setHeat() {
        if (sex.equals("0")) {
            T_Heat = Double.parseDouble(formatter.format(((13.7 * weight) + (5 * height) - (6.8 * age) + 66) * factor));
        } else if (sex.equals("1")) {
            T_Heat = Double.parseDouble(formatter.format(((9.6 * weight) + (1.8 * height) - (4.7 * age) + 655) * factor));
        }

    }

    public void setNutrition() {
        T_Protein = Double.parseDouble(formatter.format(T_Heat * protein_ratio / 4));
        T_Fat = Double.parseDouble(formatter.format(T_Heat * fat_ratio / 9));
        T_Carbohydrate = Double.parseDouble(formatter.format(T_Heat * carbohydrate_ratio / 4));
        T_Carbohydrate = Double.parseDouble(formatter.format(T_Heat * carbohydrate_ratio / 4));
        T_Na = 2400;
    }

    public Double getHeat() {
        return T_Heat;
    }

    public Double getProtein() {
        return T_Protein;
    }

    public Double getFat() {
        return T_Fat;
    }

    public Double getCarbohydrate() {
        return T_Carbohydrate;
    }

    public Double getNa() {
        return T_Na;
    }

    public Double getHeight() {
        return height;

    }
}



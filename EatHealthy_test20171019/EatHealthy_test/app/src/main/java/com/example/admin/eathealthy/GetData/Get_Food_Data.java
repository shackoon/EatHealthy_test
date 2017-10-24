package com.example.admin.eathealthy.GetData;

import android.database.Cursor;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by User on 2017/10/2.
 */

public class Get_Food_Data {
    private Cursor cursor;
    NumberFormat formatter = new DecimalFormat("#0.0");

    public Get_Food_Data(Cursor cursor) {
        this.cursor = cursor;
        this.cursor.moveToFirst();

    }

    public String getHeat() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Heat")))).toString();
    }

    public String getRHeat() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Rheat")))).toString();
    }

    public String getPortein() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Protein")))).toString();
    }

    public String getFat() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Fat")))).toString();
    }

    public String getCarbohydrates() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Carbohydrates")))).toString();
    }

    public String getFiber() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Fiber")))).toString();
    }

    public String getNa() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Na")))).toString();
    }

    public String getK() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("K")))).toString();
    }

    public String getCa() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Ca")))).toString();
    }

    public String getMg() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Mg")))).toString();
    }

    public String getFe() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Fe")))).toString();
    }

    public String getZn() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("Zn")))).toString();
    }

    public String getP() {
        return formatter.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex("P")))).toString();
    }

    public String getFoodName() {
        return cursor.getString(cursor.getColumnIndex("Name"));
    }

    public String getPerunit() {
        return cursor.getString(cursor.getColumnIndex("Perunit"));
    }

    public String getUnit() {
        return cursor.getString(cursor.getColumnIndex("Unit"));
    }

}

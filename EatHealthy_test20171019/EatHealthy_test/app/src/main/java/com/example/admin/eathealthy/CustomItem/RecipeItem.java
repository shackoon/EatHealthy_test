package com.example.admin.eathealthy.CustomItem;

/**
 * Created by User on 2017/11/2.
 */

public class RecipeItem {

    private int picfood;
    private String foodname;
    private int picyn;

    public RecipeItem(int picfood, String foodname, int picyn) {
        this.picfood = picfood;
        this.foodname = foodname;
        this.picyn = picyn;
    }


    public int getPicfood() {
        return picfood;
    }

    public void setPicfood(int picfood) {
        this.picfood = picfood;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getPicyn() {
        return picyn;
    }

    public void setPicyn(int picyn) {
        this.picyn = picyn;
    }


}

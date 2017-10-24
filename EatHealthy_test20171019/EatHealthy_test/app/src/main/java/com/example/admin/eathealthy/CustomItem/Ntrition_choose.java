package com.example.admin.eathealthy.CustomItem;

/**
 * Created by shackoon on 2017/7/20.
 */

public class Ntrition_choose {
    private String name;
    private String food_quantity_unit;
    private String k_cal;


//    public Ntrition(String name, double quantity, String food_quantity_unit) {
//        this.name = name;
//        this.quantity = quantity;
//        this.food_quantity_unit = food_quantity_unit;
//    }

    public Ntrition_choose(String name, String food_quantity_unit, String k_cal) {
        this.name = name;
        this.food_quantity_unit = food_quantity_unit;
        this.k_cal = k_cal;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setFoodQuantityUnit(String food_quantity_unit) {
        this.food_quantity_unit = food_quantity_unit;
    }

    public void setK_cal(String k_cal) {
        this.k_cal = k_cal;
    }


    public String getName() {
        return name;
    }


    public String getFoodQuantityUnit() {
        return food_quantity_unit;
    }


    public String getK_cal() {
        return k_cal;
    }


}

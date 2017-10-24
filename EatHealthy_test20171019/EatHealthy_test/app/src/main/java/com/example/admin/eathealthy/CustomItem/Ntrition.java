package com.example.admin.eathealthy.CustomItem;

/**
 * Created by shackoon on 2017/7/20.
 */

public class Ntrition {
    private String name;
    private double quantity;
    private String food_quantity_unit;
    private String key_id;


//    public Ntrition(String name, double quantity, String food_quantity_unit) {
//        this.name = name;
//        this.quantity = quantity;
//        this.food_quantity_unit = food_quantity_unit;
//    }

    public Ntrition(String name, double quantity, String food_quantity_unit, String key_id) {
        this.name = name;
        this.quantity = quantity;
        this.food_quantity_unit = food_quantity_unit;
        this.key_id = key_id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setFoodQuantityUnit(String food_quantity_unit) {
        this.food_quantity_unit = food_quantity_unit;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getName() {
        return name;
    }


    public String getFoodQuantityUnit() {
        return food_quantity_unit;
    }


    public double getQuantity() {
        return quantity;
    }

    public String getKey_id() {
        return key_id;
    }


}

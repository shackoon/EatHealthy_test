package com.example.admin.eathealthy.CustomItem;

/**
 * Created by shackoon on 2017/7/20.
 */

public class PersonInfo {
    private String column_name;
    private String value;
    public PersonInfo(String name, String value) {
        column_name = name;
        this.value = value;
    }

    public String getColumnName() {
        return column_name;
    }

    public void setName(String name) {
        column_name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

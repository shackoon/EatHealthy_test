package com.example.admin.eathealthy.CustomItem;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

/**
 * Created by shackoon on 2017/7/20.
 */

public class UserPicture {
    private byte[] imageView;
    private String key_id;
    private String account;
    private String diningTime;
    private String date;

    public UserPicture(byte[] imageView, String key_id, String account, String diningTime, String date) {
        this.imageView = imageView;
        this.key_id = key_id;
        this.account = account;
        this.diningTime = diningTime;
        this.date = date;
    }

    public byte[] getImage() {
        return imageView;
    }

    public void setImage(byte[] imageView) {
        this.imageView = imageView;
    }


    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDiningTime() {
        return diningTime;
    }

    public void setDiningTime(String diningTime) {
        this.diningTime = diningTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

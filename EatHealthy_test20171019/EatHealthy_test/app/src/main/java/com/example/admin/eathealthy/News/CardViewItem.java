package com.example.admin.eathealthy.News;

/**
 * Created by shackoon on 2017/7/20.
 */

public class CardViewItem {
    private String webname;
    private int webpicture;
    private String url;


    public CardViewItem(String webname, int webpicture, String url) {
        this.webname = webname;
        this.webpicture = webpicture;
        this.url = url;
    }

    public String GetWebname() {
        return webname;

    }

    public String GetURL() {
        return url;
    }
    public int GetPicture() {
        return webpicture;
    }

}

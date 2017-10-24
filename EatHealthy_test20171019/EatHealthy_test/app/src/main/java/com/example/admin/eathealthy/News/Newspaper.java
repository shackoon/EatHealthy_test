package com.example.admin.eathealthy.News;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.admin.eathealthy.News.CardViewItem;
import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.News.CardViewAdapter;
import com.example.admin.eathealthy.recyevent.OnRecyclerItemClickListener;

import java.util.ArrayList;

public class Newspaper extends AppCompatActivity {

    final String[] urls = {"http://www.commonhealth.com.tw/recipe/index.actiond", "https://www.top1health.com/"
            , "https://www.everydayhealth.com.tw/", "http://www.healthnews.com.tw/", "https://nutri.jtf.org.tw/index.php?idd=1&aid=3", "https://obesity.hpa.gov.tw/PDA/index.aspx", "http://www.epochtimes.com/b5/nf2897.htm"};
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview);
        context = this;
        String[] titles = {"康健健康網", "華人健康網", "雅虎早安健康", "健康醫療網", "董事基金會食品營養特區", "肥胖防治網", "大紀元飲食健康"};
        int[] pic = {R.drawable.album01, R.drawable.album02, R.drawable.album03, R.drawable.album04, R.drawable.album05, R.drawable.album06, R.drawable.album07};
        ArrayList<CardViewItem> myDataset = new ArrayList<>();

        for (int i = 0; i < titles.length; i++) {
            CardViewItem cardViewItem = new CardViewItem(titles[i], pic[i], urls[i]);
            myDataset.add(cardViewItem);
        }

        CardViewAdapter myAdapter = new CardViewAdapter(myDataset);
        RecyclerView mList = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
        mList.addOnItemTouchListener(new OnRecyclerItemClickListener(mList) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                Intent web = new Intent(context, WebAddressPage.class);
                web.putExtra(List_table_News.WEB_ADDRESS, urls[viewHolder.getAdapterPosition()]);
                startActivity(web);
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });
    }


}


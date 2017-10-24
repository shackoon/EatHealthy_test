package com.example.admin.eathealthy.News;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.admin.eathealthy.R;import com.example.admin.eathealthy.News.WebAddressPage;import java.util.ArrayList;
import java.util.HashMap;

public class List_table_News extends AppCompatActivity {
    Context context;
    public static final String WEB_ADDRESS = "URL";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_newspaper_route);

        context = this;

        String[] urls = {"http://www.commonhealth.com.tw/recipe/index.actiond","https://www.top1health.com/"
        ,"https://www.everydayhealth.com.tw/","http://www.healthnews.com.tw/","https://nutri.jtf.org.tw/index.php?idd=1&aid=3","https://obesity.hpa.gov.tw/PDA/index.aspx","http://www.epochtimes.com/b5/nf2897.htm"};
        String[] titles = {"康健健康網","華人健康網","雅虎早安健康","健康醫療網","董事基金會食品營養特區","肥胖防治網","大紀元飲食健康"};
        int length = titles.length;
        ListView listview = (ListView) findViewById(R.id.selection_info_route);
        String[] webname={"Title","URL"};
        int [] texts = {R.id.title,R.id.text1};
        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究
        ArrayList<HashMap<String,String>> items=new ArrayList<>();

        HashMap<String,String>HM;

        for(int index = 0; index < length; index++) {
            HM = new HashMap<>();
            HM.put(webname[0], titles[index]);
            HM.put(webname[1], urls[index]);
            items.add(HM);
        }


        SimpleAdapter adapter =new SimpleAdapter(this,items,R.layout.listview_item_web_address,webname,texts);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String>HM = (HashMap<String,String>)parent.getItemAtPosition(position);
                Intent intent =new Intent(context,WebAddressPage.class);
                intent.putExtra(WEB_ADDRESS,HM.get("URL"));
                startActivity(intent);
            }
        });







    }
}

package com.example.admin.eathealthy.Update_Data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.admin.eathealthy.AddFood;
import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.Data_table.MyDBHelper;

public class ChooseFood_backup extends AppCompatActivity {
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private AutoCompleteTextView autoCompleteTextView;
    private Context context = this;
    private Cursor cursor;
    private Bundle bundle;
    private String Account;
    private String from_where_activity;//記錄從哪個Activity來的
    private String select_time;
    private String select_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();
        bundle = getIntent().getExtras();
        Account = bundle.getString("Account");
        select_time = bundle.getString("select_time");
        select_date = bundle.getString("select_date");
        from_where_activity = bundle.getString("from_where");
        SetToolbarTitle();
        FindViews();
        setupAdapter();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void SetToolbarTitle() {

        switch (select_time) {
            case "breakfast":
                getSupportActionBar().setTitle("新增早餐");
                break;
            case "lunch":
                getSupportActionBar().setTitle("新增午餐");
                break;
            case "dinner":
                getSupportActionBar().setTitle("新增晚餐");
                break;
            case "other":
                getSupportActionBar().setTitle("新增其他");
                break;
            default:

        }
    }


    private void setupAdapter() {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[]{"Name"}, new int[]{android.R.id.text1}, 0);
        autoCompleteTextView.setAdapter(adapter);
        //food_list.setAdapter(adapter);

        //autocompleteadapter一改變就進到這個函式，可以自己定義要做甚麼，這裡做模糊搜尋 -----OK
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence str) {
                //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                return getCursor(str);
            }
        });

        //當我們選擇項目時使用它，執行此函數將典籍的點轉換為String。 ------已OK
        adapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            public CharSequence convertToString(Cursor cur) {
                int index = cur.getColumnIndex("Name");
                return cur.getString(index);
            }
        });
        //
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = cursor.getColumnIndex("Name");
                String now_select = cursor.getString(index);

                Intent intent = new Intent(context, AddFood.class);
                //傳值
                Bundle TransUserInfo = new Bundle();
                TransUserInfo.putString("Food", now_select);
                TransUserInfo.putString("UserName", Account);
                TransUserInfo.putString("select_time", select_time);
                TransUserInfo.putString("select_date", select_date);
                TransUserInfo.putString("from_where", from_where_activity);
                intent.putExtras(TransUserInfo);
                startActivity(intent);
                Toast.makeText(context, "position=" + Integer.toString(position) + " id=" + now_select, Toast.LENGTH_SHORT).show();
                finish();


            }
        });
    }

    //查詢，並傳回結果的函式
    public Cursor getCursor(CharSequence str) {
        String select = "Name" + " LIKE ?";
        String[] selectArgs = {"%" + str + "%"};
        cursor = db.query("food", null, select, selectArgs, null, null, null);
        return cursor;
    }

    public void FindViews() {
        //autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        //food_list = (ListView) findViewById(R.id.foodlist);
    }


}

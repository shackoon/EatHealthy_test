package com.example.admin.eathealthy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.example.admin.eathealthy.ClearableEditText.ClearableEditText;
import com.example.admin.eathealthy.CustomItem.Ntrition_choose;
import com.example.admin.eathealthy.Custom_Food_Filing.CustomFoodFiling;
import com.example.admin.eathealthy.RecycleViewCustomAdapter.ChooseFoodAdapter;
import com.example.admin.eathealthy.Data_table.Food_Data;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.recyevent.EmptyRecyclerView;
import com.example.admin.eathealthy.recyevent.OnRecyclerItemClickListener;

import java.util.ArrayList;

public class ChooseFood extends AppCompatActivity {
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

    private static ChooseFoodAdapter myAdapter;
    protected static ArrayList<Ntrition_choose> Dataset = new ArrayList<>();
    private EmptyRecyclerView mRecyclerView;
    private View mEmptyView;
    private ClearableEditText editText;


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


        //from_where_activity = bundle.getString("from_where");
        SetToolbarTitle();
        FindViews();

        Dataset.clear();
        mEmptyView = findViewById(R.id.imgView_choose_food);
        myAdapter = new ChooseFoodAdapter(Dataset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //設定此 layoutManager 為 linearlayout (類似ListView)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); //設定此 layoutManager 為垂直堆疊
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {


            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {

                String now_select = Dataset.get(viewHolder.getAdapterPosition()).getName();
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
                //Toast.makeText(context, "position=" + Integer.toString(position) + " id=" + now_select, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Toast.makeText(ChooseFood.this, editable.toString(), Toast.LENGTH_SHORT).show();
                Dataset.clear();
                if (editable.toString().equals("")) {

                } else {
                    Cursor cursor = getCursor(editable.toString());
                    while (cursor.moveToNext()) {
                        String k_cal = Integer.toString(cursor.getInt(cursor.getColumnIndex(Food_Data.HEAT_COLUMN))) + "大卡";
                        String perunit = cursor.getString(cursor.getColumnIndex(Food_Data.PERUNIT_COLUMN)) + cursor.getString(cursor.getColumnIndex(Food_Data.UNIT_COLUMN)) + " , " + k_cal;

                        Dataset.add(new Ntrition_choose(cursor.getString(cursor.getColumnIndex("Name")), perunit, ""));
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
        });

    }


    public void FindViews() {
        mRecyclerView = (EmptyRecyclerView) findViewById(R.id.recyclerview_choose_food);
        editText = (ClearableEditText) findViewById(R.id.et_choose_food);

    }


    //查詢，並傳回結果的函式
    public Cursor getCursor(CharSequence str) {
        String select = "Name" + " LIKE ?";
        String[] selectArgs = {"%" + str + "%"};
        cursor = db.query("food", null, select, selectArgs, null, null, null);
        return cursor;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_custom_food, menu);
        return true;
    }

    //menutItem選擇
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_custom_food:
                Intent intent = new Intent(this, CustomFoodFiling.class);
                //傳值
                Bundle bundle = new Bundle();
                bundle.putString("Account", Account);
                intent.putExtras(bundle);
                startActivity(intent);
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    //根據使用者選擇用餐時間，設定Toolbar標題
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


}

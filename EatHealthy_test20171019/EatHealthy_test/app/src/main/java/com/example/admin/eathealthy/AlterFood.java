package com.example.admin.eathealthy;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.eathealthy.CustomItem.FoodInfo;
import com.example.admin.eathealthy.GetData.Get_Food_Data;
import com.example.admin.eathealthy.RecycleViewCustomAdapter.FoodInfoAdapter;
import com.example.admin.eathealthy.Update_Data.Update_Today_Nutrition;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.recyevent.OnRecyclerItemClickListener;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AlterFood extends AppCompatActivity {
    private Bundle bundle;
    private String Food;
    private String UserName;
    private String select_time;
    private String select_date;
    private double copy;

    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Context context = this;
    private Get_Food_Data food_data;
    private String Heat_total;
    private String Portein_total;
    private String Fat_total;
    private String Carbohydrates_total;
    private String Na_total;

    private List<PieEntry> pieEntries = new ArrayList<>();
    private PieDataSet dataSet;
    private PieData data;
    private PieChart chart;
    private RecyclerView recyclerView;
    private FoodInfoAdapter myAdapter;
    private ArrayList<FoodInfo> Dataset = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_food);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        Food = bundle.getString("Food");
        UserName = bundle.getString("UserName");
        select_time = bundle.getString("select_time");
        select_date = bundle.getString("select_date");
        copy = bundle.getDouble("Copy");
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();

        food_data = new Get_Food_Data(db.query("food", null, "Name=?", new String[]{Food}, null, null, null));

        FindView();
        Set_Nurtition_Total();
        SetPiceData();
        SetRecyclerViewData();
        myAdapter = new FoodInfoAdapter(Dataset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context); //設定此 layoutManager 為 linearlayout (類似ListView)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); //設定此 layoutManager 為垂直堆疊
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL)); //設定分割線
        recyclerView.setLayoutManager(layoutManager); //設定 LayoutManager
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);//設定 Adapter

        //監聽事件
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                switch (viewHolder.getAdapterPosition()) {
                    case 1:
                    case 2:
                        moifyDialog("選擇幾份?", copy);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });

    }

    public void FindView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_alterfood);
        chart = (PieChart) findViewById(R.id.alter_food_piechart);
    }

    //設定數量用的dialog
    public void moifyDialog(String title, double now_number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View theView = inflater.inflate(R.layout.dialog_alterfood, null);
        // find view
        final EditText et_count = (EditText) theView.findViewById(R.id.et_alterfood);
        et_count.setText(String.valueOf(now_number));
        builder.setTitle(title);
        builder.setView(theView)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        copy = Double.parseDouble(et_count.getText().toString());
                        Set_Nurtition_Total();
                        SetPiceData();
                        SetRecyclerViewData();
                        //更新Adapter
                        myAdapter.notifyDataSetChanged();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //設定或更新RecyclerView資料
    public void SetRecyclerViewData() {
        String nurtition_name[] = {"熱量", "蛋白質", "脂肪", "碳水化合物", "鈉"};
        Dataset.clear();
        Dataset.add(new FoodInfo(food_data.getFoodName(), ""));
        Dataset.add(new FoodInfo("份數", String.valueOf(copy)));
        Dataset.add(new FoodInfo("每一份量", food_data.getPerunit() + " " + food_data.getUnit()));
        Dataset.add(new FoodInfo("營養成份", ""));
        Dataset.add(new FoodInfo(nurtition_name[0], Heat_total + " 大卡"));
        Dataset.add(new FoodInfo(nurtition_name[1], Portein_total + " 公克"));
        Dataset.add(new FoodInfo(nurtition_name[2], Fat_total + " 公克"));
        Dataset.add(new FoodInfo(nurtition_name[3], Carbohydrates_total + " 公克"));
        Dataset.add(new FoodInfo(nurtition_name[4], Na_total + " 毫克"));
    }

    //創建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nurtition_to_alter, menu);
        return true;
    }

    //menutItem選擇
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.nav_alter_nurtition:
                //新增食物至User_Food_data_table (select_date 使用者選擇的時間)
                Alter_User_Food(select_time, select_date, Food);
                //更新今日總熱量資料
                Update_Today_Nutrition update_today_nutrition = new Update_Today_Nutrition(UserName, select_date, context);//記得要改
                update_today_nutrition.update();
                //關閉Activity
                finish();
                break;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    //設定圓餅圖
    public void SetPiceData() {
        NumberFormat formatter = new DecimalFormat("#0");
        //設定輸入資料
        pieEntries.clear();
        pieEntries.add(new PieEntry(Integer.parseInt(formatter.format(Double.parseDouble(Portein_total) * 4)), "蛋白質"));
        pieEntries.add(new PieEntry(Integer.parseInt(formatter.format(Double.parseDouble(Fat_total) * 9)), "脂肪"));
        pieEntries.add(new PieEntry(Integer.parseInt(formatter.format(Double.parseDouble(Carbohydrates_total) * 4)), "碳水化合物"));
        //設定每筆資料顏色、大小等細部設定
        dataSet = new PieDataSet(pieEntries, "營養成分");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextSize(15);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);//設定值是否顯示在外面
        data = new PieData(dataSet);
        //設定圖例格式
        Legend legend = chart.getLegend();
        legend.setTextSize(15);
        //設定圖表格式與資料
        chart.setData(data);
        chart.setEntryLabelColor(getResources().getColor(R.color.black));//設定X值顏色
        chart.setCenterText(Heat_total + "大卡");
        chart.setUsePercentValues(true);
        chart.animateX(500, Easing.EasingOption.EaseInCirc);

        chart.invalidate();
        //Toast.makeText(context, (formatter.format(Double.parseDouble(Portein_total))), Toast.LENGTH_SHORT).show();

    }


    public void Set_Nurtition_Total() {
        NumberFormat formatter = new DecimalFormat("#0.0");
        Heat_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getHeat()) * copy));
        Portein_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getPortein()) * copy));
        Fat_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getFat()) * copy));
        Carbohydrates_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getCarbohydrates()) * copy));
        Na_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getNa()) * copy));
    }


    //改改食物數量至User_Food_data_table (select_date 使用者選擇的時間)
    public void Alter_User_Food(String select_time, String select_date, String food) {

        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(User_Food_Data.FOOD_QUANTITY_COLUMN, copy);
        // 新增一筆資料並取得編號
        db.update(User_Food_Data.TABLE_NAME + UserName, cv, User_Food_Data.DINING_TIME_COLUMN + "=? AND " + User_Food_Data.DATE_COLUMN + "=? AND " + User_Food_Data.FOOD_NAME_COLUMN + "=?", new String[]{select_time, select_date, food});

        Toast.makeText(context, "更改成功", Toast.LENGTH_SHORT).show();
    }


}



package com.example.admin.eathealthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.eathealthy.GetData.GetNutrition;
import com.example.admin.eathealthy.GetData.Get_Food_Data;
import com.example.admin.eathealthy.Update_Data.Update_Today_Nutrition;
import com.example.admin.eathealthy.Data_table.Item_User_Data;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.Data_table.User_Nutritional_Goals;
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

public class AddFood_backup extends AppCompatActivity {
    private Bundle bundle;
    private String Food;
    private String UserName;
    private String select_time;
    private String select_date;
    private String from_where_activity;
    private double copy = 1;
    private TextView AddFood_Name;
    private EditText numofcopy;
    private TextView Heat;
    private TextView Portein;
    private TextView Fat;
    private TextView Carbohydrates;
    private TextView Na;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    Cursor c;
    Context context = this;
    Get_Food_Data food_data;
    private String Heat_total;
    private String Portein_total;
    private String Fat_total;
    private String Carbohydrates_total;
    private String Na_total;

    private List<PieEntry> pieEntries = new ArrayList<>();
    private PieDataSet dataSet;
    private PieData data;
    private PieChart chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        Food = bundle.getString("Food");
        UserName = bundle.getString("UserName");
        select_time = bundle.getString("select_time");
        select_date = bundle.getString("select_date");
        from_where_activity = bundle.getString("from_where");
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();


        c = db.query("food", null, "Name=?", new String[]{Food}, null, null, null);
        c.moveToFirst();

        food_data = new Get_Food_Data(db.query("food", null, "Name=?", new String[]{Food}, null, null, null));

        FindView();
        chart = (PieChart) findViewById(R.id.piechart);
        Set_Nurtition_Total();
        Set_TextView_Data();
//        SetRecycleView();
        SetPiceData();
        //SetPieChart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nurtition_to_add, menu);
        return true;
    }

    //menutItem選擇
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        add_User_Food(select_date);
        add_User_Today_Nutrition(UserName, select_date);

        Update_Today_Nutrition update_today_nutrition = new Update_Today_Nutrition(UserName, select_date, context);//記得要改
        update_today_nutrition.update();


        //判斷是哪個Activity來的要求
        switch (from_where_activity) {

            case "TodayEatActivity":
                finish();
                Toast.makeText(context, from_where_activity, Toast.LENGTH_SHORT).show();
                break;

            case "PersonalHealthyAnalysis":
//                Intent intent = new Intent(context, Personl_Healthy_Analysis.class);
//                Bundle trans_account = new Bundle();
//                trans_account.putString("Account", UserName);
//                intent.putExtras(trans_account);
//                startActivity(intent);
                finish();
                Toast.makeText(context, from_where_activity, Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    public void FindView() {
        AddFood_Name = (TextView) findViewById(R.id.textView_AddFood_Name);
        numofcopy = (EditText) findViewById(R.id.editText_numofcopy);
        Heat = (TextView) findViewById(R.id.textView_Heat_KeyIn);
        Portein = (TextView) findViewById(R.id.textView1_Protein_KeyIn);
        Fat = (TextView) findViewById(R.id.textView_Fat_KeyIn);
        Carbohydrates = (TextView) findViewById(R.id.textView1_Carbohydrates_KeyIn);
        Na = (TextView) findViewById(R.id.textView1_Na_KeyIn);

        //份數改變，連動營養數量改變
        numofcopy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("") || s.toString().equals("0") || Integer.parseInt(s.toString()) < 0) {
                    copy = 1;
                    Set_TextView_Data();
                } else {
                    copy = Double.parseDouble(s.toString());
                    Set_Nurtition_Total();
                    Set_TextView_Data();
                    SetPiceData();
                }

            }
        });

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
        dataSet.setValueTextSize(15);
        dataSet.setValueFormatter(new PercentFormatter());
        data = new PieData(dataSet);
        //設定圖例格式
        Legend legend = chart.getLegend();
        legend.setTextSize(15);
        //設定圖表格式與資料
        chart.setData(data);
        chart.setCenterText(Heat_total + "大卡");
        chart.setUsePercentValues(true);
        chart.animateX(1000, Easing.EasingOption.EaseInCirc);
        chart.invalidate();
        Toast.makeText(context, (formatter.format(Double.parseDouble(Portein_total))), Toast.LENGTH_SHORT).show();

    }

//    public void SetPieChart() {
//
//        //chart.setCenterText("Rainfall for Vancouver");
//        //chart.setHoleRadius(30);

    // chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//
//                Toast.makeText(context, String.valueOf(e.getY()), Toast.LENGTH_SHORT).show();
////
////                dataSet.removeEntry((PieEntry) e);
////                chart.notifyDataSetChanged();
////                chart.animateX(1000, Easing.EasingOption.EaseInCirc);
////                chart.invalidate();
//
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
//        chart.animateX(1000, Easing.EasingOption.EaseInQuad);//動畫
//        chart.invalidate(); //顯示圖表
//    }


    public void Set_TextView_Data() {
        AddFood_Name.setText(Food);
        Heat.setText(Heat_total + " 大卡");
        Portein.setText(Portein_total + " 公克");
        Fat.setText(Fat_total + " 公克");
        Carbohydrates.setText(Carbohydrates_total + " 公克");
        Na.setText(Na_total + " 毫克");
    }

    public void Set_Nurtition_Total() {
        NumberFormat formatter = new DecimalFormat("#0.0");
        Heat_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getHeat()) * copy));
        Portein_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getPortein()) * copy));
        Fat_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getFat()) * copy));
        Carbohydrates_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getCarbohydrates()) * copy));
        Na_total = String.valueOf(formatter.format(Double.parseDouble(food_data.getNa()) * copy));
    }


    //新增食物至User_Food_data_table (select_date 使用者選擇的時間)
    public void add_User_Food(String select_date) {

        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(User_Food_Data.USER_NAME_COLUMN, UserName);
        cv.put(User_Food_Data.FOOD_NAME_COLUMN, Food);
        cv.put(User_Food_Data.FOOD_QUANTITY_COLUMN, copy);
        cv.put(User_Food_Data.PERUNIT_COLUMN,food_data.getPerunit());
        cv.put(User_Food_Data.UNIT_COLUMN,food_data.getUnit());
        cv.put(User_Food_Data.DINING_TIME_COLUMN, select_time);
        cv.put(User_Food_Data.DATE_COLUMN, select_date);
        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        db.insert(User_Food_Data.TABLE_NAME + UserName, null, cv);

        Toast.makeText(context, "新噌成功", Toast.LENGTH_SHORT).show();
    }


    private void add_User_Today_Nutrition(String Account, String select_date) {
        GetNutrition getNutrition;
        Cursor cursor_nutrition, cursor;
        //搜尋營養資料表內是否已經有今天的資料
        cursor_nutrition = db.query(User_Nutritional_Goals.TABLE_NAME + Account, null, User_Nutritional_Goals.DATE_COLUMN + "=?", new String[]{select_date}, null, null, null);
        if (cursor_nutrition.getCount() == 0) {

            //搜尋使用者基本資料

            cursor = db.query(Item_User_Data.TABLE_NAME, null, "Account=?", new String[]{Account}, null, null, null);

            //將cursor丟給GetNutrition處理，並輸出今日目標熱量、蛋白質、只防、碳水化合物、鈉。
            getNutrition = new GetNutrition(cursor);


            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            // 建立準備新增資料的ContentValues物件
            ContentValues cv = new ContentValues();
            // 加入ContentValues物件包裝的新增資料
            // 第一個參數是欄位名稱， 第二個參數是欄位的資料
            cv.put(User_Nutritional_Goals.ACCOUNT_COLUMN, Account);
            cv.put(User_Nutritional_Goals.TARGET_HEAT_COLUMN, getNutrition.getHeat());
            cv.put(User_Nutritional_Goals.TARGET_PROTEIN_COLUMN, getNutrition.getProtein());
            cv.put(User_Nutritional_Goals.TARGET_FAT_COLUMN, getNutrition.getFat());
            cv.put(User_Nutritional_Goals.TARGET_CARBOHYDRATE_COLUMN, getNutrition.getCarbohydrate());
            cv.put(User_Nutritional_Goals.TARGET_Na_COLUMN, getNutrition.getNa());
            cv.put(User_Nutritional_Goals.NOW_HEAT_COLUMN, 0);
            cv.put(User_Nutritional_Goals.NOW_PROTEIN_COLUMN, 0);
            cv.put(User_Nutritional_Goals.NOW_FAT_COLUMN, 0);
            cv.put(User_Nutritional_Goals.NOW_CARBOHYDRATE_COLUMN, 0);
            cv.put(User_Nutritional_Goals.NOW_NA_COLUMN, 0);
            cv.put(User_Nutritional_Goals.DATE_COLUMN, select_date);

            // 新增一筆資料並取得編號
            // 第一個參數是表格名稱
            // 第二個參數是沒有指定欄位值的預設值
            // 第三個參數是包裝新增資料的ContentValues物件
            db.insert(User_Nutritional_Goals.TABLE_NAME + Account, null, cv);
            Toast.makeText(context, select_date + " add OK", Toast.LENGTH_SHORT).show();

        }

    }


}

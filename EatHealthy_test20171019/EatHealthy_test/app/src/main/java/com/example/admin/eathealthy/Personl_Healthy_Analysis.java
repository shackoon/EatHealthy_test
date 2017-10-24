package com.example.admin.eathealthy;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.eathealthy.Camera.Camera;
import com.example.admin.eathealthy.ColorArcProgressBar.ColorArcProgressBar;
import com.example.admin.eathealthy.GetData.Calc_User_Nurtrition_Data;
import com.example.admin.eathealthy.GetData.GetNutrition;
import com.example.admin.eathealthy.Tabbed.TodayEatActivity;
import com.example.admin.eathealthy.Data_table.Item_User_Data;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Nutritional_Goals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Personl_Healthy_Analysis extends AppCompatActivity {

    Context context;
    private ImageButton FoodChooseButton;
    Bundle bundle;
    String Account;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private GetNutrition getNutrition;
    private ColorArcProgressBar colorArcProgressBar;
    private Calc_User_Nurtrition_Data calc_user_nurtrition_data;
    private ProgressBar pg_protein;
    private ProgressBar pg_fat;
    private ProgressBar pg_carbohydrate;
    private ProgressBar pg_na;
    private TextView tv_protein;
    private TextView tv_fat;
    private TextView tv_carbohydrate;
    private TextView tv_na;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private String nowTime;
    private TextView tv_date;
    private ImageButton btn_Left, btn_Right;
    private Calendar calendar_now;
    private Calendar today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personl_healthy_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true); //隱藏toolbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bundle = getIntent().getExtras();
        Account = bundle.getString("Account");
        context = this;
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();
        cursor = db.query(Item_User_Data.TABLE_NAME, null, "Account=?", new String[]{Account}, null, null, null);
        getNutrition = new GetNutrition(cursor);
        FindView();
        setTimeSelectLisenter();

    }


    public void setTimeSelectLisenter() {
        View.OnClickListener onClickListener;
        onClickListener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.imgbtn_left:
                        calendar_now.add(Calendar.DAY_OF_MONTH, -1);
                        //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.imgbtn_right:
                        calendar_now.add(Calendar.DAY_OF_MONTH, +1);
                        break;
                    case R.id.chart_tv_date:
                        int mYear, mMonth, mDay;//DatePicker使用
                        mYear = calendar_now.get(Calendar.YEAR);
                        mMonth = calendar_now.get(Calendar.MONTH);
                        mDay = calendar_now.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(Personl_Healthy_Analysis.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Date setDate = null;
                                try {
                                    setDate = formatter.parse(setDateFormat(year, month, day));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                calendar_now.setTime(setDate);
                                nowTime = formatter.format(calendar_now.getTime()).toString();
                                tv_date.setText(nowTime);
                                setProgressData(nowTime);
                            }
                        }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                        break;
                    default:
                        break;
                }

                nowTime = formatter.format(calendar_now.getTime()).toString();
//                if (calendar_now.getTime().compareTo(today.getTime()) == 0) {
//                    tv_date.setText("今天");
//                } else if ( == 1) {
//                    tv_date.setText("明天");
//                } else if ((calendar_now.getTimeInMillis() - today.getTimeInMillis()) / (24 * 60 * 60 * 1000) == -1) {
//                    tv_date.setText("昨天");
//                } else {
                tv_date.setText(nowTime);
//                }

                setProgressData(nowTime);
            }
        };
        btn_Left.setOnClickListener(onClickListener);
        btn_Right.setOnClickListener(onClickListener);
        tv_date.setOnClickListener(onClickListener);
    }


    //處理DatePickerDialog時間格式
    private String setDateFormat(int year, int monthOfYear, int dayOfMonth) {
        Date date = null;
        try {
            date = formatter.parse(String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(date.getTime());
    }


    public void FindView() {
        FoodChooseButton = (ImageButton) findViewById(R.id.ImageButtonKeyIn);
        colorArcProgressBar = (ColorArcProgressBar) findViewById(R.id.bar1);
        pg_protein = (ProgressBar) findViewById(R.id.pg_protein);
        pg_fat = (ProgressBar) findViewById(R.id.pg_fat);
        pg_carbohydrate = (ProgressBar) findViewById(R.id.pg_carbohydrate);
        pg_na = (ProgressBar) findViewById(R.id.pg_na);
        tv_protein = (TextView) findViewById(R.id.now_protein);
        tv_fat = (TextView) findViewById(R.id.now_fat);
        tv_carbohydrate = (TextView) findViewById(R.id.now_carbohydrate);
        tv_na = (TextView) findViewById(R.id.now_na);

        calendar_now = Calendar.getInstance();
        nowTime = formatter.format(calendar_now.getTime()).toString();
        today = Calendar.getInstance();

        tv_date = (TextView) findViewById(R.id.chart_tv_date);
        tv_date.setText(nowTime);
        btn_Left = (ImageButton) findViewById(R.id.imgbtn_left);
        btn_Right = (ImageButton) findViewById(R.id.imgbtn_right);


    }

    public void setProgressData(String nowTime) {

        calc_user_nurtrition_data = new Calc_User_Nurtrition_Data(Account, nowTime, context);

        colorArcProgressBar.setMaxValues(Float.parseFloat(String.valueOf(calc_user_nurtrition_data.get_T_Heat())));
        colorArcProgressBar.setCurrentValues(Float.parseFloat(String.valueOf(calc_user_nurtrition_data.get_Now_Heat())));

        pg_protein.setMax((int) (calc_user_nurtrition_data.get_T_Protein()));
        pg_protein.setProgress((int) (calc_user_nurtrition_data.get_Now_Protein()));
        tv_protein.setText(String.valueOf(calc_user_nurtrition_data.get_Now_Protein()) + "/" + String.valueOf(calc_user_nurtrition_data.get_T_Protein()) + " g" + " (" + calc_user_nurtrition_data.get_ProteinPercent() + "%)");
        //Toast.makeText(this,calc_user_nurtrition_data.get_Now_Protein()/ca,Toast.LENGTH_SHORT).show();


        pg_fat.setMax((int) (calc_user_nurtrition_data.get_T_Fat()));
        pg_fat.setProgress((int) (calc_user_nurtrition_data.get_Now_Fat()));
        tv_fat.setText(String.valueOf(calc_user_nurtrition_data.get_Now_Fat()) + "/" + String.valueOf(calc_user_nurtrition_data.get_T_Fat()) + " g" + " (" + calc_user_nurtrition_data.get_FatPercent() + "%)");


        pg_carbohydrate.setMax((int) (calc_user_nurtrition_data.get_T_Carbohydrate()));
        pg_carbohydrate.setProgress((int) (calc_user_nurtrition_data.get_Now_Carbohydrate()));
        tv_carbohydrate.setText(String.valueOf(calc_user_nurtrition_data.get_Now_Carbohydrate()) + "/" + String.valueOf(calc_user_nurtrition_data.get_T_Carbohydrate()) + " g" + " (" + calc_user_nurtrition_data.get_CarbohydratePercent() + "%)");


        pg_na.setMax((int) (calc_user_nurtrition_data.get_T_Na()));
        pg_na.setProgress((int) (calc_user_nurtrition_data.get_Now_Na()));
        tv_na.setText(String.valueOf(calc_user_nurtrition_data.get_Now_Na()) + "/" + String.valueOf(calc_user_nurtrition_data.get_T_Na()) + " mg" + " (" + calc_user_nurtrition_data.get_NaPercent() + "%)");
    }

    public void analysis(View view) {
//        ImageView foodpic = (ImageView) findViewById(R.id.ImageFood);
//        foodpic.setVisibility(View.VISIBLE);
//
//        HandlerThread handlerThread = new HandlerThread("A");
//        handlerThread.start();
//        Handler handler = new Handler(handlerThread.getLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                LinearLayout info = (LinearLayout) findViewById(R.id.foodInfo);
//                info.post(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                LinearLayout info = (LinearLayout) findViewById(R.id.foodInfo);
//                                info.setVisibility(View.VISIBLE);
//                            }
//                        }
//
//                );
//                Toast.makeText(context, "辨識完成,輸入成功", Toast.LENGTH_SHORT).show();
//            }
//        }, 1000);

        Intent intent = new Intent(context, Camera.class);
        startActivity(intent);
        Toast.makeText(context, "touch", Toast.LENGTH_SHORT).show();


    }

    public void choosefood(View view) {


//        formatter = new SimpleDateFormat("yyyy-MM-dd");
//        curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
//        nowTime = formatter.format(curDate);


        final String[] dining_time = {"早餐", "午餐", "晚餐", "其他"}; //輸入List裡須顯示的清單

        AlertDialog.Builder dialog_list = new AlertDialog.Builder(context);
        dialog_list.setTitle("用餐時間");
        dialog_list.setItems(dining_time, new DialogInterface.OnClickListener() {
            @Override

            //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
            public void onClick(DialogInterface dialog, int which) {
                String select_time = "";  //目前選擇用餐時間
                Intent intent = new Intent(context, ChooseFood.class);
                Bundle trans_account = new Bundle();


                switch (dining_time[which]) {
                    case "早餐":
                        select_time = "breakfast";
                        break;
                    case "午餐":
                        select_time = "lunch";
                        break;
                    case "晚餐":
                        select_time = "dinner";
                        break;
                    case "其他":
                        select_time = "other";
                        break;
                    default:
                }
                trans_account.putString("Account", Account);
                trans_account.putString("select_time", select_time);
                trans_account.putString("from_where", "PersonalHealthyAnalysis");//傳送是從哪個Acticity來的
                trans_account.putString("select_date", nowTime);//傳送新增或刪除項目的日期
                intent.putExtras(trans_account);
                startActivity(intent);
                Toast.makeText(context, "你選的是" + dining_time[which], Toast.LENGTH_SHORT).show();

            }
        });
        dialog_list.show();

    }


    public void food_list(View view) {
        Intent intent = new Intent(context, TodayEatActivity.class);
        Bundle trans_account = new Bundle();
        trans_account.putString("Account", Account);
        intent.putExtras(trans_account);
        startActivity(intent);

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


    //新增食物回來時更新資料!
    @Override
    public void onResume() {
        super.onResume();
        setProgressData(nowTime);

    }

}

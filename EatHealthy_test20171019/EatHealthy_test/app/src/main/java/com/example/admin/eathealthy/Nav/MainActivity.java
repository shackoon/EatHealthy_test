package com.example.admin.eathealthy.Nav;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.admin.eathealthy.Account_Page;
import com.example.admin.eathealthy.CookProxy;
import com.example.admin.eathealthy.Custom_Food_Filing.CustomFoodFiling;
import com.example.admin.eathealthy.GetData.GetNutrition;
import com.example.admin.eathealthy.List_table;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.News.Newspaper;
import com.example.admin.eathealthy.PersonalInformation;
import com.example.admin.eathealthy.Personl_Healthy_Analysis;
import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.RecipePlatform;
import com.example.admin.eathealthy.Data_table.Item_User_Data;
import com.example.admin.eathealthy.Data_table.User_Nutritional_Goals;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private Bundle bundle;
    private String Account;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor_nutrition;
    private Cursor cursor;
    private GetNutrition getNutrition;
    private BottomNavigationView navigation;
    private static ViewPager viewPager;
    private ChartFragment chartFragment = new ChartFragment();
    private HealthFunctionFragment healthFunctionFragment = new HealthFunctionFragment();
    private PersonalFragment personalFragment = new PersonalFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navi);
        bundle = getIntent().getExtras();
        Account = bundle.getString("Account");
        //搜尋使用者基本資料
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();
        init_User_Today_Nutrition();
        FindView();

        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        chartFragment.setArguments(bundle);
                        return chartFragment;
                    case 1:
                        healthFunctionFragment.setArguments(bundle);
                        return healthFunctionFragment;
                    case 2:
                        personalFragment.setArguments(bundle);
                        return personalFragment;
                    default:
                        return null;
                }

            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home); //設定預設位置
    }

    public void FindView() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    //------------------------------viewpager監聽事件---------------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                navigation.setSelectedItemId(R.id.navigation_chart);
                break;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_personal_info);
                break;
            default:
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //--------------------------------------------------------------------------------------------------

    //底部導覽-監聽事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            bundle.putString("Account", Account);
            FragmentManager manager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_chart:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_home:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_personal_info:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    public void onClick(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ImageButtonIdentityAnalysis:
                intent = new Intent(this, Personl_Healthy_Analysis.class);
                bundle = new Bundle();
                bundle.putString("Account", Account);
                init_User_Today_Nutrition();
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ImageButtonRecipe:
                intent = new Intent(this, RecipePlatform.class);
                startActivity(intent);
                break;
            case R.id.ImageButtonHealthPaper:
                intent = new Intent(this, Newspaper.class);
                startActivity(intent);
                break;
            case R.id.ImageButtonHealthNotice:
                intent = new Intent(this, PersonalInformation.class);
                bundle = new Bundle();
                bundle.putString("Account", Account);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ImageButtonProxy:
                intent = new Intent(this, CookProxy.class);
                startActivity(intent);
                break;
            case R.id.ImageButtonRank:
                break;
        }

    }


    private static Boolean isExit = false;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判斷是否按下Back
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否要退出
            if (isExit == false) {
                isExit = true; //記錄下一次要退出
                Toast.makeText(this, "再按一次Back退出APP", Toast.LENGTH_SHORT).show();
                handler.postDelayed(runnable, 2000);
            } else {
                finish(); // 離開程式
            }
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    //menutItem選擇
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                getSharedPreferences("Login_info", MODE_PRIVATE).edit().clear().commit();
                Intent intent = new Intent(this, Account_Page.class);
                startActivity(intent);
                finish();
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    //建立今日營養目標資料列
    public void init_User_Today_Nutrition() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        String nowTime = formatter.format(curDate);
        //搜尋營養資料表內是否已經有今天的資料
        cursor_nutrition = db.query(User_Nutritional_Goals.TABLE_NAME + Account, null, User_Nutritional_Goals.DATE_COLUMN + "=?", new String[]{nowTime}, null, null, null);
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
            cv.put(User_Nutritional_Goals.DATE_COLUMN, nowTime);

            // 新增一筆資料並取得編號
            // 第一個參數是表格名稱
            // 第二個參數是沒有指定欄位值的預設值
            // 第三個參數是包裝新增資料的ContentValues物件
            db.insert(User_Nutritional_Goals.TABLE_NAME + Account, null, cv);
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();

        }

    }


}

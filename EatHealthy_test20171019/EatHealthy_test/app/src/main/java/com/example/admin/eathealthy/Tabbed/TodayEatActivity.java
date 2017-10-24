package com.example.admin.eathealthy.Tabbed;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.eathealthy.ChooseFood;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodayEatActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public Bundle bundle;
    String Account;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    Context context = this;
    public MenuItem menu_title;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date curDate;
    Tab_Breakfast t1 = new Tab_Breakfast();
    Tab_Dinner t3 = new Tab_Dinner();
    Tab_Lunch t2 = new Tab_Lunch();
    Tab_Other t4 = new Tab_Other();
    int current_position = 0;
    private String nowTime;
    private TextView tv_date;
    private ImageButton btn_Left, btn_Right;
    private Calendar calendar_now;


    public void FindView() {
        calendar_now = Calendar.getInstance();
        nowTime = formatter.format(calendar_now.getTime()).toString();
        tv_date = (TextView) findViewById(R.id.chart_tv_date);
        tv_date.setText(nowTime);
        btn_Left = (ImageButton) findViewById(R.id.imgbtn_left);
        btn_Right = (ImageButton) findViewById(R.id.imgbtn_right);
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
                        DatePickerDialog datePickerDialog = new DatePickerDialog(TodayEatActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                                switch (current_position) {
                                    case 0:
                                        t1.setDataset(t1.userTodayEat.getFoodListData(nowTime));
                                        t1.setPicDataset(t1.userTodayEat.getPictureData(nowTime));
                                        t2.setDataset(t2.userTodayEat.getFoodListData(nowTime));
                                        t2.setPicDataset(t2.userTodayEat.getPictureData(nowTime));
                                        break;
                                    case 1:
                                        t1.setDataset(t1.userTodayEat.getFoodListData(nowTime));
                                        t1.setPicDataset(t1.userTodayEat.getPictureData(nowTime));
                                        t2.setDataset(t2.userTodayEat.getFoodListData(nowTime));
                                        t2.setPicDataset(t2.userTodayEat.getPictureData(nowTime));
                                        t3.setDataset(t3.userTodayEat.getFoodListData(nowTime));
                                        t3.setPicDataset(t3.userTodayEat.getPictureData(nowTime));

                                        break;
                                    case 2:
                                        t2.setDataset(t2.userTodayEat.getFoodListData(nowTime));
                                        t2.setPicDataset(t2.userTodayEat.getPictureData(nowTime));
                                        t3.setDataset(t3.userTodayEat.getFoodListData(nowTime));
                                        t3.setPicDataset(t3.userTodayEat.getPictureData(nowTime));
                                        t4.setDataset(t4.userTodayEat.getFoodListData(nowTime));
                                        t4.setDataset(t4.userTodayEat.getFoodListData(nowTime));
                                        break;
                                    case 3:
                                        t3.setDataset(t3.userTodayEat.getFoodListData(nowTime));
                                        t3.setPicDataset(t3.userTodayEat.getPictureData(nowTime));
                                        t4.setDataset(t4.userTodayEat.getFoodListData(nowTime));
                                        t4.setDataset(t4.userTodayEat.getFoodListData(nowTime));
                                        break;
                                }
                                t1.select_time = nowTime;
                                t2.select_time = nowTime;
                                t3.select_time = nowTime;
                                t4.select_time = nowTime;
                            }
                        }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                        break;
                    default:
                        break;
                }

                nowTime = formatter.format(calendar_now.getTime()).toString();
                tv_date.setText(nowTime);

                switch (current_position) {
                    case 0:
                        t1.setDataset(t1.userTodayEat.getFoodListData(nowTime));
                        t1.setPicDataset(t1.userTodayEat.getPictureData(nowTime));
                        t2.setDataset(t2.userTodayEat.getFoodListData(nowTime));
                        t2.setPicDataset(t2.userTodayEat.getPictureData(nowTime));
                        break;
                    case 1:
                        t1.setDataset(t1.userTodayEat.getFoodListData(nowTime));
                        t1.setPicDataset(t1.userTodayEat.getPictureData(nowTime));
                        t2.setDataset(t2.userTodayEat.getFoodListData(nowTime));
                        t2.setPicDataset(t2.userTodayEat.getPictureData(nowTime));
                        t3.setDataset(t3.userTodayEat.getFoodListData(nowTime));
                        t3.setPicDataset(t3.userTodayEat.getPictureData(nowTime));

                        break;
                    case 2:
                        t2.setDataset(t2.userTodayEat.getFoodListData(nowTime));
                        t2.setPicDataset(t2.userTodayEat.getPictureData(nowTime));
                        t3.setDataset(t3.userTodayEat.getFoodListData(nowTime));
                        t3.setPicDataset(t3.userTodayEat.getPictureData(nowTime));
                        t4.setDataset(t4.userTodayEat.getFoodListData(nowTime));
                        t4.setPicDataset(t4.userTodayEat.getPictureData(nowTime));
                        break;
                    case 3:
                        t3.setDataset(t3.userTodayEat.getFoodListData(nowTime));
                        t3.setPicDataset(t3.userTodayEat.getPictureData(nowTime));
                        t4.setDataset(t4.userTodayEat.getFoodListData(nowTime));
                        t4.setPicDataset(t4.userTodayEat.getPictureData(nowTime));
                        break;
                }
                t1.select_time = nowTime;
                t2.select_time = nowTime;
                t3.select_time = nowTime;
                t4.select_time = nowTime;

            }
        };

        btn_Left.setOnClickListener(onClickListener);
        btn_Right.setOnClickListener(onClickListener);
        tv_date.setOnClickListener(onClickListener);
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_eat);
        bundle = getIntent().getExtras();
        Account = bundle.getString("Account");
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();
        curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FindView();
        setTimeSelectLisenter();
        t1.select_time = nowTime;
        t2.select_time = nowTime;
        t3.select_time = nowTime;
        t4.select_time = nowTime;

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //getSupportActionBar().setDisplayShowTitleEnabled(false); //隱藏toolbar title

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current_position = position;
                switch (position) {
                    case 0:
                        t1.setPicDataset(t1.userTodayEat.getPictureData(nowTime));
                        t1.setDataset(t1.userTodayEat.getFoodListData(nowTime));
                        break;
                    case 1:
                        t2.setPicDataset(t2.userTodayEat.getPictureData(nowTime));
                        t2.setDataset(t2.userTodayEat.getFoodListData(nowTime));

                        break;
                    case 2:
                        t3.setPicDataset(t3.userTodayEat.getPictureData(nowTime));
                        t3.setDataset(t3.userTodayEat.getFoodListData(nowTime));
                        break;
                    case 3:
                        t4.setPicDataset(t4.userTodayEat.getPictureData(nowTime));
                        t4.setDataset(t4.userTodayEat.getFoodListData(nowTime));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] dining_time = {"早餐", "午餐", "晚餐", "其他"}; //輸入List裡須顯示的清單

//                AlertDialog.Builder dialog_list = new AlertDialog.Builder(TodayEatActivity.this);
//                dialog_list.setTitle("用餐時間");
//                dialog_list.setItems(dining_time, new DialogInterface.OnClickListener() {
//                    @Override
//
//                    //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
//                    public void onClick(DialogInterface dialog, int which) {
//                        String select_time = "";  //目前選擇用餐時間
//                        Intent intent = new Intent(TodayEatActivity.this, ChooseFood.class);
//                        Bundle trans_account = new Bundle();
//
//
//                        switch (dining_time[which]) {
//                            case "早餐":
//                                select_time = "breakfast";
//                                break;
//                            case "午餐":
//                                select_time = "lunch";
//                                break;
//                            case "晚餐":
//                                select_time = "dinner";
//                                break;
//                            case "其他":
//                                select_time = "other";
//                                break;
//                            default:
//                        }
//                        trans_account.putString("Account", Account);
//                        trans_account.putString("select_time", select_time);
//                        trans_account.putString("from_where", "TodayEatActivity");
//                        trans_account.putString("select_date", menu_title.getTitle().toString());//傳送新增或刪除項目的日期
//                        intent.putExtras(trans_account);
//                        startActivity(intent);
//                        Toast.makeText(TodayEatActivity.this, "你選的是" + dining_time[which], Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                dialog_list.show();


                String select_time = "";  //目前選擇用餐時間
                Intent intent = new Intent(TodayEatActivity.this, ChooseFood.class);
                Bundle trans_account = new Bundle();


                switch (dining_time[current_position]) {
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
                trans_account.putString("from_where", "TodayEatActivity");
                trans_account.putString("select_date", nowTime);//傳送新增或刪除項目的日期
                intent.putExtras(trans_account);
                startActivity(intent);
                Toast.makeText(TodayEatActivity.this, "你選的是" + dining_time[current_position], Toast.LENGTH_SHORT).show();

            }


        });

        //-----------------------------------------------------------------------------------------

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


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("Account", Account);
            bundle.putString("nowtime", nowTime);
            switch (position) {
                case 0:
                    t1.setArguments(bundle);
                    return t1;
                case 1:
                    t2.setArguments(bundle);
                    return t2;
                case 2:
                    t3.setArguments(bundle);
                    return t3;
                case 3:
                    t4.setArguments(bundle);
                    return t4;
                default:
                    return t1;


            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "早餐";
                case 1:
                    return "午餐";
                case 2:
                    return "晚餐";
                case 3:
                    return "其他";
            }
            return null;
        }
    }

    //新增食物回來時更新資料!
    @Override
    public void onResume() {
        super.onResume();

        if (t1.isVisible() || t2.isVisible() || t3.isVisible() || t4.isVisible()) {//等到oncreate之後再做更新
            switch (current_position) {
                case 0:
                    t1.setDataset(t1.userTodayEat.getFoodListData(nowTime));
                    break;
                case 1:
                    t2.setDataset(t2.userTodayEat.getFoodListData(nowTime));
                    break;
                case 2:
                    t3.setDataset(t3.userTodayEat.getFoodListData(nowTime));
                    break;
                case 3:
                    t4.setDataset(t4.userTodayEat.getFoodListData(nowTime));
                    break;
            }

            //t1.setDataset(getTargetDateData(menu_title.getTitle().toString(), Tab_Breakfast.TAB_BREAKFAST));
            //t1.setPicDataset(getPictureData(formatter.format(System.currentTimeMillis()), "breakfast"));
//            t2.setDataset(getTargetDateData(menu_title.getTitle().toString(), Tab_Lunch.TAB_LUNCH));
//            t3.setDataset(getTargetDateData(menu_title.getTitle().toString(), Tab_Dinner.TAB_DINNER));
//            t4.setDataset(getTargetDateData(menu_title.getTitle().toString(), Tab_Other.TAB_OTHER));
        }
        //Toast.makeText(context, "onRssume", Toast.LENGTH_SHORT).show();
    }

}

package com.example.admin.eathealthy.Nav;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.Nav.SubChartFragment.HeatChartSubFragment;
import com.example.admin.eathealthy.Nav.SubChartFragment.HeatChartByNutritionSubFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ChartFragment extends Fragment implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    private static final String ACCOUNT = "Account";
    private static String account;

    private String startDay;
    private String endDay;

    private TextView tv_date;
    private ImageButton btn_Left, btn_Right;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar_start;
    private Calendar calendar_end;

    private TabLayout mTablayout;
    private ViewPager viewPager;
    private String date[] = new String[7];

    private HeatChartSubFragment heatChartSubFragment = new HeatChartSubFragment();
    private HeatChartByNutritionSubFragment heatChartByNutritionSubFragment = new HeatChartByNutritionSubFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            account = getArguments().getString(ACCOUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chart, container, false);

        FindView(view);
        setTimeSelectLisenter();

        return view;
    }


    public void FindView(View view) {

        tv_date = view.findViewById(R.id.chart_tv_date);
        btn_Left = view.findViewById(R.id.imgbtn_left);
        btn_Right = view.findViewById(R.id.imgbtn_right);

        calendar_start = Calendar.getInstance();
        calendar_end = Calendar.getInstance();
        calendar_start.add(Calendar.DAY_OF_MONTH, -3);
        calendar_end.setTime(calendar_start.getTime());
        calendar_end.add(Calendar.DAY_OF_MONTH, +7);
        startDay = formatter.format(calendar_start.getTime()).toString();
        endDay = formatter.format(calendar_end.getTime()).toString();

        tv_date.setText(startDay + " 到 " + formatter.format(calendar_end.getTimeInMillis() - (24 * 60 * 60 * 1000)).toString());

        mTablayout = view.findViewById(R.id.tab_test);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("Account", account);
                bundle.putString("startDay", startDay);
                bundle.putString("endDay", endDay);
                switch (position) {
                    case 0:
                        heatChartSubFragment.setArguments(bundle);
                        return heatChartSubFragment;
                    case 1:
                        heatChartByNutritionSubFragment.setArguments(bundle);
                        return heatChartByNutritionSubFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        //設置各自的監聽事件來自哪裡
        viewPager.addOnPageChangeListener(ChartFragment.this);
        mTablayout.addOnTabSelectedListener(ChartFragment.this);
    }

    //Tab 與 Page 動作的監聽事件-------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                heatChartSubFragment.setBarChart(heatChartSubFragment.setBarDate_Heat(startDay, endDay));
                break;
            case 1:
                heatChartByNutritionSubFragment.setBarChart(heatChartByNutritionSubFragment.setBarDate_Nutrition(startDay, endDay));
                break;
            default:
                break;

        }
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTablayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void setTimeSelectLisenter() {
        View.OnClickListener onClickListener;
        onClickListener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.imgbtn_left:
                        //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                        calendar_start.add(Calendar.DAY_OF_MONTH, -7);
                        calendar_end.setTime(calendar_start.getTime());
                        calendar_end.add(Calendar.DAY_OF_MONTH, +7);

                        //tv_date.setText(startDay + " 到 " + endDay);
                        break;
                    case R.id.imgbtn_right:
                        calendar_start.add(Calendar.DAY_OF_MONTH, +7);
                        calendar_end.setTime(calendar_start.getTime());
                        calendar_end.add(Calendar.DAY_OF_MONTH, +7);

                        //tv_date.setText(startDay + " 到 " + endDay);
                        break;
                    case R.id.chart_tv_date:
                        int mYear, mMonth, mDay;//DatePicker使用
                        mYear = calendar_start.get(Calendar.YEAR);
                        mMonth = calendar_start.get(Calendar.MONTH);
                        mDay = calendar_start.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Date setDate = null;
                                try {
                                    setDate = formatter.parse(setDateFormat(year, month, day));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                calendar_start.setTime(setDate);
                                calendar_end.setTime(calendar_start.getTime());
                                calendar_end.add(Calendar.DAY_OF_MONTH, +7);

                                startDay = formatter.format(calendar_start.getTime()).toString();
                                endDay = formatter.format(calendar_end.getTime()).toString();

                                heatChartSubFragment.setBarChart(heatChartSubFragment.setBarDate_Heat(startDay, endDay));
                                heatChartByNutritionSubFragment.setBarChart(heatChartByNutritionSubFragment.setBarDate_Nutrition(startDay, endDay));

                                tv_date.setText(startDay + " 到 " + formatter.format(calendar_end.getTimeInMillis() - (24 * 60 * 60 * 1000)).toString());

                            }
                        }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                        break;
                    default:
                        break;
                }

                startDay = formatter.format(calendar_start.getTime()).toString();
                endDay = formatter.format(calendar_end.getTime()).toString();
                tv_date.setText(startDay + " 到 " + formatter.format(calendar_end.getTimeInMillis() - (24 * 60 * 60 * 1000)).toString());
                heatChartSubFragment.setBarChart(heatChartSubFragment.setBarDate_Heat(startDay, endDay));
                heatChartByNutritionSubFragment.setBarChart(heatChartByNutritionSubFragment.setBarDate_Nutrition(startDay, endDay));

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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}

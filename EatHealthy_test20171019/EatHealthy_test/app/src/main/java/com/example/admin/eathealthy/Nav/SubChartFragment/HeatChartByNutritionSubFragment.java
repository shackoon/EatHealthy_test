package com.example.admin.eathealthy.Nav.SubChartFragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Nutritional_Goals;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HeatChartByNutritionSubFragment extends Fragment {
    private static final String ACCOUNT = "Account";
    private static String account;

    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Cursor corsor;
    public BarChart barChart;
    private String date[] = new String[7];

    public String startDay;
    public String endDay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = getArguments().getString(ACCOUNT);
            startDay = getArguments().getString("startDay");
            endDay = getArguments().getString("endDay");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_nutrition_chart, container, false);

        FindView(view);
        helper = new MyDBHelper(getContext());
        db = helper.getWritableDatabase();
        setBarChart(setBarDate_Nutrition(startDay, endDay));


        return view;
    }

    public void FindView(View view) {
        barChart =(BarChart) view.findViewById(R.id.barchart_fa2);
    }


    public ArrayList<BarEntry> setBarDate_Nutrition(String startDay, String endDay) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        Date date1;
        Date date2;

        Calendar mDate1 = Calendar.getInstance();
        Calendar mDate2 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = simpleDateFormat.parse(startDay);
            date2 = simpleDateFormat.parse(endDay);

            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] weekDays = {"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int i = 0;

        while (mDate1.compareTo(mDate2) < 0) {
            date[i] = weekDays[mDate1.get(Calendar.DAY_OF_WEEK)];
            corsor = db.query(User_Nutritional_Goals.TABLE_NAME + account, null, User_Nutritional_Goals.DATE_COLUMN + "=?", new String[]{simpleDateFormat.format(mDate1.getTime())}, null, null, null);
            if (corsor.moveToNext()) {
                float protein = Float.parseFloat(corsor.getString(corsor.getColumnIndex(User_Nutritional_Goals.NOW_PROTEIN_COLUMN))) * 4;
                float fat = Float.parseFloat(corsor.getString(corsor.getColumnIndex(User_Nutritional_Goals.NOW_FAT_COLUMN))) * 9;
                float carbohydrate = Float.parseFloat(corsor.getString(corsor.getColumnIndex(User_Nutritional_Goals.NOW_CARBOHYDRATE_COLUMN))) * 4;

                barEntries.add(new BarEntry(i, new float[]{carbohydrate, fat, protein}));
            } else {
                barEntries.add(new BarEntry(i, 0));
            }
            //Toast.makeText(getContext(), simpleDateFormat.format(mDate1.getTime()), Toast.LENGTH_SHORT).show();
            mDate1.add(Calendar.DAY_OF_MONTH, 1);
            i++;
        }

        return barEntries;
    }


    public void setBarChart(ArrayList<BarEntry> barEntries) {

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(getColors());
        //barDataSet.setStackLabels(date);
        barDataSet.setStackLabels(new String[]{"碳水化合物", "脂肪", "蛋白質"});
        barDataSet.setDrawValues(false);

        barDataSet.setValueTextSize(14f);
        BarData theData = new BarData(barDataSet);


        barChart.setData(theData);
        barChart.setScaleEnabled(false);
        barChart.getDescription().setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        barChart.getAxisRight().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);


        Legend l = barChart.getLegend();
        //legend.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(10f);
        l.setFormToTextSpace(10f);
        l.setXEntrySpace(6f);
        l.setTextSize(15f);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(date));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(12f);//設置字體
        xAxis.setDrawGridLines(false);//格線
        //xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        barChart.setFitBars(true);
        barChart.invalidate();

    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }


    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }

        return colors;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}

package com.example.admin.eathealthy.Nav.PersonalNurtitionRatio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.Data_table.Item_User_Data;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.Data_table.User_Nutritional_Goals;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SetPersonalNurtitionRatio extends AppCompatActivity {
    private TextView tv_set_carbohydrate, tv_set_protein, tv_set_fat, tv_total_nurtition;
    private NumberPicker numberPicker_carbohydrate, numberPicker_protein, numberPicker_fat;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Cursor nurtition_cursor;
    private Bundle bundle;
    private String account;
    private int total_ratio;
    private SimpleDateFormat dateFormat;
    private Date curDate;
    private String nowTime;
    private NumberFormat formatter = new DecimalFormat("#0");
    private double T_heat;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_personal_nurtition_ratio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        account = bundle.getString("Account");
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();
        findView();
        initView();


    }

    public void findView() {
        tv_set_carbohydrate = (TextView) findViewById(R.id.tv_set_carbohydrate);
        tv_set_protein = (TextView) findViewById(R.id.tv_set_protein);
        tv_set_fat = (TextView) findViewById(R.id.tv_set_fat);
        numberPicker_carbohydrate = (NumberPicker) findViewById(R.id.numberPicker_carbohydrate);
        numberPicker_protein = (NumberPicker) findViewById(R.id.numberPicker_protein);
        numberPicker_fat = (NumberPicker) findViewById(R.id.numberPicker_fat);
        tv_total_nurtition = (TextView) findViewById(R.id.tv_total_nurtition);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        nowTime = dateFormat.format(curDate);
    }

    public void initView() {
        int numpick_minValue = 0;
        int numpick_maxValue = 100;
        int now_carbohydrate = 0;
        int now_protein = 0;
        int now_fat = 0;


        NumberPicker.OnValueChangeListener onValueChangeListener = null;

        nurtition_cursor = db.query(User_Nutritional_Goals.TABLE_NAME + account, null, User_Food_Data.DATE_COLUMN + "=?", new String[]{nowTime}, null, null, null);
        nurtition_cursor.moveToNext();
        T_heat = nurtition_cursor.getDouble(nurtition_cursor.getColumnIndex(User_Nutritional_Goals.TARGET_HEAT_COLUMN));

        //Toast.makeText(this, account, Toast.LENGTH_SHORT).show();
        cursor = db.query(Item_User_Data.TABLE_NAME, null, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{account}, null, null, null);
        //cursor = db.query(Item_User_Data.TABLE_NAME, null, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{Account}, null, null, null);
        cursor.moveToNext();
        now_carbohydrate = (int) (cursor.getDouble(cursor.getColumnIndex(Item_User_Data.CARBOHYDRATE_RATIO_COLUMN)) * 100);
        now_protein = (int) (cursor.getDouble(cursor.getColumnIndex(Item_User_Data.PROTEIN_RATIO_COLUMN)) * 100);
        now_fat = (int) (cursor.getDouble(cursor.getColumnIndex(Item_User_Data.FAT_RATIO_COLUMN)) * 100);

        //設定各numpicker的最小值、最大值、還有預設位置
        numberPicker_carbohydrate.setMaxValue(numpick_maxValue);
        numberPicker_carbohydrate.setMinValue(numpick_minValue);
        numberPicker_carbohydrate.setValue(now_carbohydrate);


        numberPicker_protein.setMaxValue(numpick_maxValue);
        numberPicker_protein.setMinValue(numpick_minValue);
        numberPicker_protein.setValue(now_protein);


        numberPicker_fat.setMaxValue(numpick_maxValue);
        numberPicker_fat.setMinValue(numpick_minValue);
        numberPicker_fat.setValue(now_fat);
        //設定各營養TestView顯示的值
        tv_set_carbohydrate.setText(formatter.format(T_heat * numberPicker_carbohydrate.getValue() / 100 / 4) + " g");
        tv_set_protein.setText(formatter.format(T_heat * numberPicker_protein.getValue() / 100 / 4) + " g");
        tv_set_fat.setText(formatter.format(T_heat * numberPicker_fat.getValue() / 100 / 9) + " g");

        //改變營養總百分比TestView顏色
        tv_total_nurtition.setTextColor(getResources().getColor(R.color.green));
        total_ratio = numberPicker_carbohydrate.getValue() + numberPicker_protein.getValue() + numberPicker_fat.getValue();
        tv_total_nurtition.setText(String.valueOf(total_ratio) + " %");

        //NumberPicker改變時監聽
        onValueChangeListener = new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                total_ratio = numberPicker_carbohydrate.getValue() + numberPicker_protein.getValue() + numberPicker_fat.getValue();
                if (total_ratio == 100) {
                    tv_total_nurtition.setTextColor(getResources().getColor(R.color.green));

                    menuItem.setEnabled(true);
                } else {
                    tv_total_nurtition.setTextColor(getResources().getColor(R.color.red));
                    menuItem.setEnabled(false);
                }
                tv_set_carbohydrate.setText(formatter.format(T_heat * numberPicker_carbohydrate.getValue() / 100 / 4) + " g");
                tv_set_protein.setText(formatter.format(T_heat * numberPicker_protein.getValue() / 100 / 4) + " g");
                tv_set_fat.setText(formatter.format(T_heat * numberPicker_fat.getValue() / 100 / 9) + " g");
                tv_total_nurtition.setText(String.valueOf(total_ratio) + " %");

            }
        };
        numberPicker_carbohydrate.setOnValueChangedListener(onValueChangeListener);
        numberPicker_protein.setOnValueChangedListener(onValueChangeListener);
        numberPicker_fat.setOnValueChangedListener(onValueChangeListener);

    }


    public void onClick(View view) {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nurtition_to_add, menu);
        menuItem = menu.findItem(R.id.nav_add_nurtition);

        return true;
    }

    //menutItem選擇
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.nav_add_nurtition:
                ContentValues cv = new ContentValues();
                // 加入ContentValues物件包裝的新增資料
                // 第一個參數是欄位名稱， 第二個參數是欄位的資料
                cv.put(Item_User_Data.CARBOHYDRATE_RATIO_COLUMN, (double) numberPicker_carbohydrate.getValue() / 100);
                cv.put(Item_User_Data.PROTEIN_RATIO_COLUMN, (double) numberPicker_protein.getValue() / 100);
                cv.put(Item_User_Data.FAT_RATIO_COLUMN, (double) numberPicker_fat.getValue() / 100);
                db.update(Item_User_Data.TABLE_NAME, cv, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{account});
                Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();
                finish();

            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}

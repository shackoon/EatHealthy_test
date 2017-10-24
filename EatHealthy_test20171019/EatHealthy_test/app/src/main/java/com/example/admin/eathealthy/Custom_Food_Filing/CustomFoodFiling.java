package com.example.admin.eathealthy.Custom_Food_Filing;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.Data_table.Food_Data;
import com.example.admin.eathealthy.Data_table.MyDBHelper;

public class CustomFoodFiling extends AppCompatActivity {

    private Bundle bundle;
    private String Account;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private EditText et_foodname, et_perunit, et_unit, et_heat, et_protein, et_fat, et_carbohydrate, et_na, et_k, et_fiber, et_ca, et_mg, et_fe, et_zn, et_p;
    private String foodname, perunit, unit, heat, protein, fat, carbohydrate, na, k, fiber, ca, mg, fe, zn, p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_food_filing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FindView();
        bundle = getIntent().getExtras();
        Account = bundle.getString("Account");
        //搜尋使用者基本資料
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    public void FindView() {
        et_foodname = (EditText) findViewById(R.id.et_customfood_name);
        et_perunit = (EditText) findViewById(R.id.et_customfood_perunit);
        et_unit = (EditText) findViewById(R.id.et_customfood_unit);
        et_heat = (EditText) findViewById(R.id.et_customfood_heat);
        et_protein = (EditText) findViewById(R.id.et_customfood_protein);
        et_fat = (EditText) findViewById(R.id.et_customfood_fat);
        et_carbohydrate = (EditText) findViewById(R.id.et_customfood_carbohydrate);
        et_na = (EditText) findViewById(R.id.et_customfood_na);
        et_k = (EditText) findViewById(R.id.et_customfood_k);
        et_fiber = (EditText) findViewById(R.id.et_customfood_fiber);
        et_ca = (EditText) findViewById(R.id.et_customfood_ca);
        et_mg = (EditText) findViewById(R.id.et_customfood_mg);
        et_fe = (EditText) findViewById(R.id.et_customfood_fe);
        et_zn = (EditText) findViewById(R.id.et_customfood_zn);
        et_p = (EditText) findViewById(R.id.et_customfood_p);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_custom_to_add, menu);
        return true;
    }

    //menutItem選擇
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //取得使用者輸入值
        foodname = et_foodname.getText().toString();
        perunit = et_perunit.getText().toString();
        unit = et_unit.getText().toString();
        heat = et_heat.getText().toString();
        protein = et_protein.getText().toString();
        fat = et_fat.getText().toString();
        carbohydrate = et_carbohydrate.getText().toString();
        na = et_na.getText().toString();
        k = et_k.getText().toString();
        fiber = et_fiber.getText().toString();
        ca = et_ca.getText().toString();
        mg = et_mg.getText().toString();
        fe = et_fe.getText().toString();
        zn = et_zn.getText().toString();
        p = et_p.getText().toString();

        //判斷輸入格式是否正確
        if (protein.equals("")) {
            protein = "0";
        }
        if (fat.equals("")) {
            fat = "0";
        }
        if (carbohydrate.equals("")) {
            carbohydrate = "0";
        }
        if (na.equals("")) {
            na = "0";
        }
        if (k.equals("")) {
            k = "0";
        }
        if (fiber.equals("")) {
            fiber = "0";
        }
        if (ca.equals("")) {
            ca = "0";
        }
        if (mg.equals("")) {
            mg = "0";
        }
        if (fe.equals("")) {
            fe = "0";
        }
        if (zn.equals("")) {
            zn = "0";
        }
        if (p.equals("")) {
            p = "0";
        }

        if (foodname.equals("")) {
            notify_dialog("必須輸入食品名稱");
        } else if (perunit.equals("")) {
            notify_dialog("必須輸入食品的每一份量");
        } else if (unit.equals("")) {
            notify_dialog("必須輸入食品的每一份量");
        } else if (heat.equals("")) {
            notify_dialog("請於必填欄位輸入數值");
        } else {
            // 建立準備新增資料的ContentValues物件
            ContentValues cv = new ContentValues();
            // 加入ContentValues物件包裝的新增資料
            // 第一個參數是欄位名稱， 第二個參數是欄位的資料
            cv.put(Food_Data.ID_COLUMN, "Custom");
            cv.put(Food_Data.CLASSIFICATION_COLUMN, "使用者自訂食品");
            cv.put(Food_Data.FOODNAME_COLUMN, foodname + "(" + Account + ")");
            cv.put(Food_Data.HEAT_COLUMN, heat);
            cv.put(Food_Data.RHEAT_COLUMN, "0");
            cv.put(Food_Data.PROTEIN_COLUMN, protein);
            cv.put(Food_Data.FAT_COLUMN, fat);
            cv.put(Food_Data.CARBOHYDRATE_COLUMN, carbohydrate);
            cv.put(Food_Data.FIBER_COLUMN, fiber);
            cv.put(Food_Data.NA_COLUMN, na);
            cv.put(Food_Data.K_COLUMN, k);
            cv.put(Food_Data.CA_COLUMN, ca);
            cv.put(Food_Data.MG_COLUMN, mg);
            cv.put(Food_Data.FE_COLUMN, fe);
            cv.put(Food_Data.Zn_COLUMN, zn);
            cv.put(Food_Data.P_COLUMN, p);
            cv.put(Food_Data.CU_COLUMN, "0");
            cv.put(Food_Data.PERUNIT_COLUMN, perunit);
            cv.put(Food_Data.UNIT_COLUMN, unit);
            // 新增一筆資料並取得編號
            // 第一個參數是表格名稱
            // 第二個參數是沒有指定欄位值的預設值
            // 第三個參數是包裝新增資料的ContentValues物件
            db.insert(Food_Data.TABLE_NAME, null, cv);
            Toast.makeText(this, "新增成功"+Account, Toast.LENGTH_SHORT).show();
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    public void notify_dialog(String msg) {

        AlertDialog.Builder dialog_list = new AlertDialog.Builder(this);
        dialog_list.setTitle("錯誤");
        //dialog_list.setIcon(R.mipmap.ic_trash);
        dialog_list.setMessage(msg);
        dialog_list.setPositiveButton("關閉", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });


        dialog_list.show();
    }

}

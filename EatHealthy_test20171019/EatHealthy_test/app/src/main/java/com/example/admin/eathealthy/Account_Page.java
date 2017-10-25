package com.example.admin.eathealthy;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.admin.eathealthy.Data_table.Item_User_Data;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.Data_table.User_Nutritional_Goals;
import com.example.admin.eathealthy.Data_table.User_Picture_Data;
import com.example.admin.eathealthy.Tabbed.TodayEatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Account_Page extends AppCompatActivity {
    TabHost tab;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private EditText account;
    private EditText password;
    private EditText check_password;
    private EditText name;
    private EditText age;
    private EditText height;
    private EditText weight;
    private RadioGroup sex;
    private EditText login_account;
    private EditText login_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        imporDatabase();
        String islogin = getSharedPreferences("Login_info", MODE_PRIVATE).getString("Account", "");
        if (islogin != "") {
            //Toast.makeText(this,islogin,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, com.example.admin.eathealthy.Nav.MainActivity.class);
            //傳值
            Bundle bundle = new Bundle();
            bundle.putString("Account", islogin);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

        tab = (TabHost) findViewById(R.id.testhost);
        tab.setup();

        TabHost.TabSpec spec1 = tab.newTabSpec("登入");
        spec1.setIndicator("登入");
        spec1.setContent(R.id.login);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("註冊");
        spec2.setIndicator("註冊");
        spec2.setContent(R.id.registered);
        tab.addTab(spec2);
        tab.setCurrentTab(0);
        //------------------------------------------------------------------------------------------------------
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();
    }

    public void imporDatabase() {
        // com.test.db 是程序的包名，请根据自己的程序调整
        // /data/data/com.test.db/
        // databases 目录是准备放 SQLite 数据库的地方，也是 Android 程序默认的数据库存储目录
        // 数据库名为 test.db
        String DB_PATH = "/data/data/com.example.admin.eathealthy/databases/";
        String DB_NAME = "database.db";

        // 检查 SQLite 数据库文件是否存在
        if ((new File(DB_PATH + DB_NAME)).exists() == false) {
            // 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
            File f = new File(DB_PATH);
            // 如 database 目录不存在，新建该目录
            if (!f.exists()) {
                f.mkdir();
            }

            try {
                // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
                InputStream is = this.getResources().openRawResource(R.raw.database);
                // 输出流
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

                // 文件写入
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                Toast.makeText(this, "copy", Toast.LENGTH_SHORT).show();
                // 关闭文件流
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public void FindRegisterViews() {
        account = (EditText) findViewById(R.id.registered_account);
        password = (EditText) findViewById(R.id.registered_password);
        check_password = (EditText) findViewById(R.id.check_registered_password);
        name = (EditText) findViewById(R.id.registered_name);
        age = (EditText) findViewById(R.id.registered_age);
        height = (EditText) findViewById(R.id.registered_tall);
        weight = (EditText) findViewById(R.id.registered_weight);
        sex = (RadioGroup) findViewById(R.id.bg);
    }

    public void FindLoginView() {
        login_account = (EditText) findViewById(R.id.editText_accout);
        login_password = (EditText) findViewById(R.id.editText_password);
    }

    public void ClearRegisterViews() {
        account.setText("");
        password.setText("");
        check_password.setText("");
        name.setText("");
        age.setText("");
        height.setText("");
        weight.setText("");

    }


    public void toLogin(View view) {
        FindLoginView();
        String login_ac = login_account.getText().toString();
        String login_pa = login_password.getText().toString();
        Cursor c = db.query("User_Basic_Data_Table", new String[]{"Password"}, "Account=?", new String[]{login_ac}, null, null, null);
        c.moveToFirst();


        if (login_ac.equals("")) {
            Toast.makeText(this, "帳號不能為空！", Toast.LENGTH_LONG).show();
        } else if (login_pa.equals("")) {
            Toast.makeText(this, "密碼不能為空！", Toast.LENGTH_LONG).show();
        } else if (c.getCount() == 0) {
            Toast.makeText(this, "帳號不存在！", Toast.LENGTH_LONG).show();
        } else if (!login_pa.equals(c.getString(0))) {
            Toast.makeText(this, "密碼錯誤！", Toast.LENGTH_LONG).show();
        } else if (c.getCount() > 0 && login_pa.equals(c.getString(0))) {
            // 建立準備新增資料的ContentValues物件
            ContentValues cv = new ContentValues();
            // 加入ContentValues物件包裝的新增資料
            // 第一個參數是欄位名稱， 第二個參數是欄位的資料
            //cv.put("Login", "yes");
            //有問題
            //db.update(Item_User_Data.TABLE_NAME, cv, "Account=" + login_ac, null);
            SharedPreferences pref = getSharedPreferences("Login_info", MODE_PRIVATE);
            pref.edit().putString("Account", login_ac).commit();


            Intent intent = new Intent(this, com.example.admin.eathealthy.Nav.MainActivity.class);
            //傳值
            Bundle bundle = new Bundle();
            bundle.putString("Account", login_ac);
            intent.putExtras(bundle);

            startActivity(intent);

            Toast.makeText(this, "登入成功！" + c.getString(0), Toast.LENGTH_LONG).show();
            finish();
        }


    }

    public void toRegister(View view) {
        FindRegisterViews();
        String ac = "";
        String pa = "";
        String check_pa = "";
        String na = "";
        int se = 0;
        View radioButton;
        int idx = 0; //判斷radiobtn按的位置
        double he = 0;
        double we = 0;
        int ag = 0;
        double BMI = 0;
        String login_state = "n";


        if (account.getText().toString().equals("")) {
            Toast.makeText(this, "帳號不能為空", Toast.LENGTH_LONG).show();

        } else if (password.getText().toString().equals("")) {
            Toast.makeText(this, "密碼不能為空", Toast.LENGTH_LONG).show();

        } else if (check_password.getText().toString().equals("")) {
            Toast.makeText(this, "確認密碼不能為空", Toast.LENGTH_LONG).show();

        } else if (name.getText().toString().equals("")) {
            Toast.makeText(this, "姓名不能為空", Toast.LENGTH_LONG).show();

        } else if (height.getText().toString().equals("")) {
            Toast.makeText(this, "身高不能為空", Toast.LENGTH_LONG).show();

        } else if (weight.getText().toString().equals("")) {
            Toast.makeText(this, "體重不能為空", Toast.LENGTH_LONG).show();

        } else if (age.getText().toString().equals("")) {
            Toast.makeText(this, "年齡不能為空", Toast.LENGTH_LONG).show();
        } else {

            ac = account.getText().toString();
            pa = password.getText().toString();
            check_pa = check_password.getText().toString();
            na = name.getText().toString();
            se = sex.getCheckedRadioButtonId();
            radioButton = sex.findViewById(se);
            idx = sex.indexOfChild(radioButton); //判斷radiobtn按的位置
            he = Double.parseDouble(height.getText().toString());
            we = Double.parseDouble(weight.getText().toString());
            ag = Integer.parseInt(age.getText().toString());
            BMI = we / ((he / 100) * (he / 100));
            Cursor c = db.query("User_Basic_Data_Table", null, "Account=?", new String[]{ac}, null, null, null);
            if (c.getCount() > 0) {
                Toast.makeText(this, "帳號已存在!", Toast.LENGTH_LONG).show();
            } else if (!pa.equals(check_pa)) {
                Toast.makeText(this, "確認密碼不相同!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "註冊成功", Toast.LENGTH_LONG).show();

                // 建立準備新增資料的ContentValues物件
                ContentValues cv = new ContentValues();

                // 加入ContentValues物件包裝的新增資料
                // 第一個參數是欄位名稱， 第二個參數是欄位的資料
                cv.put("Account", ac);
                cv.put("Password", pa);
                cv.put("Name", na);
                cv.put("Sex", idx); //
                cv.put("Height", he);
                cv.put("Weight", we);
                cv.put("Age", ag);//
                cv.put("BMI", BMI);//
                cv.put("Other", "test");
                cv.put("Login", "no");
                cv.put(Item_User_Data.FACTOR_COLUMN, "1.3");
                cv.put(Item_User_Data.PROTEIN_RATIO_COLUMN, "0.12");
                cv.put(Item_User_Data.FAT_RATIO_COLUMN, "0.25");
                cv.put(Item_User_Data.CARBOHYDRATE_RATIO_COLUMN, "0.63");
                // 新增一筆資料並取得編號
                // 第一個參數是表格名稱
                // 第二個參數是沒有指定欄位值的預設值
                // 第三個參數是包裝新增資料的ContentValues物件
                long id = db.insert("User_Basic_Data_Table", null, cv);
                Log.d("ADD", id + "");
                db.execSQL("CREATE TABLE IF NOT EXISTS " + User_Food_Data.TABLE_NAME + ac + User_Food_Data.User_Food_Data_TABLE);
                db.execSQL("CREATE TABLE IF NOT EXISTS " + User_Nutritional_Goals.TABLE_NAME + ac + User_Nutritional_Goals.User_Nutritional_Goals_TABLE);
                db.execSQL("CREATE TABLE IF NOT EXISTS " + User_Picture_Data.TABLE_NAME + ac + User_Picture_Data.User_Picture_TABLE);
                ClearRegisterViews();
                tab.setCurrentTab(0);
            }
        }


    }
}

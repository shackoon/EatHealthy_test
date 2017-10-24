package com.example.admin.eathealthy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.eathealthy.Data_table.MyDBHelper;

public class PersonalInformation extends AppCompatActivity {

    private MyDBHelper helper;
    private SQLiteDatabase db;
    private TextView name;
    private TextView age;
    private TextView height;
    private TextView weight;
    private TextView BMI;
    private AutoCompleteTextView autoCompleteTextView;
    private Context context = this;
    private Cursor cursor;

    Bundle bundle;
    String Account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();

        bundle = getIntent().getExtras();
        Account = bundle.getString("Account");

        FindInforViews();
        setupAdapter();


        Cursor c = db.query("User_Basic_Data_Table", new String[]{"Name", "age", "Height", "Weight", "BMI"}, "Account=?", new String[]{Account}, null, null, null);
        c.moveToFirst();
        name.setText(c.getString(0));
        age.setText(c.getString(1));
        height.setText(c.getString(2));
        weight.setText(c.getString(3));
        BMI.setText(c.getString(4));

    }

    private void setupAdapter() {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[]{"Name"}, new int[]{android.R.id.text1}, 0);
        autoCompleteTextView.setAdapter(adapter);

        //autocompleteadapter一改變就進到這個函式，可以自己定義要做甚麼，這裡做模糊搜尋 -----OK
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence str) {
                //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                return getCursor(str);
            }
        });

        //當我們選擇項目時使用它，執行此函數將典籍的點轉換為String。 ------已OK
        adapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            public CharSequence convertToString(Cursor cur) {
                int index = cur.getColumnIndex("Name");
                return cur.getString(index);
            }
        });
        //
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index =cursor.getColumnIndex("Name");
                String now_select=cursor.getString(index);
                Toast.makeText(context, "position=" + Integer.toString(position) + " id=" + now_select, Toast.LENGTH_SHORT).show();

            }
        });




    }

    //查詢，並傳回結果的函式
    public Cursor getCursor(CharSequence str) {
        String select = "Name" + " LIKE ?";
        String[] selectArgs = {"%" + str + "%"};
        cursor=db.query("food", null, select, selectArgs, null, null, null);
        return cursor;
    }


    public void FindInforViews() {

        name = (TextView) findViewById(R.id.info_name);
        age = (TextView) findViewById(R.id.info_age);
        height = (TextView) findViewById(R.id.info_height);
        weight = (TextView) findViewById(R.id.info_weight);
        BMI = (TextView) findViewById(R.id.info_BMI);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
    }
}

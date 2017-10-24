package com.example.admin.eathealthy;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.admin.eathealthy.Data_table.MyDBHelper;

public class List_table extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);

        ListView list = (ListView) findViewById(R.id.list);
        MyDBHelper helper = new MyDBHelper(this);
        Cursor c = helper.getReadableDatabase().query("User_Basic_Data_Table", null, null, null, null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.basic_data_list,
                c,
                new String[] {"_id", "Account","Password","Login"},
                new int[] {R.id.list_id,R.id.list_Account,R.id.list_Password,R.id.list_Name}, 0);
        list.setAdapter(adapter);
    }
}

package com.example.admin.eathealthy.Nav;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.admin.eathealthy.CustomItem.PersonInfo;
import com.example.admin.eathealthy.Nav.PersonalNurtitionRatio.SetPersonalNurtitionRatio;
import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.RecycleViewCustomAdapter.PersonInfoAdapter;
import com.example.admin.eathealthy.Update_Data.Update_Today_Nutrition;
import com.example.admin.eathealthy.Data_table.Item_User_Data;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.recyevent.OnRecyclerItemClickListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PersonalFragment extends Fragment {

    private static final String ACCOUNT = "Account";
    private String account;

    private RecyclerView recyclerView;
    private PersonInfoAdapter myAdapter;
    protected ArrayList<PersonInfo> Dataset = new ArrayList<>();
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Cursor c;
    private Update_Today_Nutrition update_today_nutrition;

    private final String NAME_COLUMN = "姓名";
    private final String SEX_COLUMN = "性別";
    private final String HEIGHT_COLUMN = "身高";
    private final String WEIGHT_COLUMN = "體重";
    private final String AGE_COLUMN = "年齡";
    private final String BMI_COLUMN = "BMI";
    private final String FACTOR_COLUMN = "活動因子";
    private final String PROTEIN_RATIO_COLUMN = "蛋白質比例";
    private final String FAT_RATIO_COLUMN = "脂肪比例";
    private final String CARBOHYDRATE_RATIO_COLUMN = "碳水化合物比例";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = getArguments().getString(ACCOUNT);
        }
        helper = new MyDBHelper(getContext());
        db = helper.getWritableDatabase();
        update_today_nutrition = new Update_Today_Nutrition(account, getContext());
        init_person_data();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_personal, container, false);
        myAdapter = new PersonInfoAdapter(Dataset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext()); //設定此 layoutManager 為 linearlayout (類似ListView)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); //設定此 layoutManager 為垂直堆疊
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_Personal);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL)); //設定分割線
        recyclerView.setLayoutManager(layoutManager); //設定 LayoutManager
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);//設定 Adapter
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                //Toast.makeText(getContext(), Dataset.get(viewHolder.getAdapterPosition()).getColumnName(), Toast.LENGTH_SHORT).show();
                switch (Dataset.get(viewHolder.getAdapterPosition()).getColumnName()) {
                    case NAME_COLUMN:
                        break;
                    case SEX_COLUMN:
                        break;
                    case HEIGHT_COLUMN:
                        moifyDialog(HEIGHT_COLUMN, "修改" + HEIGHT_COLUMN, Integer.parseInt(c.getString(c.getColumnIndex(Item_User_Data.HEIGHT_COLUMN))), "公分(cm)");

                        break;
                    case WEIGHT_COLUMN:
                        moifyDialog(WEIGHT_COLUMN, "修改" + WEIGHT_COLUMN, Integer.parseInt(c.getString(c.getColumnIndex(Item_User_Data.WEIGHT_COLUMN))), "公斤(kg)");

                        break;
                    case AGE_COLUMN:
                        moifyDialog(AGE_COLUMN, "修改" + AGE_COLUMN, Integer.parseInt(c.getString(c.getColumnIndex(Item_User_Data.AGE_COLUMN))), "歲");

                        break;
                    case FACTOR_COLUMN:


                        final String[] select_factor = {"久坐族／無運動習慣者", "輕度運動者／一周一至三天運動", "中度運動者／一周三至五天運動", "激烈運動者／一周六至七天運動", "超激烈運動者／體力活的工作"}; //輸入List裡須顯示的清單
                        boolean[] checkedItems = new boolean[select_factor.length];
                        AlertDialog.Builder dialog_list = new AlertDialog.Builder(getContext());
                        dialog_list.setTitle("選擇活動量");
                        dialog_list.setItems(select_factor, new DialogInterface.OnClickListener() {
                            @Override
                            //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
                            public void onClick(DialogInterface dialog, int which) {
                                Double factor = 0.0;  //目前選擇用餐時間
                                ContentValues cv;
                                switch (select_factor[which]) {
                                    case "久坐族／無運動習慣者":
                                        factor = 1.2;
                                        break;
                                    case "輕度運動者／一周一至三天運動":
                                        factor = 1.375;
                                        break;
                                    case "中度運動者／一周三至五天運動":
                                        factor = 1.55;
                                        break;
                                    case "激烈運動者／一周六至七天運動":
                                        factor = 1.725;
                                        break;
                                    case "超激烈運動者／體力活的工作":
                                        factor = 1.9;
                                        break;
                                    default:
                                        break;
                                }
                                cv = new ContentValues();
                                // 加入ContentValues物件包裝的新增資料
                                // 第一個參數是欄位名稱， 第二個參數是欄位的資料


                                cv.put(Item_User_Data.FACTOR_COLUMN, factor);
                                db.update(Item_User_Data.TABLE_NAME, cv, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{account});
                                init_person_data();
                                myAdapter.notifyDataSetChanged();
                                update_today_nutrition.change_Target_Nurtition();
                                //Toast.makeText(getContext(), "你選的是" + select_factor[which], Toast.LENGTH_SHORT).show();


                            }
                        });
                        dialog_list.show();

                        break;
                    case PROTEIN_RATIO_COLUMN:
                    case FAT_RATIO_COLUMN:
                    case CARBOHYDRATE_RATIO_COLUMN:
                        Intent intent = new Intent(getContext(), SetPersonalNurtitionRatio.class);
                        //傳值
                        Bundle TransUserInfo = new Bundle();
                        TransUserInfo.putString("Account", account);
                        intent.putExtras(TransUserInfo);
                        startActivity(intent);
                        break;


                }

            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void init_person_data() {
        NumberFormat formatter = new DecimalFormat("#0");

        c = db.query(Item_User_Data.TABLE_NAME, null, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{account}, null, null, null);
        c.moveToFirst();
        Dataset.clear();
        Dataset.add(new PersonInfo(NAME_COLUMN, c.getString(c.getColumnIndex(Item_User_Data.NAME_COLUMN))));
        Dataset.add(new PersonInfo(SEX_COLUMN, c.getInt(c.getColumnIndex(Item_User_Data.SEX_COLUMN)) == 0 ? "男生" : "女生"));
        Dataset.add(new PersonInfo(HEIGHT_COLUMN, c.getString(c.getColumnIndex(Item_User_Data.HEIGHT_COLUMN)) + "公分"));
        Dataset.add(new PersonInfo(WEIGHT_COLUMN, c.getString(c.getColumnIndex(Item_User_Data.WEIGHT_COLUMN)) + "公斤"));
        Dataset.add(new PersonInfo(AGE_COLUMN, c.getString(c.getColumnIndex(Item_User_Data.AGE_COLUMN)) + "歲"));
        Dataset.add(new PersonInfo(BMI_COLUMN, c.getString(c.getColumnIndex(Item_User_Data.BMI_COLUMN))));
        Dataset.add(new PersonInfo(FACTOR_COLUMN, c.getString(c.getColumnIndex(Item_User_Data.FACTOR_COLUMN))));
        formatter.format(c.getDouble(c.getColumnIndex(Item_User_Data.PROTEIN_RATIO_COLUMN)) * 100);
        Dataset.add(new PersonInfo(PROTEIN_RATIO_COLUMN, formatter.format(c.getDouble(c.getColumnIndex(Item_User_Data.PROTEIN_RATIO_COLUMN)) * 100) + "%"));
        Dataset.add(new PersonInfo(FAT_RATIO_COLUMN, formatter.format(c.getDouble(c.getColumnIndex(Item_User_Data.FAT_RATIO_COLUMN)) * 100) + "%"));
        Dataset.add(new PersonInfo(CARBOHYDRATE_RATIO_COLUMN, formatter.format(c.getDouble(c.getColumnIndex(Item_User_Data.CARBOHYDRATE_RATIO_COLUMN)) * 100) + "%"));


    }


    public void moifyDialog(final String from, String title, int now_number, final String unit) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View theView = inflater.inflate(R.layout.dialog_height_weight_age, null);
        // find view
        final NumberPicker unit_euro = (NumberPicker) theView.findViewById(R.id.euro_picker);
        final NumberPicker cent = (NumberPicker) theView.findViewById(R.id.cent_picker);
        final TextView tv_unit = (TextView) theView.findViewById(R.id.tv_dialog_unit);
        tv_unit.setText(unit);
        builder.setTitle(title);
        //設定numberpicker範圍
        unit_euro.setMinValue(1);
        unit_euro.setMaxValue(250);
        unit_euro.setValue(now_number);

        builder.setView(theView)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double height, weight;
                        String bmi;
                        NumberFormat formatter = new DecimalFormat("#0.0");
                        ContentValues cv;
                        switch (from) {
                            case HEIGHT_COLUMN:
                                cv = new ContentValues();
                                cv.put(Item_User_Data.HEIGHT_COLUMN, (double) unit_euro.getValue());
                                db.update(Item_User_Data.TABLE_NAME, cv, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{account});
                                //Toast.makeText(getContext(), String.valueOf(unit_euro.getValue()), Toast.LENGTH_SHORT).show();
                                break;
                            case WEIGHT_COLUMN:
                                cv = new ContentValues();
                                cv.put(Item_User_Data.WEIGHT_COLUMN, (double) unit_euro.getValue());
                                db.update(Item_User_Data.TABLE_NAME, cv, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{account});
                                break;
                            case AGE_COLUMN:
                                cv = new ContentValues();
                                cv.put(Item_User_Data.AGE_COLUMN, unit_euro.getValue());
                                db.update(Item_User_Data.TABLE_NAME, cv, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{account});
                                break;
                            default:
                                break;
                        }
                        //重算bmi
                        cv = new ContentValues();
                        c = db.query(Item_User_Data.TABLE_NAME, null, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{account}, null, null, null);
                        c.moveToFirst();
                        height = c.getDouble(c.getColumnIndex(Item_User_Data.HEIGHT_COLUMN));
                        weight = c.getDouble(c.getColumnIndex(Item_User_Data.WEIGHT_COLUMN));
                        bmi = formatter.format(weight / ((height / 100) * (height / 100)));
                        cv.put(Item_User_Data.BMI_COLUMN, bmi);
                        db.update(Item_User_Data.TABLE_NAME, cv, Item_User_Data.ACCOUNT_COLUM + "=?", new String[]{account});

                        init_person_data();
                        myAdapter.notifyDataSetChanged();
                        update_today_nutrition.change_Target_Nurtition();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    //更改資料回來時更新資料!
    @Override
    public void onResume() {
        super.onResume();
        init_person_data();
        myAdapter.notifyDataSetChanged();
        update_today_nutrition.change_Target_Nurtition();
    }

}

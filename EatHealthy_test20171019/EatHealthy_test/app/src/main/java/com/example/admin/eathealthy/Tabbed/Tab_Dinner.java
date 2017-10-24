package com.example.admin.eathealthy.Tabbed;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.eathealthy.AlterFood;
import com.example.admin.eathealthy.CustomItem.Ntrition;
import com.example.admin.eathealthy.CustomItem.UserPicture;
import com.example.admin.eathealthy.Data_table.User_Picture_Data;
import com.example.admin.eathealthy.GetData.Get_Food_Data;
import com.example.admin.eathealthy.R;
import com.example.admin.eathealthy.RecycleViewCustomAdapter.AddFoodAdapter;
import com.example.admin.eathealthy.RecycleViewCustomAdapter.UserPictureAdapter;
import com.example.admin.eathealthy.Update_Data.Update_Today_Nutrition;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.recyevent.EmptyRecyclerView;
import com.example.admin.eathealthy.recyevent.OnRecyclerItemClickListener;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.BitmapCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Tab_Dinner extends Fragment {
    private static UserPictureAdapter userPictureAdapter;
    private static RecyclerView pic_recyclerview;
    protected static ArrayList<UserPicture> picDataset = new ArrayList<>();
    private static AddFoodAdapter myAdapter;
    protected static ArrayList<Ntrition> Dataset = new ArrayList<>();
    public final static String TAB_DINNER = "dinner";
    private Update_Today_Nutrition update_today_nutrition;
    private Bundle bundle;
    private String Account;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor_food_quantity;
    private String now_select;
    private SimpleDateFormat formatter;
    private Date curDate;
    private String nowTime;
    protected String select_time;//新增、刪除時上面的時間
    Get_Food_Data getUserFoodData;
    //----------------------------------
    private EmptyRecyclerView mRecyclerView;
    private View mEmptyView;

    private static final int RESULT_OK = -1;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageFileLocation = "";
    //--------------test-----------------------------------------------------
    protected UserTodayEat userTodayEat;
    protected String nowtime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        Account = bundle.getString("Account");
        helper = new MyDBHelper(getContext());
        db = helper.getWritableDatabase();
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        nowTime = formatter.format(curDate);
        //test-----------------------------------------------------------------
        nowtime = bundle.getString("nowtime");
        userTodayEat = new UserTodayEat(Account, TAB_DINNER, getContext());

        Dataset.clear();
        Dataset.addAll(userTodayEat.getFoodListData(nowtime));
        picDataset.clear();
        picDataset.addAll(userTodayEat.getPictureData(nowtime));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fab_breakfast, container, false);
        //Toast.makeText(getContext(), Account + "!!", Toast.LENGTH_SHORT).show();

        FindView(view);
        setFoodRecyclerView(view);
        setPicRecyclerView(view);


        return view;
    }

    public void FindView(View view) {
        mRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.breakfast_emptyRecyclerView);
        mEmptyView = view.findViewById(R.id.empty_view);
        pic_recyclerview = view.findViewById(R.id.recycler_picture);

    }

    public void setFoodRecyclerView(View view) {


        myAdapter = new AddFoodAdapter(Dataset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext()); //設定此 layoutManager 為 linearlayout (類似ListView)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); //設定此 layoutManager 為垂直堆疊
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setEmptyView(mEmptyView);

        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                //按一下更改食物數量
                now_select = Dataset.get(viewHolder.getAdapterPosition()).getName();
                Intent intent = new Intent(getContext(), AlterFood.class);
                Bundle trans_account = new Bundle();
                trans_account.putString("Food", now_select);
                trans_account.putString("UserName", Account);
                trans_account.putString("select_time", TAB_DINNER);
                trans_account.putString("select_date", select_time);
                trans_account.putDouble("Copy", Dataset.get(viewHolder.getAdapterPosition()).getQuantity());
                intent.putExtras(trans_account);
                startActivity(intent);

            }

            @Override
            public void onLongClick(final RecyclerView.ViewHolder viewHolder) { //List常按
                now_select = Dataset.get(viewHolder.getAdapterPosition()).getName();
                final String key_id = Dataset.get(viewHolder.getAdapterPosition()).getKey_id();
                AlertDialog.Builder dialog_list = new AlertDialog.Builder(getContext());
                dialog_list.setTitle("刪除飲食紀錄");
                dialog_list.setIcon(R.mipmap.ic_trash);
                dialog_list.setMessage("是否刪除：" + Dataset.get(viewHolder.getAdapterPosition()).getName() + " 此筆紀錄?");
                dialog_list.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        db.delete(User_Food_Data.TABLE_NAME + Account, User_Food_Data.FOOD_NAME_COLUMN + " =? And " + User_Food_Data.DATE_COLUMN + " =? And " + User_Food_Data.KEY_ID + "=?", new String[]{now_select, select_time, key_id});
                        update_today_nutrition = new Update_Today_Nutrition(Account, select_time, getContext());
                        update_today_nutrition.update();
                        Dataset.remove(viewHolder.getAdapterPosition());

                        myAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                        //Toast.makeText(getContext(), select_time, Toast.LENGTH_SHORT).show();

                    }
                });
                dialog_list.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog_list.show();
            }
        });

    }

    public void setPicRecyclerView(final View view) {

        userPictureAdapter = new UserPictureAdapter(picDataset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext()); //設定此 layoutManager 為 linearlayout (類似ListView)
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); //設定此 layoutManager 為垂直堆疊
        pic_recyclerview.setLayoutManager(layoutManager);
        //pic_recyclerview.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        pic_recyclerview.setAdapter(userPictureAdapter);
        pic_recyclerview.addOnItemTouchListener(new OnRecyclerItemClickListener(pic_recyclerview) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == 0) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
                }
            }

            @Override
            public void onLongClick(final RecyclerView.ViewHolder viewHolder) {

                if (viewHolder.getAdapterPosition() == 0) {

                } else {

                    final String key_id = picDataset.get(viewHolder.getAdapterPosition()).getKey_id();
                    AlertDialog.Builder dialog_list = new AlertDialog.Builder(getContext());
                    dialog_list.setTitle("刪除照片");
                    dialog_list.setIcon(R.mipmap.ic_trash);
                    dialog_list.setMessage("是否刪此照片?");
                    dialog_list.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            db.delete(User_Picture_Data.TABLE_NAME + Account, User_Picture_Data.KEY_ID + " =?", new String[]{key_id});
                            picDataset.remove(viewHolder.getAdapterPosition());
                            userPictureAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                            //Toast.makeText(getContext(), select_time, Toast.LENGTH_SHORT).show();

                        }
                    });
                    dialog_list.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog_list.show();

                }

            }
        });
    }


    public void setDataset(ArrayList<Ntrition> Dataset) {
        this.Dataset.clear();
        this.Dataset.addAll(Dataset);
        if (myAdapter != null) {
            myAdapter.notifyDataSetChanged();
        }
    }

    public void initDataset(ArrayList<Ntrition> Dataset) {
        this.Dataset.clear();
        this.Dataset.addAll(Dataset);
    }


    public void setPicDataset(ArrayList<UserPicture> picDataset) {
        this.picDataset.clear();
        this.picDataset.addAll(picDataset);
        if (myAdapter != null) {
            userPictureAdapter.notifyDataSetChanged();
//            userPictureAdapter.notifyItemInserted(userPictureAdapter.getItemCount());
        }
    }

    public void initPicDataset(ArrayList<UserPicture> picDataset) {
        this.picDataset.clear();
        this.picDataset.addAll(picDataset);
    }


    @Override
    public void onResume() {
        super.onResume();
        // Toast.makeText(getContext(), "onResume", Toast.LENGTH_SHORT).show()

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callCameraApp();
            } else {
                Toast.makeText(getContext(), "您沒有取得權限", Toast.LENGTH_SHORT).show();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void callCameraApp() {
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String authorities = getContext().getPackageName() + ".fileprovider";
        Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, photoFile);
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);

    }

    File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);
        mImageFileLocation = image.getAbsolutePath();

        return image;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {


            Tiny.BitmapCompressOptions options = new Tiny.BitmapCompressOptions();
            Tiny.getInstance().source(mImageFileLocation).asBitmap().withOptions(options).compress(new BitmapCallback() {
                @Override
                public void callback(boolean isSuccess, Bitmap bitmap) {
                    ContentValues cv = new ContentValues();
                    cv.put(User_Picture_Data.ACCOUNT_COLUMN, Account);
                    cv.put(User_Picture_Data.PICTURE_COLUMN, image_add_toByte(bitmap));
                    cv.put(User_Picture_Data.TITLE_COLUMN, "test");
                    cv.put(User_Picture_Data.DESCRIBE_COLUMN, "123");
                    cv.put(User_Picture_Data.DINING_TIME_COLUMN, TAB_DINNER);
                    cv.put(User_Picture_Data.DATE_COLUMN, select_time);
                    db.insert(User_Picture_Data.TABLE_NAME + Account, null, cv);

                    picDataset.clear();
                    picDataset.addAll(userTodayEat.getPictureData(select_time));
                    userPictureAdapter.notifyItemInserted(userPictureAdapter.getItemCount());
                }
            });

        }
    }


    public byte[] image_add_toByte(Bitmap bit) {//轉成Byte[]，並壓縮圖片
        Bitmap bitmap = bit;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] byteArray = stream.toByteArray();
        String kb = Integer.toString(stream.toByteArray().length / 1024) + "KB";
        Toast.makeText(getContext(), kb, Toast.LENGTH_SHORT).show();
        return byteArray;
    }
}

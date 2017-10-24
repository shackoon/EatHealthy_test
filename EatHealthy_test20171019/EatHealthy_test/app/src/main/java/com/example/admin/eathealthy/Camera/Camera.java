package com.example.admin.eathealthy.Camera;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.admin.eathealthy.Data_table.MyDBHelper;
import com.example.admin.eathealthy.Data_table.User_Food_Data;
import com.example.admin.eathealthy.Data_table.User_Nutritional_Goals;
import com.example.admin.eathealthy.Data_table.User_Picture_Data;
import com.example.admin.eathealthy.R;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.BitmapCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera extends AppCompatActivity {
    private ImageView imageView;
    private Button btn_add, btn_choose;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private Context context;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageFileLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        imageView = (ImageView) findViewById(R.id.imageView);
        btn_choose = (Button) findViewById(R.id.btn_add);
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:


                // 建立準備新增資料的ContentValues物件
                ContentValues cv = new ContentValues();
                // 加入ContentValues物件包裝的新增資料
                // 第一個參數是欄位名稱， 第二個參數是欄位的資料
                cv.put(User_Picture_Data.ACCOUNT_COLUMN, "wowo");
                cv.put(User_Picture_Data.PICTURE_COLUMN, imageViewToByte(imageView));
                cv.put(User_Picture_Data.TITLE_COLUMN, "test");
                cv.put(User_Picture_Data.DESCRIBE_COLUMN, "123");
                cv.put(User_Picture_Data.DINING_TIME_COLUMN, "dinner");
                cv.put(User_Picture_Data.DATE_COLUMN, "2017-10-19");
                db.insert(User_Picture_Data.TABLE_NAME + "wowo", null, cv);
                Toast.makeText(this, "新增成功", Toast.LENGTH_SHORT).show();
                imageView.setImageDrawable(getDrawable(R.drawable.beef));


                break;
            case R.id.btn_choose:
                ActivityCompat.requestPermissions(Camera.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
                break;
            case R.id.btn_list:
                Intent intent = new Intent(this, PictureList.class);
                startActivity(intent);
                break;
        }
    }


    public byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String kb = Integer.toString(stream.toByteArray().length / 1024);
        Toast.makeText(Camera.this, kb, Toast.LENGTH_SHORT).show();
        return byteArray;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, 0);
                callCameraApp();
            } else {
                Toast.makeText(getApplicationContext(), "您沒有取得權限", Toast.LENGTH_SHORT).show();
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
        String authorities = getApplicationContext().getPackageName() + ".fileprovider";
        Uri imageUri = FileProvider.getUriForFile(this, authorities, photoFile);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            //Uri uri = data.getData();
//            try {
////                InputStream inputStream = getContentResolver().openInputStream(uri);
////                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//                //imageView.setImageBitmap(bitmap);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            //rotateImage(setReducedImageSize());
            // Glide.with(this).load(mImageFileLocation).asBitmap().into(imageView);

            Tiny.BitmapCompressOptions options = new Tiny.BitmapCompressOptions();
            options.height = 960;//some compression configuration.
            options.width = 1280;//some compression configuration.

            Tiny.getInstance().source(mImageFileLocation).asBitmap().withOptions(options).compress(new BitmapCallback() {
                @Override
                public void callback(boolean isSuccess, Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    String bit = Integer.toString(bitmap.getByteCount() / 1024);
                    Toast.makeText(Camera.this, bit, Toast.LENGTH_SHORT).show();
                }
            });

            //Glide.with(this).load(mImageFileLocation).asBitmap().into(imageView);
            Toast.makeText(this, mImageFileLocation, Toast.LENGTH_SHORT).show();

        }


//        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//        imageView.setImageBitmap(bitmap);


    }


}

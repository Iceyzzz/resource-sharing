package zt.com.resourcesharing.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;
import zt.com.resourcesharing.utils.HttpUtil;

import static zt.com.resourcesharing.utils.DateConvert.stampToDate;

//个人信息
public class  InfoManageActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tb_info;
    private EditText et_rename;
    private CircleImageView head_info;
    private LinearLayout linear_head;
    private Spinner sp_gender;
    private TextView tv_birthday;
    private Button btn_birthday;
    private EditText et_indiv;
//    private Button btn_upload;
    Context mContext;
    List<String> list=new ArrayList<String>();


    private AlertDialog.Builder builder=null;
    private AlertDialog alert;
    private Uri imageUri;
    public static final int CHOOSE_PHOTO=1;//照片
    public static final int CHOOSE_CAMERA=2;//相机
    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_manage);

        tb_info=(Toolbar)findViewById(R.id.tb_info);
        et_rename=(EditText)findViewById(R.id.et_rename);
        head_info=(CircleImageView)findViewById(R.id.head_info);
        linear_head=(LinearLayout)findViewById(R.id.linear_head);
        sp_gender=(Spinner)findViewById(R.id.sp_gender);
        tv_birthday=(TextView)findViewById(R.id.tv_birthday);
        et_indiv=(EditText)findViewById(R.id.et_indiv);
        btn_birthday=(Button)findViewById(R.id.btn_birthday);
//        btn_upload=(Button)findViewById(R.id.btn_upload);
        setSupportActionBar(tb_info);
        setTitle("");

        btn_birthday.setOnClickListener(this);
        linear_head.setOnClickListener(this);
        head_info.setOnClickListener(this);
//        btn_upload.setOnClickListener(this);

        SharedPreferences pref=getSharedPreferences("myshare",MODE_PRIVATE);
        int userId=pref.getInt("id",-1);
        Log.d("InfoManUserId", String.valueOf(userId));

        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("id", String.valueOf(userId))
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url("http://yiezi.ml/json/viewMember")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        String responseData=response.body().string();
                        Log.d("InfoManage",responseData);
                        try {
                            JSONArray jsonArray=new JSONArray(responseData);
                            JSONObject jsonObject=jsonArray.getJSONObject(1);
                      //      final String img=jsonObject.getString("userImg");

                            final String nick=jsonObject.getString("yhnc");
                            final String gender=jsonObject.getString("xb");
                            final Date birth=stampToDate(jsonObject.getString("csny"));
                           // final Date birth=formatter.parse(jsonObject.getString("csny"));
                            final String indiv=jsonObject.getString("grjj");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                            //        Glide.with(mContext).load(img).into(head_info);
                                    et_rename.setText(nick);
                                    sp_gender.setSelection(list.indexOf(gender));
                                    tv_birthday.setText(formatter.format(birth));
                                    et_indiv.setText(indiv);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


        //准备数据
        list.add("男");
        list.add("女");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        //给适配器设置样式
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        sp_gender.setAdapter(adapter);

    }

    //添加菜单项资源res/menu/toolbar_info.xml 到菜单menu中
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_info,menu);//找到菜单的内容
        return true;
    }
    //给菜单项添加事件处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.finish:
                sendRequestPost();
            //    Toast.makeText(this,"修改成功！",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
/*
    public void showImageDialog(){
        String item[]=new String []{"相册","拍照"};
        builder=new AlertDialog.Builder(InfoManageActivity.this);//显示当前对话框
        alert=builder.setTitle("选择获取图片方式：").setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        imageFromAlbum();
                        break;
                    case 1:
                        imageFromCamera();
                        break;
                }
            }
        }).create();
        alert.show();
    }
    //引用
    //相册方式
        public void imageFromAlbum(){
            //   Toast.makeText(this,"相册",Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(InfoManageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(InfoManageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                openAlbum();
            }
        }
        private void openAlbum(){
            Intent intent=new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent,CHOOSE_PHOTO);//打开相册
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
                case 1:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openAlbum();
                    } else {
                        Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case CHOOSE_CAMERA:
                    if (resultCode == RESULT_OK) {
                        try {
                            // 将拍摄的照片显示出来
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            head_info.setImageBitmap(bitmap);
                            save(bitmap);//将图片存储

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
  //      Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            head_info.setImageBitmap(bitmap);
            save(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
    //拍照方式
    public void imageFromCamera(){
       // Toast.makeText(this,"拍照",Toast.LENGTH_SHORT).show();

        //创建File对象，用于存储拍照后的图片
        File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(InfoManageActivity.this,"zt.com.resourcesharing.fileprovider",outputImage);
        }else {
            imageUri=Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,CHOOSE_CAMERA);
    }

    private void save(Bitmap bitmap){
        SharedPreferences.Editor editor=getSharedPreferences("bitmap_data",MODE_PRIVATE).edit();
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,bos);
        String img=new String(Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT));
        editor.putString("bitmap",img);
        editor.apply();
    }

    */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_birthday:
                new DatePickerDialog(InfoManageActivity.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_birthday.setText(String.format("%d-%d-%d",year,month+1,dayOfMonth));//多个占位符 按顺序
                    }
                },2000,0,1).show();

     /*           DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_birthday.setText(String.format("%d-%d-%d",year,month+1,dayOfMonth));//多个占位符 按顺序
                    }
                };
               DatePickerDialog dialog=new DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT,dateSetListener,1949,10,1);
                dialog.show();*/

                break;
            case R.id.linear_head:
                //showImageDialog();
                break;
            case R.id.head_info:
               // showImageDialog();
                break;

        }
    }

    //更改资料
    private void sendRequestPost(){
        final String reName=et_rename.getText().toString().trim();
        final String gender=sp_gender.getSelectedItem().toString();
        final String birth=tv_birthday.getText().toString();
        final String reIndiv=et_indiv.getText().toString().trim();
        SharedPreferences pref1=getSharedPreferences("myshare",MODE_PRIVATE);
        int userId1=pref1.getInt("id",-1);

        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("id", String.valueOf(userId1))
                .add("yhnc",reName)
                .add("xb",gender)
                .add("csny",birth)
                .add("grjj",reIndiv)
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url("http://yiezi.ml/json/updateMember")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"修改资料连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String str=jsonObject.getString("result");
                        if(str.equals("true")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    et_rename.setText(reName);
                                    sp_gender.setSelection(list.indexOf(gender));
                                    tv_birthday.setText(birth);
                                    et_indiv.setText(reIndiv);
                                    Toast.makeText(getApplicationContext(),"修改成功！",Toast.LENGTH_SHORT).show();

                                }
                            });
                            finish();//销毁活动
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}

package zt.com.resourcesharing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;
import zt.com.resourcesharing.bean.Category;
import zt.com.resourcesharing.utils.HttpUtil;

public class AddMyBlogActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tb_addBlog;
    private Button btn_addBlogBack;
    private EditText et_addTitle;
    private Spinner sp_addCategory;
    private EditText et_addContent;
    private List<Category> listCate=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_my_blog);

        tb_addBlog=(Toolbar)findViewById(R.id.tb_addBlog);
        btn_addBlogBack=(Button)findViewById(R.id.btn_addBlogBack);
        et_addTitle=(EditText)findViewById(R.id.et_addTitle);
        sp_addCategory=(Spinner)findViewById(R.id.sp_addCategory);
        et_addContent=(EditText)findViewById(R.id.et_addContent);

        btn_addBlogBack.setOnClickListener(this);
        setSupportActionBar(tb_addBlog);
        setTitle("");

        HttpUtil.sendOkHttpRequest("http://yiezi.ml/json/browseBlogCategory", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"分类连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            int id=jsonObject.getInt("id");
                            Log.e("AddBlogCateID", String.valueOf(id));
                            String cateName=jsonObject.getString("flmc");
                            Category category=new Category(id,cateName);
                            listCate.add(category);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<Category> adapter=new ArrayAdapter<Category>(AddMyBlogActivity.this,android.R.layout.simple_spinner_item,listCate);
                                //给适配器设置样式
                                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                sp_addCategory.setAdapter(adapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_send,menu);//找到菜单的内容
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.send:
                sendRequestPost();
                break;
        }
        return true;
    }

    private void sendRequestPost(){
        SharedPreferences pref=getSharedPreferences("myshare",MODE_PRIVATE);
        int userId=pref.getInt("id",-1);
        int position=sp_addCategory.getSelectedItemPosition();
        Category category1=listCate.get(position);
        int cateId=category1.getId();
     //   String cateId=sp_addCategory.getSelectedItem().toString();
        String title=et_addTitle.getText().toString().trim();
        String content=et_addContent.getText().toString();
        Log.e("cateId", String.valueOf(cateId));

        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("userId", String.valueOf(userId))
                .add("blogCategoryId", String.valueOf(cateId))
                .add("bwbt",title)
                .add("bwnr",content)
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url("http://yiezi.ml/json/addBlog")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"发表连接失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    Log.e("AddBlogRSD",responseData);
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String str=jsonObject.getString("result");
                        final String msg=jsonObject.getString("message");
                        if(str.equals("true")){
                            Intent intent=new Intent(AddMyBlogActivity.this,BlogNumActivity.class);
                            startActivity(intent);
                            finish();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"发表成功！",Toast.LENGTH_LONG).show();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addBlogBack:
                Intent intent=new Intent(AddMyBlogActivity.this,BlogNumActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}

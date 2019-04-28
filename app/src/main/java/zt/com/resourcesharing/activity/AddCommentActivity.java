package zt.com.resourcesharing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;
//发表评论
public class AddCommentActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tb_addComment;
    private Button btn_addComBack;
    private EditText et_addComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);

        tb_addComment=(Toolbar)findViewById(R.id.tb_addComment);
        btn_addComBack=(Button)findViewById(R.id.btn_addComBack);
        et_addComment=(EditText)findViewById(R.id.et_addComment);

        btn_addComBack.setOnClickListener(this);

        setSupportActionBar(tb_addComment);
        setTitle("");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addComBack:
                //Intent intent=new Intent();
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_send,menu);
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
        SharedPreferences pref=getSharedPreferences("comment",MODE_PRIVATE);
        int commentId=pref.getInt("id",-1);
        String comment=et_addComment.getText().toString();

        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("blogId", String.valueOf(commentId))
                .add("plnr",comment)
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        final Request request=new Request.Builder()
                .url("http://yiezi.ml/addBlogCommentForPhone")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"发表评论连接失败！",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String str=jsonObject.getString("operation");
                        if(str.equals("success")){
//                            Intent intent=new Intent(AddCommentActivity.this,CategoryContentActivity.class);
//                            startActivity(intent);
                            finish();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"评论成功！",Toast.LENGTH_LONG).show();
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
}

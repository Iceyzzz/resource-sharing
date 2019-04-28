package zt.com.resourcesharing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;

/**
 * Created by Administrator on 2018/3/16.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_login;
    private Button btn_log;
    private EditText et_loguser;
    private EditText et_logpwd;
    private Button btn_logback;
//    private static final int UPDATE=1;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tb_login=(Toolbar)findViewById(R.id.tb_login);
        btn_log=(Button)findViewById(R.id.btn_log);
        et_loguser=(EditText)findViewById(R.id.et_loguser);
        et_logpwd=(EditText)findViewById(R.id.et_logpwd);
        btn_logback=(Button)findViewById(R.id.btn_logback);

        btn_logback.setOnClickListener(this);
        btn_log.setOnClickListener(this);


        setSupportActionBar(tb_login);
        setTitle("");


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logback:
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_log:
                sendRequestPost();
                break;
        }
    }
    private void sendRequestPost(){
        final String username=et_loguser.getText().toString().trim();
        final String userpwd=et_logpwd.getText().toString().trim();
        final String message=null;

        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("zh",username)
                .add("dlmm",userpwd)
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url("http://yiezi.ml/signinMember/json")
                .post(requestBody)
                .build();
            client.newCall(request).enqueue(new Callback() {
                 String message=null;
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_loguser.setText("");
                            et_logpwd.setText("");
                            Toast.makeText(getApplicationContext(),"登录连接失败",Toast.LENGTH_LONG).show();
                        }
                    });

                }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if(response.isSuccessful()){
                        try {
                            String responseData=response.body().string();
                            Log.d("LoginActivity",responseData);
                            JSONArray jsonArray=new JSONArray(responseData);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            String str=jsonObject.getString("result");
                            Log.d("LoginActivity",jsonObject.toString());
                            if(str.equals("true")) {
                                JSONObject jsonObject1=jsonArray.getJSONObject(1);
                                int id=jsonObject1.getInt("id");
                                SharedPreferences.Editor editor=getSharedPreferences("myshare",MODE_PRIVATE).edit();//获取对象
                                editor.putInt("id",id);//添加数据
                                editor.apply();//提交数据
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                //Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT).show();
                                message="登录成功！";
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        et_loguser.setText("");
                                        et_logpwd.setText("");
                                    }
                                });
                                //Toast.makeText(getApplicationContext(),"用户名或密码错误！",Toast.LENGTH_LONG).show();
                                message="用户名或密码错误！";
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }

            });
    }
}

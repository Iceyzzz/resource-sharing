package zt.com.resourcesharing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;
import zt.com.resourcesharing.utils.HttpUtil;

public class ChangePWDActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tb_change;
    private Button btn_changeback;
    private EditText et_oldpwd;
    private EditText et_newpwd;
    private EditText et_re_newpwd;
    private Button btn_ok;
    String message=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pwd);

        tb_change=(Toolbar)findViewById(R.id.tb_change);
        btn_changeback=(Button)findViewById(R.id.btn_changeback);
        et_oldpwd=(EditText)findViewById(R.id.et_oldpwd);
        et_newpwd=(EditText)findViewById(R.id.et_newpwd);
        et_re_newpwd=(EditText)findViewById(R.id.et_re_newpwd);
        btn_ok=(Button)findViewById(R.id.btn_ok);

        setSupportActionBar(tb_change);
        setTitle("");

        btn_changeback.setOnClickListener(this);
        btn_ok.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_changeback:
                Intent intent1=new Intent(ChangePWDActivity.this,SettingActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_ok:
                sendRequestPost();
                break;
        }
    }

    private void sendRequestPost(){
        final String oldPwd=et_oldpwd.getText().toString().trim();
        final String newPwd=et_newpwd.getText().toString().trim();
        final String reNewPwd=et_re_newpwd.getText().toString().trim();
        SharedPreferences pref=getSharedPreferences("myshare",MODE_PRIVATE);
        int userId=pref.getInt("id",-1);

        if(newPwd==oldPwd){
            et_oldpwd.setText("");
            et_newpwd.setText("");
            et_re_newpwd.setText("");
            Toast.makeText(getApplicationContext(),"密码不能与之前相同！",Toast.LENGTH_SHORT).show();
        }else {
            //创建一个OkHttpClient实例
            OkHttpClient client=new OkHttpClient();
            //requestBody存放待提交的参数
            RequestBody requestBody=new FormBody.Builder()
                    .add("id", String.valueOf(userId))
                    .add("oldPwd",oldPwd)
                    .add("dlmm",newPwd)
                    .build();
            //创建一个request对象 url()方法设置目标的网络地址
            Request request=new Request.Builder()
                    .url("http://yiezi.ml/json/updateMember")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                String message=null;
                @Override
                public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"更改密码连接失败",Toast.LENGTH_LONG).show();
                            }
                        });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                        String responseData=response.body().string();
                        Log.d("onResponseChange",responseData);
                        try {
                            JSONArray jsonArray=new JSONArray(responseData);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            String str=jsonObject.getString("result");
                            if(str.equals("true")){
                                Intent intent=new Intent(ChangePWDActivity.this,MainActivity.class);
                                startActivity(intent);
                                message="更改成功！";
                            }
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
}

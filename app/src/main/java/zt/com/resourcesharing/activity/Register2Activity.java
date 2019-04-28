package zt.com.resourcesharing.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import zt.com.resourcesharing.utils.CodeVerify;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tb_register;
    private Button btn_regback;
    private EditText et_reguser;
    private EditText et_regnick;
    private EditText et_regpwd;
    private EditText et_regrepwd;
    private TextView tv_rege_birth;
    private Button btn_rege_birth;
    private RadioGroup rg_gender;
    private EditText et_verify;
    private ImageView image_verify;
    private Button btn_reg;
    private String codeStr;//产生验证码
    private CodeVerify codeVerify;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        tb_register=(Toolbar)findViewById(R.id.tb_register);
        btn_regback=(Button)findViewById(R.id.btn_regback);
        et_reguser=(EditText)findViewById(R.id.et_reguser);
        et_regnick=(EditText)findViewById(R.id.et_regnick);
        tv_rege_birth=(TextView) findViewById(R.id.tv_rege_birth);
        btn_rege_birth=(Button)findViewById(R.id.btn_rege_birth);
        et_regpwd=(EditText)findViewById(R.id.et_regpwd);
        et_regrepwd=(EditText)findViewById(R.id.et_regrepwd);
        rg_gender=(RadioGroup)findViewById(R.id.rg_gender);
        et_verify=(EditText)findViewById(R.id.et_verify);
        image_verify=(ImageView)findViewById(R.id.image_verify);
        btn_reg=(Button)findViewById(R.id.btn_reg);

        setSupportActionBar(tb_register);
        setTitle("");

        btn_regback.setOnClickListener(this);
        btn_rege_birth.setOnClickListener(this);
        image_verify.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 RadioButton rb=(RadioButton)findViewById(checkedId);
                  gender=rb.getText().toString();
            }
        });


        //将验证码用图片的形式显示出来
        image_verify.setImageBitmap(CodeVerify.getInstance().createBitmap());
        codeStr=CodeVerify.getInstance().getCode().toLowerCase();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regback:
                Intent intent=new Intent(Register2Activity.this,AccountManageActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.image_verify:
                codeVerify=CodeVerify.getInstance();
                Bitmap bitmap=codeVerify.createBitmap();
                image_verify.setImageBitmap(bitmap);
                codeStr=CodeVerify.getInstance().getCode().toLowerCase();
                break;
            case R.id.btn_rege_birth:
                new DatePickerDialog(Register2Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_rege_birth.setText(String.format("%d-%d-%d",year,month+1,dayOfMonth));//多个占位符 按顺序
                    }
                },2000,0,1).show();
                break;
            case R.id.btn_reg:
                sendRequestPost();
                break;
        }
    }

    private void sendRequestPost(){
        final String username=et_reguser.getText().toString().trim();
        final String nick=et_regnick.getText().toString().trim();
        final String userpwd=et_regpwd.getText().toString().trim();
        final String repwd=et_regrepwd.getText().toString().trim();
        final String birth=tv_rege_birth.getText().toString();
//        final String gender= String.valueOf(rg_gender.getCheckedRadioButtonId());//不确定能否取到值
        final String verifyStr=et_verify.getText().toString().trim();



        if (null == verifyStr || TextUtils.isEmpty(verifyStr)) {
            Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
        } else if(!verifyStr.equals(codeStr)){//equalsIgnoreCase(codeStr)&&userpwd==repwd
            et_verify.setText("");
            Toast.makeText(getApplicationContext(), "验证码错误！", Toast.LENGTH_SHORT).show();
        }else {
            //创建一个OkHttpClient实例
            OkHttpClient client=new OkHttpClient();
            //requestBody存放待提交的参数
            RequestBody requestBody=new FormBody.Builder()
                    .add("zh",username)
                    .add("yhnc",nick)
                    .add("dlmm",userpwd)
                    .add("xb",gender)
                    .add("csny",birth)
                    .build();
            //创建一个request对象 url()方法设置目标的网络地址
            Request request=new Request.Builder()
                    .url("http://yiezi.ml/json/registerUse")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"注册连接失败",Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if(response.isSuccessful()) {
                        try {
                            String responseData=response.body().string();
                            try {
                                JSONArray jsonArray=new JSONArray(responseData);
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                String str=jsonObject.getString("result");
                                if(str.equals("true")){
                                    Intent intent = new Intent(Register2Activity.this,AccountManageActivity.class);
                                    startActivity(intent);
                                    finish();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else {
                                    final String message=jsonObject.getString("message");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });

        }
    }
}

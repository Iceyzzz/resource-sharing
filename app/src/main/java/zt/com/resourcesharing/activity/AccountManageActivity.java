package zt.com.resourcesharing.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import zt.com.resourcesharing.R;
//账号管理
public class AccountManageActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tb_account;
    private TextView tv_add;
    private TextView tv_exit;
    private AlertDialog.Builder builder=null;
    private AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acoount_manage);

        tb_account=(Toolbar)findViewById(R.id.tb_account);
        tv_add=(TextView)findViewById(R.id.tv_add);
        tv_exit=(TextView)findViewById(R.id.tv_exit);
        setSupportActionBar(tb_account);
        setTitle("");

        tv_add.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add:
                Intent intent1=new Intent(AccountManageActivity.this,Register2Activity.class);
                startActivity(intent1);
                break;
            case R.id.tv_exit:
                builder=new AlertDialog.Builder(AccountManageActivity.this);//显示当前对话框
                alert=builder.setTitle("提示").setMessage("您确定要退出登录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent2=new Intent(AccountManageActivity.this,MainActivity.class);
                                startActivity(intent2);
                                finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"您点击了取消！",Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alert.show();
                break;
        }
    }
}

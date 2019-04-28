package zt.com.resourcesharing.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import zt.com.resourcesharing.R;

public class SettingActivity extends AppCompatActivity {

    private Toolbar tb_set;
    private LinearLayout linear_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);

        tb_set=(Toolbar)findViewById(R.id.tb_set);
        linear_change=(LinearLayout)findViewById(R.id.linear_change);
        setSupportActionBar(tb_set);
        setTitle("");

        linear_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,ChangePWDActivity.class);
                startActivity(intent);
            }
        });
    }
}

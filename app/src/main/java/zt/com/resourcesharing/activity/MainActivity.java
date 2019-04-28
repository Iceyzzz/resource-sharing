package zt.com.resourcesharing.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import zt.com.resourcesharing.R;
import zt.com.resourcesharing.adapter.StartPagerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp_home;
    private Button btn_login;
    private Button btn_register;
    private ArrayList<View> dots=new ArrayList<View>();
    private int oldPosition=0;//记录上一次点的位置
    private int currentItem;//当前页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }
    public void initView(){
        vp_home=(ViewPager)findViewById(R.id.vp_home);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_register=(Button)findViewById(R.id.btn_register);

        //将一个一个的点装入ArrayList集合中
        dots.add(findViewById(R.id.point1));
        dots.add(findViewById(R.id.point2));
        dots.add(findViewById(R.id.point3));

        //准备HomePagerAdapter中的数据：将一个一个待显示的页面装入list集合中
        LayoutInflater layoutInflater=getLayoutInflater();
        View view1=layoutInflater.inflate(R.layout.page1,null);
        View view2=layoutInflater.inflate(R.layout.page2,null);
        View view3=layoutInflater.inflate(R.layout.page3,null);

        List<View> list=new ArrayList<>();
        list.add(view1);
        list.add(view2);
        list.add(view3);
        //实例化适配器
        StartPagerAdapter adapter=new StartPagerAdapter(list);
        //将adapter信息显示到ViewPager上
        vp_home.setAdapter(adapter);

        dots.get(0).setBackgroundResource(R.drawable.dot_focus);
        vp_home.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                dots.get(position).setBackgroundResource(R.drawable.dot_focus);
                oldPosition=position;
                currentItem=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Intent intent1=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case  R.id.btn_register:
                Intent intent2=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}

package zt.com.resourcesharing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zt.com.resourcesharing.R;
import zt.com.resourcesharing.fragment.BlogFragment;
import zt.com.resourcesharing.fragment.FindFragment;
import zt.com.resourcesharing.fragment.MineFragment;

/**
 * Created by Administrator on 2018/3/16.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linear_blog;
    private LinearLayout linear_find;
    private LinearLayout linear_mine;
//    private LinearLayout linear_manage;

    private ImageView icon_blog;
    private ImageView icon_find;
    private ImageView icon_mine;
//    private ImageView icon_manage;

    private TextView tv_blog;
    private TextView tv_find;
    private TextView tv_mine;
//    private TextView tv_manage;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        initView();
        //主界面上自动显示博客的内容
        replaceFragment(new BlogFragment(),"BlogFragment");
    }
    //初始化
    public void initView(){
        linear_blog=(LinearLayout)findViewById(R.id.linear_blog);
        linear_find=(LinearLayout)findViewById(R.id.linear_find);
        linear_mine=(LinearLayout)findViewById(R.id.linear_mine);
 //       linear_manage=(LinearLayout)findViewById(R.id.linear_manage);

        icon_blog=(ImageView)findViewById(R.id.icon_blog);
        icon_find=(ImageView)findViewById(R.id.icon_find);
        icon_mine=(ImageView)findViewById(R.id.icon_mine);
  //      icon_manage=(ImageView)findViewById(R.id.icon_manage);

        tv_blog=(TextView)findViewById(R.id.tv_blog);
        tv_find=(TextView)findViewById(R.id.tv_find);
        tv_mine=(TextView)findViewById(R.id.tv_mine);
  //      tv_manage=(TextView)findViewById(R.id.tv_manage);

        linear_mine.setOnClickListener(this);
        linear_find.setOnClickListener(this);
        linear_blog.setOnClickListener(this);
   //     linear_manage.setOnClickListener(this);
    }
    //动态添加Fragment
    public void replaceFragment(Fragment fragment,String tag) {
        //1.获取碎片管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2.开启一个事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //3.添加碎片
        transaction.replace(R.id.frameLayout, fragment, tag);
        //4.提交事务
        transaction.commit();
    }

    //重置底部导航栏
    public void resetBottom(){
        icon_blog.setImageResource(R.drawable.icon_blog);
        icon_find.setImageResource(R.drawable.icon_find);
        icon_mine.setImageResource(R.drawable.icon_user);
 //       icon_manage.setImageResource(R.drawable.icon_manage);

        tv_blog.setTextColor(Color.parseColor("#B7B7B7"));
        tv_find.setTextColor(Color.parseColor("#B7B7B7"));
        tv_mine.setTextColor(Color.parseColor("#B7B7B7"));
 //       tv_manage.setTextColor(Color.parseColor("#B7B7B7"));
    }
    @Override
    public void onClick(View v) {
        resetBottom();
        switch (v.getId()){
            case R.id.linear_blog:
                icon_blog.setImageResource(R.drawable.icon_blog_press);
                tv_blog.setTextColor(Color.parseColor("#67CCFF"));
                replaceFragment(new BlogFragment(),"BlogFragment");
                break;
            case R.id.linear_find:
                icon_find.setImageResource(R.drawable.icon_find_press);
                tv_find.setTextColor(Color.parseColor("#67CCFF"));
                replaceFragment(new FindFragment(),"FindFragment");
                break;
            case R.id.linear_mine:
                icon_mine.setImageResource(R.drawable.icon_user_press);
                tv_mine.setTextColor(Color.parseColor("#67CCFF"));
                replaceFragment(new MineFragment(),"MineFragment");
                break;

        }
    }
}

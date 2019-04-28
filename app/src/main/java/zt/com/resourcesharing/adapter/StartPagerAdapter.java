package zt.com.resourcesharing.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */
//启动页 页面管理适配器
public class StartPagerAdapter extends PagerAdapter {
    //定义容纳显示页面的集合对象，该对象中是一个一个待显示的页面
    private List<View> list;

    //构造方法

    public StartPagerAdapter() {
    }

    public StartPagerAdapter(List<View> list) {
        this.list = list;
    }

    //ViewPager组件中要显示的页面总数
    @Override
    public int getCount() {
        return list.size();
    }

    //初始化，把哪个页面放到当前页面显示，创建指定位置的页面视图
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //将待显示的页面依次放入container中（container相当于单选按钮组）
        container.addView(list.get(position));
        return list.get(position);
    }

    //判断哪一个页面来显示(判断初始化中是否一一对应)
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    //移出一个指定位置上的页面，即滑动页面时，消失的那个页面

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}

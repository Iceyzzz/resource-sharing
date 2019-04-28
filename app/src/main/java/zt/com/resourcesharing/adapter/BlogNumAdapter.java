package zt.com.resourcesharing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import zt.com.resourcesharing.R;
import zt.com.resourcesharing.bean.BlogNum;

/**
 * Created by Administrator on 2018/5/2.
 */

//个人博文适配器
public class BlogNumAdapter extends BaseAdapter{
    private LinkedList<BlogNum> blogNumList;
    private Context mContext;
    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd");

    public BlogNumAdapter(LinkedList<BlogNum> blogNumList, Context mContext) {
        this.blogNumList = blogNumList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {//获取当前适配器要显示数据的总数
        return blogNumList.size();
    }

    @Override
    public Object getItem(int position) {//适配器要显示数据的分项
        return null;
    }

    @Override
    public long getItemId(int position) {//编号
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(holder==null){
            //创建缓冲布局界面，获取界面上的组件
            convertView= LayoutInflater.from(mContext).inflate(R.layout.blog_list_item,parent,false);
            holder=new ViewHolder();
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_blogName);
            holder.tv_time=(TextView)convertView.findViewById(R.id.tv_blogTime);
            holder.tv_click=(TextView)convertView.findViewById(R.id.tv_blogClick);
            holder.tv_content=(TextView)convertView.findViewById(R.id.tv_blogContent);
            convertView.setTag(holder);//将对象放入缓冲器中
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tv_name.setText(blogNumList.get(position).getName());
        holder.tv_time.setText(formatter.format(blogNumList.get(position).getTime()));
        holder.tv_click.setText(blogNumList.get(position).getClick());
        holder.tv_content.setText(blogNumList.get(position).getContent());
        return convertView;
    }
    //在类中增加一个静态内部类，用以保存缓冲布局（显示组件每一分项的布局）界面上的组件
    static class ViewHolder{
        TextView tv_name;
        TextView tv_time;
        TextView tv_click;
        TextView tv_content;

    }
}

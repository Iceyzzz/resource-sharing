package zt.com.resourcesharing.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import zt.com.resourcesharing.R;
import zt.com.resourcesharing.bean.BlogNum;
import zt.com.resourcesharing.bean.Content;
import zt.com.resourcesharing.bean.Essay;

/**
 * Created by Administrator on 2018/5/2.
 */

//每个分类的所有内容适配器ListView
public class FindAdapter extends BaseAdapter{
    private LinkedList<Essay> essayList;
    private Context mContext;
    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd");

    public FindAdapter(LinkedList<Essay> essayList, Context mContext) {
        this.essayList = essayList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {//获取当前适配器要显示数据的总数
        return essayList.size();
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
 //       Essay essay=getItem(position);
        ViewHolder holder=null;
        if(holder==null){
            //创建缓冲布局界面，获取界面上的组件
            convertView= LayoutInflater.from(mContext).inflate(R.layout.cate_cardview_item,parent,false);
            holder=new ViewHolder();
            holder.cardView=(CardView)convertView;
//            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
//            holder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
//            holder.tv_details=(TextView)convertView.findViewById(R.id.tv_details);

            holder.essay_title=(TextView)convertView.findViewById(R.id.essay_title);
            holder.essay_click=(TextView)convertView.findViewById(R.id.essay_click);
            holder.essay_writer=(TextView)convertView.findViewById(R.id.essay_writer);
            holder.essay_time=(TextView)convertView.findViewById(R.id.essay_time);
            holder.essay_cate=(TextView)convertView.findViewById(R.id.essay_cate);
            holder.essay_brief=(TextView)convertView.findViewById(R.id.essay_brief);
            convertView.setTag(holder);//将对象放入缓冲器中
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
//        holder.tv_name.setText(contentList.get(position).getName());
//        holder.tv_time.setText(formatter.format(contentList.get(position).getTime()));
//        holder.tv_details.setText(contentList.get(position).getDetails());
        holder.essay_title.setText(essayList.get(position).geteTitle());
        holder.essay_click.setText(essayList.get(position).geteClick());
        holder.essay_writer.setText(essayList.get(position).geteWriter());
        holder.essay_time.setText(formatter.format(essayList.get(position).geteTime()));
        holder.essay_cate.setText(essayList.get(position).geteCategory());
        holder.essay_brief.setText(essayList.get(position).geteBrief());
        return convertView;
    }
    //在类中增加一个静态内部类，用以保存缓冲布局（显示组件每一分项的布局）界面上的组件
    static class ViewHolder{
        CardView cardView;
//        TextView tv_name;
//        TextView tv_time;
//        TextView tv_details;
        TextView essay_title;
        TextView essay_click;
        TextView essay_writer;
        TextView essay_time;
        TextView essay_cate;
        TextView essay_brief;

    }
}

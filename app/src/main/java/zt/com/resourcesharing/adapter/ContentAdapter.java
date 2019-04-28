package zt.com.resourcesharing.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import zt.com.resourcesharing.bean.Content;
import zt.com.resourcesharing.R;

/**
 * Created by Administrator on 2018/4/15.
 */
//博客圈内容适配器
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private Context mContext;
    private List<Content> mList;
    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd HH:mm");
    //内部类
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_name;
        TextView tv_time;
        TextView tv_details;
//        TextView tv_recom;
//        TextView tv_like;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            tv_name=(TextView)view.findViewById(R.id.tv_name);
            tv_time=(TextView)view.findViewById(R.id.tv_time);
            tv_details=(TextView)view.findViewById(R.id.tv_details);
//            tv_recom=(TextView)view.findViewById(R.id.tv_recom);
//            tv_like=(TextView)view.findViewById(R.id.tv_like);
        }
    }
    //构造函数 把要展示的数据源传进来
    public ContentAdapter(List<Content> mList){
        this.mList=mList;
    }

    public ContentAdapter(Context mContext, List<Content> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    //创建ViewHolder实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.find_cardview_item,parent,false);//加载布局
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    //对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Content content=mList.get(position);//通过position参数得到当前项的Content实例
        holder.tv_name.setText(content.getName());
        holder.tv_time.setText(formatter.format(content.getTime()));
        holder.tv_details.setText(content.getDetails());
    }
    //一共有多少子项
    @Override
    public int getItemCount() {
        return mList.size();
    }
}

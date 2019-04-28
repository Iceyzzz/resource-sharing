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

import zt.com.resourcesharing.R;
import zt.com.resourcesharing.bean.Essay;

/**
 * Created by Administrator on 2018/5/9.
 */
//每个分类所有内容适配器
public class CateContentAdapter extends RecyclerView.Adapter<CateContentAdapter.ViewHolder>{
    private Context mContext;
    private List<Essay> mEssayList;
    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd");

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView essay_title;
        TextView essay_click;
        TextView essay_writer;
        TextView essay_time;
        TextView essay_cate;
        TextView essay_brief;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            essay_title=(TextView)view.findViewById(R.id.essay_title);
            essay_click=(TextView)view.findViewById(R.id.essay_click);
            essay_writer=(TextView)view.findViewById(R.id.essay_writer);
            essay_time=(TextView)view.findViewById(R.id.essay_time);
            essay_cate=(TextView)view.findViewById(R.id.essay_cate);
            essay_brief=(TextView)view.findViewById(R.id.essay_brief);
        }
    }

    public CateContentAdapter(List<Essay> mEssayList) {
        this.mEssayList = mEssayList;
    }

    public CateContentAdapter(Context mContext, List<Essay> mEssayList) {
        this.mContext = mContext;
        this.mEssayList = mEssayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.cate_cardview_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Essay essay=mEssayList.get(position);
        holder.essay_title.setText(essay.geteTitle());
        holder.essay_click.setText(essay.geteClick());
        holder.essay_writer.setText(essay.geteWriter());
        holder.essay_time.setText(formatter.format(essay.geteTime()));
        holder.essay_cate.setText(essay.geteCategory());
        holder.essay_brief.setText(essay.geteBrief());
    }

    @Override
    public int getItemCount() {
        return mEssayList.size();
    }


}

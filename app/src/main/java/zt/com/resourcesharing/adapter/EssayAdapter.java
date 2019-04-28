package zt.com.resourcesharing.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import zt.com.resourcesharing.R;
import zt.com.resourcesharing.activity.EssayDetailsActivity;
import zt.com.resourcesharing.bean.Essay;

/**
 * Created by Administrator on 2018/5/9.
 */
//各分类内容适配器
public class EssayAdapter extends RecyclerView.Adapter<EssayAdapter.ViewHolder>{
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

    public EssayAdapter(List<Essay> mEssayList) {
        this.mEssayList = mEssayList;
    }

    public EssayAdapter(Context mContext, List<Essay> mEssayList) {
        this.mContext = mContext;
        this.mEssayList = mEssayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.essay_cardview_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.essay_brief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();//获取用户的点击的position
                Essay essay=mEssayList.get(position);//通过position拿到相应的essay实例
                int id=essay.getId();
                Intent intent=new Intent(v.getContext(), EssayDetailsActivity.class);
                intent.putExtra("id_data",id);//通过putExtra()方法传递了一个字符串
                v.getContext().startActivity(intent);
            }
        });
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

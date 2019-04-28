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
import zt.com.resourcesharing.bean.Comment;
import zt.com.resourcesharing.bean.Essay;

/**
 * Created by Administrator on 2018/5/9.
 */
//每个分类所有内容适配器
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context mContext;
    private List<Comment> mCommentList;
    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd");

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_cName;
        TextView tv_cTime;
        TextView tv_comment;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            tv_cName=(TextView)view.findViewById(R.id.tv_cName);
            tv_cTime=(TextView)view.findViewById(R.id.tv_cTime);
            tv_comment=(TextView)view.findViewById(R.id.tv_comment);

        }
    }

    public CommentAdapter(List<Comment> mCommentList) {
        this.mCommentList = mCommentList;
    }

    public CommentAdapter(Context mContext, List<Comment> mCommentList) {
        this.mContext = mContext;
        this.mCommentList = mCommentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.comment_cardview_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment=mCommentList.get(position);
        holder.tv_cName.setText(comment.getcName());
        holder.tv_cTime.setText(formatter.format(comment.getcTime()));
        holder.tv_comment.setText(comment.getComments());

    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }


}

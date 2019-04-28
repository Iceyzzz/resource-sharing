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
import zt.com.resourcesharing.bean.Search;

/**
 * Created by Administrator on 2018/5/9.
 */
//每个分类所有内容适配器
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private Context mContext;
    private List<Search> mSearchList;
    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd HH:mm");

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView search_title;
        TextView search_click;
        TextView search_writer;
        TextView search_time;
        TextView search_cate;
        TextView search_brief;
        TextView search_content;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            search_title=(TextView)view.findViewById(R.id.tv_searchTitle);
            search_click=(TextView)view.findViewById(R.id.tv_searchClick);
            search_writer=(TextView)view.findViewById(R.id.tv_searchName);
            search_time=(TextView)view.findViewById(R.id.tv_searchTime);
            search_cate=(TextView)view.findViewById(R.id.tv_searchCategory);
            search_brief=(TextView)view.findViewById(R.id.tv_searchBrief);
            search_content=(TextView)view.findViewById(R.id.tv_searchContent);
        }
    }

    public SearchAdapter(List<Search> mSearchList) {
        this.mSearchList = mSearchList;
    }

    public SearchAdapter(Context mContext, List<Search> mSearchList) {
        this.mContext = mContext;
        this.mSearchList = mSearchList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.search_cardview_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Search search=mSearchList.get(position);
        holder.search_title.setText(search.getTitle());
        holder.search_click.setText(search.getClick());
        holder.search_writer.setText(search.getWriter());
        holder.search_time.setText(formatter.format(search.getTime()));
        holder.search_cate.setText(search.getCategory());
        holder.search_brief.setText(search.getBrief());
        holder.search_content.setText(search.getContent());
    }

    @Override
    public int getItemCount() {
        return mSearchList.size();
    }


}

package zt.com.resourcesharing.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;
import zt.com.resourcesharing.activity.CategoryContentActivity;
import zt.com.resourcesharing.bean.Category;
import zt.com.resourcesharing.utils.HttpUtil;

/**
 * Created by Administrator on 2018/4/25.
 */
//分类适配器
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> mList;

    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView category_name;
        public ViewHolder(View view ){
            super(view);
            category_name=(TextView)view.findViewById(R.id.category_name);
        }
    }

    public CategoryAdapter(List<Category> mList) {
        this.mList = mList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Category category=mList.get(position);
                String data_name=category.getName();
                int id=category.getId();
                Log.d("CategoryAdapter", String.valueOf(id));
                Intent intent=new Intent(v.getContext(), CategoryContentActivity.class);
                Bundle bd=new Bundle();//实例化
                //将数据放入bundle
                bd.putCharSequence("category_name",data_name);
                bd.putInt("id",id);
                intent.putExtras(bd);
 //               intent.putExtra("category_name",data_name);
                v.getContext().startActivity(intent);

//                Toast.makeText(v.getContext(),"你点击了"+category.getName(),Toast.LENGTH_LONG).show();

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Category category=mList.get(position);
        holder.category_name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}

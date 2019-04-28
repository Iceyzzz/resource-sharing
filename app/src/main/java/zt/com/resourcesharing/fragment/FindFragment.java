package zt.com.resourcesharing.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zt.com.resourcesharing.R;
import zt.com.resourcesharing.activity.AddCommentActivity;
import zt.com.resourcesharing.activity.CommentListActivity;
import zt.com.resourcesharing.adapter.ContentAdapter;
import zt.com.resourcesharing.adapter.FindAdapter;
import zt.com.resourcesharing.bean.Content;
import zt.com.resourcesharing.utils.HttpUtil;

import static android.content.Context.MODE_PRIVATE;
import static zt.com.resourcesharing.utils.DateConvert.stampToDate;

/**
 * Created by Administrator on 2018/3/30.
 */

public class FindFragment extends Fragment {
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView rv_find;
    private List<Content> contentList=new LinkedList<Content>();
    private ContentAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.find_fragment,container,false);

        swipe_refresh=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        rv_find=(RecyclerView) view.findViewById(R.id.rv_find);

        getOkHttpRequest();

        //设置下拉刷新进度条的颜色
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contentList.clear();
                //去网络上请求最新的数据
                getOkHttpRequest();
                adapter.notifyDataSetChanged();
                swipe_refresh.setRefreshing(false);
            }
        });


//        //给Listview对象注册一个上下文菜单
//        registerForContextMenu(list_find);
        return view;
    }

    public void getOkHttpRequest(){
        final Activity activity=getActivity();
        HttpUtil.sendOkHttpRequest("http://yiezi.ml/browseAllBlogoSphere_json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplication(),"好友圈连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        String responseData=response.body().string();
                        Log.d("FindFragment",responseData);
                        try {
                            JSONArray jsonArray=new JSONArray(responseData);
                            for(int i=1;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String name=jsonObject.getString("yhnc");
                                Date time=stampToDate(jsonObject.getString("wordDate"));
                             //   Date time=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(jsonObject.getString("wordDate"));
                                String details=jsonObject.getString("userWord");

                                Content content=new Content(name,time,details);
                                contentList.add(content);
                            }
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                  //  adapter=new FindAdapter((LinkedList<Content>) contentList,FindFragment.this.getActivity());
                                    LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
                                    rv_find.setLayoutManager(layoutManager);
                                    adapter=new ContentAdapter(contentList);
                                    rv_find.setAdapter(adapter);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
/*
    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.find_review_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //得到选中数据的位置
        AdapterView.AdapterContextMenuInfo aci=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position=aci.position;
        switch (item.getItemId()){
            case R.id.look_review:
                //Toast.makeText(getActivity(),"评论",Toast.LENGTH_LONG).show();
                Intent intent1=new Intent(getActivity(), CommentListActivity.class);
                startActivity(intent1);
                break;
            case R.id.add_review:
                Intent intent2=new Intent(getActivity(), AddCommentActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onContextItemSelected(item);
    }*/
}

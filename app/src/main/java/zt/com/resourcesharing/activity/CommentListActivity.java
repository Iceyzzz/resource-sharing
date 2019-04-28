package zt.com.resourcesharing.activity;

import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;
import zt.com.resourcesharing.adapter.CommentAdapter;
import zt.com.resourcesharing.bean.Comment;

import static zt.com.resourcesharing.utils.DateConvert.stampToDate;

public class CommentListActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Comment> commentList=new ArrayList<>();
    private CommentAdapter commentAdapter;
    private SwipeRefreshLayout swipe_commentList;
    private RecyclerView rv_comment;
    private Toolbar tb_commentList;
    private Button btn_commentBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);

        swipe_commentList=(SwipeRefreshLayout)findViewById(R.id.swipe_commentList);
        rv_comment=(RecyclerView)findViewById(R.id.rv_comment);
        tb_commentList=(Toolbar)findViewById(R.id.tb_commentList);
        btn_commentBack=(Button)findViewById(R.id.btn_commentBack);
        btn_commentBack.setOnClickListener(this);

        setSupportActionBar(tb_commentList);
        setTitle("");

        sendRequestPost();

        swipe_commentList.setColorSchemeResources(R.color.colorPrimary);
        swipe_commentList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentList.clear();
                sendRequestPost();
                commentAdapter.notifyDataSetChanged();
                swipe_commentList.setRefreshing(false);
            }
        });

    }
    public void sendRequestPost(){
        SharedPreferences pref=getSharedPreferences("comment",MODE_PRIVATE);
        int commentId=pref.getInt("id",-1);
        Log.e("CommentListId", String.valueOf(commentId));
        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("blogId", String.valueOf(commentId))
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url("http://yiezi.ml/browseBlogCommentByBlogIdForPhone")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"查看评论连接失败！",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    Log.e("comment",responseData);
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String flag=jsonObject.getString("operation");
                        if(flag.equals("success")){
                            if(jsonArray.length()==1){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplication(),"暂无评论！",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else {
                                for (int i = 1; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("userName");
                                    Date time = stampToDate(object.getString("plsj"));
                                    String content = object.getString("plnr");
                                    Comment comment = new Comment(name, time, content);
                                    commentList.add(comment);
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(CommentListActivity.this);
                                        rv_comment.setLayoutManager(layoutManager);
                                        commentAdapter = new CommentAdapter(commentList);
                                        rv_comment.setAdapter(commentAdapter);
                                    }
                                });
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_commentBack:
                finish();
                break;
        }
    }
}

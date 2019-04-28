package zt.com.resourcesharing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;
import zt.com.resourcesharing.adapter.BlogNumAdapter;
import zt.com.resourcesharing.bean.BlogNum;
import zt.com.resourcesharing.utils.HttpUtil;

import static zt.com.resourcesharing.utils.DateConvert.stampToDate;

//个人博文
public class BlogNumActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipe_myRefresh;
    private ListView list_blog;
    private List<BlogNum> numList=new LinkedList<BlogNum>();
    private BlogNumAdapter numAdapter=null;
    private Toolbar tb_blogNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_num);

        swipe_myRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_myRefresh);
        list_blog=(ListView)findViewById(R.id.list_blog);
        tb_blogNum=(Toolbar)findViewById(R.id.tb_blogNum);
        setSupportActionBar(tb_blogNum);
        setTitle("");

        sendRequestPost();

        swipe_myRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipe_myRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                numList.clear();
                sendRequestPost();
                numAdapter.notifyDataSetChanged();
                swipe_myRefresh.setRefreshing(false);
            }
        });

        //给listview对象注册一个上下文菜单
        registerForContextMenu(list_blog);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_myblog,menu);//找到菜单的内容
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Intent intent=new Intent(BlogNumActivity.this,AddMyBlogActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    private void sendRequestPost(){
        SharedPreferences pref=getSharedPreferences("myshare",MODE_PRIVATE);//获取sharedPreferences对象
        int userId=pref.getInt("id",-1);//去获取当前所存储的用户id，如果没有找到相应值，就会使用方法中传入的默认值来代替
        Log.d("BlogNumUserId", String.valueOf(userId));

        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("userId", String.valueOf(userId))
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url("http://yiezi.ml/browseMyBlogs_json")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"个人博客连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    Log.d("onResponse",responseData);
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        JSONObject object=jsonArray.getJSONObject(0);
                        String str=object.getString("operation");
                        if(str.equals("success")){
                            for(int i=1;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                int id=jsonObject.getInt("id");
                                String name=jsonObject.getString("bwbt");
                                Date time=stampToDate(jsonObject.getString("bwcjsj"));
                            //    Date time=new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("bwcjsj"));
                                String click=jsonObject.getString("bwdjcs");
                                String content=jsonObject.getString("bwnr");
                                numList.add(new BlogNum(id,name,time,click,content));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    numAdapter=new BlogNumAdapter((LinkedList<BlogNum>) numList,BlogNumActivity.this);
                                    list_blog.setAdapter(numAdapter);
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"您还没有发表任何博文哦！",Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_blog_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //得到选中数据的位置
        AdapterView.AdapterContextMenuInfo aci=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final int position=aci.position;
        Log.d("ListPosition", String.valueOf(position));
        BlogNum blogNum=numList.get(position);
        int id=blogNum.getId();
        Log.d("onContextItemSelectedId", String.valueOf(id));
        switch (item.getItemId()){
            case R.id.list_look:
                Intent intent=new Intent(BlogNumActivity.this,BlogDetailsActivity.class);
                intent.putExtra("id_myBlog",id);
                startActivity(intent);
                break;
            case R.id.list_del:
                numList.remove(position);
                delBlog(id);
                numAdapter.notifyDataSetChanged();//通知适配器更新数据

                break;
        }
        return super.onContextItemSelected(item);
    }
    //删除博文
    private void delBlog(int id){
        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("id", String.valueOf(id))
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url("http://yiezi.ml/json/delBlogs")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"删除博文连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String str=jsonObject.getString("result");
                        final String message=jsonObject.getString("message");
                        if(str.equals("true")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

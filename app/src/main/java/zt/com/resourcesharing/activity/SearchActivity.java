package zt.com.resourcesharing.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import zt.com.resourcesharing.adapter.SearchAdapter;
import zt.com.resourcesharing.bean.Search;

import static zt.com.resourcesharing.utils.DateConvert.stampToDate;

public class SearchActivity extends AppCompatActivity {
    private List<Search> searchList=new ArrayList<>();
    private SearchAdapter searchAdapter;
    private RecyclerView rv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        rv_search=(RecyclerView)findViewById(R.id.rv_search);
        SharedPreferences pref=getSharedPreferences("input_data",MODE_PRIVATE);
        String input=pref.getString("input","");


        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("keyWord",input)
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        final Request request=new Request.Builder()
                .url("http://yiezi.ml/serachBlogs_json")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        JSONArray jsonData=jsonArray.getJSONArray(1);
                        for(int i=0;i<jsonData.length();i++){
                            JSONObject jsonObject=jsonData.getJSONObject(i);
                            String title=jsonObject.getString("bwbt");
                            String click=jsonObject.getString("bwdjcs");
                            String name=jsonObject.getString("editor");
                            Date time=stampToDate(jsonObject.getString("bwcjsj"));
                           // Date time=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(jsonObject.getString("bwcjsj"));
                            String cate=jsonObject.getString("blogCategoryId");
                            String brief=jsonObject.getString("bwjj");
                            String content=jsonObject.getString("bwnr");
                            Search search=new Search(title,click,name,time,cate,brief,content);
                            searchList.add(search);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager layoutManager=new LinearLayoutManager(SearchActivity.this);
                                rv_search.setLayoutManager(layoutManager);
                                searchAdapter=new SearchAdapter(searchList);
                                rv_search.setAdapter(searchAdapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}

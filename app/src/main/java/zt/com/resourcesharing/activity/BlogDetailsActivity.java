package zt.com.resourcesharing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;
import zt.com.resourcesharing.utils.HttpUtil;

import static zt.com.resourcesharing.utils.DateConvert.stampToDate;

//个人博客详细内容
public class BlogDetailsActivity extends AppCompatActivity {

    private Toolbar tb_blogDetails;
    private TextView tv_detTitle;
    private TextView tv_detTime;
    private TextView tv_detClick;
    private TextView tv_detCategory;
    private TextView tv_detContent;
    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_details);

        tb_blogDetails=(Toolbar)findViewById(R.id.tb_blogDetails);
        tv_detTitle=(TextView)findViewById(R.id.tv_detTitle);
        tv_detTime=(TextView)findViewById(R.id.tv_detTime);
        tv_detClick=(TextView)findViewById(R.id.tv_detClick);
        tv_detCategory=(TextView)findViewById(R.id.tv_detCategory);
        tv_detContent=(TextView)findViewById(R.id.tv_detContent);

        setSupportActionBar(tb_blogDetails);
        setTitle("");
        SharedPreferences pref=getSharedPreferences("myshare",MODE_PRIVATE);
        final int userId=pref.getInt("id",-1);

        Intent intent=getIntent();
        final int idBlog=intent.getIntExtra("id_myBlog",-1);


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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        for(int i=1;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id=jsonObject.getInt("id");
                            if(id==idBlog){
                                final String title=jsonObject.getString("bwbt");
                                final Date time=stampToDate(jsonObject.getString("bwcjsj"));
                             //   final Date time=new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("bwcjsj"));
                                final String click=jsonObject.getString("bwdjcs");
                                final String category=jsonObject.getString("blogCategoryId");
                                final String content=jsonObject.getString("bwnr");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_detTitle.setText(title);
                                        tv_detTime.setText(formatter.format(time));
                                        tv_detCategory.setText(category);
                                        tv_detClick.setText(click);
                                        tv_detContent.setText(content);
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
}

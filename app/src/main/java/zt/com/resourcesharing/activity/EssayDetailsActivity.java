package zt.com.resourcesharing.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

//右端各分类的详细内容
public class EssayDetailsActivity extends AppCompatActivity {

    private Toolbar tb_essayDetails;
    private TextView tv_essayTitle;
    private TextView tv_essayName;
    private TextView tv_essayTime;
    private TextView tv_essayClick;
    private TextView tv_essayCategory;
    private TextView tv_essayContent;
    SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.essay_details);

        tv_essayTitle=(TextView)findViewById(R.id.tv_essayTitle);
        tv_essayName=(TextView)findViewById(R.id.tv_essayName);
        tv_essayTime=(TextView)findViewById(R.id.tv_essayTime);
        tv_essayClick=(TextView)findViewById(R.id.tv_essayClick);
        tv_essayCategory=(TextView)findViewById(R.id.tv_essayCategory);
        tv_essayContent=(TextView)findViewById(R.id.tv_essayContent);
        setSupportActionBar(tb_essayDetails);
        setTitle("");

        Intent intent=getIntent();
        final int id=intent.getIntExtra("id_data",-1);

        HttpUtil.sendOkHttpRequest("http://yiezi.ml/browseBlogsByPhone/json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"各个分类的详细内容连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            int idEssay=jsonObject.getInt("id");
                            if(idEssay==id){
                                final String title=jsonObject.getString("bwbt");
                                final String name=jsonObject.getString("editor");
                                final Date time=stampToDate(jsonObject.getString("bwcjsj"));
                              //  final Date time=new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("bwcjsj"));
                                final String click=jsonObject.getString("bwdjcs");
                                final String category=jsonObject.getString("blogCategoryId");
                                final String content=jsonObject.getString("bwnr");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_essayTitle.setText(title);
                                        tv_essayName.setText(name);
                                        tv_essayTime.setText(formatter.format(time));
                                        tv_essayClick.setText(click);
                                        tv_essayCategory.setText(category);
                                        tv_essayContent.setText(content);
                                    }
                                });
                                break;
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

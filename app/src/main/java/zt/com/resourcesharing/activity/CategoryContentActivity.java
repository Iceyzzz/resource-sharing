package zt.com.resourcesharing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
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
import java.util.ArrayList;
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
import zt.com.resourcesharing.adapter.CateContentAdapter;
import zt.com.resourcesharing.adapter.CategoryAdapter;
import zt.com.resourcesharing.adapter.EssayAdapter;
import zt.com.resourcesharing.adapter.FindAdapter;
import zt.com.resourcesharing.bean.Essay;
import zt.com.resourcesharing.utils.HttpUtil;

import static zt.com.resourcesharing.utils.DateConvert.stampToDate;

//单个分类的所有内容
public class CategoryContentActivity extends AppCompatActivity {

    private List<Essay> essayList=new LinkedList<Essay>();
    private FindAdapter adapter;
    private ListView list_cateContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_content);

        list_cateContent=(ListView) findViewById(R.id.list_cateContent);
 /*       list_cateContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Essay essay=essayList.get(position);
                int essayId=essay.getId();
                Log.e("CateContIdOfBlog", String.valueOf(essayId));
                SharedPreferences.Editor editor=getSharedPreferences("comment",MODE_PRIVATE).edit();//获取对象
                editor.putInt("id",essayId);//添加数据
                editor.apply();//提交数据
            }
        });*/
        Intent intent=getIntent();
        String data=intent.getStringExtra("category_name");
        final int id=intent.getIntExtra("id",-1);//如果没有id就取-1
        Log.d("CategoryContentId", String.valueOf(id));

        Toast.makeText(getApplicationContext(),"你点击了"+data,Toast.LENGTH_LONG).show();

        //给Listview对象注册一个上下文菜单
        registerForContextMenu(list_cateContent);

        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("id", String.valueOf(id))
                .add("order", String.valueOf(1))
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url("http://yiezi.ml/browseBlogsByCate/json")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"单个分类显示连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                        if(response.isSuccessful()){
                            String responseData=response.body().string();
                            Log.d("CateContentIsSuccessful",responseData);
                            try {
                                JSONArray jsonArray=new JSONArray(responseData);
                                JSONArray jsonData = jsonArray.getJSONArray(1);
                                for(int i=0;i<jsonData.length();i++){
                                    JSONObject jsonObject=jsonData.getJSONObject(i);
                                    int cateId=jsonObject.getInt("blogCategoryId");
                                    if(cateId==id){
                                        String title = jsonObject.getString("bwbt");
                                        String click = jsonObject.getString("bwdjcs");
                                        String writer = jsonObject.getString("editor");
                                        Date date=stampToDate(jsonObject.getString("bwcjsj"));
                                    //    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("bwcjsj"));
                                        String category = jsonObject.getString("blogCategoryId");
                                        String brief = jsonObject.getString("bwnr");
                                        int blogId=jsonObject.getInt("id");
                                        Log.e("A comment of blog", String.valueOf(blogId));

                                        Essay essay = new Essay(blogId,title, click, writer, date, category, brief);
                                        essayList.add(essay);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
//                                                LinearLayoutManager layoutManager=new LinearLayoutManager(CategoryContentActivity.this);
//                                                rv_cateContent.setLayoutManager(layoutManager);
//                                                cateContentAdapter=new CateContentAdapter(essayList);
                                                adapter=new FindAdapter((LinkedList<Essay>) essayList,CategoryContentActivity.this);
                                                list_cateContent.setAdapter(adapter);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.find_review_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //得到选中数据的位置
        AdapterView.AdapterContextMenuInfo aci=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position=aci.position;
        Essay essay1=essayList.get(position);
        int blogCommId=essay1.getId();
        Log.e("CateContentBlogCommId", String.valueOf(blogCommId));
        SharedPreferences.Editor editor=getSharedPreferences("comment",MODE_PRIVATE).edit();//获取对象
        editor.putInt("id",blogCommId);//添加数据
        editor.apply();//提交数据

        switch (item.getItemId()){
            case R.id.look_review:
                //Toast.makeText(getActivity(),"评论",Toast.LENGTH_LONG).show();
                Intent intent1=new Intent(CategoryContentActivity.this, CommentListActivity.class);
                startActivity(intent1);
                break;
            case R.id.add_review:
                Intent intent2=new Intent(CategoryContentActivity.this, AddCommentActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onContextItemSelected(item);
    }
}

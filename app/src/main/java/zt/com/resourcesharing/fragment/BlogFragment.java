package zt.com.resourcesharing.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

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
import zt.com.resourcesharing.activity.SearchActivity;
import zt.com.resourcesharing.adapter.CategoryAdapter;
import zt.com.resourcesharing.adapter.EssayAdapter;
import zt.com.resourcesharing.bean.Category;
import zt.com.resourcesharing.bean.Essay;
import zt.com.resourcesharing.utils.HttpUtil;

import static android.content.Context.MODE_PRIVATE;
import static zt.com.resourcesharing.utils.DateConvert.stampToDate;

/**
 * Created by Administrator on 2018/3/30.
 */

public class BlogFragment extends Fragment implements View.OnClickListener {

    private List<Category> categoryList=new ArrayList<>();
    private List<Essay> essayList=new ArrayList<>();
    private RecyclerView rv_category;
    private RecyclerView rv_essay;
    private CategoryAdapter categoryAdapter;
    private EssayAdapter essayAdapter;
    private SliderLayout slider_icon;
    private EditText et_search;
    private Button btn_search;
    TextSliderView textSliderView1;
    TextSliderView textSliderView2;
    TextSliderView textSliderView3;
    TextSliderView textSliderView4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.blog_fragment,container,false);

        rv_category=(RecyclerView)view.findViewById(R.id.rv_category);
        rv_essay=(RecyclerView)view.findViewById(R.id.rv_essay);
        slider_icon=(SliderLayout)view.findViewById(R.id.slider_icon);
        et_search=(EditText)view.findViewById(R.id.et_search);
        btn_search=(Button)view.findViewById(R.id.btn_search);
        //初始化
        textSliderView1=new TextSliderView(this.getActivity());
        textSliderView2=new TextSliderView(this.getActivity());
        textSliderView3=new TextSliderView(this.getActivity());
        textSliderView4=new TextSliderView(this.getActivity());

        btn_search.setOnClickListener(this);

        //左端分类显示数据
        initCategory();

        //轮播图
        initSlider();

        //显示数据 每个分类的内容
        initEssay();

        return view;
    }

    //避免第一张切换太快
    @Override
    public void onStart() {
        super.onStart();
        slider_icon.startAutoCycle(4000,4000,true);
    }

    //为防止旋转时出现内存泄漏
    @Override
    public void onStop() {
        slider_icon.stopAutoCycle();
        super.onStop();
    }

    public void initSlider(){
        final Activity activity=getActivity();
        HttpUtil.sendOkHttpRequest("http://yiezi.ml/showPicForPhone", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"轮播图连接失败！",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    try {
                        JSONObject jsonObject=new JSONObject(responseData);
                        String img=jsonObject.getString("bl");
                        Log.e("img", img);
                        final JSONArray jsonArray=new JSONArray(img);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    textSliderView1.description("1").image(jsonArray.getString(0));
                                    textSliderView2.description("2").image(jsonArray.getString(1));
                                    textSliderView3.description("3").image(jsonArray.getString(2));
                                    textSliderView4.description("4").image(jsonArray.getString(3));
                                    slider_icon.addSlider(textSliderView1);
                                    slider_icon.addSlider(textSliderView2);
                                    slider_icon.addSlider(textSliderView3);
                                    slider_icon.addSlider(textSliderView4);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

/*        TextSliderView textSliderView1=new TextSliderView(this.getActivity());
        textSliderView1.description("最新").image(R.drawable.slide1);

        TextSliderView textSliderView2=new TextSliderView(this.getActivity());
        textSliderView2.description("生活").image(R.drawable.slide2);

        TextSliderView textSliderView3=new TextSliderView(this.getActivity());
        textSliderView3.description("美食").image(R.drawable.slide3);

        slider_icon.addSlider(textSliderView1);
        slider_icon.addSlider(textSliderView2);
        slider_icon.addSlider(textSliderView3);
*/
        slider_icon.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);//设置下标的点
        slider_icon.setCustomAnimation(new DescriptionAnimation());//设置动画
        slider_icon.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        slider_icon.setDuration(5000); //设置动画效果5秒自动旋转

    }

    public void initEssay(){
        final Activity activity=getActivity();
        HttpUtil.sendOkHttpRequest("http://yiezi.ml/browseBlogsByPhone/json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //得到服务器返回的具体内容
                if(response.isSuccessful()){
                    try {
                        String responseData=response.body().string();
                        Log.d("Essay",responseData);
                        try {
                            JSONArray jsonArray=new JSONArray(responseData);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                int id=jsonObject.getInt("id");
                                String title=jsonObject.getString("bwbt");
                                String click=jsonObject.getString("bwdjcs");
                                String writer=jsonObject.getString("editor");
                                Date date=stampToDate(jsonObject.getString("bwcjsj"));
                             //   Date date=new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("bwcjsj"));
                                String category=jsonObject.getString("blogCategoryId");
                                String brief=jsonObject.getString("bwnr");
                                Essay essay=new Essay(id,title,click,writer,date,category,brief);
                                essayList.add(essay);
                            }
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LinearLayoutManager layoutManager2=new LinearLayoutManager(getActivity());
                                    rv_essay.setLayoutManager(layoutManager2);
                                    essayAdapter=new EssayAdapter(essayList);
                                    rv_essay.setAdapter(essayAdapter);
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

    public void initCategory(){
        String message=null;
        final Activity activity=getActivity();
        HttpUtil.sendOkHttpRequest("http://yiezi.ml/json/browseBlogCategory", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"分类连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        String responseData=response.body().string();
                        Log.d("BlogFragment",responseData);
                        try {
                            JSONArray jsonArray=new JSONArray(responseData);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                int id=jsonObject.getInt("id");
                                String cateName=jsonObject.getString("flmc");
                                Category category=new Category(id,cateName);
                                categoryList.add(category);
                            }
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //layoutManager指定RecyclerView的布局方式，LinearLayoutManager线性布局
                                    LinearLayoutManager layoutManager1=new LinearLayoutManager(getActivity());
                                    rv_category.setLayoutManager(layoutManager1);
                                    categoryAdapter=new CategoryAdapter(categoryList);
                                    rv_category.setAdapter(categoryAdapter);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                sendRequestPost();
                break;
        }
    }

    //查询方法
    private void sendRequestPost(){
        final Activity activity=getActivity();
        final String input=et_search.getText().toString().trim();
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("input_data",MODE_PRIVATE).edit();//获取对象
        editor.putString("input",input);//添加数据
        editor.apply();//提交数据

        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("keyWord",input)
                .build();
        Request request=new Request.Builder()
                .url("http://yiezi.ml/serachBlogs_json")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"查询连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData=response.body().string();
                    try {
                        JSONArray jsonArray=new JSONArray(responseData);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String str=jsonObject.getString("operation");
                        if(str.equals("success")){
                            Intent intent=new Intent(getActivity(), SearchActivity.class);
                            startActivity(intent);
                        }else {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"搜索不到该内容！",Toast.LENGTH_LONG).show();
                                }
                            });
                        }

//                        JSONArray jsonData=jsonArray.getJSONArray(1);
//                        for(int i=0;i<jsonData.length();i++){
//                            JSONObject jsonObject=jsonData.getJSONObject(i);
//
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    //将时间戳转化为时间
//    public static Date stampToDate(String s){
//    //    String res;
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        long lt=new Long(s);
//        Date date=new Date(lt);
////        res=simpleDateFormat.format(date);
//        return date;
//    }
}

package zt.com.resourcesharing.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zt.com.resourcesharing.R;
import zt.com.resourcesharing.activity.AccountManageActivity;
import zt.com.resourcesharing.activity.BlogNumActivity;
import zt.com.resourcesharing.activity.InfoManageActivity;
import zt.com.resourcesharing.activity.SettingActivity;
import zt.com.resourcesharing.utils.HttpUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MineFragment extends Fragment implements View.OnClickListener {
    private TextView tv_account;
    private TextView tv_set;
    private TextView tv_info;
    private CircleImageView circle_head;
    private TextView tv_blogNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.mine_fragment,container,false);
        tv_account=(TextView)view.findViewById(R.id.tv_account);
        tv_set=(TextView)view.findViewById(R.id.tv_set);
        tv_info=(TextView)view.findViewById(R.id.tv_info);
        circle_head=(CircleImageView)view.findViewById(R.id.circle_head);
        tv_blogNum=(TextView)view.findViewById(R.id.tv_blogNum);

        tv_account.setOnClickListener(this);
        tv_set.setOnClickListener(this);
        tv_info.setOnClickListener(this);
        tv_blogNum.setOnClickListener(this);
/*
        SharedPreferences pre=getActivity().getSharedPreferences("bitmap_data", Context.MODE_PRIVATE);
        String img_head=pre.getString("bitmap","");
        Bitmap bitmap=null;
        if(img_head!=""){
            byte[] bytes=Base64.decode(img_head.getBytes(),1);
            bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        }
        circle_head.setImageBitmap(bitmap);*/
//        ByteArrayInputStream bis=new ByteArrayInputStream(android.util.Base64.encode(img_head.getBytes(), Base64.DEFAULT));


        return view;
    }

    public void sendRequestPost(){
        final Activity activity=getActivity();
        SharedPreferences pref=getActivity().getSharedPreferences("myshare",MODE_PRIVATE);
        int userId=pref.getInt("id",-1);
        Log.d("InfoManUserId", String.valueOf(userId));

        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //requestBody存放待提交的参数
        RequestBody requestBody=new FormBody.Builder()
                .add("id", String.valueOf(userId))
                .build();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url("http://yiezi.ml/json/viewMember")
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
                        JSONObject jsonObject=jsonArray.getJSONObject(1);
                        final String img=jsonObject.getString("userImg");
                        Log.e("img",img);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                               circle_head.setImageResource();

                            }
                        });
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
            case R.id.tv_account:
                Intent intent1=new Intent(getActivity(),AccountManageActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_set:
                Intent intent2=new Intent(getActivity(),SettingActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_info:
                Intent intent3=new Intent(getActivity(),InfoManageActivity.class);
                startActivity(intent3);
                break;
            case R.id.tv_blogNum:
                Intent intent4=new Intent(getActivity(),BlogNumActivity.class);
                startActivity(intent4);
                break;
        }
    }
}

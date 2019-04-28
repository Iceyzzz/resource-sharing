package zt.com.resourcesharing.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/17.
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        //创建一个OkHttpClient实例
        OkHttpClient client=new OkHttpClient();
        //创建一个request对象 url()方法设置目标的网络地址
        Request request=new Request.Builder()
                .url(address)
                .build();
        //enqueue()方法将最终请求结果回调到okhttp3.Callback当中
        client.newCall(request).enqueue(callback);
    }
}

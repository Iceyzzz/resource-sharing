package zt.com.resourcesharing.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2018/5/17.
 */
//将时间戳转化为时间
public class DateConvert {

    public static Date stampToDate(String s){
        //    String res;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");//格式化时间
        long lt=new Long(s);
        Date date=new Date(lt);
//        res=simpleDateFormat.format(date);
        return date;
    }
}

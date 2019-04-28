package zt.com.resourcesharing.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Administrator on 2018/5/16.
 */

//引用
//用于图片验证码的工具类
public class CodeVerify {
    //随机数数组
    private static final char[] CHARS=
            {'0','1','2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static CodeVerify codeVerify;
    private static final int CODE_LENGTH=4;//验证码默认随机数的个数
    private static final int CODE_SIZE=60;//默认字体大小
    private static final int LINE_NUMBER=4;//默认干扰线条的条数
    private static final int BASE_LEFT=20;//左边距
    private static final int RANGE_LEFT=30;//左边距范围值
    private static final int BASE_TOP=70;//上边距
    private static final int RANGE_TOP=15;//上边距范围值
    private static final int WIDTH=300;//默认宽度
    private static final int HEIGHT=100;//默认高度

    private String code;
    private StringBuilder builder=new StringBuilder();
    private int padding_left,padding_top;
    private Random random=new Random();
    public static CodeVerify getInstance(){
        if(codeVerify==null){
            codeVerify=new CodeVerify();
        }
        return codeVerify;
    }
    //生成验证码图片
    public Bitmap createBitmap(){
        //每次生成验证码图片时初始化
        padding_left=0;
        padding_top=0;

        Bitmap bitmap=Bitmap.createBitmap(WIDTH,HEIGHT, Bitmap.Config.ARGB_8888);//宽 高 色彩存储
        Canvas canvas=new Canvas(bitmap);//画布

        code=createCode();

        canvas.drawColor(Color.WHITE);
        Paint paint=new Paint();
        paint.setTextSize(CODE_SIZE);

        //画验证码
        for(int i=0;i<code.length();i++){
            randomTextStyle(paint);
            randomPadding();
            canvas.drawText(code.charAt(i) + "" , padding_left, padding_top, paint);
        }
        //干扰线
        for(int i=0;i<LINE_NUMBER; i++){
            drawLine(canvas, paint);
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);//保存
        canvas.restore();
        return bitmap;
    }
    //得到图片中的验证码字符串
    public String getCode(){
        return code;
    }
    //生成验证码
    public String createCode(){
        builder.delete(0,builder.length());//使用之前首先清空内容
        for(int i=0;i<CODE_LENGTH;i++){
            builder.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return builder.toString();
    }
    //生成干扰线
    private void drawLine(Canvas canvas,Paint paint){
        int color=randomColor();
        int startX=random.nextInt(WIDTH);
        int startY=random.nextInt(HEIGHT);
        int stopX=random.nextInt(WIDTH);
        int stopY=random.nextInt(HEIGHT);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }
    //随机颜色
    private int randomColor(){
        builder.delete(0,builder.length());//使用之前首先清空内容
        String haxString;
        for(int i=0;i<3;i++){
            haxString=Integer.toHexString(random.nextInt(0xFF));
            if(haxString.length()==1){
                haxString="0"+haxString;
            }
            builder.append(haxString);
        }
        return Color.parseColor("#"+builder.toString());
    }
    //随机文本样式，颜色，粗细，倾斜度
    private void randomTextStyle(Paint paint){
        int color=randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());//true为粗体，false为非粗体
        float skewX=random.nextInt(11)/10;
        skewX=random.nextBoolean()?skewX:-skewX;
        paint.setTextSkewX(skewX);//float类型参数，负数表示右斜，整数左斜
    }
    //随机间距
    private void randomPadding(){
        padding_left+=BASE_LEFT+random.nextInt(RANGE_LEFT);
        padding_top=BASE_TOP+random.nextInt(RANGE_TOP);
    }
}


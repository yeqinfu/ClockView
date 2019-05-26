package com.ppandroid.dancinggrass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * created by yeqinfu 2019/5/26
 *
 **/
public class GrassView extends View {
    public GrassView(Context context) {
        super(context);
    }

    public GrassView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    public GrassView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        /*y轴翻转*/
        canvas.scale(1,-1);
        /*画布向下平移*/
        canvas.translate(0,-mHeight);
        Random random=new Random();
        for (int i=0;i<20;i++){
            Point point=new Point();
            point.y=0;
            point.x=random.nextInt(10)*mWidth/10;

            new Grass(random.nextInt(10),150+random.nextFloat()*150f,point).drawSelf(canvas);
        }

      /*  *//*测试代码，系统提供的api可以画出曲线，但是没法画出随着曲线变动的叶子*//*
        Path path=new Path();
        path.moveTo(mWidth/2,0);
        path.quadTo(mWidth/4,mHeight/8,mWidth/2,mHeight/4);
        path.quadTo(mWidth*3/4,mHeight*3/8,mWidth/2,mHeight/2);
        canvas.drawPath(path,getTextPaint());

        *//***叶子测试*//*
        Point top=new Point(mWidth/4,mHeight*3/8);
        Point buttom=new Point(mWidth*2/4,mHeight*1/8);
        canvas.drawCircle(top.x,top.y,5,getTextPaint());
        canvas.drawCircle(buttom.x,buttom.y,5,getTextPaint());
        canvas.save();*/
       /* canvas.translate(buttom.x,buttom.y);
        double a=(buttom.y-top.y)*1.0/(buttom.x-top.x);

        float di=180+(float) (test/(2*Math.PI)*360);
        canvas.rotate(di);
        float distance= (float) Math.sqrt((top.y-buttom.y)*(top.y-buttom.y)+(top.x-buttom.x)*(top.x-buttom.x));
        Path path1=new Path();
        path1.moveTo(0,0);

        path1.quadTo(distance/2,distance/4,distance,0);
        canvas.drawPath(path1,getTextPaint());*/
      /*  canvas.restore();*/

      //  canvas.drawCircle(0,0,50,getTextPaint());
    }

    int mWidth = 0;
    int mHeight = 0;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    Paint textPaint;
    public Paint getTextPaint() {
        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setColor(Color.BLACK);
            textPaint.setStyle(Paint.Style.STROKE);
        }
        return textPaint;
    }
}

package com.ppandroid.dancinggrass;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.Random;

/**
 * created by yeqinfu 2019/5/26
 * 婀娜曲线的小草对象是也！
 **/
public class Grass {
    /**小草高度，默认是2，一个单位高度是一个二阶贝塞尔曲线弧*/
    int height=5;
    /**每一个单位高度的真实高度*/
    float radianHeight=300;
    /**小草扭动幅度，也就是控制点偏移量*/
    float radianOffset=radianHeight/4;
    /**小草的根部位置*/
    Point rootPostion=null;
    /**
     * 叶子和茎的偏移角度
     *
     * */
    float angle=60;
    /**
     * 叶子的数量，不包括顶部一个
     * 目前先暂定，一个弧度只有一个叶子，其实可以控制更多
     * TODO 自定义多个叶子
     * */
    int leafNumber=height;

    public Grass(Point rootPostion) {
        this.rootPostion = rootPostion;
    }

    public Grass(int height, float radianHeight, Point rootPostion) {
        this.height = height;
        this.radianHeight = radianHeight;
        this.rootPostion = rootPostion;
        radianOffset=radianHeight/4;
       /*开始方向随机*/
        radianOffset= new Random().nextBoolean()?radianOffset:-radianOffset;
    }

    public void drawSelf(Canvas canvas){
        canvas.save();
        /*移动到根部*/
        canvas.translate(rootPostion.x,rootPostion.y);
        Path path=new Path();
        path.moveTo(0,0);
        for (int i=1;i<height+1;i++){
            Point p1=new Point((int)radianOffset,(int)((i-1)*radianHeight+radianHeight/2));
            Point p2=new Point(0, (int) (radianHeight*i));
            path.quadTo(p1.x,p1.y,p2.x,p2.y);

            Point p0=new Point(0, (int) ((i-1)*radianHeight));
            Point buttom=calculateBerzierTwo(p0,p1,p2,0.5f);
            Point top=new Point();
            top.x= (int) (buttom.x+radianOffset);
            top.y= (int) (buttom.y+radianHeight/3);
            radianOffset=-radianOffset;
           // canvas.drawLine(0,0,0,radianHeight*height,getTextPaint());
           // canvas.drawCircle(buttom.x,buttom.y,20,getTextPaint());
            new Leaf(top,buttom).drawSelf(canvas);
        }
        canvas.drawPath(path,getTextPaint());
        canvas.restore();
    }

    public float getBerzierTwo(float p0,float p1,float p2,float t){
        return (1-t)*(1-t)*p0+2*t*(1-t)*p1+t*t*p2;
    }
    public Point calculateBerzierTwo(Point p0,Point p1,Point p2,float t){
        Point bPoint=new Point();
        bPoint.x= (int) getBerzierTwo(p0.x,p1.x,p2.x,t);
        bPoint.y= (int) getBerzierTwo(p0.y,p1.y,p2.y,t);
        return bPoint;

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

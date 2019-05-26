package com.ppandroid.dancinggrass;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

/**
 * created by yeqinfu 2019/5/26
 * 可爱的叶子
 **/
public class Leaf {
    /**
     * 定点和根部 只要确定这两个点就可以画出叶子了
     */
    Point top;
    Point buttom;
    /*两点的距离*/
    float distance;
    /*两点的斜率*/
    float a;
    /*atan值*/
    double atan;
    double rotateValue;

    public Leaf(Point top, Point buttom) {
        this.top = top;
        this.buttom = buttom;
        distance = (float) Math.sqrt((top.y - buttom.y) * (top.y - buttom.y) + (top.x - buttom.x) * (top.x - buttom.x));


    }

    /**
     * 把叶子画出来吧！
     *
     * @param canvas
     */
    public void drawSelf(Canvas canvas) {
        canvas.save();
        a = (buttom.y - top.y) * 1.0f / (buttom.x - top.x);
        atan = Math.atan(a);
        rotateValue = (float) (atan / (2 * Math.PI) * 360);
        if (atan<0){
            rotateValue=180+rotateValue;
        }
        /*移动到根部*/
        canvas.translate(buttom.x, buttom.y);
        canvas.rotate((float) rotateValue);
        Path path=new Path();
        path.moveTo(0,0);
        path.quadTo(distance/2,distance/4,distance,0);
        path.moveTo(0,0);
        path.quadTo(distance/2,-distance/4,distance,0);
        canvas.drawPath(path,getTextPaint());
        canvas.restore();

    }

    Paint textPaint;

    public Paint getTextPaint() {
        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setColor(Color.BLACK);
            textPaint.setStyle(Paint.Style.FILL);
        }
        return textPaint;
    }
}

package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yeqinfu on 22/05/2019
 * 线填充不规则图形
 */
public class LineFillView extends View {
    public LineFillView(Context context) {
        super(context);
    }

    public LineFillView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public LineFillView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path=CommonPath.getPath01(0,0,getWidth(),getHeight());
       // canvas.drawPath(path,getBoundaryPaint());
        Path fillPath=CommonPath.getParallelLinesPath(0,0,getWidth(),getHeight(),30);
       // canvas.drawPath(fillPath,getFillPaint());
        //path.op(fillPath, Path.Op.INTERSECT);
        //canvas.drawPath(path,getBoundaryPaint());

        Path heartPath=CommonPath.getHeartPath(100,100,200,200);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           float[] re= path.approximate(1);
        }
       // canvas.drawPath(heartPath,getFillPaint());
        float[] all=new float[1000];
        float[] all2=new float[1000];
        int x=0;
        for (int i=0;i<1000;i++){
            all[i]=100;
            all2[i]=101;
            if (i%2==0){
                all[i]=x;
                all2[i]=x;
                x++;

            }
        }


        canvas.drawPoints(all,getFillPaint());
        canvas.drawPoints(all2,getFillPaint());

    }

    /***
     * *********************************************
     * ***************画笔放置区域********************
     * *********************************************
     * */
    private Paint boundaryPaint=null;
    /**
     * 边界画笔
     * @return
     */
    public Paint getBoundaryPaint(){
        if (boundaryPaint==null){
            boundaryPaint=new Paint();
            boundaryPaint.setAntiAlias(true);
            boundaryPaint.setColor(Color.RED);
            boundaryPaint.setStyle(Paint.Style.STROKE);
        }
        return boundaryPaint;

    }
    private Paint fillPaint=null;
    /**
     * 边界画笔
     * @return
     */
    public Paint getFillPaint(){
        if (fillPaint==null){
            fillPaint=new Paint();
            fillPaint.setAntiAlias(true);
            fillPaint.setColor(Color.BLUE);
            fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        return fillPaint;

    }


}

package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yeqinfu on 23/05/2019
 */
public class ClockView extends View {
    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int mWidth = 0;
    int mHeight = 0;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /***
     * 每个圆形的半径是根据以下文字进行测量得到的，每个半径之间嵌入一定的间隔
     * 间隔变量 radiusOffset
     * */
    private String year = "2019年";
    private String month = "十二月";
    private String day = "三十一号";
    private String dayhelf = "下午";
    private String hour = "十二点";
    private String minute = "五十九分";
    private String second = "五十九秒";
    private float radiusOffset = 10;
    private Paint textPaint = null;
    /**
     * sp 转 px
     *
     * @param spValue {@code spValue}
     * @return {@code pxValue}
     */
    public  int sp2px( float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    public Paint getTextPaint() {
        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(sp2px(6));
            textPaint.setStyle(Paint.Style.FILL);
        }
        return textPaint;
    }
    private float getTextLength(Paint paint,String text){
        return paint.measureText(text);
    }
    private float getTextLength(String text){
        return getTextLength(getTextPaint(),text);
    }
    /***
     * 各种半径
     */
    private float yearRadius=0;
    private float monthRadius=0;
    private float dayRadius=0;
    private float dayHalfRadius=0;
    private float hourRadius=0;
    private float minuteRadius=0;
    private float secondRadius=0;
    private void init(){
        yearRadius=getTextLength(year)/2+radiusOffset;
        monthRadius=yearRadius+radiusOffset+getTextLength(month);
        dayRadius=monthRadius+radiusOffset+getTextLength(day);
        dayHalfRadius=dayRadius+radiusOffset+getTextLength(dayhelf);
        hourRadius=dayHalfRadius+radiusOffset+getTextLength(hour);
        minuteRadius=hourRadius+radiusOffset+getTextLength(minute);
        secondRadius=minuteRadius+radiusOffset+getTextLength(second);
        commonTextHeight=-getTextPaint().ascent() + getTextPaint().descent();
    }

    /**
     * 因为画笔只有一根，高度肯定是只有一个
     */
    private float commonTextHeight=0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        /**画中心*/
        drawTextOnMiddle(canvas,year,0,0);
        /**画月份*/
        double initAngle = 0;
        canvas.save();

        for (int i=0;i<12;i++){
            Polar polar=new Polar(monthRadius,initAngle);
            drawTextToPointLeft(canvas,polar.toPoint(),CommonNumber.getMonths()[i]);
            canvas.rotate(360/12);
        }
        canvas.restore();
        canvas.save();
        /**画日期*/
        String[] days=CommonNumber.getDays(null);
        initAngle=0;
        for (int i=0;i<days.length;i++){
            Polar polar=new Polar(dayRadius,initAngle);
            drawTextToPointLeft(canvas,polar.toPoint(),days[i]);
            canvas.rotate(360.0f/(days.length));
        }
        canvas.restore();
        /**画上午下午*/
        canvas.save();
        initAngle=0;
        Polar polar1=new Polar(dayHalfRadius,initAngle);
        drawTextToPointLeft(canvas,polar1.toPoint(),"上午");
        canvas.rotate(180);
        drawTextToPointLeft(canvas,polar1.toPoint(),"下午");
        canvas.restore();
        /**画小时*/
        canvas.save();
        initAngle=0;
        String[] hours=CommonNumber.getHours();
        for (int i=0;i<12;i++){
            Polar polar=new Polar(hourRadius,initAngle);
            drawTextToPointLeft(canvas,polar.toPoint(),hours[i]);
            canvas.rotate(360.0f/12);
        }
        canvas.restore();
        /**画分钟*/
        canvas.save();
        initAngle=0;
        String[] minutes=CommonNumber.getMinutes();
        for (int i=0;i<60;i++){
            Polar polar=new Polar(minuteRadius,initAngle);
            drawTextToPointLeft(canvas,polar.toPoint(),minutes[i]);
            canvas.rotate(360.0f/60);
        }
        canvas.restore();

        /**画秒*/
        canvas.save();
        initAngle=0;
        String[] seconds=CommonNumber.getSeconds();
        for (int i=0;i<60;i++){
            Polar polar=new Polar(secondRadius,initAngle);
            drawTextToPointLeft(canvas,polar.toPoint(),seconds[i]);
            canvas.rotate(360.0f/60);
        }
        canvas.restore();







    }

    private void drawTextToPointLeft(Canvas canvas,Point toPoint, String targetText) {
        Rect bounds = new Rect();
        getTextPaint().getTextBounds(targetText, 0, targetText.length(), bounds);
        float width =bounds.right-bounds.left;
        toPoint.x= (int) (toPoint.x-width/2);
        drawTextOnMiddle(canvas,targetText,toPoint.x,toPoint.y);

    }

    private void drawTextOnMiddle(Canvas canvas, String targetText, float x, float y) {
        drawTextOnMiddle(canvas,targetText,x,y,getTextPaint());
    }
    /**
     * 一个帮助你把文字写在指定点并居住在指定点位置的方法
     * 这里的偏移直角坐标系采用默认指教坐标系
     */
    private void drawTextOnMiddle(Canvas canvas, String targetText, float x, float y, Paint paint) {
        /*文字测量*/
        Rect bounds = new Rect();
        paint.getTextBounds(targetText, 0, targetText.length(), bounds);
        float width =bounds.right-bounds.left;
        float height=bounds.bottom-bounds.top;
        canvas.drawText(targetText, x - width / 2, y + height / 2, paint);
    }

    /**
     * 极坐标类
     */
    static class Polar {
        double radius;
        double angle;

        public Polar(double radius, double angle) {
            this.radius = radius;
            this.angle = angle;
        }

        public double getRadius() {

            return radius;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        public double getAngle() {
            return angle;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        public Point toPoint() {
            Point point = new Point();
            point.x = (int) (Math.cos(angle) * radius);
            point.y = (int) (Math.sin(angle) * radius);
            return point;
        }
    }

}

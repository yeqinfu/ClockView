package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Calendar;

/**
 * 年不做处理，我不相信能用一年
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
    private Paint textPaintGray = null;

    /**
     * sp 转 px
     *
     * @param spValue {@code spValue}
     * @return {@code pxValue}
     */
    public int sp2px(float spValue) {
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

    public Paint getTextPaintGray() {
        if (textPaintGray == null) {
            textPaintGray = new Paint();
            textPaintGray.setAntiAlias(true);
            textPaintGray.setColor(Color.GRAY);
            textPaintGray.setTextSize(sp2px(6));
            textPaintGray.setStyle(Paint.Style.FILL);
        }
        return textPaint;
    }

    private float getTextLength(Paint paint, String text) {
        return paint.measureText(text);
    }

    private float getTextLength(String text) {
        return getTextLength(getTextPaint(), text);
    }

    /***
     * 各种半径
     */
    private float yearRadius = 0;
    private float monthRadius = 0;
    private float dayRadius = 0;
    private float dayHalfRadius = 0;
    private float hourRadius = 0;
    private float minuteRadius = 0;
    private float secondRadius = 0;

    private void init() {
        yearRadius = getTextLength(year) / 2 + radiusOffset;
        monthRadius = yearRadius + radiusOffset + getTextLength(month);
        dayRadius = monthRadius + radiusOffset + getTextLength(day);
        dayHalfRadius = dayRadius + radiusOffset + getTextLength(dayhelf);
        hourRadius = dayHalfRadius + radiusOffset + getTextLength(hour);
        minuteRadius = hourRadius + radiusOffset + getTextLength(minute);
        secondRadius = minuteRadius + radiusOffset + getTextLength(second);
        commonTextHeight = -getTextPaint().ascent() + getTextPaint().descent();
       // startAnimator();
        setDateAndStart(null);
    }
    public void setDateAndStart(Calendar calendar){
        if (calendar==null){
            calendar=Calendar.getInstance();
        }
        monthRepeat=calendar.get(Calendar.MONTH);
        dayRepeat=calendar.get(Calendar.DAY_OF_MONTH);
        dayHalfRepeat=calendar.get(Calendar.HOUR_OF_DAY)>12?1:0;
        if (calendar.get(Calendar.HOUR_OF_DAY)>12){
            hourRepeat=calendar.get(Calendar.HOUR_OF_DAY)-12;
        }else{
            hourRepeat=calendar.get(Calendar.HOUR_OF_DAY);
        }
        minuteRepeat=calendar.get(Calendar.MINUTE);
        secondRepeat=calendar.get(Calendar.SECOND);
        startAnimator();



    }

    /**
     * 因为画笔只有一根，高度肯定是只有一个
     */
    private float commonTextHeight = 0;
    /*判断是否是动画状态*/
    private boolean isMove = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        /**画中心*/
        drawTextOnMiddle(canvas, year, 0, 0);
        /**画月份*/
        double initAngle = 0;
        canvas.save();
        canvas.rotate(monthRepeat * (360.0f / 12) + (float) monInitAngle);

        for (int i = 0; i < 12; i++) {
            Polar polar = new Polar(monthRadius, initAngle);
            drawTextToPointLeft(canvas, polar.toPoint(), CommonNumber.getMonths()[i], getTextPaint());
            canvas.rotate(-360 / 12);
        }
        canvas.restore();
        canvas.save();
        /**画日期*/
        String[] days = CommonNumber.getDays(null);
        initAngle = 0;
        canvas.rotate(dayRepeat * (360.0f / days.length) + (float) dayInitAngle);
        for (int i = 0; i < days.length; i++) {
            Polar polar = new Polar(dayRadius, initAngle);
            drawTextToPointLeft(canvas, polar.toPoint(), days[i], getTextPaint());
            canvas.rotate(-360.0f / (days.length));
        }
        canvas.restore();
        /**画上午下午*/
        canvas.save();
        initAngle = 0;
        canvas.rotate(dayHalfRepeat * 180 + (float) dayHalfInitAngle);
        Polar polar1 = new Polar(dayHalfRadius, initAngle);
        drawTextToPointLeft(canvas, polar1.toPoint(), "上午", getTextPaint());
        canvas.rotate(180);
        drawTextToPointLeft(canvas, polar1.toPoint(), "下午", getTextPaint());
        canvas.restore();
        /**画小时*/
        canvas.save();
        initAngle = 0;
        String[] hours = CommonNumber.getHours();
        canvas.rotate(hourRepeat * 30 + (float) hourInitAngle);
        for (int i = 0; i < 12; i++) {
            Polar polar = new Polar(hourRadius, initAngle);
            drawTextToPointLeft(canvas, polar.toPoint(), hours[i], getTextPaint());
            canvas.rotate(-360.0f / 12);
        }
        canvas.restore();
        /**画分钟*/
        canvas.save();
        initAngle = 0;
        String[] minutes = CommonNumber.getMinutes();
        canvas.rotate(minuteRepeat * 6 + (float) minuteInitAngle);
        for (int i = 0; i < 60; i++) {
            Polar polar = new Polar(minuteRadius, initAngle);
            drawTextToPointLeft(canvas, polar.toPoint(), minutes[i], getTextPaint());
            canvas.rotate(-360.0f / 60);
        }
        canvas.restore();

        /**画秒*/
        canvas.save();
        initAngle = 0;
        String[] seconds = CommonNumber.getSeconds();
        /*移动和禁止状态画笔颜色不一样*/
        Paint p = isMove ? getTextPaintGray() : getTextPaint();
        canvas.rotate(secondRepeat * 6 + (float) secondInitAngle);
        Log.d("yeqinfu", "---------->" + secondRepeat + "==" + secondInitAngle);
        for (int i = 0; i < 60; i++) {
            Polar polar = new Polar(secondRadius, initAngle);
            drawTextToPointLeft(canvas, polar.toPoint(), seconds[i], p);
            canvas.rotate((float) -(360.0f / 60));
        }
        canvas.restore();


    }


    private void drawTextToPointLeft(Canvas canvas, Point toPoint, String targetText, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(targetText, 0, targetText.length(), bounds);
        float width = bounds.right - bounds.left;
        toPoint.x = (int) (toPoint.x - width / 2);
        drawTextOnMiddle(canvas, targetText, toPoint.x, toPoint.y, paint);

    }

    private void drawTextOnMiddle(Canvas canvas, String targetText, float x, float y) {
        drawTextOnMiddle(canvas, targetText, x, y, getTextPaint());
    }

    /**
     * 一个帮助你把文字写在指定点并居住在指定点位置的方法
     * 这里的偏移直角坐标系采用默认指教坐标系
     */
    private void drawTextOnMiddle(Canvas canvas, String targetText, float x, float y, Paint paint) {
        /*文字测量*/
        Rect bounds = new Rect();
        paint.getTextBounds(targetText, 0, targetText.length(), bounds);
        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;
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


    private int secondRepeat = 0;
    double secondInitAngle = 0;
    private int minuteRepeat = 0;
    double minuteInitAngle = 0;
    private int hourRepeat = 0;
    double hourInitAngle = 0;
    private int dayHalfRepeat = 0;
    double dayHalfInitAngle = 0;
    private int dayRepeat = 0;
    double dayInitAngle = 0;
    private int monthRepeat = 0;
    double monInitAngle = 0;

     ValueAnimator last;
    public void startAnimator() {
        if (last!=null){
            last.cancel();
        }
        final ValueAnimator valueAnimator= ValueAnimator.ofFloat(0, 1);
        last=valueAnimator;

        /***
         * 秒针动画
         * */

        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(59);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                secondInitAngle = ((float) animation.getAnimatedValue() * 360.0 / 60);
                if (secondRepeat == 59) {
                    minuteInitAngle = secondInitAngle;
                    if (minuteRepeat == 59) {
                        hourInitAngle = ((float) animation.getAnimatedValue() * 360.0 / 12);
                        if (hourRepeat == 11) {
                            dayHalfInitAngle = ((float) animation.getAnimatedValue() * 360.0 / 2);
                            int dayLength=CommonNumber.getDays(null).length;
                            if (dayHalfRepeat == 1) {
                                dayInitAngle = ((float) animation.getAnimatedValue() * 360.0 /dayLength);
                                if (dayRepeat==1){
                                    monInitAngle=((float) animation.getAnimatedValue() * 360.0 / 12);
                                }
                            }

                        }

                    }

                }

                invalidate();

            }

        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {

            int secondTemp = 0;
            int minuteTemp = 0;
            int hourTemp = 0;
            int dayHalfTemp = 0;
            int dayTemp = 0;

            @Override
            public void onAnimationRepeat(Animator animation) {
                secondRepeat++;
                if (secondRepeat == 1) {
                    minuteRepeat += secondTemp;
                    secondTemp = 0;
                    minuteInitAngle = 0;

                    hourRepeat += minuteTemp;
                    minuteTemp = 0;
                    hourInitAngle = 0;

                    dayHalfRepeat += hourTemp;
                    hourTemp = 0;
                    dayHalfInitAngle = 0;

                    dayRepeat += dayHalfTemp;
                    dayHalfTemp = 0;
                    dayInitAngle = 0;

                    monthRepeat += dayTemp;
                    dayTemp = 0;
                    monInitAngle = 0;
                }
                super.onAnimationRepeat(animation);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //  isMove=false;
                //  invalidate();
                secondRepeat++;
                valueAnimator.start();
                secondTemp = 1;
                secondRepeat = secondRepeat % 60;
                minuteRepeat = minuteRepeat % 60;
                hourRepeat = hourRepeat % 12;
                dayHalfRepeat = dayHalfRepeat % 2;
                int dayLength=CommonNumber.getDays(null).length;
                dayRepeat=dayRepeat%dayLength;
                monthRepeat=monthRepeat%12;


                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isMove = true;
                super.onAnimationStart(animation);

            }

        });
        valueAnimator.start();


    }


}

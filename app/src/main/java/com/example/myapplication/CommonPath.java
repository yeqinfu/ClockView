package com.example.myapplication;

import android.graphics.Path;

/**
 * Created by yeqinfu on 22/05/2019
 * 放置常用的不规则path
 */
public class CommonPath {

    /**
     *
     * 不规则，有空画成注释哈哈
     * @param width
     * @param height
     * @return
     */
    public static Path getPath01(int startx,int starty,int width,int height){
        Path path=new Path();
        path.moveTo(startx,starty+height*2/5);
        path.lineTo(startx+width/5,starty+height/5);
        path.lineTo(startx+width*2/5,starty+height*2/5);
        path.lineTo(startx+width*4/5,starty+0);
        path.lineTo(startx+width,starty+height*1/5);
        path.lineTo(startx+width*4/5,starty+height*3/5);
        path.lineTo(startx+width,starty+height*4/5);
        path.lineTo(startx+width*4/5,starty+height);
        path.lineTo(startx+width*3/5,starty+height*4/5);
        path.lineTo(startx+width*2/5,starty+height*4/5);
        path.close();
        return path;
    }

    /**
     * 心型图
     * @param width
     * @param height
     * @return
     */
    public static Path getHeartPath(int startx,int starty,int width,int height){
        Path path=new Path();
        path.moveTo(startx,starty+height/4);
        path.lineTo(startx+width/8,starty+height/8);
        path.lineTo(startx+width*3/8,starty+height/8);
        path.lineTo(startx+width/2,starty+height/4);
        path.lineTo(startx+width*5/8,starty+height/8);
        path.lineTo(startx+width*7/8,starty+height/8);
        path.lineTo(startx+width,starty+height/4);
        path.lineTo(startx+width,starty+height/2);
        path.lineTo(startx+width/2,starty+height);
        path.lineTo(startx,starty+height/2);
        path.close();
        return path;
    }

    /**
     * 宽高等分斜平行线
     * @param width
     * @param height
     * @param lineNumber 需要在范围图形画多少根线
     * @return
     */
    public static Path getParallelLinesPath(int startx,int starty,int width,int height,int lineNumber){
        Path path=new Path();
        int widthOffset=width/lineNumber;
        int heightOffset=height/lineNumber;

        for (int i=1;i<lineNumber;i++){
            path.moveTo(startx+i*widthOffset,0);
            path.lineTo(0,starty+i*heightOffset);
            path.moveTo(startx+(lineNumber-i)*widthOffset,height);
            path.lineTo(width,starty+(lineNumber-i)*heightOffset);
        }
        path.moveTo(startx+(lineNumber)*widthOffset,0);
        path.lineTo(0,starty+(lineNumber)*heightOffset);

        return path;
    }

}

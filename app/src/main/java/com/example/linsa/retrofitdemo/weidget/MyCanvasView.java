package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.linsa.retrofitdemo.util.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linsa on 2017/12/6:10:53.
 * des: CanvasView的使用
 */

public class MyCanvasView extends View {

    private Paint paint;

    /**
     * Y轴方向的偏移量
     */
    private int mDy = 0;

    /**
     * X轴方向的偏移量
     */
    private int mDx = 0;

    /**
     * 定义绘制的类型
     */
    private int mType;

    public MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mDx, mDy);
        switch (mType) {
            case Common.TYPE_XY:
                drawAxis(canvas);
                break;
            case Common.TYPE_ARGB:
                drawARGB(canvas);
                break;
            case Common.TYPE_TEXT:
                drawText(canvas);
                break;
            case Common.TYPE_PATH:
                drawPath(canvas);
                break;
            case Common.TYPE_OVAL:
                drawOval(canvas);
                break;
        }

    }

    /**
     * 自定义绘制椭圆
     * @param canvas
     */
    private void drawOval(Canvas canvas) {




    }

    public void setDxAndDy(int type, int dx, int dy) {
        this.mType = type;
        this.mDy = dy;
        this.mDx = dx;
        invalidate();
    }


    //绘制坐标系
    private void drawAxis(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(6);

        //用绿色画x轴，用蓝色画y轴

        //第一次绘制坐标轴
        paint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, paint);//绘制x轴
        paint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, paint);//绘制y轴

        //对坐标系平移后，第二次绘制坐标轴
        canvas.translate(canvasWidth / 4, canvasWidth / 4);//把坐标系向右下角平移
        paint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, paint);//绘制x轴
        paint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, paint);//绘制y轴

        //再次平移坐标系并在此基础上旋转坐标系，第三次绘制坐标轴
        canvas.translate(canvasWidth / 4, canvasWidth / 4);//在上次平移的基础上再把坐标系向右下角平移
        canvas.rotate(30);//基于当前绘图坐标系的原点旋转坐标系
        paint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, paint);//绘制x轴
        paint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, paint);//绘制y轴


    }


    private void drawARGB(Canvas canvas) {
        canvas.drawARGB(255, 139, 197, 186);
    }


    private void drawText(Canvas canvas){
        paint.reset();
        paint.setTextSize(38);
        paint.setFakeBoldText(true);//将画笔设置为粗体
        paint.setUnderlineText(true);//设置具有下划线
        paint.setTextAlign(Paint.Align.LEFT);//设置右对齐
        paint.setColor(Color.BLUE);
        canvas.drawText("This love is good !",400,500,paint);

        paint.reset();
        paint.setTextSize(38);
        paint.setFakeBoldText(true);//将画笔设置为粗体
        paint.setUnderlineText(true);//设置具有下划线
        canvas.rotate(30);
        paint.setTextAlign(Paint.Align.LEFT);//设置右对齐
        paint.setColor(Color.BLUE);
        canvas.drawText("This love is good !",400,500,paint);


        paint.reset();
        paint.setTextSize(38);
        paint.setFakeBoldText(true);//将画笔设置为粗体
        paint.setUnderlineText(true);//设置具有下划线
        paint.setTextAlign(Paint.Align.LEFT);//设置右对齐
        paint.setColor(Color.BLUE);
        canvas.drawText("This love is good !",400,500,paint);
    }


    private void drawPoint(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int x = canvasWidth / 2;
        int deltaY = canvasHeight / 3;
        int y = deltaY / 2;
        paint.setColor(0xff8bc5ba);//设置颜色
        paint.setStrokeWidth(50 );//设置线宽，如果不设置线宽，无法绘制点

        //绘制Cap为BUTT的点
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(x, y, paint);

        //绘制Cap为ROUND的点
        canvas.translate(0, deltaY);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(x, y, paint);

        //绘制Cap为SQUARE的点
        canvas.translate(0, deltaY);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(x, y, paint);
    }


    private void drawPath(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int deltaX = canvasWidth / 4;
        int deltaY = (int)(deltaX * 0.75);

        paint.setColor(0xff8bc5ba);//设置画笔颜色
        paint.setStrokeWidth(4);//设置线宽

        /*--------------------------用Path画填充面-----------------------------*/
        paint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        Path path = new Path();
        //向Path中加入Arc
        RectF arcRecF = new RectF(0, 0, deltaX, deltaY);
        path.addArc(arcRecF, 0, 135);
        //向Path中加入Oval
        RectF ovalRecF = new RectF(deltaX, 0, deltaX * 2, deltaY);
        path.addOval(ovalRecF, Path.Direction.CCW);
        //向Path中添加Circle
        path.addCircle((float)(deltaX * 2.5), deltaY / 2, deltaY / 2, Path.Direction.CCW);
        //向Path中添加Rect
        RectF rectF = new RectF(deltaX * 3, 0, deltaX * 4, deltaY);
        path.addRect(rectF, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        /*--------------------------用Path画线--------------------------------*/
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, deltaY * 2);
        Path path2 = path;
        canvas.drawPath(path2, paint);

        /*-----------------使用lineTo、arcTo、quadTo、cubicTo画线--------------*/
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, deltaY * 2);
        Path path3 = new Path();
        //用pointList记录不同的path的各处的连接点
        List<Point> pointList = new ArrayList<Point>();
        //1. 第一部分，绘制线段
        path3.moveTo(0, 0);
        path3.lineTo(deltaX / 2, 0);//绘制线段
        pointList.add(new Point(0, 0));
        pointList.add(new Point(deltaX / 2, 0));
        //2. 第二部分，绘制椭圆右上角的四分之一的弧线
        RectF arcRecF1 = new RectF(0, 0, deltaX, deltaY);
        path3.arcTo(arcRecF1, 270, 90);//绘制圆弧
        pointList.add(new Point(deltaX, deltaY / 2));
        //3. 第三部分，绘制椭圆左下角的四分之一的弧线
        //注意，我们此处调用了path的moveTo方法，将画笔的移动到我们下一处要绘制arc的起点上
        path3.moveTo(deltaX * 1.5f, deltaY);
        RectF arcRecF2 = new RectF(deltaX, 0, deltaX * 2, deltaY);

        path3.arcTo(arcRecF2, 90, 90);//绘制圆弧
        pointList.add(new Point((int)(deltaX * 1.5), deltaY));
        //4. 第四部分，绘制二阶贝塞尔曲线
        //二阶贝塞尔曲线的起点就是当前画笔的位置，然后需要添加一个控制点，以及一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 1.5f, deltaY);
        //绘制二阶贝塞尔曲线
        path3.quadTo(deltaX * 2, 0, deltaX * 2.5f, deltaY / 2);
        pointList.add(new Point((int)(deltaX * 2.5), deltaY / 2));
        //5. 第五部分，绘制三阶贝塞尔曲线，三阶贝塞尔曲线的起点也是当前画笔的位置
        //其需要两个控制点，即比二阶贝赛尔曲线多一个控制点，最后也需要一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 2.5f, deltaY / 2);
        //绘制三阶贝塞尔曲线
        path3.cubicTo(deltaX * 3, 0, deltaX * 3.5f, 0, deltaX * 4, deltaY);
        pointList.add(new Point(deltaX * 4, deltaY));

        //Path准备就绪后，真正将Path绘制到Canvas上
        canvas.drawPath(path3, paint);

        //最后绘制Path的连接点，方便我们大家对比观察
        paint.setStrokeWidth(10);//将点的strokeWidth要设置的比画path时要大
        paint.setStrokeCap(Paint.Cap.ROUND);//将点设置为圆点状
        paint.setColor(0xff0000ff);//设置圆点为蓝色
        for(Point p : pointList){
            //遍历pointList，绘制连接点
            canvas.drawPoint(p.x, p.y, paint);
        }
    }
}

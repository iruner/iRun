package com.example.irun;

import android.content.Context;  
import android.content.res.TypedArray;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.graphics.RectF;  
import android.util.AttributeSet;  
import android.view.View; 

public class RoundBarView extends View {

	private int firstColor;  
    private int secondColor;  
    private int circleWidth;  
    private int speed;  
      
    private int progress;  
    private Paint paint;  
      
    //默认的布局文件调用的是两个参数的构造方法    
    public RoundBarView(Context context, AttributeSet attrs) {    
    	super(context, attrs);
    	
    	TypedArray typedArray = context.getTheme().    
                obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, 0, 0);    
                
        firstColor = typedArray.getColor(R.styleable.CustomProgressBar_firstColor, Color.GREEN);    
        secondColor = typedArray.getColor(R.styleable.CustomProgressBar_secondColor, Color.BLACK);    
        circleWidth = (int) typedArray.getDimension(R.styleable.CustomProgressBar_circleWidth, 80);    
        speed = typedArray.getInt(R.styleable.CustomProgressBar_speed, 0);  
           
        paint = new Paint();    
         
        typedArray.recycle();    
    }    
 
    @Override  
    protected void onDraw(Canvas canvas) {  
        int centre = getWidth() / 2;  
        int radius = centre - circleWidth;  
          
        paint.setStrokeWidth(circleWidth); // 设置圆环的宽度    
        paint.setAntiAlias(true); // 消除锯齿    
        paint.setStyle(Paint.Style.STROKE); // 设置空心   
          
        RectF oval = new RectF(centre - radius, centre - radius,   
            centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限  
          
        paint.setColor(firstColor); // 设置圆环的颜色    
        canvas.drawCircle(centre, centre, radius, paint); // 画出圆环    
        paint.setColor(secondColor); // 设置圆环的颜色    
        canvas.drawArc(oval, -90, progress, false, paint); // 根据进度画圆弧   
    }  
      
    public void start(final int targetProgress)  
    {  
        new Thread()    
        {    
            public void run()    
            {  
                progress = 0;  
                //targetProgress在0-100  
                //对应弧度在0-360  
                while (progress < targetProgress * 3.6)    
                {    
                    progress++;     
                    postInvalidate();    
                    try    
                    {    
                        Thread.sleep(speed);    
                    } catch (InterruptedException e)    
                    {    
                        e.printStackTrace();    
                    }    
                }    
            };    
        }.start();  
    }  
}

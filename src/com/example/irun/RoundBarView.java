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
      
    //Ĭ�ϵĲ����ļ����õ������������Ĺ��췽��    
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
          
        paint.setStrokeWidth(circleWidth); // ����Բ���Ŀ��    
        paint.setAntiAlias(true); // �������    
        paint.setStyle(Paint.Style.STROKE); // ���ÿ���   
          
        RectF oval = new RectF(centre - radius, centre - radius,   
            centre + radius, centre + radius); // ���ڶ����Բ������״�ʹ�С�Ľ���  
          
        paint.setColor(firstColor); // ����Բ������ɫ    
        canvas.drawCircle(centre, centre, radius, paint); // ����Բ��    
        paint.setColor(secondColor); // ����Բ������ɫ    
        canvas.drawArc(oval, -90, progress, false, paint); // ���ݽ��Ȼ�Բ��   
    }  
      
    public void start(final int targetProgress)  
    {  
        new Thread()    
        {    
            public void run()    
            {  
                progress = 0;  
                //targetProgress��0-100  
                //��Ӧ������0-360  
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

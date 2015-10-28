package com.example.irun;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;  
import android.graphics.Paint;  
import android.graphics.Rect;
import android.util.AttributeSet;  
import android.view.View;  
import android.view.animation.Animation;
import android.view.animation.Transformation;
  
public class HistogramAxleView extends View {  
  
    private Paint paint; 
    
    //轴的属性
    private String[] xStrings;//x轴上的信息  
    private String[] yStrings;//y轴上的信息  
    private int width;
    private int height;
    
    //柱状条的属性
    private int total = 1000;//整条的数值  
//    private int startValue = 0;//y轴起始值
    
    private int[] targetHeight = new int[3];//目标的高度   
    private int[] target = new int[3];//目标的数值  
      
    private int[] nowHeight = new int[3];//当前的高度  
    private int[] now = new int[3];//当前的数值
    
    private Bitmap bitmap;//柱状图使用的图片   
    
    //供外部调用 
    public void show(int[] targetValue) {
    	
//    	int max = 0;
		for (int i = 0; i < targetValue.length; i++) {
			target[i] = targetValue[i];
			
//			if(i >= 1) {
//				max = Math.max(target[i], target[i-1]);
//			}
		}
		
//		startValue = max / 100 * 100;
//		yStrings = new String[]{startValue + 100 + "", startValue + 80 + "", 
//				startValue + 60 + "", startValue + 40 + "", 
//				startValue + 20 + "", startValue + 0 + ""};
		
		HistogramAnimation ha = new HistogramAnimation();
		ha.setDuration(3000);
		startAnimation(ha);
	}
    
    public HistogramAxleView(Context context, AttributeSet attrs) {  
        super(context, attrs);

        paint = new Paint();
        xStrings = new String[]{"步数/步", "距离/KM", "卡路里/KCAL"};
        yStrings = new String[]{"1000", "800", "600", "400", "200", "0"};
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.column);  
    }  

    //左上角为原点，右为x轴方向，下为y轴方向  
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
   
        width = (int) (getWidth() * 0.8f);  
        height = (int) (getHeight() * 0.8f);
        
        //1.绘制坐标线  
        paint.setColor(Color.DKGRAY);  
        int startX = (int) (getWidth() * 0.1f);  
        int startY = (int) (getHeight() * 0.1f);  
        canvas.drawLine(startX, startY, startX, startY + height, paint);  
        canvas.drawLine(startX, startY + height, startX + width, startY + height, paint);  
          
        //2.绘制x轴上的信息  
        int[] recordX = new int[3];//记录x坐标，为绘制柱状条做准备
        
        paint.setColor(Color.BLACK);  
        int xOffset = width / xStrings.length;  
        for (int i = 0; i < xStrings.length; i++) {
        	recordX[i] = (int) (startX * 1.2f + xOffset * i);
            canvas.drawText(xStrings[i], recordX[i], startY + height * 1.05f, paint);  
        }  
          
        //3.绘制y轴上的信息和坐标内部的水平线  
        int yOffset = height / yStrings.length;  
        for (int i = 0; i < yStrings.length; i++) {  
            int x = (int) (startX * 0.1f);  
            int y = (int) (startY + yOffset * i * 1.2f);  
              
            paint.setColor(Color.BLACK);  
            canvas.drawText(yStrings[i], x, y, paint);  
              
            paint.setColor(Color.LTGRAY);  
            canvas.drawLine(x * 12f, y * 1.0f, x + width, y * 1.0f, paint);  
        }
        
        //4.绘制柱状条
        paint.setAntiAlias(true);    
        paint.setStyle(Paint.Style.FILL);    
        paint.setTextSize(18);    
        paint.setColor(Color.parseColor("#6DCAEC"));
           
        for (int i = 0; i < target.length; i++) {
        	Rect rect = new Rect(recordX[i], startY + height - nowHeight[i], recordX[i] +(int)(width * 0.1f), startY + height);  
            canvas.drawBitmap(bitmap, null, rect, paint);  
            canvas.drawText(now[i] + "", recordX[i], startY + height - nowHeight[i] - 10, paint);
            
		}     
    }
    
    private class HistogramAnimation extends Animation {    
        @Override    
        protected void applyTransformation(float interpolatedTime, Transformation t) {    
            super.applyTransformation(interpolatedTime, t);    
              
            for (int i = 0; i < target.length; i++) {
            	
//            	if (target[i] > startValue) {
//            		targetHeight[i] = (int) ((target[i] - startValue) * height / total);
//				}
            	
            	targetHeight[i] = (int) (target[i] * height / total);
            	
            	//interpolatedTime取值0-1，代表动画进度  
                now[i] = (int)(target[i] * interpolatedTime);  
                nowHeight[i] = (int)(targetHeight[i] * interpolatedTime); 
			}        
            //可以通过此函数调用onDraw  
            postInvalidate();  
        }    
    }
}

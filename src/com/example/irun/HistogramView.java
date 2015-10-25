package com.example.irun;

import android.content.Context;  
import android.content.res.TypedArray;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.graphics.Rect;  
import android.graphics.RectF;  
import android.util.AttributeSet;  
import android.view.View;  
import android.view.animation.Animation;  
import android.view.animation.Transformation;  
import android.widget.ImageView;  
import android.widget.Toast;  
  
public class HistogramView extends View {  
  
    private int viewWidth;//控件宽度  
      
    private int viewHeight;//控件高度   
    private int total = 5000;//整条的数值  
      
    private int targetHeight;//目标的高度   
    private int target;//目标的数值  
      
    private int nowHeight;//当前的高度  
    private int now = 0;//当前的数值  
  
    private Paint paint;  
    private Rect rect;//图片的区域  
    private Bitmap bitmap;//柱状图使用的图片   
    private HistogramAnimation ha;//从0开始上升的动画  
      
    //供外部调用  
    public void show(int target) {  
        this.target = target;     
        startAnimation(ha);  
    }  
      
    public HistogramView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
          
        paint = new Paint();  
        paint.setAntiAlias(true);    
        paint.setStyle(Paint.Style.FILL);    
        paint.setTextSize(18);    
        paint.setColor(Color.parseColor("#6DCAEC"));  
          
        rect = new Rect();  
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.column);  
        ha = new HistogramAnimation();  
        ha.setDuration(3000);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);  
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);  
        targetHeight = target * viewHeight / total;  
        setMeasuredDimension(viewWidth, viewHeight);  
    }  
      
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
  
        rect = new Rect(0, viewHeight - nowHeight, viewWidth, viewHeight);  
        canvas.drawBitmap(bitmap, null, rect, paint);  
        canvas.drawText(now + "", 0, viewHeight- nowHeight - 10, paint);  
    }  
      
    private class HistogramAnimation extends Animation {    
        @Override    
        protected void applyTransformation(float interpolatedTime, Transformation t) {    
            super.applyTransformation(interpolatedTime, t);    
              
            //interpolatedTime取值0-1，代表动画进度  
            now = (int)(target * interpolatedTime);  
            nowHeight = (int)(targetHeight * interpolatedTime);  
            //可以通过此函数调用onDraw  
            postInvalidate();  
        }    
    }  
}

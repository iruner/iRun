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
  
    private int viewWidth;//�ؼ����  
      
    private int viewHeight;//�ؼ��߶�   
    private int total = 5000;//��������ֵ  
      
    private int targetHeight;//Ŀ��ĸ߶�   
    private int target;//Ŀ�����ֵ  
      
    private int nowHeight;//��ǰ�ĸ߶�  
    private int now = 0;//��ǰ����ֵ  
  
    private Paint paint;  
    private Rect rect;//ͼƬ������  
    private Bitmap bitmap;//��״ͼʹ�õ�ͼƬ   
    private HistogramAnimation ha;//��0��ʼ�����Ķ���  
      
    //���ⲿ����  
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
              
            //interpolatedTimeȡֵ0-1������������  
            now = (int)(target * interpolatedTime);  
            nowHeight = (int)(targetHeight * interpolatedTime);  
            //����ͨ���˺�������onDraw  
            postInvalidate();  
        }    
    }  
}

package com.example.irun;

import java.lang.ref.WeakReference;  


import android.annotation.SuppressLint;  
import android.content.Context;  
import android.content.res.TypedArray;  
import android.graphics.Bitmap;  
import android.graphics.Bitmap.Config;  
import android.graphics.BitmapShader;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Matrix;  
import android.graphics.Paint;  
import android.graphics.Path;  
import android.graphics.PorterDuff.Mode;  
import android.graphics.PorterDuffXfermode;  
import android.graphics.RectF;  
import android.graphics.Xfermode;  
import android.graphics.drawable.Drawable;  
import android.util.AttributeSet;  
import android.util.Log;  
import android.util.TypedValue;  
import android.widget.ImageView;  
  
public class RoundImageViewByXfermode extends ImageView {  
      
    private float borderRadius;//圆角的大小  
    private int type;//图片的类型，圆形or圆角  
    public static final int TYPE_CIRCLE = 0;//圆形  
    public static final int TYPE_ROUND = 1;//圆角  
      
    private Paint paint;  
    private Xfermode xfermode;  
    private WeakReference<Bitmap> weakReference;//缓存最终的Bitmap  
  
    public RoundImageViewByXfermode(Context context, AttributeSet attrs)  
    {  
        super(context, attrs);  
  
        TypedArray typedArray = context.obtainStyledAttributes(attrs,  
                R.styleable.RoundImageViewByXfermode);  
  
        borderRadius = typedArray.getDimension(R.styleable.  
                RoundImageViewByXfermode_borderRadius, 0);  
  
        type = typedArray.getInt(R.styleable.  
                RoundImageViewByXfermode_type, TYPE_CIRCLE);  
  
        paint = new Paint();  
        paint.setAntiAlias(true);//消除锯齿  
        xfermode = new PorterDuffXfermode(Mode.DST_IN);  
          
        typedArray.recycle();  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
  
        //如果类型是圆形，则强制改变view的宽高一致，以小值为准  
        if (type == TYPE_CIRCLE)  
        {  
            int min = Math.min(getMeasuredWidth(), getMeasuredHeight());  
            setMeasuredDimension(min, min);  
        }  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas)  
    {  
        //在缓存中取出bitmap    
        Bitmap bitmap = weakReference == null ? null : weakReference.get();  
        if(bitmap == null || bitmap.isRecycled())  
        {  
            Drawable drawable = getDrawable();  
            int dWidth = drawable.getIntrinsicWidth();  
            int dHeight = drawable.getIntrinsicHeight();  
              
            float scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f / dHeight);    
            drawable.setBounds(0, 0, (int)(scale * dWidth), (int)(scale * dHeight));  
              
              
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);  
            //canvas转为bitmap  
            //然后在drawCanvas上的操作也都会在bitmap上进行记录  
            Canvas drawCanvas = new Canvas(bitmap);  
            //将drawable绘制在drawCanvas上  
            drawable.draw(drawCanvas);  
              
            Bitmap maskBitmap = getMaskBitmap();  
            paint.setXfermode(xfermode);  
            drawCanvas.drawBitmap(maskBitmap, 0,0, paint);  
              
            canvas.drawBitmap(bitmap, 0, 0, null);  
              
            weakReference = new WeakReference<Bitmap>(bitmap);  
        }  
        else   
        {  
            paint.setXfermode(null);    
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);    
            return;  
        }  
    }  
  
    public Bitmap getMaskBitmap()  
    {  
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),  
                Bitmap.Config.ARGB_8888);  
        Canvas canvas = new Canvas(bitmap);  
        Paint paint = new Paint();  
  
        if (type == TYPE_ROUND)  
        {  
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()),  
                    borderRadius, borderRadius, paint);  
        } else  
        {  
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2,  
                    paint);  
        }  
  
        return bitmap;  
    }  
      
    //主要是因为我们缓存了，当调用invalidate时，将缓存清除  
    @Override  
    public void invalidate() {  
        weakReference = null;  
        super.invalidate();  
    }  
}

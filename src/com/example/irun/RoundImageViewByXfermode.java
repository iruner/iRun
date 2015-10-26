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
      
    private float borderRadius;//Բ�ǵĴ�С  
    private int type;//ͼƬ�����ͣ�Բ��orԲ��  
    public static final int TYPE_CIRCLE = 0;//Բ��  
    public static final int TYPE_ROUND = 1;//Բ��  
      
    private Paint paint;  
    private Xfermode xfermode;  
    private WeakReference<Bitmap> weakReference;//�������յ�Bitmap  
  
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
        paint.setAntiAlias(true);//�������  
        xfermode = new PorterDuffXfermode(Mode.DST_IN);  
          
        typedArray.recycle();  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
  
        //���������Բ�Σ���ǿ�Ƹı�view�Ŀ��һ�£���СֵΪ׼  
        if (type == TYPE_CIRCLE)  
        {  
            int min = Math.min(getMeasuredWidth(), getMeasuredHeight());  
            setMeasuredDimension(min, min);  
        }  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas)  
    {  
        //�ڻ�����ȡ��bitmap    
        Bitmap bitmap = weakReference == null ? null : weakReference.get();  
        if(bitmap == null || bitmap.isRecycled())  
        {  
            Drawable drawable = getDrawable();  
            int dWidth = drawable.getIntrinsicWidth();  
            int dHeight = drawable.getIntrinsicHeight();  
              
            float scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f / dHeight);    
            drawable.setBounds(0, 0, (int)(scale * dWidth), (int)(scale * dHeight));  
              
              
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);  
            //canvasתΪbitmap  
            //Ȼ����drawCanvas�ϵĲ���Ҳ������bitmap�Ͻ��м�¼  
            Canvas drawCanvas = new Canvas(bitmap);  
            //��drawable������drawCanvas��  
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
      
    //��Ҫ����Ϊ���ǻ����ˣ�������invalidateʱ�����������  
    @Override  
    public void invalidate() {  
        weakReference = null;  
        super.invalidate();  
    }  
}

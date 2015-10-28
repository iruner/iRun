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
    
    //�������
    private String[] xStrings;//x���ϵ���Ϣ  
    private String[] yStrings;//y���ϵ���Ϣ  
    private int width;
    private int height;
    
    //��״��������
    private int total = 1000;//��������ֵ  
//    private int startValue = 0;//y����ʼֵ
    
    private int[] targetHeight = new int[3];//Ŀ��ĸ߶�   
    private int[] target = new int[3];//Ŀ�����ֵ  
      
    private int[] nowHeight = new int[3];//��ǰ�ĸ߶�  
    private int[] now = new int[3];//��ǰ����ֵ
    
    private Bitmap bitmap;//��״ͼʹ�õ�ͼƬ   
    
    //���ⲿ���� 
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
        xStrings = new String[]{"����/��", "����/KM", "��·��/KCAL"};
        yStrings = new String[]{"1000", "800", "600", "400", "200", "0"};
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.column);  
    }  

    //���Ͻ�Ϊԭ�㣬��Ϊx�᷽����Ϊy�᷽��  
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
   
        width = (int) (getWidth() * 0.8f);  
        height = (int) (getHeight() * 0.8f);
        
        //1.����������  
        paint.setColor(Color.DKGRAY);  
        int startX = (int) (getWidth() * 0.1f);  
        int startY = (int) (getHeight() * 0.1f);  
        canvas.drawLine(startX, startY, startX, startY + height, paint);  
        canvas.drawLine(startX, startY + height, startX + width, startY + height, paint);  
          
        //2.����x���ϵ���Ϣ  
        int[] recordX = new int[3];//��¼x���꣬Ϊ������״����׼��
        
        paint.setColor(Color.BLACK);  
        int xOffset = width / xStrings.length;  
        for (int i = 0; i < xStrings.length; i++) {
        	recordX[i] = (int) (startX * 1.2f + xOffset * i);
            canvas.drawText(xStrings[i], recordX[i], startY + height * 1.05f, paint);  
        }  
          
        //3.����y���ϵ���Ϣ�������ڲ���ˮƽ��  
        int yOffset = height / yStrings.length;  
        for (int i = 0; i < yStrings.length; i++) {  
            int x = (int) (startX * 0.1f);  
            int y = (int) (startY + yOffset * i * 1.2f);  
              
            paint.setColor(Color.BLACK);  
            canvas.drawText(yStrings[i], x, y, paint);  
              
            paint.setColor(Color.LTGRAY);  
            canvas.drawLine(x * 12f, y * 1.0f, x + width, y * 1.0f, paint);  
        }
        
        //4.������״��
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
            	
            	//interpolatedTimeȡֵ0-1������������  
                now[i] = (int)(target[i] * interpolatedTime);  
                nowHeight[i] = (int)(targetHeight[i] * interpolatedTime); 
			}        
            //����ͨ���˺�������onDraw  
            postInvalidate();  
        }    
    }
}

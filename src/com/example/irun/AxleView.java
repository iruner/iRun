package com.example.irun;

import android.content.Context;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.util.AttributeSet;  
import android.view.View;  
  
public class AxleView extends View {  
  
    private Paint paint;  
    private String[] xStrings;//x���ϵ���Ϣ  
    private String[] yStrings;//y���ϵ���Ϣ  
      
    public AxleView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
          
        paint = new Paint();  
        xStrings = new String[]{"��һ", "�ܶ�", "����",  
                "����", "����", "����", "����",};  
        yStrings = new String[]{"5000", "4000", "3000", "2000",  
                "1000","0",};  
    }  
      
    //���Ͻ�Ϊԭ�㣬��Ϊx�᷽����Ϊy�᷽��  
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
          
        //��Ϊ���ÿ��Ϊmatch_parent������Ϊ�˲�Խ�磬��Ҫ��ȥһ��ֵ  
        int width = getWidth() - 100;  
        int height = getHeight() - 100;  
          
        //1.����������  
        paint.setColor(Color.DKGRAY);  
        int startX = 50;  
        int startY = 30;  
        canvas.drawLine(startX, startY, startX, startY + height, paint);  
        canvas.drawLine(startX, startY + height, startX + width, startY + height, paint);  
          
        //2.����x���ϵ���Ϣ  
        paint.setColor(Color.BLACK);  
        int xOffset = width / xStrings.length;  
        for (int i = 0; i < xStrings.length; i++) {  
            canvas.drawText(xStrings[i], startX + xOffset * i + 10, startY + height + 20, paint);  
        }  
          
        //3.����y���ϵ���Ϣ�������ڲ���ˮƽ��  
        int yOffset = height / yStrings.length;  
        for (int i = 0; i < yStrings.length; i++) {  
            int x = startX - 50;  
            int y = startY + yOffset * i + 100;  
              
            paint.setColor(Color.BLACK);  
            canvas.drawText(yStrings[i], x, y, paint);  
              
            paint.setColor(Color.LTGRAY);  
            canvas.drawLine(x + 50, y, x + 50 + width, y, paint);  
        }  
    }  
}

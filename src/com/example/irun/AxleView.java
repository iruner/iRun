package com.example.irun;

import android.content.Context;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.util.AttributeSet;  
import android.view.View;  
  
public class AxleView extends View {  
  
    private Paint paint;  
    private String[] xStrings;//x轴上的信息  
    private String[] yStrings;//y轴上的信息  
      
    public AxleView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
          
        paint = new Paint();  
        xStrings = new String[]{"周一", "周二", "周三",  
                "周四", "周五", "周六", "周日",};  
        yStrings = new String[]{"5000", "4000", "3000", "2000",  
                "1000","0",};  
    }  
      
    //左上角为原点，右为x轴方向，下为y轴方向  
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
          
        //因为设置宽高为match_parent，所以为了不越界，需要减去一个值  
        int width = getWidth() - 100;  
        int height = getHeight() - 100;  
          
        //1.绘制坐标线  
        paint.setColor(Color.DKGRAY);  
        int startX = 50;  
        int startY = 30;  
        canvas.drawLine(startX, startY, startX, startY + height, paint);  
        canvas.drawLine(startX, startY + height, startX + width, startY + height, paint);  
          
        //2.绘制x轴上的信息  
        paint.setColor(Color.BLACK);  
        int xOffset = width / xStrings.length;  
        for (int i = 0; i < xStrings.length; i++) {  
            canvas.drawText(xStrings[i], startX + xOffset * i + 10, startY + height + 20, paint);  
        }  
          
        //3.绘制y轴上的信息和坐标内部的水平线  
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

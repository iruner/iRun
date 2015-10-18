package com.example.irun;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.Toast;

public class RegularExpressionUtil {

	private static Context context;
	public static void init(Context c)
	{
		if(context == null)
		{
			context = c;
		}
	}
	
	public static SpannableString change(String content)
	{
		//   \\��ʾ\     \[��ʾ[       [\u4e00-\u9fa5]��ʾ����
		//���������ʽ���ڼ����[]Ϊ�߽磬[]������һ�����������ĵ��ַ���
		Pattern pattern = Pattern.compile("\\[[\u4e00-\u9fa5]{1,}\\]");
		SpannableString ss = new SpannableString(content);
		Matcher matcher = pattern.matcher(ss);
		while (matcher.find()) 
		{
			int position = check(matcher.group());
			if(position != -1)
			{
				Drawable d = context.getResources().getDrawable(Expression.drawable[position]);    
		        d.setBounds(0, 0, d.getIntrinsicWidth()/2, d.getIntrinsicHeight()/2);
		        ImageSpan span = new ImageSpan(d);
		        //��aaa[�Ǻ�]bbbΪ��
		        //start-3��end-7;setSpan��������㣬�����յ�
		        ss.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			}
		}
		return ss;
	}
	
	//����Ƿ��б���
	private static int check(String s)
	{
		for (int i = 0; i < Expression.describe.length; i++) 
		{
			if(s.equalsIgnoreCase(Expression.describe[i]))
			{
				return i;
			}
		}
		return -1;
	}
}

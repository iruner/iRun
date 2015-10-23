package com.example.irun;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

//聊天界面
public class ChatFragment extends Fragment implements 
      OnClickListener, OnItemClickListener {

	private EditText editText;//输入要发送的信息
	private Button sendButton;//发送按钮
	private Button backButton;//返回按钮
	private Button faceButton;//表情按钮
	private TextView titleText;//标题
	
	private ListView listView;//聊天信息的滑动列表
	private ChatMsgListViewAdapter adapter;//聊天信息的适配器
	private List<ChatMsgEntity> list;//聊天信息的数据

	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private List<GridView> pageList;
	
	private int pageItemCount = 12;//每页12个表情
	private String toID;//表示跟谁聊天的窗口
	private MessageReceiver messageReceiver;//接受监听的对象
	
	public ChatFragment(String toID)
	{
		this.toID = toID;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat, container, false);
		
		editText = (EditText)view.findViewById(R.id.editText);
		sendButton = (Button)view.findViewById(R.id.btn_send);
		backButton = (Button)view.findViewById(R.id.btn_back);
		faceButton = (Button)view.findViewById(R.id.btn_face);
		titleText = (TextView)view.findViewById(R.id.title);
		
		listView = (ListView)view.findViewById(R.id.listview);
		viewPager = (ViewPager)view.findViewById(R.id.viewPage);
		
		sendButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
		faceButton.setOnClickListener(this);
		titleText.setText("跟" + toID + "的聊天");
		
		list = new ArrayList<ChatMsgEntity>();
		adapter = new ChatMsgListViewAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		pageList = new ArrayList<GridView>();
		
		int pageCount = 0;
		//Expression.drawable.length为26，即有3页
		for (int i = 0; i < Expression.drawable.length;)
		{
			GridView gridView = (GridView) layoutInflater.inflate(R.layout.grid_view, null);
			gridView.setTag(pageCount);
			//填充数据
			List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < pageItemCount; j++) 
			{
				if(i < Expression.drawable.length)
				{
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("face", Expression.drawable[i]);
			        gridList.add(item);
			        i++;
				}			
			}
			//第三个参数是单个grid的布局文件
			//第四个参数是Map对象的哪些key对应的value来生成列表项
			//第五个参数表示要填充的组件， Map对象key对应的资源与填充组件的顺序有对应关系 
			SimpleAdapter gridAdapter = new SimpleAdapter(getActivity(), gridList, R.layout.grid_face, 
					new String[]{"face"}, new int[]{R.id.expression});
			gridView.setAdapter(gridAdapter);
			gridView.setOnItemClickListener(this);
			pageList.add(gridView);
			pageCount++;
		}
		
		pagerAdapter = new PagerAdapter() {
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(pageList.get(position));
			}
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = pageList.get(position);
				container.addView(view);
				return view;
			}
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return pageList.size();
			}
		};
		
		viewPager.setAdapter(pagerAdapter);
		RegularExpressionUtil.init(getActivity());
		initMessageReceiver();
		
		return view;
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_send) {
			send();
		}
		else if(v.getId() == R.id.btn_back) {
			MainActivity.bottomBar.setVisibility(View.VISIBLE);
			FragmentManager fm = getFragmentManager();  
	        FragmentTransaction tx = fm.beginTransaction();  
	        tx.hide(this);
	        tx.show(fm.findFragmentByTag("ChooseChatFragment"));
	        tx.commit();
		}
		else if(v.getId() == R.id.btn_face) {
			if(viewPager.getVisibility() == View.VISIBLE) {
				viewPager.setVisibility(View.GONE);
			} else {
				viewPager.setVisibility(View.VISIBLE);
			}
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int pos = (Integer)(parent.getTag()) * pageItemCount + position;
		String describe = Expression.describe[pos];
		
		SpannableString ss = new SpannableString(describe);
		Drawable d = getResources().getDrawable(Expression.drawable[pos]);    
        d.setBounds(0, 0, d.getIntrinsicWidth()/2, d.getIntrinsicHeight()/2);
        ImageSpan span = new ImageSpan(d);
        ss.setSpan(span, 0, describe.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        editText.append(ss);
	}
	
	private void send()
	{
		String content = editText.getText().toString();
		if(content.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName(UserInfo.getId());
			entity.setDate(getDate());
			entity.setMessage(RegularExpressionUtil.change(content));
			entity.setMsgType(true);
			
			list.add(entity);
			adapter.notifyDataSetChanged();//通知ListView，数据已发生改变
			listView.setSelection(listView.getCount() - 1);//发送一条消息时，ListView显示选择最后一项
			
			editText.setText("");
			
			try {
				JSONObject root = new JSONObject();
				root.put("content", content);
				root.put("fromID", UserInfo.getId());
				root.put("toID", toID);
				SocketService.send(root.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}	
	}
	
	private String getDate() 
	{
		long time = System.currentTimeMillis();;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = new Date(time);  
        return format.format(d);  
    } 
	
	private void initMessageReceiver()
	{
		messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SocketService");
        getActivity().registerReceiver(messageReceiver,filter);
	}
		
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
						
			String content = intent.getStringExtra("jsonString");
			try 
			{
				JSONObject root = new JSONObject(content);
				
				//错误：root.getString("toID").equalsIgnoreCase(UserInfo.getID())
				//因为对于每一个窗口来说，UserInfo.getID()都一样
				//toID表示发信息给谁
				boolean a = (root.getString("toID").equalsIgnoreCase("Group")) && (toID.equalsIgnoreCase("Group"));
				boolean b = (!root.getString("toID").equalsIgnoreCase("Group")) && (toID.equalsIgnoreCase(root.getString("fromID")));
				if(a || b)
				{
					if(content != null) 
					{
						ChatMsgEntity entity = new ChatMsgEntity();
						entity.setName(root.getString("fromID"));
						entity.setDate(getDate());
						SpannableString ss = RegularExpressionUtil.change(root.getString("content"));
						entity.setMessage(ss);
						entity.setMsgType(false);
						
						list.add(entity);
						adapter.notifyDataSetChanged();//通知ListView，数据已发生改变
						listView.setSelection(listView.getCount() - 1);//发送一条消息时，ListView显示选择最后一项
					}
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}		
		}		
    }
}

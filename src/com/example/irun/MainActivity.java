package com.example.irun;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.unity3d.player.UnityPlayerActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;  
import android.app.FragmentManager;  
import android.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

//锟斤拷Activity锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟叫伙拷
public class MainActivity extends Activity {

	private FragmentManager fm;
	public static View rootView2;
	public static View bottomBar;
	private RadioGroup radioGroup;
	
	private MainFragment fragment1;
	private ChooseChatFragment fragment2;
	private MyMapFragment fragment3;
	private MeFragment fragment4;
	
	private int currentIndex = 0;//当前是第几个页面，从0开始算
	private Datebase database = new Datebase();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			database.createtable();
		}catch (Exception e){e.getMessage();}


		fm = getFragmentManager();
		rootView2 = findViewById(R.id.rootView2);
		bottomBar = findViewById(R.id.bottomBar);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.rb1){
					setFragmentShow(0);
				}
				else if(checkedId == R.id.rb2){
					setFragmentShow(1);
				}
				else if(checkedId == R.id.rb3){
					setFragmentShow(2);
				}
				else if(checkedId == R.id.rb4){
					setFragmentShow(3);
				}
			}
		});	

//		bottomBar.setVisibility(View.GONE);
		setFragmentShow(0);
		startService(new Intent(MainActivity.this, SocketService.class));
		
		BroadcastReceiver receiver = new UnityBroadcast();
	    IntentFilter filter = new IntentFilter();
	    filter.addAction("back");
	    registerReceiver(receiver, filter);
	    
	    PushManager.startWork(getApplicationContext(),

                PushConstants.LOGIN_TYPE_API_KEY,"uy0hVGTGyIMACCmWwz4YAKrB");
	}
	
	@Override
	protected void onDestroy() {
		stopService(new Intent(MainActivity.this, SocketService.class));
		stopService(new Intent(MainActivity.this, MusicService.class));
		stopService(new Intent(MainActivity.this, AccelerometerSensorService.class));
		super.onDestroy();
	}

	//瀹氫箟鎸夎繑鍥為敭鏃跺仛鐨勪簨鎯咃紝杩欓噷闇�浠庡皢鍫嗘爤澶村厓绱爌ush鍑�
	@Override
	public void onBackPressed() {
		backToMainFragment();
	}
	
	public void backToMainFragment()
	{
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		MainActivity.rootView2.setVisibility(View.VISIBLE);
		MainFragment.drawerLayout.closeDrawers();
		fm.popBackStack();
	}
	
	private void setFragmentShow(int index)
	{
		FragmentTransaction ft = fm.beginTransaction();
		
		if (index > currentIndex) 
		{
			ft.setCustomAnimations(R.animator.slide_left_in, R.animator.slide_left_out);
		} 
		else if (index < currentIndex)
		{
			ft.setCustomAnimations(R.animator.slide_right_in,R.animator.slide_right_out);
		}
		
		if(fragment1 != null)
		{
			ft.hide(fragment1);
		}
		if(fragment2 != null)
		{
			ft.hide(fragment2);
		}
		if(fragment3 != null)
		{
			ft.hide(fragment3);
		}
		if(fragment4 != null)
		{
			ft.hide(fragment4);
		}
		
		switch (index) 
		{
		case 0:
			radioGroup.getChildAt(0).setSelected(true);
			if(fragment1 == null)
			{
				fragment1 = new MainFragment();
				ft.add(R.id.content, fragment1,"MainFragment");
			}
			else 
			{
				ft.show(fragment1);
			}
			break;

		case 1:
			radioGroup.getChildAt(1).setSelected(true);
			if(fragment2 == null)
			{
				fragment2 = new ChooseChatFragment();
				ft.add(R.id.content, fragment2,"ChooseChatFragment");
			}
			else 
			{
				ft.show(fragment2);
			}
			break;
			
		case 2:
			radioGroup.getChildAt(2).setSelected(true);
			if(fragment3 == null)
			{
				fragment3 = new MyMapFragment();
				ft.add(R.id.content, fragment3,"MyMapFragment");
			}
			else 
			{
				ft.show(fragment3);
			}
			break;
	
		case 3:
			radioGroup.getChildAt(3).setSelected(true);
			if(fragment4 == null)
			{
				fragment4 = new MeFragment();
				ft.add(R.id.content, fragment4,"MeFragment");
			}
			else 
			{
				ft.show(fragment4);
			}
			break;
			
		default:
			break;
		}
		
		currentIndex = index;
		ft.commit();
	}
	
	private class UnityBroadcast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			backToMainFragment();
		}
	}
}

package com.example.irun;

import java.util.Random;

import com.unity3d.player.UnityPlayer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//��ʾģ�͵Ľ��棬ͬʱ���мƲ����Ĺ���
public class MainFragment extends Fragment implements OnClickListener {

	private FragmentManager fragmentManager;
	public static DrawerLayout drawerLayout;
	private View topBarLayout;
	private View buttonLayout;
	private View textViewLayout;
	
	//-----�Ʋ�����
	private TextView tvTime;
	private TextView tvStep;
	private TextView tvDistance;
	private TextView tvCalories;
	private TextView tvVelocity;
		
	private long startTime;//��ʼʱ�䣬�ڰ��¿�ʼ��ťʱ��ʼ��������ͣ���ٴο�ʼ��Ҳ���ʼ��
	private long recordingTime;//��������ͣ��ťʱ����¼�ѹ�ȥ��ʱ��
	private float step;//����
	private float distance;//����
	private float calories;//����
	private float velocity;//�ٶ�
	
	private int stepLength;//����
	private int weight;//����
	//-----�Ʋ�����
	
	private Button buttonOpenDrawer;
	private ImageButton buttonStart;
	private ImageButton buttonPause;
	private ImageButton buttonClear;
	Datebase database = new Datebase();
	public static boolean canRandom = true;//ģ���ܷ�������������Ͷ���
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
	
		fragmentManager = getFragmentManager();
		drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);  
		topBarLayout = view.findViewById(R.id.topBarLayout);
		buttonLayout = view.findViewById(R.id.buttonLayout);
		textViewLayout = view.findViewById(R.id.textViewLayout);
		
		tvTime = (TextView) view.findViewById(R.id.time);
		tvStep = (TextView) view.findViewById(R.id.step);
		tvDistance = (TextView) view.findViewById(R.id.distance);
		tvCalories = (TextView) view.findViewById(R.id.calories);
		tvVelocity = (TextView) view.findViewById(R.id.velocity);
		
		if (StepSettingFragment.sharedPreferences == null) {
			StepSettingFragment.sharedPreferences = getActivity().getSharedPreferences(
					StepSettingFragment.SETP_SHARED_PREFERENCES,
					Context.MODE_PRIVATE);
		}
		stepLength = StepSettingFragment.sharedPreferences.getInt(
				StepSettingFragment.STEP_LENGTH_VALUE, 70);
		weight = StepSettingFragment.sharedPreferences.getInt(
				StepSettingFragment.WEIGHT_VALUE, 50);
		
//		UnityPlayer unityPlayer = new UnityPlayer(getActivity());
//		View playerView = unityPlayer.getView();
//		RelativeLayout ll = (RelativeLayout)view.findViewById(R.id.unityViewLyaout);
//		ll.addView(playerView);
		
		buttonOpenDrawer = (Button)view.findViewById(R.id.buttonOpenDrawer);
		buttonStart = (ImageButton)view.findViewById(R.id.buttonStart);
		buttonPause = (ImageButton)view.findViewById(R.id.buttonPause);
		buttonClear = (ImageButton)view.findViewById(R.id.buttonClear);
		
		buttonOpenDrawer.setOnClickListener(this);
		buttonStart.setOnClickListener(this);
		buttonPause.setOnClickListener(this);
		buttonClear.setOnClickListener(this);
		
		buttonStart.setEnabled(true);
		buttonPause.setEnabled(false);
		
		drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
			@Override
			public void onDrawerOpened(View drawerView) {
				MainFragment.canRandom = false;
//				UnityPlayer.UnitySendMessage("Main Camera","Move","-0.25");
			}
			
			@Override
			public void onDrawerClosed(View drawerView) {
				MainFragment.canRandom = true;
//				UnityPlayer.UnitySendMessage("Main Camera","Move","0.25");
			}
		});
			
		handler = new Handler();
		
//		hide();
//		new Handler().postDelayed(new Runnable() {			
//			@Override
//			public void run() {
//				show();
//				Intent intent = new Intent(getActivity(),MusicService.class);
//		        Bundle bundle = new Bundle();
//		        bundle.putInt("id",R.raw.univ0019);
//		        intent.putExtras(bundle);
//		        getActivity().startService(intent);
//			}
//		}, 3500);
//		
//		new Handler().postDelayed(new Runnable() {			
//			@Override
//			public void run() {
//				random();
//				handler.postDelayed(this,10000);
//			}
//		}, 12000);
			
		return view;
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.buttonStart)
		{
			Intent intent = new Intent(getActivity(), AccelerometerSensorService.class);
			getActivity().startService(intent);
			startTime = System.currentTimeMillis();
			updateInfo();
			
			canRandom = false;
			buttonStart.setEnabled(false);
			buttonPause.setEnabled(true);
			buttonStart.setVisibility(ImageView.GONE);
//			UnityPlayer.UnitySendMessage("Main Camera","Move","0.25");
//			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","0_RUN00_F");
		}
		//ȷ�����¿�ʼ������ٰ���ͣ
		else if(v.getId() == R.id.buttonPause)
		{
			Intent intent = new Intent(getActivity(), AccelerometerSensorService.class);
			getActivity().stopService(intent);
			recordingTime += System.currentTimeMillis() - startTime;
			handler.removeCallbacks(calculateRunnable);
			
			canRandom = true;
			buttonStart.setEnabled(true);
			buttonPause.setEnabled(false);
			buttonStart.setVisibility(ImageView.VISIBLE);
//			UnityPlayer.UnitySendMessage("Main Camera","Move","-0.25");
//			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","0_WAIT00");
		}
		else if(v.getId() == R.id.buttonClear)
		{
			try{

				buttonStart.setVisibility(ImageView.VISIBLE);

				database.insert(step, distance, calories);

			}catch (Exception e){e.getMessage();}
			StepDetector.CURRENT_SETP = 0;


			startTime = 0;//��ʼʱ�䣬�ڰ��¿�ʼ��ťʱ��ʼ��������ͣ���ٴο�ʼ��Ҳ���ʼ��
			recordingTime = 0;//��������ͣ��ťʱ����¼�ѹ�ȥ��ʱ��
			step = 0;//����
			distance = 0;//����
			calories = 0;//����
			velocity = 0;//�ٶ�
			
			tvTime.setText(getFormatTime(0));
			tvStep.setText(step + "");
			tvDistance.setText(String.format("%.2f",distance));
			tvCalories.setText(String.format("%.2f",calories));
			tvVelocity.setText(String.format("%.2f",velocity));
		}
		else if(v.getId() == R.id.buttonOpenDrawer)
		{
			drawerLayout.openDrawer(Gravity.RIGHT);
		}
	}
	
	Handler handler;
	Runnable calculateRunnable;
	private void updateInfo()
	{	
		calculateRunnable = new Runnable() {
			
			@Override
			public void run() {
				
				step = StepDetector.CURRENT_SETP;
				
				if (StepDetector.CURRENT_SETP % 2 == 0) {
					distance = (StepDetector.CURRENT_SETP / 2) * 3 * stepLength * 0.01f;
				} else {
					distance = ((StepDetector.CURRENT_SETP / 2) * 3 + 1) * stepLength * 0.01f;
				}
				
				calories = weight * distance * 0.001f;
				velocity = distance * 1000 / (recordingTime + System.currentTimeMillis() - startTime);
				
				tvTime.setText(getFormatTime(recordingTime + System.currentTimeMillis() - startTime));
				tvStep.setText(step + "");
				tvDistance.setText(String.format("%.2f",distance));
				tvCalories.setText(String.format("%.2f",calories));
				tvVelocity.setText(String.format("%.2f",velocity));
				
				handler.postDelayed(this, 300);
			}
		};
		handler.post(calculateRunnable);	
	}
	
	private String getFormatTime(long time) {
		time = time / 1000;
		long second = time % 60;
		long minute = (time % 3600) / 60;
		long hour = time / 3600;

		// ��������ʾ��λ
		// String strMillisecond = "" + (millisecond / 10);
		// ����ʾ��λ
		String strSecond = ("00" + second)
				.substring(("00" + second).length() - 2);
		// ����ʾ��λ
		String strMinute = ("00" + minute)
				.substring(("00" + minute).length() - 2);
		// ʱ��ʾ��λ
		String strHour = ("00" + hour)
				.substring(("00" + hour).length() - 2);

		return strHour + ":" + strMinute + ":" + strSecond;
		// + strMillisecond;
	}
	
	public void hide()
	{
		MainActivity.bottomBar.setVisibility(View.GONE);
		topBarLayout.setVisibility(View.INVISIBLE);
		buttonLayout.setVisibility(View.INVISIBLE);
		textViewLayout.setVisibility(View.INVISIBLE);
	}
	
	public void show()
	{
		MainActivity.bottomBar.setVisibility(View.VISIBLE);
		topBarLayout.setVisibility(View.VISIBLE);
		buttonLayout.setVisibility(View.VISIBLE);
		textViewLayout.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		if(!hidden) {
			canRandom = true;
		}
		else {
			canRandom = false;
		}
		super.onHiddenChanged(hidden);
	}
	
	int[] musicID = new int[] {
			R.raw.univ0020,R.raw.univ0021,R.raw.univ0022,R.raw.univ0023,
			R.raw.univ0024,R.raw.univ0025,R.raw.univ0026,R.raw.univ0027,
			R.raw.univ0028,R.raw.univ0029,R.raw.univ0030,
			};
	
	Runnable randomRunnable;
	private void random()
	{
		if(canRandom) 
		{
			UnityPlayer.UnitySendMessage("unitychan","PlayRandomAnim","null");
			Random random = new Random();
			int num = random.nextInt(11);
			Intent intent = new Intent(getActivity(), MusicService.class);
	        Bundle bundle = new Bundle();
	        bundle.putInt("id",musicID[num]);
	        intent.putExtras(bundle);
	        getActivity().startService(intent);
		}
	}
}

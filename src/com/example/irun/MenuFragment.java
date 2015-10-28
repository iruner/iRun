package com.example.irun;

import java.util.ArrayList;

import com.unity3d.player.UnityPlayer;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

//侧滑菜单
public class MenuFragment extends Fragment implements OnClickListener,OnItemClickListener {

    private ListView drawerList;  
    private ArrayList<String> list;  
    private ArrayAdapter<String> adapter; 
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, container, false);
		
		drawerList = (ListView) view.findViewById(R.id.drawerList);
		list = new ArrayList<String>();	
		list.add("账户");
		list.add("目标");
		list.add("天气");
		list.add("反馈");
		list.add("统计");
		list.add("设置");
		list.add("退出");
		adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_string, list);
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(this);
		
		Button loginButton = (Button) view.findViewById(R.id.drawerLogin);
		loginButton.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.drawerLogin)
		{
			MainActivity.bottomBar.setVisibility(View.GONE);
			MainFragment.drawerLayout.closeDrawers();
			
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.hide(fm.findFragmentByTag("MainFragment"));
			
			if(fm.findFragmentByTag("LoginFragment") == null)
			{
				ft.add(R.id.content,new LoginFragment(), "LoginFragment");
			}
			else 
			{
				ft.show(fm.findFragmentByTag("LoginFragment"));				
			}		
			ft.commit();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {		
//		if(position == 0) {
//			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE01");
//		}
//		else if(position == 1) {
//			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE02");
//		}
//		else if(position == 2) {
//			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE03");
//		}
//		else if(position == 3) {
//			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE04");
//		}
//		else if(position == 4) {
//			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE05");
//		}

		if(position == 0) {
			MainActivity.bottomBar.setVisibility(View.GONE);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.content, new PsnInfoFragment());
			ft.hide(fm.findFragmentByTag("MainFragment"));
			ft.addToBackStack(null);
			ft.commit();
		}
		if(position == 1) {
			MainActivity.bottomBar.setVisibility(View.GONE);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.content, new TaskFragment());
			ft.hide(fm.findFragmentByTag("MainFragment"));
			ft.addToBackStack(null);
			ft.commit();
		}
		if(position == 2) {
			MainActivity.bottomBar.setVisibility(View.GONE);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.content, new WeatherFragment(), "WeatherFragment");
			ft.hide(fm.findFragmentByTag("MainFragment"));
			ft.addToBackStack(null);
			ft.commit();
		}
		else if(position == 4) {
			MainActivity.bottomBar.setVisibility(View.GONE);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.content, new StatisticsFragment());
			ft.hide(fm.findFragmentByTag("MainFragment"));
			ft.addToBackStack(null);
			ft.commit();
		}
		else if(position == 5) {
			MainActivity.bottomBar.setVisibility(View.GONE);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.content, new StepSettingFragment());
			ft.addToBackStack(null);
			ft.commit();

		if(position == 4) {
			toOtherFragment(new TaskFragment());
		}
		else if(position == 5) {
			toOtherFragment(new StatisticsFragment());
		}
		else if(position == 6) {
			toOtherFragment(new StepSettingFragment());

		}
		else if(position == 6) {

		}
	}}
	
	private void toOtherFragment(Fragment f) 
	{
		MainActivity.bottomBar.setVisibility(View.GONE);
		
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.content, f);
		ft.hide(fm.findFragmentByTag("MainFragment"));
		ft.addToBackStack(null);
		ft.commit();
	}
}

package com.example.irun;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//µÇÂ¼½çÃæ
public class LoginFragment extends Fragment implements OnClickListener {

	private EditText editUser;
	private EditText editPassword;
	private Button buttonLogin;
	private Button buttonRegister;
	private MessageReceiver messageReceiver;
	Datebase database = new Datebase();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		
		editUser = (EditText) view.findViewById(R.id.user);
		editPassword = (EditText) view.findViewById(R.id.password);
		buttonLogin = (Button) view.findViewById(R.id.btnLogin);
		buttonRegister = (Button) view.findViewById(R.id.btnRegister);

		buttonLogin.setOnClickListener(this);
		buttonRegister.setOnClickListener(this);
        initMessageReceiver();
        
		return view;
	}
	
	private void initMessageReceiver()
	{
		messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SocketService");
        getActivity().registerReceiver(messageReceiver, filter);
	}
	
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(messageReceiver);
		super.onDestroy();
	}
	
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String content = intent.getStringExtra("jsonString");
			try {
				JSONObject root = new JSONObject(content);
				boolean isLoginSuccess = root.getBoolean("result");
				if(isLoginSuccess)
				{
					Toast.makeText(getActivity(), "login success", Toast.LENGTH_SHORT).show();
					UserInfo.setId(editUser.getText().toString());
					toMainFragment();
				}
				else 
				{
					Toast.makeText(getActivity(), "login fail", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}		
		}		
    }
	
	@Override
	public void onClick(View v) {
		String user = editUser.getText().toString();
		String password = editPassword.getText().toString();
		
		if(v.getId() == R.id.btnLogin)
		{
			if((user.length() > 0) && (password.length() > 0))
			{
				String a = null;
				try 
				{
					a = database.select(user);
				}catch (Exception e)
				{				
				};

				if(password.equals(a))
				{
					Toast.makeText(getActivity(), "login success", Toast.LENGTH_SHORT).show();
					toMainFragment();
//					try 
//					{
//						JSONObject root = new JSONObject();
//						root.put("user", editUser.getText());
//						root.put("password", editPassword.getText());
//						SocketService.send(root.toString());
//					} catch (JSONException e) 
//					{
//						e.printStackTrace();
//					}		
				}
				else
				{
					Toast.makeText(getActivity(), "login fail", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(getActivity(), "login fail", Toast.LENGTH_SHORT).show();
			}
		}
		else if(v.getId() == R.id.btnRegister)
		{
			if((user.length() > 0) && (password.length() > 0))
			{
				Toast.makeText(getActivity(), "register success", Toast.LENGTH_SHORT).show();
				try
				{
					database.insert1(user,password);
				}catch (Exception e)
				{
					e.getMessage();
				}
			}
			else 
			{
				Toast.makeText(getActivity(), "register fail", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void toMainFragment()
	{		
		MainActivity.rootView2.setVisibility(View.VISIBLE);
		MainFragment.drawerLayout.closeDrawers();
		
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		//ft.show(fm.findFragmentByTag("MainFragment"));
		ft.remove(this);
		ft.commit();
	}
}

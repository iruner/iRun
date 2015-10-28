package com.example.irun;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.unity3d.player.UnityPlayerActivity;

public class ExtendUnityPlayerActivity extends UnityPlayerActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	}
	
	@Override
	public boolean onKeyDown(int arg0, KeyEvent arg1) {
		if(arg0 == arg1.KEYCODE_BACK)
		{
			sendBroadcast(new Intent("back"));
		}
		return super.onKeyDown(arg0, arg1);
	}
}

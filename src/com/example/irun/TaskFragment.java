package com.example.irun;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskFragment extends Fragment {
	
	private TextView tvNow;
	private TextView tvTarget;
	private RoundBarView roundBarView;
	
	private float now = 2400;
	private float target = 3500;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task, container, false);
		
		tvNow = (TextView) view.findViewById(R.id.tvNow);
		tvTarget = (TextView) view.findViewById(R.id.tvTarget);
		roundBarView = (RoundBarView) view.findViewById(R.id.rbv1);
		
		tvNow.setText(now + "");
		tvTarget.setText(target + "");
		roundBarView.start((int)(now / target * 100));
		return view;
	}

}

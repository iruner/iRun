package com.example.irun;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TaskFragment extends Fragment {
	
	private RoundBarView roundBarView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task, container, false);
		
		roundBarView = (RoundBarView) view.findViewById(R.id.rbv1);
		roundBarView.start(90);
		return view;
	}

}

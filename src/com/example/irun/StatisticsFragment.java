package com.example.irun;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class StatisticsFragment extends Fragment {
	
	private HistogramView[] hvs = new HistogramView[7];  
    private int[] values = new int[]{  
            1500,2000,2500,4500,4000,3500,1000  
    };  
    private int[] ids = new int[]{  
            R.id.hv1, R.id.hv2, R.id.hv3, R.id.hv4,  
            R.id.hv5, R.id.hv6, R.id.hv7,  
    }; 
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_statistics, container, false);
		
		for (int i = 0; i < hvs.length; i++) {  
            hvs[i] = (HistogramView) view.findViewById(ids[i]);  
            hvs[i].show(values[i]);  
        } 
		
		return view;
	}

}

package com.example.irun;

import java.util.ArrayList;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class StatisticsFragment extends Fragment {


	private HistogramView[] hvs = new HistogramView[3];
    private int[] values = new int[3];

    private int[] ids = new int[]{  
            R.id.hv1, R.id.hv2, R.id.hv3
          //  , R.id.hv4, R.id.hv5, R.id.hv6, R.id.hv7,
    }; 
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        values[0]=values[1]=values[2]=1000;
//�������ݿ����
   float step,distance,calories;
        step=distance=calories=0F;
        try{
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.irun/db.db", null);

            Cursor cursor = db.query ("record",null,null,null,null,null,null);
            if(cursor.moveToFirst()) {
//�����α�
                for (int i = 0; i < cursor.getCount() + 1; i++) {

//���ID
                    //     int id = cursor.getInt(0);

                    step =step+ cursor.getFloat(1);

                    distance = distance+ cursor.getFloat(2);
                    calories =calories+ cursor.getFloat(3);
                    cursor.move(1);
                }}}catch (Exception e){e.getMessage();}



        values[0] = (int)step;
        values[1] = (int)distance;
        values[2] = (int)calories;




		for (int i = 0; i < hvs.length; i++) {  
            hvs[i] = (HistogramView) view.findViewById(ids[i]);  
            hvs[i].show(values[i]);  
        } 
		
		return view;
	}

}

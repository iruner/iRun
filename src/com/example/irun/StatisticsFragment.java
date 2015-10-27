package com.example.irun;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticsFragment extends Fragment {

    private int[] values = new int[3];
    private HistogramAxleView ha;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_statistics, container, false);

		//锟斤拷锟斤拷锟斤拷锟捷匡拷锟斤拷锟�
		float step,distance,calories;
        step=distance=calories=0F;
        try{
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.irun/db.db", null);

            Cursor cursor = db.query ("record",null,null,null,null,null,null);
            if(cursor.moveToFirst()) {
//锟斤拷锟斤拷锟轿憋拷
                for (int i = 0; i < cursor.getCount() + 1; i++) {

//锟斤拷锟絀D
                    //     int id = cursor.getInt(0);

                    step =step+ cursor.getFloat(1);

                    distance = distance+ cursor.getFloat(2);
                    calories =calories+ cursor.getFloat(3);
                    cursor.move(1);
                }}}catch (Exception e){e.getMessage();}

        values[0] = (int)step;
        values[1] = (int)distance;
        values[2] = (int)calories;

        ha = (HistogramAxleView) view.findViewById(R.id.ha);
        ha.show(values);
        
		return view;
	}

}

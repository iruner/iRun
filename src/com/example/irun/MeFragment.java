package com.example.irun;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.view.View.OnClickListener;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/10/20.
 */
public class MeFragment extends Fragment implements OnClickListener {

    private TableRow tr1;
    private TableRow tr2;

    private TextView tv1;
    private TextView tv2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);


        tr1 = (TableRow) view.findViewById(R.id.more_page_row1);
        tr2 = (TableRow) view.findViewById(R.id.more_page_row2);

        tr1.setOnClickListener(this);
        tr1.setOnClickListener(this);

        tv1 = (TextView) view.findViewById(R.id.id_personal);
        tv2 = (TextView) view.findViewById(R.id.id_stepsetting);


        return view;
    }

    public void onClick(View v){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        Fragment weatherFragment=new WeatherFragment();
        switch(v.getId()){
            case R.id.more_page_row1:
                ft.hide(this);
                ft.show(weatherFragment);
                //ft.addToBackStack(w);
                ft.commit();
                break;
            case R.id.more_page_row2:
                tv1.setText("clicked");
                break;
        }
    }
}

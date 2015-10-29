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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        tr1 = (TableRow) view.findViewById(R.id.row_weather);
        tr2 = (TableRow) view.findViewById(R.id.row_psn_info);

        tr1.setOnClickListener(this);
        tr2.setOnClickListener(this);

        return view;
    }

    public void onClick(View v){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        switch(v.getId()){
            case R.id.row_psn_info:{
                MainActivity.rootView2.setVisibility(View.INVISIBLE);
                ft.hide(this);
                ft.add(R.id.rootView, new PsnInfoFragment(), "PsnInfoFragment");
                ft.addToBackStack(null);
                ft.commit();
            }
                break;
            case R.id.row_weather:{
            	MainActivity.rootView2.setVisibility(View.INVISIBLE);
                ft.hide(this);
                //锟斤拷R.id.*锟斤拷锟节碉拷layout锟斤拷锟斤拷Fragment锟斤拷new **()锟斤拷指锟斤拷牟锟斤拷锟�**
                ft.add(R.id.rootView, new WeatherFragment(), "WeatherFragment");
                ft.addToBackStack(null);
                ft.commit();
            }
                break;

            default:
                break;
        }
    }
}

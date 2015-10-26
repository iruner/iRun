package com.example.irun;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Administrator on 2015/10/20.
 */
public class PsnInfoSettingFragment extends Fragment implements OnClickListener {

    private ImageButton im;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psn_settings, container, false);
        im=(ImageButton)view.findViewById(R.id.summit_settings);
        im.setOnClickListener(this);
        return view;
    }

    public void onClick(View v){
        FragmentManager fm=getFragmentManager();
        fm.popBackStack();
//        FragmentTransaction ft=fm.beginTransaction();
//        ft.hide(this);
//        ft.commit();
    }
}

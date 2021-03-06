package com.example.irun;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableRow;

/**
 * Created by Administrator on 2015/10/20.
 */
public class PsnInfoFragment extends Fragment implements OnClickListener {

    private ImageButton im;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psn_info, container, false);
        im=(ImageButton)view.findViewById(R.id.info_set);
        im.setOnClickListener(this);
        return view;
    }


    public void onClick(View v){

		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.rootView, new PsnInfoSettingFragment());
		ft.addToBackStack(null);
		ft.commit();
    }
}

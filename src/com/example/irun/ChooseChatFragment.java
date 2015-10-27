package com.example.irun;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

//ѡ������ģʽ�Ľ��棬��ΪȺ�ĺ�һ��һ����
public class ChooseChatFragment extends Fragment implements OnClickListener,OnChildClickListener {
	
	private TextView textTitle;
	private EditText editText;
	private Button buttonAdd;
	private Button buttonGroup;
	private Button buttonDel;
	
	private ExpandableListView elv;  
    private ExpandableAdapter ea;  
    private List<String> group;//����������
    private List<List<String>> child;//��id
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_choose_chat, container, false);
		textTitle = (TextView)view.findViewById(R.id.titleText);
		editText = (EditText)view.findViewById(R.id.editText);
		buttonAdd = (Button)view.findViewById(R.id.buttonAdd);
		buttonGroup = (Button)view.findViewById(R.id.buttonGroup);
		buttonDel = (Button)view.findViewById(R.id.buttonDel);

		elv = (ExpandableListView) view.findViewById(R.id.elv);
		
//		textTitle.setText(UserInfo.getId() + "��ͨѶ¼");
		textTitle.setText("Chatting Room");
		buttonAdd.setOnClickListener(this);
		buttonGroup.setOnClickListener(this);
		buttonDel.setOnClickListener(this);
		elv.setOnChildClickListener(this);
		
		
		group = new ArrayList<String>();  
        child = new ArrayList<List<String>>();  
          
        group.add("Ĭ�Ϸ���");  
        group.add("Сѧͬѧ");  
        group.add("����ͬѧ");  
        group.add("����ͬѧ");  
        group.add("��ѧͬѧ");  
          
        for (int i = 0; i < group.size(); i++)   
        {  
            List<String> tempList = new ArrayList<String>();  
            for (int j = 0; j < 3; j++) {  
                tempList.add(group.get(i) + j);  
            }  
            child.add(tempList);  
        }
        
		ea = new ExpandableAdapter(getActivity(), group, child);  
        elv.setAdapter(ea);
		elv.setGroupIndicator(null);//�����ô���ָʾ��ͼ��
		
		return view;
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.buttonAdd)
		{
			if(editText.getText().toString().length() > 0)
			{
				child.get(0).add(editText.getText().toString());
				ea.notifyDataSetChanged();
				editText.setText("");
			}		
		}
		else if(v.getId() == R.id.buttonGroup)
		{		
			toChatFragment("Group");
		}
		else if(v.getId() == R.id.buttonDel)
		{		
			for (int i = 0; i < child.get(0).size(); i++) 
			{
		         if(child.get(0).get(i).equalsIgnoreCase(editText.getText().toString()))
		         {
		        	child.get(0).remove(i);
			        ea.notifyDataSetChanged();
		            editText.setText(""); 	
		         }
			}
		}
	}

	private void toChatFragment(String tag)
	{
		MainActivity.bottomBar.setVisibility(View.GONE);
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction(); 
		ft.hide(this);
		//��ֻ֤��ʼ��һ��
		//����ChatFragment��Ϊһ��Fragment����һ��tag������Fragment֮����л�
		//ͬʱChatFragment��Ϊһ�����촰�ڣ�Ҳ��һ��tag����ʾ��˭�����죬��tagΪ11����ʾ��11������
		//����ChatFragment�ڽ�����Ϣʱֻ���ܸ��Լ�tag��ȵģ��Ϳ��Խ���Ӧ����Ϣ��ʾ�ڶ�Ӧ�Ĵ���
		if(fm.findFragmentByTag(tag) == null)
		{
			ft.add(R.id.content, new ChatFragment(tag), tag);
		}
		else 
		{  
			ft.show(fm.findFragmentByTag(tag));
		}        
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		toChatFragment(child.get(groupPosition).get(childPosition));
		return true;
	}
}
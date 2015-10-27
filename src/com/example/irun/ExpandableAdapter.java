package com.example.irun;

import java.util.List;  

import android.content.Context;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseExpandableListAdapter;  
import android.widget.TextView;  
  
public class ExpandableAdapter extends BaseExpandableListAdapter {  
  
    private LayoutInflater layoutInflater;  
    private List<String> group;  
    private List<List<String>> child;  
      
    public ExpandableAdapter(Context context, List<String> group,   
            List<List<String>> child) {  
        layoutInflater = LayoutInflater.from(context);  
        this.group = group;  
        this.child = child;  
    }  
      
    @Override  
    public int getGroupCount() {  
        return group.size();  
    }  
  
    @Override  
    public int getChildrenCount(int groupPosition) {  
        return child.get(groupPosition).size();  
    }  
  
    @Override  
    public Object getGroup(int groupPosition) {  
        return group.get(groupPosition);  
    }  
  
    @Override  
    public Object getChild(int groupPosition, int childPosition) {  
        return child.get(groupPosition).get(childPosition);  
    }  
  
    @Override  
    public long getGroupId(int groupPosition) {  
        return groupPosition;  
    }  
  
    @Override  
    public long getChildId(int groupPosition, int childPosition) {  
        return childPosition;  
    }  
  
    //表明大組和小组id是否稳定的更改底层数据  
    @Override  
    public boolean hasStableIds() {  
        return true;  
    }  
  
    @Override  
    public View getGroupView(int groupPosition, boolean isExpanded,  
            View convertView, ViewGroup parent) {  
        View view = convertView;  
//      if(view == null) {  
            view = layoutInflater.inflate(R.layout.list_group, null);  
            TextView textView = (TextView) view.findViewById(R.id.groupText);  
            textView.setText(group.get(groupPosition));  
//      }  
        return view;  
    }  
  
    @Override  
    public View getChildView(int groupPosition, int childPosition,  
            boolean isLastChild, View convertView, ViewGroup parent) {  
        View view = convertView;  
//      if(view == null) {  
            view = layoutInflater.inflate(R.layout.list_child, null);  
            TextView textView = (TextView) view.findViewById(R.id.childText);  
            textView.setText(child.get(groupPosition).get(childPosition));  
//      }  
        return view;  
    }  
  
    //得到小组成员是否被选择  
    @Override  
    public boolean isChildSelectable(int groupPosition, int childPosition) {  
        return true;  
    }  
  
}

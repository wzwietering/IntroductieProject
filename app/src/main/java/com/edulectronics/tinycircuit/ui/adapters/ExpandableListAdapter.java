package com.edulectronics.tinycircuit.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Models.ExpandedMenu;
import com.edulectronics.tinycircuit.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Wilmer on 1-12-2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ExpandedMenu> headers;
    private HashMap<ExpandedMenu, List<String>> children;
    ExpandableListView expandableListView;

    public ExpandableListAdapter(Context context, List<ExpandedMenu> listHeader,
                                 HashMap<ExpandedMenu, List<String>> listChildren,
                                 ExpandableListView view) {
        this.context = context;
        this.headers = listHeader;
        this.children = listChildren;
        this.expandableListView = view;
    }

    @Override
    public int getGroupCount() {
        return this.headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try{
            return this.children.get(this.headers.get(groupPosition)).size();
        }catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.children.get(this.headers.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandedMenu headerTitle = (ExpandedMenu) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.header, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.submenu);
        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
        textView.setText(headerTitle.getIconName());
        headerIcon.setImageResource(headerTitle.getIconImage());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listsubmenu, null);
        }
        TextView txtListChild = (TextView) convertView.findViewById(R.id.submenu);
        ImageView childImage = (ImageView) convertView.findViewById(R.id.submenuimage);
        txtListChild.setText(childText);
        childImage.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
package com.edulectronics.tinycircuit.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Models.MenuItem;
import com.edulectronics.tinycircuit.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Wilmer on 1-12-2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<MenuItem> headers;
    private HashMap<MenuItem, List<MenuItem>> children;
    ExpandableListView expandableListView;

    public ExpandableListAdapter(Context context, List<MenuItem> listHeader,
                                 HashMap<MenuItem, List<MenuItem>> listChildren,
                                 ExpandableListView view) {
        this.context = context;
        this.headers = listHeader;
        this.children = listChildren;
        this.expandableListView = view;
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return children.get(headers.get(groupPosition)).size();
        } catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition);
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
        MenuItem headerTitle = (MenuItem) getGroup(groupPosition);
        return createMenuItem(convertView, headerTitle, R.layout.header, R.id.iconimage);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MenuItem childText = (MenuItem) getChild(groupPosition, childPosition);
        return createMenuItem(convertView, childText, R.layout.listsubmenu, R.id.submenuimage);
    }

    private View createMenuItem(View convertView, MenuItem text, int layout, int icon) {
        if (convertView == null) {
            convertView = inflateView(layout);
        }
        ImageView image = (ImageView) convertView.findViewById(icon);
        return getView(convertView, text, image);
    }

    private View inflateView(int layout) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(layout, null);
    }

    public View getView(View convertView, MenuItem menuItem, ImageView imageView){
        TextView textView = (TextView) convertView.findViewById(R.id.submenu);
        textView.setText(menuItem.getIconName());
        imageView.setImageResource(menuItem.getIconImage());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
package com.edulectronics.tinycircuit.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Models.MenuItem;
import com.edulectronics.tinycircuit.R;

import java.util.List;

/**
 * Created by Jesper on 1/17/2017.
 */

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<MenuItem> componentlist;
    ListView listView;

    public ListAdapter(Context context, List<MenuItem> componentlist, ListView list){
        this.context = context;
        this.componentlist = componentlist;
        this.listView = list;
    }

    public int getCount(){
        return  componentlist.size();
    }

    public long getItemId(int position){
        return position;
    }

    public MenuItem getItem(int position){
        return componentlist.get(position);
    }

    private View createMenuItem(View convertView, MenuItem menuItem, int layout, int icon) {
        if (convertView == null) {
            convertView = inflateView(layout);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.submenu);
        textView.setText(menuItem.getIconName());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iconimage);
        imageView.setImageResource(menuItem.getIconImage());

        return convertView;
    }

    private View inflateView(int layout) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(layout, null);
    }

    public View getView(int id, View convertView, ViewGroup viewGroup){

        return createMenuItem(convertView, getItem(id), R.layout.header, R.id.iconimage);
    }
}

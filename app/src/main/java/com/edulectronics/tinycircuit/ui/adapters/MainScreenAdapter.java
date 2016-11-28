package com.edulectronics.tinycircuit.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edulectronics.tinycircuit.Components.IComponent;
import com.edulectronics.tinycircuit.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.content.Context;
import android.widget.ImageView;

/**
 * Created by bernd on 28/11/2016.
 */

public class MainScreenAdapter extends BaseAdapter {
    ArrayList<IComponent> components;
    Context context;

    MainScreenAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return components.size();
    }

    @Override
    public Object getItem(int position) {
        return components.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.component, parent, false);
        ImageView image = (ImageView) row.findViewById(R.id.imageView);
        image.setImageResource(getImageId(position));
        return row;
    }

    private int getImageId(int position) {
        return R.mipmap.ic_launcher;
    }
}

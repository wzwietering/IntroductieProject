package com.edulectronics.tinycircuit.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.IComponent;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
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

    public MainScreenAdapter(Context context, CircuitController controller) {
        this.components = controller.getComponents();
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
        // TODO: replace with components image
        return R.mipmap.ic_launcher;
    }
}

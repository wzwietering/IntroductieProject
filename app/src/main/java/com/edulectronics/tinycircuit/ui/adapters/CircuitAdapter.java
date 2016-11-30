package com.edulectronics.tinycircuit.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.IComponent;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Factories.ImageFactory;
import com.edulectronics.tinycircuit.R;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import android.content.Context;
import android.widget.ImageView;

/**
 * Created by bernd on 28/11/2016.
 */

public class CircuitAdapter extends BaseAdapter {
    Context context;
    CircuitController controller;

    public CircuitAdapter(Context context, CircuitController controller) {
        this.controller = controller;
        this.context = context;
    }

    @Override
    public int getCount() {
        return controller.circuit.width * controller.circuit.height;
    }

    /*Range = [0, width * height - 1]*/
    @Override
    public Object getItem(int position) {
         return controller.getComponents()
                [position % controller.circuit.height]
                [position / controller.circuit.height];
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
        ImageFactory imageFactory = new ImageFactory();
        imageFactory.GetImage(getItem(position));
        return row;
    }

    private int getImageId(int position) {
        // TODO: replace with components image
        return R.mipmap.resistor;
    }
}

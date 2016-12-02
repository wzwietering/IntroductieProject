package com.edulectronics.tinycircuit.UI.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.UI.Draggables.GridCell;
/**
 * This class is used with a GridView object. It provides a set of ImageCell objects
 * that support dragging and dropping.
 *
 */

public class CircuitAdapter extends BaseAdapter {
    public ViewGroup mParentView = null;
    private Context context;
    private CircuitController controller;

    public CircuitAdapter(Context context, CircuitController controller) {
        this.controller = controller;
        this.context = context;
    }

    public int getCount() {
        return controller.circuit.size;
    }

    public Object getItem(int position)
    {
        return controller.circuit.components[position];
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * getView
     * Return a view object for the grid.
     *
     * @return ImageCell
     */
    public View getView (int position, View convertView, ViewGroup parent)
    {
        mParentView = parent;

        GridCell v;
        if (convertView == null) {
            // If it's not recycled, create a new ImageCell.
            v = new GridCell (context, controller);
            v.setLayoutParams(new GridView.LayoutParams(85, 85));
            v.setScaleType(ImageView.ScaleType.CENTER_CROP);
            v.setPadding(8, 8, 8, 8);

        } else {
            v = (GridCell) convertView;
        }

        v.mCellNumber = position;
        v.mGrid = (GridView) mParentView;
        v.isEmpty = true;
        v.setBackgroundResource(R.color.cell_empty);

        // Set up to relay events to the activity.
        // The activity decides which events trigger drag operations.
        // Activities like the Android Launcher require a long click to get a drag operation started.
        v.setOnTouchListener((View.OnTouchListener) context);
        v.setOnClickListener((View.OnClickListener) context);
        v.setOnLongClickListener((View.OnLongClickListener) context);

        if(controller.circuit.occupied(position))
        {
            Component component = controller.circuit.getComponent(position);
            v.setComponent(component);
        }

        return v;
    }
}
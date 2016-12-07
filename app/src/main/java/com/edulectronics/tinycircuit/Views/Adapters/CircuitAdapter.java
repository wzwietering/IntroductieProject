package com.edulectronics.tinycircuit.Views.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;
/**
 * This class is used with a GridView object. It provides a set of ImageCell objects
 * that support dragging and dropping.
 *
 */

public class CircuitAdapter extends BaseAdapter {
    public ViewGroup mParentView = null;
    private Context context;
    private CircuitController circuitController;

    public CircuitAdapter(Context context) {
        this.context = context;
        this.circuitController = CircuitController.getInstance();
    }

    public int getCount() {
        return circuitController.circuit.size;
    }

    public Object getItem(int position)
    {
        return circuitController.circuit.components[position];
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
            v = new GridCell(context);
            int cellSize = context.getResources().getInteger(R.integer.cell_size);
            v.setLayoutParams(new GridView.LayoutParams(cellSize, cellSize));
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

        if(circuitController.circuit.occupied(position))
        {
            Component component = circuitController.circuit.getComponent(position);
            v.setComponent(component);
        }

        return v;
    }
}
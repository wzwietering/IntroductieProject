package com.edulectronics.tinycircuit.Views.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;
/**
 * This class is used with a GridView object. It provides a set of ImageCell objects
 * that support dragging and dropping.
 *
 */

public class CircuitAdapter extends BaseAdapter {
    private Context context;
    private CircuitController circuitController;

    public CircuitAdapter(Context context, CircuitController circuitController) {
        this.context = context;
        this.circuitController = circuitController;
    }

    public int getCount() {
        return circuitController.getCircuitSize();
    }

    public Object getItem(int position)
    {
        return circuitController.getComponent(position);
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
        return createGridCell(position, convertView, parent);
    }

    private GridCell createGridCell(int position, View convertView, ViewGroup parent) {
        GridCell v = createGridCellByView(convertView);
        v.mCellNumber = position;
        v.mGrid = (GridView) parent;

        if (circuitController.circuit.occupied(position))
            v.setComponent(circuitController.circuit.getComponent(position));

        // Set up to relay events to the activity.
        // The activity decides which events trigger drag operations.
        // Activities like the Android Launcher require a long click to get a drag operation started.
        v.setOnTouchListener((View.OnTouchListener) context);
        v.setOnClickListener((View.OnClickListener) context);
        v.setOnLongClickListener((View.OnLongClickListener) context);

        return v;
    }

    private GridCell createGridCellByView(View convertView) {
        if (convertView == null) {
            // If it's not recycled, create a new ImageCell.
            return createNewGridCell();
        }
        return (GridCell) convertView;
    }

    private GridCell createNewGridCell() {
        GridCell cell = new GridCell(circuitController, context);
        int cellSize = context.getResources().getInteger(R.integer.cell_size);
        cell.setLayoutParams(new GridView.LayoutParams(cellSize, cellSize));
        cell.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return cell;
    }
}
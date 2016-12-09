package com.edulectronics.tinycircuit.Controllers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.DrawView;

/**
 * Created by bernd on 09/12/2016.
 */

public class DrawingController {

    private int componentWidth, componentHeight, gridWidth;
    private Canvas canvas;
    private DrawView view;

    public DrawingController(int gridWidth, DrawView view) {
        this.gridWidth = gridWidth;
        this.view = view;
    }

    public Point getNodeLocation(int position, ConnectionPointOrientation orientation) {
        return new Point(getXLocation(position, orientation), getYLocation(position, orientation));
    }

    private int getXLocation(int position, ConnectionPointOrientation orientation) {
        if (orientation == ConnectionPointOrientation.Left)
            return ((position % gridWidth) - 1) * R.integer.cell_size;
        return (position % gridWidth) * R.integer.cell_size;
    }

    private int getYLocation(int position, ConnectionPointOrientation orientation) {
        if (orientation == ConnectionPointOrientation.Top)
            return ((position / gridWidth) - 1) * R.integer.cell_size;
        return (position / gridWidth) * R.integer.cell_size;
    }
}

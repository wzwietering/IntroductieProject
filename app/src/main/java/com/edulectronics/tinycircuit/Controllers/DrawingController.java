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

    private int gridWidth, cellSize;

    public DrawingController(int gridWidth, int cellSize) {
        this.gridWidth = gridWidth;
        this.cellSize = cellSize;
    }

    public Point getNodeLocation(int position, ConnectionPointOrientation orientation) {
        return new Point(getXLocation(position, orientation), getYLocation(position, orientation));
    }

    private int getXLocation(int position, ConnectionPointOrientation orientation) {
        if (orientation == ConnectionPointOrientation.Top || orientation == ConnectionPointOrientation.Bottom)
            return (getColumn(position) - 1) * 150 + (int)(0.5 * cellSize);
        if (orientation == ConnectionPointOrientation.Left)
            return (getColumn(position) - 1) * 150;
        return getColumn(position) * 150;
    }

    private int getYLocation(int position, ConnectionPointOrientation orientation) {
        if (orientation == ConnectionPointOrientation.Left || orientation == ConnectionPointOrientation.Right)
            return (getRow(position) - 1) * 150 + (int)(0.5 * cellSize);
        if (orientation == ConnectionPointOrientation.Top)
            return (getRow(position) - 1) * 150;
        return getRow(position) * 150;
    }

    private int getRow(int position) {
        return (position / gridWidth) + 1;
    }

    private int getColumn(int position) {
        return (position % gridWidth) + 1;
    }
}

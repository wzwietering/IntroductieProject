package com.edulectronics.tinycircuit.Controllers;

import android.graphics.Point;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

/**
 * Created by bernd on 09/12/2016.
 */

public class DrawingController {

    private int componentWidth, componentHeight, gridWidth, gridHeight;

    public DrawingController(int componentWidth, int componentHeight, int gridWidth, int gridHeight) {
        this.componentWidth = componentWidth;
        this.componentHeight = componentHeight;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    public Point getNodeLocation(int position, ConnectionPointOrientation orientation) {
        return new Point(getXLocation(position, orientation), getYLocation(position, orientation));
    }

    private int getXLocation(int position, ConnectionPointOrientation orientation) {
        if (orientation == ConnectionPointOrientation.Left)
            return (position - 1) * componentWidth;
        return position * componentWidth;
    }

    private int getYLocation(int position, ConnectionPointOrientation orientation) {
        if (orientation == ConnectionPointOrientation.Top)
            return (position - 1) * componentHeight;
        return position * componentHeight;
    }
}

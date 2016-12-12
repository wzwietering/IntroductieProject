package com.edulectronics.tinycircuit.Controllers;

import android.graphics.Point;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

/**
 * Created by bernd on 09/12/2016.
 */

public class CoordinateHelper {

    private int gridWidth, cellSize;

    public CoordinateHelper(int gridWidth, int cellSize) {
        this.gridWidth = gridWidth;
        this.cellSize = cellSize;
    }

    public Point getNodeLocation(int position, ConnectionPointOrientation orientation) {
        return new Point(getXLocation(position, orientation), getYLocation(position, orientation));
    }

    private int getXLocation(int position, ConnectionPointOrientation orientation) {
        switch (orientation) {
            case Top:
            case Bottom:
                return (getColumn(position) - 1) * cellSize + (int) (0.5 * cellSize);
            case Left:
                return (getColumn(position) - 1) * cellSize;
            default:
                return getColumn(position) * cellSize;
        }
    }

    private int getYLocation(int position, ConnectionPointOrientation orientation) {
        switch (orientation) {
            case Left:
            case Right:
                return (getRow(position) - 1) * cellSize + (int) (0.5 * cellSize);
            case Top:
                return (getRow(position) - 1) * cellSize;
            default:
                return getColumn(position) * cellSize;
        }
    }

    private int getRow(int position) {
        return (position / gridWidth) + 1;
    }

    private int getColumn(int position) {
        return (position % gridWidth) + 1;
    }
}

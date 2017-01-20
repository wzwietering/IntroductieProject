package com.edulectronics.tinycircuit.Helpers;

import android.content.res.Resources;
import android.graphics.Point;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

import java.security.InvalidParameterException;

public class CoordinateHelper {

    private int gridWidth, cellWidth, cellHeight;

    public CoordinateHelper(int gridWidth, int cellWidth, int cellHeight) {
        this.gridWidth = gridWidth;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public Point getNodeLocation(int position, ConnectionPointOrientation orientation) {
        return new Point(getXLocation(position, orientation), getYLocation(position, orientation));
    }

    public int getXLocation(int position, ConnectionPointOrientation orientation) {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int gridCells = (int) Math.floor(width / cellHeight);
        switch (orientation) {
            case Left:
                return (getColumn(position) - 1) * cellWidth + 10;
            case Right:
                return getColumn(position) * cellWidth - 10;
            default:
                throw new InvalidParameterException("Invalid orientation given");
        }
    }

    public int getYLocation(int position, ConnectionPointOrientation orientation) {
        switch (orientation) {
            case Left:
            case Right:
                return (getRow(position) - 1) * cellHeight + (int) (0.5 * cellHeight);
            default:
                throw new InvalidParameterException("Invalid orientation given");
        }
    }

    private int getRow(int position) {
        return (position / gridWidth) + 1;
    }

    private int getColumn(int position) {
        return (position % gridWidth) + 1;
    }
}

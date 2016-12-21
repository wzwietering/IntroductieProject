package com.edulectronics.tinycircuit.Controllers;

import android.graphics.Point;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

import java.security.InvalidParameterException;

/**
 * Created by bernd on 09/12/2016.
 */

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
        switch (orientation) {
            case Top:
            case Bottom:
                return (getColumn(position) - 1) * cellWidth + (int) (0.5 * cellWidth);
            case Left:
                return (getColumn(position) - 1) * cellWidth;
            case Right:
                return getColumn(position) * cellWidth;
            default:
                throw new InvalidParameterException("Invalid orientation given");
        }
    }

    public int getYLocation(int position, ConnectionPointOrientation orientation) {
        switch (orientation) {
            case Left:
            case Right:
                return (getRow(position) - 1) * cellHeight + (int) (0.5 * cellHeight);
            case Top:
                return (getRow(position) - 1) * cellHeight;
            case Bottom:
                return getColumn(position) * cellHeight;
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

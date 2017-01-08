package com.edulectronics.tinycircuit.Helpers;

import android.content.res.Resources;
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
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int gridCells = (int) Math.floor(width / cellHeight);
        switch (orientation) {
            case Top:
            case Bottom:
                return (getColumn(position) - 1) * cellWidth + (int) (0.5 * cellWidth);
            case Left:
                return (getColumn(position) - 1) * cellWidth + 13;
            case Right:
                return getColumn(position) * cellWidth + magicOffsetFunction(gridCells);
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
                return getRow(position) * cellHeight;
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

    // This interesting function was found by collecting the right offset for different screens, and
    // then those offsets were combined with the grid cells and put into this calculator:
    // http://www.xuru.org/rt/PR.asp#CopyPaste to get the function. The first number is the amount
    // of gridcells in the width, the second one is the desired offset:
    // 7, -34; 11, -27; 5, -26
    private int magicOffsetFunction(int gridCells){
        return (int) Math.round(0.9583333333 * Math.pow(gridCells, 2) - 15.5 * gridCells + 27.54166667);
    }
}

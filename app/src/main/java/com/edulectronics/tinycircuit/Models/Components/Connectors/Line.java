package com.edulectronics.tinycircuit.Models.Components.Connectors;

import android.graphics.Point;

/**
 * Created by bernd on 14/12/2016.
 */

public class Line {
    private Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }
}

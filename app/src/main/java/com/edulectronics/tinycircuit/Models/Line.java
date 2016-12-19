package com.edulectronics.tinycircuit.Models;

        import android.graphics.Point;

import com.edulectronics.tinycircuit.Views.WireView;

/**
 * Created by bernd on 12/12/2016.
 */

public class Line {
    public Point a, b;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public boolean isTouched(Point point){
        float width = WireView.paint.getStrokeWidth() * 4;
        if(point.x > a.x - width && point.y > a.y - width &&
                point.x < b.x + width && point.y < b.y + width) {
            return true;
        //This else if is necessary in case the wire is connected from right to left
        } else if (point.x < a.x + width && point.y < a.y + width &&
                point.x > b.x - width && point.y > b.y - width){
            return true;
        } else {
            return false;
        }
    }
}

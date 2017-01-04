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
        //Modify the multiplier to change the detection area of the tap.
        float width = WireView.paint.getStrokeWidth() * 4;
        int minX = Math.min(a.x, b.x);
        int maxX = Math.max(a.x, b.x);
        int minY = Math.min(a.y, b.y);
        int maxY = Math.max(a.y, b.y);
        if(point.x > minX - width && point.y > minY - width &&
                point.x < maxX + width && point.y < maxY + width) {
            return true;
        } else {
            return false;
        }
    }
}

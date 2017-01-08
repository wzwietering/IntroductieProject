package com.edulectronics.tinycircuit.Models.Components.Connectors;

import android.graphics.Point;
import android.view.MotionEvent;

import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Line;

import java.util.List;

/**
 * Created by Maaike on 19-12-2016.
 */

public class Connection {
    public ConnectionPoint pointA;
    public ConnectionPoint pointB;

    private List<Line> lines;

    public Connection(ConnectionPoint a, ConnectionPoint b) {
        this.pointA = a;
        this.pointB = b;
    }

    public boolean hasOutputConnection(ConnectionPoint connectionPoint) {
        ConnectionPoint outGoingConnectionPoint = connectionPoint == pointA ? pointB : pointA;

        if(outGoingConnectionPoint.getParentComponent().getClass() == Powersource.class) {
            // Next component is a powersource, so always return true.
            return true;
        } else {
            return outGoingConnectionPoint.getParentComponent().hasOutputConnection(pointB);
        }
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }

    public boolean isTouched(MotionEvent motionEvent){
        Point point = new Point((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
        for (Line line : lines){
            if(line.isTouched(point)){
                return true;
            }
        }
        return false;
    }

    public ConnectionPoint getOtherPoint(ConnectionPoint connectionPoint){
        if (connectionPoint == pointB || connectionPoint.getParentComponent() == pointB.getParentComponent()){
            return pointA;
        } else {
            return pointB;
        }
    }
}

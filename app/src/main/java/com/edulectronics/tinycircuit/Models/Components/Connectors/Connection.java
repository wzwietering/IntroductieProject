package com.edulectronics.tinycircuit.Models.Components.Connectors;


import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;

import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Views.Wire;

import java.util.List;

/**
 * Created by Maaike on 19-12-2016.
 */

public class Connection {
    public ConnectionPoint pointA;
    public ConnectionPoint pointB;

    // The drawn lines that represent this connections wire
    private List<Wire> wires;

    public Connection(ConnectionPoint a, ConnectionPoint b) {
        this.pointA = a;
        this.pointB = b;
    }

    // Checks to see whether the outgoing connectionpoint also has connection (this is checked
    // though the whole circuit until either:
    // - there is no outgoing connection, thus the method returns false.
    // - the call stack arrives at a powersource, thus return true since we came full-circle.
    // @param connectionPoint: the incoming connectionPoint, either point a or point b in this
    // connection. We want to check whether the OTHER connectionPoint has an outgoing connection.
    public boolean hasOutputConnection(ConnectionPoint connectionPoint) {
        // Check whether the incoming connection point is A or B. We have to check if the OHTER
        // one has an outgoing connection.
        ConnectionPoint outGoingConnectionPoint = connectionPoint == pointA ? pointB : pointA;

        // ALWAYS check if parent is a powersource FIRST. Otherwise you get stackoverflow.
        if (outGoingConnectionPoint.getParentComponent().getClass() == Powersource.class) {
            // Next component is a powersource, so always return true.
            return true;
        } else {
            // We only have an outgoing connection if the outgoing connectionPoint has a
            // connection, so return that! (recurse!) =)
            return outGoingConnectionPoint.getParentComponent().hasOutputConnection(outGoingConnectionPoint);
        }
    }

    public void setWires(List<Wire> wires) {
        this.wires = wires;
    }

    public List<Wire> getWires() {
        return wires;
    }

    public boolean isTouched(MotionEvent motionEvent) {
        Point point = new Point((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
        for (Wire wire : wires) {
            if (wire.isTouched(point)) {
                return true;
            }
        }
        return false;
    }

    public ConnectionPoint getOtherPoint(ConnectionPoint connectionPoint) {
        if (connectionPoint == pointB || connectionPoint.getParentComponent() == pointB.getParentComponent()) {
            return pointA;
        } else {
            return pointB;
        }
    }
}

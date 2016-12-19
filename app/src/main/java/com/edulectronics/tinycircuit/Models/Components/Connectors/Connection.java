package com.edulectronics.tinycircuit.Models.Components.Connectors;

import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Line;

import java.util.ArrayList;
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
}

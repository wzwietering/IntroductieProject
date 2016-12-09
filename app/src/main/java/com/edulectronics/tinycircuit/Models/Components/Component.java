package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

/**
 * This is the component base class. All components have to be derived from this base.
 */
public abstract class Component implements IComponent {

    private double resistance;
    public int position;

    protected List<ConnectionPoint> connectionPoints = new ArrayList<ConnectionPoint>(4);

    public Component() {
        this.connectionPoints.add(new ConnectionPoint(this, ConnectionPointOrientation.Left));
        this.connectionPoints.add(new ConnectionPoint(this, ConnectionPointOrientation.Right));
    }

    public boolean hasOutputConnection(ConnectionPoint connectionpoint) {
        for (ConnectionPoint c : getOutgoingConnections(connectionpoint)) {
            if (c.hasOutputConnection())
                return true;
        }
        return false;
    }

    public ConnectionPoint getConnectionPointByIndex(int i) {
        if(i < connectionPoints.size()) {
            return connectionPoints.get(i);
        }
        return null;
    }

    public List<ConnectionPoint> getOutgoingConnections(ConnectionPoint connectionPoint){
        int incomingConnection = connectionPoints.indexOf(connectionPoint);
        List<ConnectionPoint> outgoingConnections = new ArrayList<ConnectionPoint>(connectionPoints);
        outgoingConnections.remove(incomingConnection);

        return outgoingConnections;
    }

    public List<ConnectionPoint> getConnectionPoints() {
        return connectionPoints;
    }

    public int getImage() {
        return R.mipmap.ic_launcher;
    }

    public void setPosition(int position){
        this.position = position;
    }
}

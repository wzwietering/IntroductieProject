package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

/**
 * This is the component base class. All components have to be derived from this base.
 */
public abstract class Component implements IComponent {

    public int position;

    // Determines whether or not each path in the circuit that traverses this component has
    // a resistor somewhere.
    protected boolean hasResistance;

    protected List<ConnectionPoint> connectionPoints = new ArrayList<ConnectionPoint>(4);

    public Component() {
        this.connectionPoints.add(new ConnectionPoint(this, ConnectionPointOrientation.Left));
        this.connectionPoints.add(new ConnectionPoint(this, ConnectionPointOrientation.Right));
    }

    public boolean isConductive() {
        return true;
    }

    public void setResistance(boolean resistance) {
        this.hasResistance = resistance;
    };

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
        if(incomingConnection != -1){
            outgoingConnections.remove(incomingConnection);
        }


        return outgoingConnections;
    }

    public List<ConnectionPoint> getConnectionPoints() {
        List<ConnectionPoint> connectionPointList = connectionPoints;
        connectionPointList.removeAll(Collections.singleton(null));
        return connectionPointList;
    }

    public void removeAllConnections(){
        Connector connector = new Connector();
        for(ConnectionPoint cp: getConnectionPoints()){
            for(Connection c: cp.getConnections())
                connector.disconnect(c.pointA, c.pointB);
        }
    }

    public void setPosition(int position){
        this.position = position;
    }

    private ConnectionPoint getConnectionPointByOrientation(ConnectionPointOrientation orientation) {
        for (ConnectionPoint c: connectionPoints) {
            if (c.orientation == orientation)
                return c;
        }
        return null;
    }

    // Let the component handle the click. Only return true if something changed!!
    // If true is returned the view is redrawn and we don't need to do that if nothing changed.
    public boolean handleClick() {
        // Do nothing by default. A lot of components, e.g. a lightbulb, are not clickable.
        return false;
    };

    public void handleInputHigh() {};

    public void reset() {
        this.hasResistance = true;
    }
}

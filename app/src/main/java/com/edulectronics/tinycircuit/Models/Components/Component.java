package com.edulectronics.tinycircuit.Models.Components;

import android.support.annotation.NonNull;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.R;

import java.lang.reflect.Array;
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

    protected List<ConnectionPoint> connectionPoints = new ArrayList<ConnectionPoint>(4);

    public Component() {
        this.connectionPoints.add(new ConnectionPoint(this, ConnectionPointOrientation.Left));
        this.connectionPoints.add(new ConnectionPoint(this, ConnectionPointOrientation.Right));
    }

    public boolean hasOutputConnection(ConnectionPoint connectionpoint) {
        int incomingConnection = connectionPoints.indexOf(connectionpoint);
        List<ConnectionPoint> outgoingConnections = new ArrayList<ConnectionPoint>(connectionPoints);
        outgoingConnections.remove(incomingConnection);

        for (ConnectionPoint c : outgoingConnections) {
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

    public int getImage () {
        return R.mipmap.ic_launcher;
    }
}

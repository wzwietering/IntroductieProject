package com.edulectronics.tinycircuit.Models.Components.Connectors;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public class ConnectionPoint {

    public ConnectionPointOrientation orientation;
    private Component parentComponent;
    private ArrayList<Connection> connections = new ArrayList<Connection>();

    public ConnectionPoint(Component parent, ConnectionPointOrientation orientation) {
        this.parentComponent = parent;
        this.orientation = orientation;
    }

    public boolean hasOutputConnection() {
        for (Connection connection: connections) {
            if(connection.hasOutputConnection(this))
                return true;
        }
        return false;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Component getParentComponent() {
        return parentComponent;
    }

    public void addConnection(Connection connection) {
        if(this.getConnection(connection.pointA, connection.pointB) == null) {
            connections.add(connection);
        }
    }

    public void removeConnection(ConnectionPoint pointA, ConnectionPoint pointB) {
        Connection c = this.getConnection(pointA, pointB);
        if(c != null) {
            connections.remove(c);
        }
    }

    private Connection getConnection(ConnectionPoint pointA, ConnectionPoint pointB) {
        for (Connection c: this.connections) {
            if (c.pointA == pointA && c.pointB == pointB
                || c.pointB == pointA && c.pointA == pointB)
            {
                return c;
            }
        }
        return null;
    }
}

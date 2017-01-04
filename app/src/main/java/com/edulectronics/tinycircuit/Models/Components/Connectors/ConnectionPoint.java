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

    /*
    TODO: Depending on implementation for current calulations, voltageIn and -Out might
    be replaced by a single Voltage value.
    */
    private double voltageOut;
    private double voltageIn;

    public ConnectionPoint(Component parent, ConnectionPointOrientation orientation) {
        this.parentComponent = parent;
        this.orientation = orientation;
    }

    public double getVoltageOut() {
        return this.voltageOut;
    }

    public double getVoltageIn() {
        return this.voltageIn;
    }

    // Set a new output voltage. Only a component is allowed to do this to its own connection
    // points.
//    public void setVoltageOut(double voltage) {
//        this.voltageOut = voltage;
//        for (Connection connection: connections) {
//            connection.calculateVoltageIn();
//        }
//    }

//    // Returns the accumulated voltage of all connectionpoints.
//    private void calculateVoltageIn() {
//        double voltage = 0;
//        for (ConnectionPoint connectionPoint: connections) {
//            voltage += connectionPoint.getVoltageOut();
//        }
//        this.voltageIn = voltage;
//        parentComponent.handleInputChange();
//    }

    public boolean hasOutputConnection() {
        for (Connection connection: connections) {
            // ALWAYS check if parent is a powersource FIRST. Otherwise you get stackoverflow.
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

package com.edulectronics.tinycircuit.Models.Components.Connectors;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaike on 28-11-2016.
 */

public class ConnectionPoint {

    public ConnectionPointOrientation orientation;
    private Component parentComponent;
    private List<ConnectionPoint> connections = new ArrayList<ConnectionPoint>();

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

    // Set a new output voltage. Only a component is allowed to do this to its own connection
    // points.
    public void setVoltageOut(double voltage) {
        this.voltageOut = voltage;
        for (ConnectionPoint connectionPoint: connections) {
            connectionPoint.calculateVoltageIn();
        }
    }

    // Returns the accumulated voltage of all connectionpoints.
    private void calculateVoltageIn() {
        double voltage = 0;
        for (ConnectionPoint connectionPoint: connections) {
            voltage += connectionPoint.getVoltageOut();
        }
        this.voltageIn = voltage;
        parentComponent.handleInputChange();
    }

    public void connect(ConnectionPoint connectionPoint) {
        if(!connections.contains(connectionPoint)) {
            connections.add(connectionPoint);
        }
    }

    public void disconnect(ConnectionPoint connectionPoint) {
        if(connections.contains(connectionPoint)) {
            connections.remove(connectionPoint);
        }
    }

    public boolean hasOutputConnection() {
        for (ConnectionPoint connection: connections) {
            // ALWAYS check if parent is a powersource FIRST. Otherwise you get stackoverflow.
            if(connection.parentComponent.getClass() == Powersource.class ||
                    connection.parentComponent.hasOutputConnection(connection))
                return true;
        }
        return false;
    }

    public List<ConnectionPoint> getConnections() {
        return connections;
    }

    public Component getParentComponent() {
        return parentComponent;
    }

    public double getVoltageIn() {
        return this.voltageIn;
    }

}

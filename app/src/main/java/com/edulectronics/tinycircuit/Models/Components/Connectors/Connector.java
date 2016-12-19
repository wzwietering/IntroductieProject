package com.edulectronics.tinycircuit.Models.Components.Connectors;

/**
 * Created by Maaike on 28-11-2016.
 */
public class Connector {

    public static void connect(ConnectionPoint pointA, ConnectionPoint pointB) {
        if(pointA != null && pointB != null) {
            Connection connection = new Connection(pointA, pointB);
            pointA.addConnection(connection);
            pointB.addConnection(connection);
        }
    }

    public static void disconnect(ConnectionPoint pointA, ConnectionPoint pointB) {
        if(pointA != null && pointB != null) {
            pointA.removeConnection(pointA, pointB);
            pointB.removeConnection(pointA, pointB);
        }
    }
}

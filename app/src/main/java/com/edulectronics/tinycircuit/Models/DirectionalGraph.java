package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaike on 2-1-2017.
 */

public class DirectionalGraph {

    private final Powersource source;
    public List<Component> nodes = new ArrayList<Component>();
    public List<Edge> edges = new ArrayList<Edge>();

    public DirectionalGraph(Powersource source) {
        this.source = source;
        nodes.add(source);
            ConnectionPoint output = source.getOutput();
            List<Connection> connections = output.getConnections();

            for (Connection connection: connections) {
                Edge edge = new Edge(source, connection.pointB.getParentComponent());
                nodes.add(connection.pointB.getParentComponent()) ;
                addEdges(connection.pointB.getParentComponent(), connection.pointB);
        }
    }

    private void addEdges(Component component, ConnectionPoint input) {
        for (ConnectionPoint connectionPoint : component.getOutgoingConnections(input) ) {
            List<Connection> connections = connectionPoint.getConnections();

            for (Connection connection: connections) {
                if(!edges.contains(connection)) {
                    Edge edge = new Edge(component, connection.pointB.getParentComponent());
                }
                if(!nodes.contains(connection.pointB.getParentComponent())) {
                    nodes.add(connection.pointB.getParentComponent()) ;
                    addEdges(connection.pointB.getParentComponent(), connection.pointB);
                }
            }
        }
    }

    public void findAllPaths(Component component) {
        List<Component> path = new ArrayList<Component>();
        getNeighbour(component)
    }

    List<Component> getNeighbours(Component component) {

        for (Edge edge: this.edges) {
            if(edge.a == component) {
                path.add(component);
            }
        }
    }
}
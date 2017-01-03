package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Maaike on 2-1-2017.
 */

public class DirectionalGraph {

    private final Powersource source;
    public Set<Component> nodes = new HashSet<>();
    public List<Edge> edges = new ArrayList<Edge>();

    public DirectionalGraph(Powersource source) {
        this.source = source;
        nodes.add(source);
        ConnectionPoint output = source.getOutput();
        List<Connection> connections = output.getConnections();

        for (Connection connection : connections) {
            // Point B should not be hardcoded
            Edge edge = new Edge(source, connection.pointB.getParentComponent());
            edges.add(edge);
            nodes.add(connection.pointB.getParentComponent());
            addEdges(connection.pointB.getParentComponent(), connection.pointB);
        }
    }

    private void addEdges(Component component, ConnectionPoint input) {
        for (ConnectionPoint connectionPoint : component.getOutgoingConnections(input)) {
            List<Connection> connections = connectionPoint.getConnections();

            for (Connection connection : connections) {
                Component parentComponent = connection.getOtherPoint(input).getParentComponent();
                if (!edges.contains(connection)) {
                    Edge edge = new Edge(component, parentComponent);
                    edges.add(edge);
                }
                if (!nodes.contains(parentComponent)) {
                    nodes.add(parentComponent);
                    addEdges(parentComponent, connection.getOtherPoint(input));
                }
            }
        }
    }

    public void findAllPaths(Component component) {
        List<Component> path = new ArrayList<Component>();
        path = getNeighbours(component);
    }

    private List<Component> getNeighbours(Component component) {
        List<Component> path = new ArrayList<>();
        for (Edge edge : this.edges) {
            if (edge.a == component) {
                path.add(component);
            }
        }
        return path;
    }
}
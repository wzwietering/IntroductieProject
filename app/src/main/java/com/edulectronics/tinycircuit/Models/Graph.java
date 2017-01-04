package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Maaike on 2-1-2017.
 */

public class Graph {

    private final Powersource source;
    private final Object base;

    public Set<Object> nodes = new HashSet<>();
    public List<Edge> edges = new ArrayList<Edge>();

    Stack connectionPath;
    List<Stack> connectionPaths;

    public Graph(Powersource source, ArrayList<Connection> connections) {
        this.source = source;
        this.base = new Object();

        nodes.add(source);
        nodes.add(base);

        for (Connection connection: connections) {

            Edge edge = new Edge(connection.pointA.getParentComponent(), connection.pointB.getParentComponent());

            // Relay all source inputs to an object named 'base'. This way we can check
            // all paths between source and base, even though they really go from source to source.
            if(edge.a == source || edge.b == source) {
                if(source.getInput() == connection.pointA) {
                    edge.a = base;
                }
                if( source.getInput() == connection.pointB) {
                    edge.b = base;
                }
            }

            edges.add(edge);

            nodes.add(connection.pointA.getParentComponent());
            nodes.add(connection.pointB.getParentComponent());
        }
    }

    public List<Stack> findAllPaths() {
        connectionPath = new Stack();
        connectionPaths = new ArrayList<>();

        findPaths(source, base);
        return this.connectionPaths;
    }

    private void findPaths(Object sourceNode, Object targetNode) {
        for (Object nextNode : getNeighbours(sourceNode)) {
            if (nextNode.equals(targetNode)) {
                Stack temp = new Stack();
                for (Object node1 : connectionPath)
                    temp.add(node1);
                connectionPaths.add(temp);
            } else if ( connectionPath.size() < 10) {
                connectionPath.push(nextNode);
                findPaths(nextNode, targetNode);
                connectionPath.pop();
            }
        }
    }

    // Get all components that are connected to this component by an edge in the graph.
    private List<Object> getNeighbours(Object component) {
        List<Object> neighbours = new ArrayList<>();
        for (Edge edge : this.edges) {
            if (edge.a == component) {
                if(!neighbours.contains(edge.b))
                    neighbours.add(edge.b);
            }
            else if (edge.b == component) {
                if(!neighbours.contains(edge.a))
                neighbours.add(edge.a);
            }
        }
        return neighbours;
    }
}
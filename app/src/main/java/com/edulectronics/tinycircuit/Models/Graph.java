package com.edulectronics.tinycircuit.Models;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Switch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Maaike on 2-1-2017.
 */
// A graph of the circuit. Contains the nodes (components in cicuit) and edges
// (a pair of components representing a connection between the components.
public class Graph {

    // We have a source and base between which we can check all connections.
    // In the circuit, these are represented by the same powersource.
    // In the graph however, the connections of the powersource output are 'source',
    // and connection of the powersource input are 'base'.
    public final Powersource source;
    public final Component base;

    public Set<Component> nodes = new HashSet<>();
    List<Edge> edges = new ArrayList<Edge>();

    // temporary stack of components which will be saved as a path
    Stack<Component> connectionPath;
    // List of all path stacks that can be made between source and base.
    List<Stack> connectionPaths;

    public Graph(Powersource source, ArrayList<Connection> connections) {
        this.source = source;
        this.base = new Powersource();

        nodes.add(source);
        nodes.add(base);

        for (Connection connection : connections) {
            Edge edge = new Edge(connection.pointA.getParentComponent(), connection.pointB.getParentComponent());

            // Relay all source inputs to an object named 'base'. This way we can check
            // all paths between source and base, even though they really go from source to source.
            if (edge.a == source || edge.b == source) {
                if (source.getInput() == connection.pointA) {
                    edge.a = base;
                }
                if (source.getInput() == connection.pointB) {
                    edge.b = base;
                }
            }

            // Only add edges if they conduct power (and thus are a path).
            // A switch turned off is not a path!
            if(edge.a.isConductive() && edge.b.isConductive()) {
                edges.add(edge);
            }

            nodes.add(connection.pointA.getParentComponent());
            nodes.add(connection.pointB.getParentComponent());
        }
    }

    public List<Stack> findAllPaths() {
        connectionPath = new Stack();
        connectionPaths = new ArrayList<>();

        findPaths(base, source);
        return this.connectionPaths;
    }

    private void findPaths(Component sourceNode, Component targetNode) {
        for (Component nextNode : getNeighbours(sourceNode)) {
            if (nextNode.equals(targetNode)) {
                Stack temp = new Stack();
                for (Component node1 : connectionPath)
                    temp.add(node1);
                connectionPaths.add(temp);
            } else if (connectionPath.size() < 10) {
                connectionPath.push(nextNode);
                findPaths(nextNode, targetNode);
                connectionPath.pop();
            }
        }
    }

    // Get all components that are connected to this component by an edge in the graph.
    private Set<Component> getNeighbours(Component component) {
        Set<Component> neighbours = new HashSet<>();
        for (Edge edge : this.edges) {
            if (edge.a == component) {
                    neighbours.add(edge.b);
            } else if (edge.b == component) {
                    neighbours.add(edge.a);
            }
        }
        return neighbours;
    }
}
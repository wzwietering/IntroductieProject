package com.edulectronics.tinycircuit.Controllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import com.edulectronics.tinycircuit.Helpers.CoordinateHelper;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Wire;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilmer on 11-12-2016.
 */

public class ConnectionController {
    private Context context;
    private CircuitController circuitController;
    private Component firstComponent;
    private ConnectionPointOrientation firstOrientation;

    private List<Wire> wires = new ArrayList<Wire>();

    private CoordinateHelper coordinateHelper;

    private int cellHeight, cellWidth;
    public boolean connecting = false;

    public ConnectionController (Context context, int cellWidth, int cellHeight) {
        this.context = context;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public void setControllers(CircuitController circuitController) {
        this.circuitController = circuitController;
        coordinateHelper = new CoordinateHelper(circuitController.circuit.width, cellWidth, cellHeight);
    }

    public void makeWire(Component component, MotionEvent event) {
        if (mayConnect(component, event)) {
            if (!connecting) {
                setSelected(component, event);
            } else {
                connect(component, event);
            }
        }
    }

    public void cancelConnection() {
        this.connecting = false;
    }

    private void connect(Component component, MotionEvent event) {
        Connector connector = new Connector();
        ConnectionPointOrientation secondOrientation = getClickedArea((int) event.getX());

        ConnectionPoint firstConnectionPoint = getConnectionPoint(firstComponent, firstOrientation);
        ConnectionPoint secondConnectionPoint = getConnectionPoint(component, secondOrientation);

        //Connectionpoints should not connect with themself
        if(firstConnectionPoint != secondConnectionPoint) {
            connector.connect(
                    getConnectionPoint(firstComponent, firstOrientation),
                    getConnectionPoint(component, secondOrientation));
            connecting = false;
            redrawWires();
        }
    }

    // Redraw all the wire views in the grid
    public void redrawWires() {
        // First clear the existing wires
        clearWires();

        // ... and rebuild.
        for (Connection c : circuitController.getAllConnections()) {
            if (c != null) {
                setLines(c);
            }
        }
        for (Wire wire : this.wires) {
            wire.invalidate();
        }
    }

    private void clearWires() {
        DrawerLayout parentLayout = (DrawerLayout)((Activity)context).findViewById(R.id.wires);
        for (Wire wire : wires) {
            parentLayout.removeView(wire);
        }
        this.wires.clear();
    }


    private void setSelected(Component component, MotionEvent event) {
        firstComponent = component;
        firstOrientation = getClickedArea((int) event.getX());
        connecting = true;
    }

    private boolean mayConnect(Component component, MotionEvent event) {
        return (component != null & event.getAction() == MotionEvent.ACTION_UP);
    }

    private ConnectionPoint getConnectionPoint(Component component, ConnectionPointOrientation cpo) {
        for (ConnectionPoint cp : component.getConnectionPoints()) {
            if (cp.orientation == cpo) {
                return cp;
            }
        }
        return null;
    }

    public void setLines(Connection c) {

        Point pointA = coordinateHelper.getNodeLocation(
                c.pointA.getParentComponent().position,
                c.pointA.orientation);

        Point pointB = coordinateHelper.getNodeLocation(
                c.pointB.getParentComponent().position,
                c.pointB.orientation
        );

        List<Wire> connectionWires = new ArrayList<Wire>();

        boolean drawVerticalLineFirst = true;

        // If your connectionpoint is on the same side as the draw direction of the wire,
        // draw the horizontal wire first. Otherwise, draw the vertical wire first.
        if ((pointA.x < pointB.x && c.pointA.orientation == ConnectionPointOrientation.Right)
                || (pointA.x > pointB.x && c.pointA.orientation == ConnectionPointOrientation.Left)) {
                drawVerticalLineFirst = false;
        }

        // Components are at the same height, and either:
        // ... drawVerticalineFirst is true, meaning we will intersect component A
        // because the components are at the same height.
        // ... or their orientations are the same, meaning we will always intersect component B
        if (pointA.y == pointB.y
                && (drawVerticalLineFirst
                || c.pointA.orientation == c.pointB.orientation)) {
            // we draw a short vertical wire to go around.
            Wire startWire = (drawVerticalLineFirst && c.pointA.orientation == c.pointB.orientation)
                    ? moveUp(pointA)
                    : moveDown(pointA);
            connectionWires.add(startWire);
            pointA = startWire.b;
            drawVerticalLineFirst = false;
        }

        // We intersect component B because we draw a vertical line first, and the connectionpoint
        // is on the 'other side' of the component (considering where we start off)
        if (drawVerticalLineFirst &&
                ((pointB.x < pointA.x && c.pointB.orientation == ConnectionPointOrientation.Left)
                || (pointB.x > pointA.x && c.pointB.orientation == ConnectionPointOrientation.Right))) {
                Wire endWire = pointB.y > pointA.y ? moveDown(pointB) : moveUp(pointB);
                connectionWires.add(endWire);
                pointB = endWire.b;
        }

        connectionWires.addAll(getInBetweenLines(pointA, pointB, drawVerticalLineFirst));

        // Parent layout
        DrawerLayout parentLayout = (DrawerLayout)((Activity)context).findViewById(R.id.wires);

        for (Wire wire : connectionWires) {
            // Add the view to the parent layout
            parentLayout.addView(wire);
            this.wires.add(wire);
        }
        c.setWires(connectionWires);
    }

    private Wire moveUp(Point point) {
        return createLine(point,
                new Point(point.x, point.y + (int) (0.5 * cellHeight)));
    }

    private Wire moveDown(Point point) {
        return createLine(point,
                new Point(point.x, point.y - (int) (0.5 * cellHeight)));
    }

    public List<Wire> getInBetweenLines(Point a, Point b, boolean drawVerticalLineFirst) {
        List<Wire> wires = new ArrayList<>();
        Point lastPoint = a;
        Wire l;

        l = drawVerticalLineFirst ? getVerticalWire(lastPoint, b) : getHorizontalWire(lastPoint, b);

        if (l != null) {
            lastPoint = l.b;
            wires.add(l);
        }

        l = drawVerticalLineFirst ? getHorizontalWire(lastPoint, b) : getVerticalWire(lastPoint, b);

        if (l != null) {
            wires.add(l);
        }
        return wires;
    }
    private Wire createLine(Point a, Point b) {
        Wire wire = new Wire(context, null);
        wire.setCoordinates(a, b);
        return wire;
    }

    public Wire getHorizontalWire(Point a, Point b) {
        if (a.x == b.x)
            return null;
        return createLine(new Point(a.x, a.y), new Point(b.x, a.y));
    }

    public Wire getVerticalWire(Point a, Point b) {
        if (a.y == b.y)
            return null;
        return createLine(new Point(a.x, a.y), new Point(a.x, b.y));
    }

    public ConnectionPointOrientation getClickedArea(int x) {
        if (x < 0.5 * cellHeight) {
            return ConnectionPointOrientation.Left;
        } else if (x >= 0.5 * cellHeight) {
            return ConnectionPointOrientation.Right;
        } else {
            throw new IllegalArgumentException();
        }
    }
}

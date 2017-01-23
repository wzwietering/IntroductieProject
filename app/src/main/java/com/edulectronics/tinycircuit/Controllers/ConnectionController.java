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

        connectionWires.addAll(getInBetweenLines(c, pointA, pointB, drawVerticalLineFirst));

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

    public ArrayList<Wire> getInBetweenLines(Connection c, Point a, Point b, boolean drawVerticalLineFirst) {
        ArrayList<Wire> wires = new ArrayList<Wire>();
        Point lastPoint = a;
        boolean intersection = false;

        //Detect an intersection with another component and avoid the component
        for (Component component : circuitController.getComponents()){
            // Check if the horizontal wire of a two part wire intersects a component and
            // if the component is not a parent
            if ((drawVerticalLineFirst
                    ? intersectsComponent(component.coordinates, b, new Point(a.x, b.y))
                    : intersectsComponent(component.coordinates, a, new Point(b.x, lastPoint.y)))
                    && component != c.pointA.getParentComponent()
                    && component != c.pointB.getParentComponent()){
                //One intersection is enough to know that the wire should be different
                intersection = true;
                break;
            }
        }

        // Regular two part line
        if (!intersection) {
            wires.addAll(drawVerticalLineFirst
                    ? getWires(c, lastPoint, new Point(lastPoint.x, b.y))
                    : getWires(c, lastPoint, new Point(b.x, lastPoint.y)));

            if (wires.size() > 0) {
                lastPoint = wires.get(wires.size() - 1).b;
            }

            wires.addAll(drawVerticalLineFirst
                    ? getWires(c, lastPoint, new Point(b.x, lastPoint.y))
                    : getWires(c, lastPoint, new Point(lastPoint.x, b.y)));
        } else {
            //A three part line which avoids components
            //First vertical part
            wires.add(getHalfLine(a, lastPoint));
            lastPoint = wires.get(wires.size() - 1).b;
            //Horizontal part
            wires.addAll(getWires(c, lastPoint, new Point(b.x, lastPoint.y)));
            lastPoint = wires.get(wires.size() - 1).b;
            //Final vertical part
            wires.addAll(getWires(c, lastPoint, b));
        }
        return wires;
    }
    private Wire createLine(Point a, Point b) {
        Wire wire = new Wire(context, null);
        wire.setCoordinates(a, b);
        return wire;
    }

    private boolean intersectsComponent(Point coordinates, Point a, Point b) {
        //it's a horizontal line
        if(a.y == b.y) {
            if(coordinates.y + cellHeight/2 >= a.y &&
                    coordinates.y - cellHeight/2 < a.y &&
                    coordinates.x + cellWidth > Math.min(a.x, b.x) &&
                    coordinates.x - cellWidth < Math.max(a.x, b.x))
                return true;
        }
        // it's a vertical line, these never intersect with other components.
        return false;
    }

    public ArrayList<Wire> getWires(Connection c, Point a, Point b) {
        ArrayList<Wire> wires = new ArrayList<Wire>();
        wires.add(createLine(new Point(a.x, a.y), new Point(b.x, b.y)));
        return wires;
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

    //Creates a vertical line which is half a cell height long
    public Wire getHalfLine(Point startPoint, Point endPoint) {
        if (startPoint.y < endPoint.y) {
            // Go up half a cell
            return createLine(startPoint,
                    new Point(startPoint.x, startPoint.y + (int) (0.5 * cellHeight)));
        } else {
            // Go down half a cell
            return createLine(startPoint,
                    new Point(startPoint.x, startPoint.y - (int) (0.5 * cellHeight)));
        }
    }
}

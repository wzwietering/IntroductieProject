package com.edulectronics.tinycircuit.Controllers;

import android.graphics.Point;
import android.view.MotionEvent;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Line;
import com.edulectronics.tinycircuit.Views.WireView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilmer on 11-12-2016.
 */

public class ConnectionController {
    private Component firstComponent;
    private ConnectionPointOrientation firstOrientation;
    private WireView wireView;
    private int cellHeight, cellWidth;
    public boolean connecting = false;

    public ConnectionController(WireView wireView, int cellWidth, int cellHeight) {
        this.wireView = wireView;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
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

    private void connect(Component component, MotionEvent event) {
        Connector connector = new Connector();
        ConnectionPointOrientation secondOrientation = getClickedArea((int) event.getX(), (int) event.getY());
        connector.connect(
                getConnectionPoint(firstComponent, firstOrientation),
                getConnectionPoint(component, secondOrientation));
        connecting = false;
        redraw();
    }

    private void setSelected(Component component, MotionEvent event) {
        firstComponent = component;
        firstOrientation = getClickedArea((int) event.getX(), (int) event.getY());
        connecting = true;
    }

    private boolean mayConnect(Component component, MotionEvent event) {
        return (component != null & event.getAction() == MotionEvent.ACTION_DOWN);
    }

    public ConnectionPointOrientation getClickedArea(int x, int y) {
        if (x < 0.5 * cellHeight && y < 0.5 * cellHeight) {
            if (x >= y) {
                return ConnectionPointOrientation.Top;
            } else {
                return ConnectionPointOrientation.Left;
            }
        } else if (x >= 0.5 * cellHeight && y < 0.5 * cellHeight) {
            if (y < cellHeight - x) {
                return ConnectionPointOrientation.Top;
            } else {
                return ConnectionPointOrientation.Right;
            }
        } else if (x < 0.5 * cellHeight && y >= 0.5 * cellHeight) {
            if (y < cellHeight - x) {
                return ConnectionPointOrientation.Left;
            } else {
                return ConnectionPointOrientation.Bottom;
            }
        } else if (x >= 0.5 * cellHeight && y >= 0.5 * cellHeight) {
            if (x >= y) {
                return ConnectionPointOrientation.Right;
            } else {
                return ConnectionPointOrientation.Bottom;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private ConnectionPoint getConnectionPoint(Component component, ConnectionPointOrientation cpo) {
        for (ConnectionPoint cp : component.getConnectionPoints()) {
            if (cp.orientation == cpo) {
                return cp;
            }
        }
        return null;
    }


    public void setLines(Connection c, Point startPoint, Point endPoint) {
        List<Line> lines = new ArrayList<Line>();
        Line startLine = getEndPointLine(startPoint, endPoint, c.pointA.orientation);
        Line endLine = getEndPointLine(endPoint, startPoint, c.pointB.orientation);

        // If the connection point has a orientation left or right we firstComponent draw a vertical
        // line, then a horizontal one. It looks better (trust me).
        boolean drawVerticalLineFirst = c.pointA.orientation == ConnectionPointOrientation.Left
                || c.pointA.orientation == ConnectionPointOrientation.Right;

        lines.add(startLine);
        lines.addAll(getInBetweenLines(startLine.b, endLine.b, drawVerticalLineFirst));
        lines.add(endLine);

        c.setLines(lines);
    }

    public List<Line> getInBetweenLines(Point a, Point b, boolean drawVerticalLineFirst) {
        List<Line> lines = new ArrayList<>();
        Point lastPoint = a;
        Line l;

        l = drawVerticalLineFirst ? getVerticalWire(lastPoint, b) : getHorizontalWire(lastPoint, b);

        if (l != null) {
            lastPoint = l.b;
            lines.add(l);
        }

        l = drawVerticalLineFirst ? getHorizontalWire(lastPoint, b) : getVerticalWire(lastPoint, b);

        if (l != null) {
            lines.add(l);
        }
        return lines;
    }

    public Line getHorizontalWire(Point a, Point b) {
        if (a.x == b.x)
            return null;
        return new Line(new Point(a.x, a.y), new Point(b.x, a.y));
    }

    public Line getVerticalWire(Point a, Point b) {
        if (a.y == b.y)
            return null;
        return new Line(new Point(a.x, a.y), new Point(a.x, b.y - (Math.abs(a.y - b.y) % 150)));
    }

    // First go half a cell up/down/left/right, ddepending on where the connectionpoint is
    // and where the endpoint is. Otherwise the next lines will be drawn right through other
    // components.
    public Line getEndPointLine(Point startPoint, Point endPoint, ConnectionPointOrientation orientation) {
        switch (orientation) {
            case Left:
            case Right:
                if (startPoint.y < endPoint.y) {
                    // Go up half a cell
                    return new Line(startPoint,
                            new Point(startPoint.x, startPoint.y + (int) (0.5 * cellHeight)));
                } else {
                    // Go down half a cell
                    return new Line(startPoint,
                            new Point(startPoint.x, startPoint.y - (int) (0.5 * cellHeight)));
                }
            case Bottom:
            case Top:
                if (startPoint.x > endPoint.x) {
                    // Go left half a cell
                    return new Line(startPoint,
                            new Point(startPoint.x - (int) (0.5 * cellWidth), startPoint.y));
                } else {
                    // Go right half a cell
                    return new Line(startPoint,
                            new Point(startPoint.x + (int) (0.5 * cellWidth), startPoint.y));
                }
            default:
                throw new IllegalArgumentException();
        }
    }

    public void redraw() {
        wireView.invalidate();
    }
}

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

public class WireController {
    private Component first;
    private ConnectionPointOrientation cpoFirst;
    private WireView wireView;
    private int cellHeight, cellWidth;
    private boolean connecting = false;

    public WireController(WireView wireView, int cellWidth, int cellHeight) {
        this.wireView = wireView;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public void wire(Component component, MotionEvent event) {
        if(component != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!connecting) {
                    first = component;
                    cpoFirst = area((int) event.getX(), (int) event.getY());
                    connecting = true;
                } else {
                    Connector connector = new Connector();
                    ConnectionPointOrientation cpoSecond = area((int) event.getX(), (int) event.getY());
                    connector.connect(getConnectionPoint(first, cpoFirst), getConnectionPoint(component, cpoSecond));
                    connecting = false;
                    wireView.invalidate();
                }
            }
        }
    }

    private ConnectionPointOrientation area(int x, int y) {
        if (x < 0.5 * cellWidth && y < 0.5 * cellHeight) {
            if (x >= y) {
                return ConnectionPointOrientation.Top;
            } else {
                return ConnectionPointOrientation.Left;
            }
        } else if (x >= 0.5 * cellWidth && y < 0.5 * cellHeight) {
            if (y < 0.5 * cellHeight - x) {
                return ConnectionPointOrientation.Top;
            } else {
                return ConnectionPointOrientation.Right;
            }
        } else if (x < 0.5 * cellWidth && y >= 0.5 * cellHeight) {
            if (y < 0.5 * cellHeight - x) {
                return ConnectionPointOrientation.Left;
            } else {
                return ConnectionPointOrientation.Bottom;
            }
        } else if (x >= 0.5 * cellWidth && y >= 0.5 * cellHeight) {
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

    public List<Line> getWires(Point a, Point b) {
        List<Line> lines = new ArrayList<>();
        Point lastPoint = a;



        for (int i = 0; i <= 2; i++) {
            Line w;
            if (i % 2 == 0) {
                w = getVerticalWire(lastPoint, b);
            } else {
                w = getHorizontalWire(lastPoint, b);
            }

            if (w != null) {
                lastPoint = w.b;
                lines.add(w);
            }
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
                    return new Line (startPoint,
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

    public void setLines(Connection c, Point startPoint, Point endPoint) {

        List<Line> lines = new ArrayList<Line>();
        Line startLine = getEndPointLine(startPoint, endPoint, c.pointA.orientation);
        Line endLine = getEndPointLine(endPoint, startPoint, c.pointB.orientation);

        lines.add(startLine);
        lines.addAll(getWires(startLine.b, endLine.b));
        lines.add(endLine);

        c.setLines(lines);
    }
}

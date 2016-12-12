package com.edulectronics.tinycircuit.Controllers;

import android.graphics.Point;
import android.view.MotionEvent;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Wire;
import com.edulectronics.tinycircuit.Views.DrawView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilmer on 11-12-2016.
 */

public class WireController {
    private Component first;
    private ConnectionPointOrientation cpoF;
    private DrawView drawView;
    private int cellSize, halfCellSize;
    private boolean connecting = false;

    public WireController(DrawView drawView) {
        this.drawView = drawView;
    }

    public void wire(Component component, MotionEvent event, int cellSize) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.cellSize = cellSize;
            this.halfCellSize = (int) (0.5 * cellSize);
            if (!connecting) {
                first = component;
                cpoF = area((int) event.getX(), (int) event.getY());
                System.out.println("First: " + cpoF);
                connecting = true;
            } else {
                Connector connector = new Connector();
                ConnectionPointOrientation cpoS = area((int) event.getX(), (int) event.getY());
                System.out.println("Second: " + cpoS);

                connector.connect(getConnectionPoint(first, cpoF), getConnectionPoint(component, cpoS));
                connecting = false;
                drawView.invalidate();
            }
        }
    }

    private ConnectionPointOrientation area(int x, int y) {
        if (x < halfCellSize && y < halfCellSize) {
            if (x >= y) {
                return ConnectionPointOrientation.Top;
            } else {
                return ConnectionPointOrientation.Left;
            }
        } else if (x >= halfCellSize && y < halfCellSize) {
            if (y < cellSize - x) {
                return ConnectionPointOrientation.Top;
            } else {
                return ConnectionPointOrientation.Right;
            }
        } else if (x < halfCellSize && y >= halfCellSize) {
            if (y < cellSize - x) {
                return ConnectionPointOrientation.Left;
            } else {
                return ConnectionPointOrientation.Bottom;
            }
        } else if (x >= halfCellSize && y >= halfCellSize) {
            if (x >= y) {
                return ConnectionPointOrientation.Right;
            } else {
                return ConnectionPointOrientation.Bottom;
            }
        }
        return null;
    }

    private ConnectionPoint getConnectionPoint(Component component, ConnectionPointOrientation cpo) {
        for (ConnectionPoint cp : component.getConnectionPoints()) {
            if (cp.orientation == cpo) {
                return cp;
            }
        }
        return null;
    }

    public List<Wire> getWires(Point a, Point b) {
        List<Wire> wires = new ArrayList<>();
        Point lastPoint = a;

        for (int i = 0; i <= 2; i++) {
            Wire w;
            if (i % 2 == 0) {
                w = getVerticalWire(lastPoint, b);
            } else {
                w = getHorizontalWire(lastPoint, b);
            }

            if (w != null) {
                lastPoint = w.b;
                wires.add(w);
            }
        }
        return wires;
    }

    public Wire getHorizontalWire(Point a, Point b) {
        if (a.x == b.x)
            return null;
        return new Wire(new Point(a.x, a.y), new Point(b.x, a.y));
    }

    public Wire getVerticalWire(Point a, Point b) {
        if (a.y == b.y)
            return null;
        return new Wire(new Point(a.x, a.x), new Point(a.x, b.y - (Math.abs(a.y - b.y) % 150)));
    }
}

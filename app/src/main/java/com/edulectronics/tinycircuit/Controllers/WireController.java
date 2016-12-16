package com.edulectronics.tinycircuit.Controllers;

import android.view.MotionEvent;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Views.WireView;

/**
 * Created by Wilmer on 11-12-2016.
 */

public class WireController {
    public Component first;
    private ConnectionPointOrientation cpoFirst;
    private WireView wireView;
    public int cellSize, halfCellSize;
    private boolean connecting = false;

    public WireController(WireView wireView) {
        this.wireView = wireView;
    }

    public void wire(Component component, MotionEvent event, int cellSize) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.cellSize = cellSize;
            this.halfCellSize = (int) (0.5 * cellSize);
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

    public ConnectionPointOrientation area(int x, int y) {
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
}
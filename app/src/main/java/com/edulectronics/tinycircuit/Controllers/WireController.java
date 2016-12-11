package com.edulectronics.tinycircuit.Controllers;

import android.view.MotionEvent;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Views.DrawView;

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
                ConnectionPointOrientation cpoS = area((int) event.getX(), (int) event.getY());
                System.out.println("Second: " + cpoS);

                first.connect(cpoF, getConnectionPoint(component, cpoS));
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
}

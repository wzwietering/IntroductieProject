package com.edulectronics.tinycircuit.Controllers;

import android.view.MotionEvent;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Views.DrawView;

/**
 * Created by Wilmer on 11-12-2016.
 */

public class WireController {
    private Component first, second;
    private ConnectionPointOrientation cpoF, cpoS;
    private DrawView drawView;
    private int cellSize, halfCellSize;

    public WireController(DrawView drawView) {
        this.drawView = drawView;
    }

    public void wire(Component component, MotionEvent event, int cellSize) {
        this.cellSize = cellSize;
        this.halfCellSize = (int) (0.5 * cellSize);
        if (first == null) {
            first = component;
            cpoF = area((int) event.getX(), (int) event.getY());
            System.out.println(cpoF);
        } else {
            second = component;
            cpoS = area((int) event.getX(), (int) event.getY());
            System.out.println(cpoS);

            if(component.getConnectionPoints().indexOf(cpoS) != -1){
                first.connect(cpoF, component.getConnectionPoints().get(component.getConnectionPoints().indexOf(cpoS)));
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
}

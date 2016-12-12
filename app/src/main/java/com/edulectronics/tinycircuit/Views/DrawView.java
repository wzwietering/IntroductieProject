package com.edulectronics.tinycircuit.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Controllers.DrawingController;
import com.edulectronics.tinycircuit.Controllers.WireController;
import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Wire;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 9-12-2016.
 */

public class DrawView extends View {
    Paint paint = new Paint();
    private CircuitController controller;
    private DrawingController drawingController;
    private WireController wireController;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
        wireController = new WireController(this);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        paint.setStrokeWidth(size.y / 100);
    }

    public void setControllers(CircuitController circuitController) {
        this.controller = circuitController;
        drawingController = new DrawingController(circuitController.circuit.width, getContext().getResources().getInteger(R.integer.cell_size));
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Component c: controller.getComponents()) {
            if (c != null) {
                for (ConnectionPoint connection: c.getConnectionPoints()) {
                    for (ConnectionPoint connectedTo: connection.getConnections()) {
                        Point startPoint = drawingController.getNodeLocation(c.position, connection.orientation);
                        Point endPoint = drawingController.getNodeLocation(connectedTo.getParentComponent().position, connectedTo.orientation);
                        for (Wire wire: wireController.getWires(startPoint, endPoint)) {
                            canvas.drawLine(wire.a.x, wire.a.y, wire.b.x, wire.b.y, paint);
                        }
                    }
                }
            }
        }
        super.onDraw(canvas);
    }
}

package com.edulectronics.tinycircuit.Views;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 9-12-2016.
 */

public class DrawView extends View {
    Paint paint;
    private int strokeWidth = 8;
    private CircuitController controller;
    private DrawingController drawingController;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(strokeWidth);
    }

    public void setControllers(CircuitController controller) {
        this.controller = controller;
        drawingController = new DrawingController(controller.circuit.width, getContext().getResources().getInteger(R.integer.cell_size));
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Component c: controller.getComponents()) {
            if (c != null) {
                for (ConnectionPoint connection: c.getConnectionPoints()) {
                    for (ConnectionPoint connectedTo: connection.getConnections()) {
                        Point startPoint = drawingController.getNodeLocation(c.position, connection.orientation);
                        Point endPoint = drawingController.getNodeLocation(connectedTo.getParentComponent().position, connectedTo.orientation);
                        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);
                    }
                }
            }
        }
        super.onDraw(canvas);
    }
}

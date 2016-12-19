package com.edulectronics.tinycircuit.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Controllers.CoordinateHelper;
import com.edulectronics.tinycircuit.Controllers.WireController;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Line;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilmer on 9-12-2016.
 */

public class WireView extends View {
    public static Paint paint = new Paint();
    private CircuitController controller;
    private CoordinateHelper coordinateHelper;
    private WireController wireController;

    private Point screenSize = new Point();

    public WireView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(getResources().getColor(R.color.wire_default));

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getSize(screenSize);
        paint.setStrokeWidth(screenSize.y / 100);
    }

    public void setControllers(CircuitController controller) {
        int cellWidth = screenSize.x / controller.circuit.width;
        int cellHeight = getContext().getResources().getInteger(R.integer.cell_size);

        this.controller = controller;
        coordinateHelper = new CoordinateHelper(controller.circuit.width, cellWidth, cellHeight);
        wireController = new WireController(this, cellWidth, cellHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Connection c : controller.getAllConnections()) {
            if (c != null) {
                Point startPoint = coordinateHelper.getNodeLocation(
                        c.pointA.getParentComponent().position,
                        c.pointA.orientation);

                Point endPoint = coordinateHelper.getNodeLocation(
                        c.pointB.getParentComponent().position,
                        c.pointB.orientation
                );

                wireController.setLines(c, startPoint, endPoint);

                for (Line line : c.getLines()) {
                    canvas.drawLine(line.a.x, line.a.y, line.b.x, line.b.y, paint);
                }
            }
        }
        super.onDraw(canvas);
    }
}

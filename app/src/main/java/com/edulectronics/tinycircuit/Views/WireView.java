package com.edulectronics.tinycircuit.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Controllers.CoordinateHelper;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 9-12-2016.
 */

public class WireView extends View {
    private int strokeWidth = 8
    Paint paint = new Paint();
    private CircuitController controller;
    private CoordinateHelper coordinateHelper;

    public WireView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        paint.setStrokeWidth(size.y / 100);
    }

    public void setControllers(CircuitController controller) {
        this.controller = controller;
        coordinateHelper = new CoordinateHelper(controller.circuit.width, getContext().getResources().getInteger(R.integer.cell_size));
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Component c : controller.getComponents()) {
            if (c != null) {
                for (ConnectionPoint connection : c.getConnectionPoints()) {
                    for (ConnectionPoint connectedTo : connection.getConnections()) {
                        Point startPoint = coordinateHelper.getNodeLocation(c.position, connection.orientation);
                        Point endPoint = coordinateHelper.getNodeLocation(connectedTo.getParentComponent().position, connectedTo.orientation);
                        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);
                    }
                }
            }
        }
        super.onDraw(canvas);
    }
}

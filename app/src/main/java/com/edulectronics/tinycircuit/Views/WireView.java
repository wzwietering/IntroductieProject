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
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Line;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 9-12-2016.
 */

public class WireView extends View {
    Paint paint = new Paint();
    private CircuitController controller;
    private CoordinateHelper coordinateHelper;
    private WireController wireController;

    public WireView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(getResources().getColor(R.color.wire_default));
        wireController = new WireController(this);
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
                for (ConnectionPoint connectionPoint: c.getConnectionPoints()) {
                    for (Connection connection: connectionPoint.getConnections()) {
                        Point startPoint = coordinateHelper.getNodeLocation(c.position, connectionPoint.orientation);

                        Point endPoint = coordinateHelper.getNodeLocation(
                                connectionPoint.getParentComponent().position,
                                connectionPoint.orientation
                        );
                        for (Line line : wireController.getWires(startPoint, endPoint)) {
                            canvas.drawLine(line.a.x, line.a.y, line.b.x, line.b.y, paint);
                        }
                    }
                }
            }
        }
        super.onDraw(canvas);
    }
}

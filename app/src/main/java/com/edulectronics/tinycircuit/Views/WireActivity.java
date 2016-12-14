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
import com.edulectronics.tinycircuit.Controllers.ConnectionController;
import com.edulectronics.tinycircuit.Controllers.CoordinateHelper;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Line;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Wire;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 9-12-2016.
 */

public class WireActivity extends View {
    Paint paint = new Paint();
    private ConnectionController connectionController;

    public WireActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        paint.setStrokeWidth(size.y / 100);
    }

    public void setControllers(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Line line : connectionController.getWireLines()) {
            canvas.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y, paint);
        }
        super.onDraw(canvas);
    }
}

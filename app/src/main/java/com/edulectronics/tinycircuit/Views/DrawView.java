package com.edulectronics.tinycircuit.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.telecom.Connection;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;

/**
 * Created by Wilmer on 9-12-2016.
 */

public class DrawView extends View {
    Paint paint = new Paint();
    private int strokeWidth = 8;
    private CircuitController controller;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(strokeWidth);
    }

    public void setController(CircuitController controller) {
        this.controller = controller;
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Component c: controller.getComponents()) {
            if (c != null) {
                for (ConnectionPoint connection: c.getConnectionPoints()) {
                    for (ConnectionPoint connectedTo: connection.getConnections()) {
                        
                    }
                }
            }
        }
        super.onDraw(canvas);
    }
}

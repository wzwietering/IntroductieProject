package com.edulectronics.tinycircuit.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.edulectronics.tinycircuit.R;


/**
 * Created by bernd on 12/12/2016.
 */

public class Wire extends View {
    public Point a, b;
    public Paint paint = new Paint();
    private Point screenSize = new Point();
    private WireDrawingMode drawingMode = WireDrawingMode.normal;
    private int thisDelay = 0;
    private int numberOfFlashes = 0;


    public Wire(Context context, AttributeSet attr) {
        super(context, attr);
        paint.setColor(getResources().getColor(R.color.wire_default));

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getSize(screenSize);
        paint.setStrokeWidth(screenSize.y / 100);
    }

    public void setCoordinates(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (this.drawingMode) {
            case normal:
                canvas.drawLine(a.x, a.y, b.x, b.y, paint);
                break;
            case highlight:
                canvas.drawLine(a.x, a.y, b.x, b.y, paint);

                if (numberOfFlashes < 4) {
                    if (paint.getColor() == Color.WHITE) {
                        paint.setColor(Color.YELLOW);
                    } else {
                        paint.setColor(Color.WHITE);
                    }
                    numberOfFlashes++;
                    postInvalidateDelayed(300); // set time here
                }
        }
        super.onDraw(canvas);
    }

    // Highlight the wire. We change the paint color andd call the onDraw() method again with
    // a delay.
    public void highLight(int color, int delay) {

        // Set drawingmode (which is checked in the onDraw() method)
        this.drawingMode = WireDrawingMode.highlight;

        Handler handler = new Handler(this.getContext().getMainLooper());
        // We need a runnable that accepts argument. Create the class here because it will NOT
        // be used anywhere else, and I don't see the point of making a whole new file for this.
        class drawingRunnable implements Runnable {
            private final Wire wire;
            private final int color;

            public drawingRunnable(Wire wire, int color) {
                this.wire = wire;
                this.color = color;
            }
            // This runnable invalidates the wire and thus onDraw() is called on the wire.
            public void run() {
                wire.paint.setColor(this.color);
                wire.invalidate();
            }
        }
        this.numberOfFlashes = 0;
        handler.postDelayed(new drawingRunnable(this, color), delay);
    }

    public boolean isTouched(Point point){
        //Modify the multiplier to change the detection area of the tap.
        float width = this.paint.getStrokeWidth() * 4;
        int minX = Math.min(a.x, b.x);
        int maxX = Math.max(a.x, b.x);
        int minY = Math.min(a.y, b.y);
        int maxY = Math.max(a.y, b.y);
        if(point.x > minX - width && point.y > minY - width &&
                point.x < maxX + width && point.y < maxY + width) {
            return true;
        } else {
            return false;
        }
    }

    public enum WireDrawingMode {
        normal,
        highlight,
        running
    }
}

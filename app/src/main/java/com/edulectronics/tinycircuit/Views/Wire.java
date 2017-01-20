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
    public Paint whitePaint = new Paint();
    public Paint highlightPaint = new Paint();

    private Point screenSize = new Point();
    public WireDrawingMode drawingMode = WireDrawingMode.normal;

    // The next variables aree all necessary for drawing the wire.
    // These determine which end of the wire gets drawn first:
    public Point drawStart;
    public Point drawEnd;
    // these are counters for drawing the line
    int drawUpToX;
    int drawUpToY;
    // And these are intervals which we use for drawing; they can be positive and negative.
    private int drawIntervalX;
    private int drawIntervalY;

    // used for flash mode to keep track of the number of times we flashed
    private int numberOfFlashes;
    // used for 'running' highlight mode to see what color we used last
    boolean useHighlightColorFirst = true;


    public Wire(Context context, AttributeSet attr) {
        super(context, attr);
        whitePaint.setColor(getResources().getColor(R.color.wire_default));

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getSize(screenSize);
        whitePaint.setStrokeWidth(screenSize.y / 100);

        highlightPaint.setStrokeWidth(whitePaint.getStrokeWidth());
    }

    public void setCoordinates(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (this.drawingMode) {
            case normal:
                canvas.drawLine(a.x, a.y, b.x, b.y, whitePaint);
                break;

            // Highlight runs along the circuit to show which way the current flows.
            case runningHighlight:
                checkIfEndWasReached();

                // Draw the running highlight...
                canvas.drawLine(drawStart.x, drawStart.y, drawUpToX, drawUpToY, this.highlightPaint);
                // ... and the rest of the line still in white.
                canvas.drawLine(drawEnd.x, drawEnd.y, drawUpToX, drawUpToY, this.whitePaint);

                // Up the coordinates and repeat! (until we're at drawEnd)
                if (drawUpToX != drawEnd.x || drawUpToY != drawEnd.y) {
                    drawUpToX += drawIntervalX;
                    drawUpToY += drawIntervalY;
                    postInvalidateDelayed(10);
                }
                break;

            // Current flows through the whole circuit displayed like a dotted line.
            case staticHighlight:
                boolean useHighlightColor = this.useHighlightColorFirst;

                while (drawUpToX != drawEnd.x || drawUpToY != drawEnd.y) {
                    drawUpToX += drawIntervalX * 2;
                    drawUpToY += drawIntervalY * 2;
                    checkIfEndWasReached();

                    canvas.drawLine(drawUpToX - drawIntervalX * 2,
                            drawUpToY - drawIntervalY * 2,
                            drawUpToX,
                            drawUpToY,
                            useHighlightColor ? this.highlightPaint : this.whitePaint);

                    useHighlightColor = !useHighlightColor;
                }

                this.useHighlightColorFirst = !this.useHighlightColorFirst;
                // Reset counters drawUpToX and Y.
                this.resetCounters();

                // ... and repeat every second.
                postInvalidateDelayed(1000);
                break;

            // Flash this wire to indicate a mistake.
            case flashingHighlight:
                canvas.drawLine(a.x, a.y, b.x, b.y, highlightPaint);
                // Flash five times.
                if (numberOfFlashes < 5) {
                    if (numberOfFlashes % 2 == 0) {
                        canvas.drawLine(a.x, a.y, b.x, b.y, highlightPaint);
                    } else {
                        canvas.drawLine(a.x, a.y, b.x, b.y, whitePaint);
                    }
                    numberOfFlashes++;
                    postInvalidateDelayed(300);
                }
                break;
        }
        super.onDraw(canvas);
    }

    private void checkIfEndWasReached() {
        if (drawIntervalX > 0 && drawUpToX > drawEnd.x
                || drawIntervalX < 0 && drawUpToX < drawEnd.x) {
            drawUpToX = drawEnd.x;
        }
        if (drawIntervalY > 0 && drawUpToY > drawEnd.y
                || drawIntervalY < 0 && drawUpToY < drawEnd.y) {
            drawUpToY = drawEnd.y;
        }
    }

    // Highlight the wire. We change the paint color andd call the onDraw() method again with
    // a delay.
    public void scheduleHighLight(int color, int delay, WireDrawingMode drawingMode) {
        Handler handler = new Handler(this.getContext().getMainLooper());
        // We need a runnable that accepts argument. Create the class here because it will NOT
        // be used anywhere else, and I don't see the point of making a whole new file for this.
        class drawingRunnable implements Runnable {
            private final Wire wire;
            private final int color;
            private final WireDrawingMode drawingMode;

            public drawingRunnable(Wire wire, int color, WireDrawingMode drawingMode) {
                this.wire = wire;
                this.color = color;
                this.drawingMode = drawingMode;
            }

            // This runnable invalidates the wire and thus onDraw() is called on the wire.
            public void run() {
                // Set drawingmode (which is checked in the onDraw() method)
                wire.drawingMode = drawingMode;
                wire.highlightPaint.setColor(this.color);
                wire.resetCounters();
                wire.invalidate();
            }
        }
        // Post a new runnable to the UI thread with a delay.
        handler.postDelayed(new drawingRunnable(this, color, drawingMode), delay);
    }

    // Reset the counter that are used for drawing the wire.
    private void resetCounters() {
        this.numberOfFlashes = 0;
        // Reset the coordinate counters
        this.drawUpToX = drawStart.x;
        this.drawUpToY = drawStart.y;
    }

    public boolean isTouched(Point point) {
        //Modify the multiplier to change the detection area of the tap.
        float width = this.whitePaint.getStrokeWidth() * 4;
        int minX = Math.min(a.x, b.x);
        int maxX = Math.max(a.x, b.x);
        int minY = Math.min(a.y, b.y);
        int maxY = Math.max(a.y, b.y);
        if (point.x > minX - width && point.y > minY - width &&
                point.x < maxX + width && point.y < maxY + width) {
            return true;
        } else {
            return false;
        }
    }

    // Set the direction in which the wire will be drawn.
    public void setDrawDirection(Point a, Point b) {
        this.drawStart = new Point(a);
        this.drawEnd = new Point(b);
        this.drawUpToX = a.x;
        this.drawUpToY = a.y;
        this.drawIntervalX = b.x == a.x ? 0 : b.x > a.x ? 10 : -10;
        this.drawIntervalY = b.y == a.y ? 0 : b.y > a.y ? 10 : -10;
    }

    public int getLength() {
        return Math.max(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
    }

    public enum WireDrawingMode {
        normal,
        runningHighlight,
        staticHighlight,
        flashingHighlight
    }
}

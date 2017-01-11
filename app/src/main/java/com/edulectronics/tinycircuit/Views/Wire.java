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

import com.edulectronics.tinycircuit.R;


/**
 * Created by bernd on 12/12/2016.
 */

public class Wire extends View {
    public Point a, b;
    Paint paint = new Paint();
    private Point screenSize = new Point();


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
        canvas.drawLine(a.x, a.y, b.x, b.y, paint);
        super.onDraw(canvas);

    }

    public void highLight(Canvas canvas) {

        LineThread thread = new LineThread(canvas);
        thread.run();
    }

    public class LineThread implements Runnable {
        Canvas canvas;

        public LineThread(Canvas canvas) {
            this.canvas = canvas;
        }

        public void run() {
            try {
                Paint paint = new Paint();
                paint.setColor(Color.YELLOW);

                int minX = Math.min(a.x, b.x);
                int maxX = Math.max(a.x, b.x);
                int minY = Math.min(a.y, b.y);
                int maxY = Math.max(a.y, b.y);

                if (minX != maxX) {
                    for (int x = minX; x < maxX; x+=5) {
                        {
                            canvas.drawLine(minX, minY, x, minY, paint);
                     //      Thread.sleep(10);
                        }
                    }
                }

                if (minY != maxY) {
                    for (int y = minY; y < maxY; y+=5) {
                        canvas.drawLine(minX, minY, minX, y, paint);
                       // Thread.sleep(10);
                    }
                }
            }
            catch (Exception ex) {

            }
        }
    }
}

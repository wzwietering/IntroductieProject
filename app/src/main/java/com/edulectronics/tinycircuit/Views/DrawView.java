package com.edulectronics.tinycircuit.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Wilmer on 9-12-2016.
 */

public class DrawView extends View {
    Paint paint = new Paint();
    private int strokeWidth = 8;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(strokeWidth);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(0, 0, 300, 300, paint);
        super.onDraw(canvas);
    }
}

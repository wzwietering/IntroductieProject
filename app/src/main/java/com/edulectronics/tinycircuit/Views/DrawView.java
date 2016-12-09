package com.edulectronics.tinycircuit.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Wilmer on 9-12-2016.
 */

public class DrawView extends View {
    private Canvas canvas;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.canvas = canvas;
    }

    public Canvas getCanvas(){
        return canvas;
    }
}

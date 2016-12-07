package com.edulectronics.tinycircuit.Views;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
    }

    public void freePlayStart(View v) {
        Intent freeplay = new Intent(MenuActivity.this, CircuitActivity.class);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int cellSize = getResources().getInteger(R.integer.cell_size);
        //Width or height divided by cellsize fits the maxiumum amount of cells inside the screen
        CircuitController.getInstance().setProperties(null, size.x / cellSize, size.y / cellSize);
        CircuitController.getInstance().setProperties(null, 20, 20);
        startActivity(freeplay);
    }
    public void exerciseMenuStart(View v){
        Intent exercise = new Intent(MenuActivity.this, ExerciseMenuActivity.class);
        startActivity(exercise);
    }
}

package com.edulectronics.tinycircuit.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        CircuitController.getInstance().setProperties(null, 20, 20);
        startActivity(freeplay);
    }
    public void exerciseMenuStart(View v){
        Intent exercise = new Intent(MenuActivity.this, ExerciseMenuActivity.class);
        startActivity(exercise);
    }
}

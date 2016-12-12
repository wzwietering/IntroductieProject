package com.edulectronics.tinycircuit.Views;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Scenarios.FreePlayScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.Scenario1;
import com.edulectronics.tinycircuit.Models.Scenarios.ScenarioFactory;
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
        ScenarioFactory factory = new ScenarioFactory();
        IScenario scenario = factory.getScenario("freeplay");

        Intent intent = new Intent(this, CircuitActivity.class);
        intent.putExtra("scenario", scenario );
        startActivity(intent);
    }

    public void exerciseMenuStart(View v){
        Intent exercise = new Intent(this, ExerciseMenuActivity.class);
        startActivity(exercise);
    }
}

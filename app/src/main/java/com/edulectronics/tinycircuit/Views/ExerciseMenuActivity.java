package com.edulectronics.tinycircuit.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Models.Factories.ScenarioFactory;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.R;

public class ExerciseMenuActivity extends AppCompatActivity {
    int exercise_amount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);

        //fills the horizontal scrollView
        LinearLayout item;
        for (int n = 1; n <= exercise_amount; n++) {
            item = (LinearLayout) findViewById(R.id.Linearlayout);
            View button = getLayoutInflater().inflate(R.layout.choise_button, null);
            TextView text = (TextView) button.findViewById(R.id.levelnumber);
            text.setText(Integer.toString(n));
            item.addView(button);
        }
    }

    //Identifier for which button was pressed
    public void startExercise(View v) {
        TextView text = (TextView) v.findViewById(R.id.levelnumber);
        String levelnumber = text.getText().toString();

        ImageView imageView = (ImageView) v.findViewById(R.id.exercise_image);
        imageView.setImageResource(R.drawable.exercisebuttonpressed);

        ScenarioFactory factory = new ScenarioFactory();
        IScenario scenario = factory.getScenario(levelnumber);

        Intent intent = new Intent(this, CircuitActivity.class);
        intent.putExtra("scenario", scenario);
        startActivity(intent);
    }
}

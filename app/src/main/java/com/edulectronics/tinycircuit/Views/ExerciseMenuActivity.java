package com.edulectronics.tinycircuit.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulectronics.tinycircuit.R;

public class ExerciseMenuActivity extends AppCompatActivity {
    int n, exercise_amount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);

        //fills the horizontal scrollView
        LinearLayout item;
        for (n = 1; n <= exercise_amount; n++) {
            item = (LinearLayout) findViewById(R.id.Linearlayout);
            View button = getLayoutInflater().inflate(R.layout.choise_button, null);
            TextView text = (TextView) button.findViewById(R.id.levelnumber);
            text.setText(Integer.toString(n));
            item.addView(button);
        }
    }

    //Identifier for which button was pressed
     public void startExercise(View v){
        TextView text = (TextView) v.findViewById(R.id.levelnumber);
        n = Integer.parseInt(text.getText().toString());

        switch (n) {
        }
    }
}

package com.edulectronics.tinycircuit.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Controllers.MessageController;
import com.edulectronics.tinycircuit.DataStorage.VariableHandler;
import com.edulectronics.tinycircuit.Models.MessageArgs;
import com.edulectronics.tinycircuit.Models.MessageTypes;
import com.edulectronics.tinycircuit.R;

public class ExerciseMenuActivity extends AppCompatActivity {
    int exercise_amount = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        makeMenu();

    }

    private void makeMenu() {
        setContentView(R.layout.activity_exercise_menu);

        //fills the horizontal scrollView
        LinearLayout item;
        for (int n = 1; n <= exercise_amount; n++) {
            item = (LinearLayout) findViewById(R.id.Linearlayout);
            View button = getLayoutInflater().inflate(R.layout.choise_button, null);
            TextView text = (TextView) button.findViewById(R.id.levelnumber);
            ImageView image = (ImageView) button.findViewById(R.id.exercise_image);
            if (levelAvailable(n)){
                image.setEnabled(true);
            } else {
                image.setEnabled(false);
            }
            text.setText(Integer.toString(n));
            item.addView(button);
        }
    }

    //Recreate the menu every time the activity is started to apply language changes
    @Override
    protected void onStart(){
        super.onStart();
        makeMenu();
    }

    //Identifier for which button was pressed
    public void startExercise(View v) {

        TextView text = (TextView) v.findViewById(R.id.levelnumber);
        String levelNumber = text.getText().toString();
        if (levelAvailable(Integer.parseInt(levelNumber))){
            Intent intent = new Intent(this, CircuitActivity.class);
            intent.putExtra("scenario", levelNumber);
            startActivity(intent);
        } else {
            MessageController messageController = new MessageController(getFragmentManager());
            messageController.displayMessage(new MessageArgs(getResources().getString(R.string.scenario_locked_explanation), MessageTypes.ScenarioLocked));
        }
    }

    public boolean levelAvailable(int levelNumber){
        VariableHandler variableHandler = new VariableHandler(getApplicationContext());
        int currentScenario = variableHandler.loadProgress();

        return currentScenario >= levelNumber;
    }
}

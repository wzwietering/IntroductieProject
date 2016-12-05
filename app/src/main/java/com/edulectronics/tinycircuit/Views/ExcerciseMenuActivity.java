package com.edulectronics.tinycircuit.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulectronics.tinycircuit.R;

public class excerciseMenuActivity extends AppCompatActivity {
    int n, excercise_amount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_menu);

        //fills the horizontal scrollView
        LinearLayout item;
        for (n = 1; n <= excercise_amount; n++) {
            item = (LinearLayout) findViewById(R.id.Linearlayout);
            View button = getLayoutInflater().inflate(R.layout.choise_button, null);
            TextView text = (TextView) button.findViewById(R.id.textview1);
            text.setText(Integer.toString(n));
            item.addView(button);
        }
    }

    //Identifier for which button was pressed
     public void startExcercise(View v){
        TextView text = (TextView) v.findViewById(R.id.textview1);
        n = Integer.parseInt(text.getText().toString());

        switch (n) {
        }
    }
}

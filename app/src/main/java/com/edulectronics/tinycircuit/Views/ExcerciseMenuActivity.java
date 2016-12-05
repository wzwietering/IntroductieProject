package com.edulectronics.tinycircuit.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulectronics.tinycircuit.R;

public class excerciseMenuActivity extends AppCompatActivity {
    int n, excercise_amount=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_menu);

        LinearLayout item;
        for (n=1; n <= excercise_amount; n++) {     //fills the horizontal scrollView
            item = (LinearLayout) findViewById(R.id.Linearlayout);
            View Button = getLayoutInflater().inflate(R.layout.choise_button, null);
            TextView text = (TextView) Button.findViewById(R.id.textview1);
            text.setText("" + n);
            item.addView(Button);
        }
    }

     public void StartExcercise(View v){   //Identifier for which button was pressed
        TextView text = (TextView)  v.findViewById(R.id.textview1);
        n = Integer.parseInt(text.getText().toString());

        switch (n) {
        }
    }
}

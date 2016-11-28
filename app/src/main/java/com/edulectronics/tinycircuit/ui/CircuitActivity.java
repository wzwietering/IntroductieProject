package com.edulectronics.tinycircuit.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.adapters.CircuitAdapter;

public class CircuitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        GridView circuit = (GridView) findViewById(R.id.circuit);

        /*Prevents scrolling, stuff can still be rendered outside screen*/
        circuit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }
        });
        Intent intent = getIntent();
        CircuitController controller = (CircuitController) intent.getSerializableExtra("Controller");
        circuit.setNumColumns(controller.circuit.width);
        circuit.setAdapter(new CircuitAdapter(this, controller));
    }
}

package com.edulectronics.tinycircuit.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.adapters.MainScreenAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView circuit = (GridView) findViewById(R.id.circuit);
        circuit.setAdapter(new MainScreenAdapter(this));
    }
}

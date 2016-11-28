package com.edulectronics.tinycircuit.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.adapters.MainScreenAdapter;

import java.io.Serializable;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements Serializable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView circuit = (GridView) findViewById(R.id.circuit);
        HashSet s = new HashSet();
        s.add(new Lightbulb());
        circuit.setAdapter(new MainScreenAdapter(this, new CircuitController(s, 5, 5)));
}

package com.edulectronics.tinycircuit.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;

import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Adapters.MainMenuAdapter;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        ListView listView = (ListView) findViewById(R.id.buttonArea);
        String[] items = {getResources().getString(R.string.exercise), getResources().getString(R.string.freeplay)};
        listView.setAdapter(new MainMenuAdapter(this, items));
    }
}

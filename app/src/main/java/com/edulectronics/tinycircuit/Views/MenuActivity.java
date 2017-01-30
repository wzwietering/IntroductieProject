package com.edulectronics.tinycircuit.Views;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.edulectronics.tinycircuit.DataStorage.VariableHandler;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Adapters.MainMenuAdapter;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
    }

    //Recreate the menu every time the activity is started to apply language changes
    @Override
    protected void onStart(){
        super.onStart();
        setLanguage();
        createMenu();
    }

    public void openSettings(View view){
        this.startActivity(new Intent(this, SettingsActivity.class));
    }

    public void aboutActivity(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        this.startActivity(intent);
    }

    private void createMenu() {
        ListView listView = (ListView) findViewById(R.id.buttonArea);
        String[] items = {getResources().getString(R.string.exercise), getResources().getString(R.string.freeplay)};
        listView.setAdapter(new MainMenuAdapter(this, items));
    }

    //Get the language from the save file
    private void setLanguage(){
        VariableHandler variableHandler = new VariableHandler(getApplicationContext());
        Locale locale = new Locale(variableHandler.getLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}

package com.edulectronics.tinycircuit.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.edulectronics.tinycircuit.DataStorage.VariableHandler;
import com.edulectronics.tinycircuit.R;

import java.util.Locale;

/**
 * Created by Wilmer on 30-1-2017.
 */

public class SettingsActivity extends AppCompatActivity{
    private static String language = "nl";
    private VariableHandler variableHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        variableHandler = new VariableHandler(getApplicationContext());
    }

    //Start the activity, set the flag and the app language right
    @Override
    protected void onStart(){
        super.onStart();
        language = variableHandler.getLanguage();
        setFlag();
        setVibration();
    }

    //Save the settings when the users leaves the activity
    @Override
    protected void onPause(){
        save();
        super.onPause();
    }

    public void changeLanguage(View view) {
        switchLanguage();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        save();
        finish();
        startActivity(getIntent());
    }

    private void switchLanguage() {
        if (language.equals("nl")) {
            language = "en";
        } else {
            language = "nl";
        }
        setFlag();
    }

    private void setFlag() {
        ImageView flag = (ImageView) findViewById(R.id.flag);
        if (language.equals("nl")) {
            flag.setImageResource(R.drawable.dutchflag);
        } else {
            flag.setImageResource(R.drawable.englishflag);
        }
    }

    private void setVibration(){
        //Specify the package because we also have a class named Switch
        android.widget.Switch vibrationSwitch = (Switch) findViewById(R.id.vibration_switch);
        vibrationSwitch.setChecked(variableHandler.getVibration());
    }

    private void save(){
        android.widget.Switch vibrationSwitch = (Switch) findViewById(R.id.vibration_switch);
        variableHandler.saveSettings(language, vibrationSwitch.isChecked());
    }

    //Beautiful dialog to ask whether the user is sure about his or her actions
    public void areYouSureDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.reset_warning));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                variableHandler.resetProgress();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

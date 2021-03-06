package com.edulectronics.tinycircuit.DataStorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class VariableHandler {
    //Shared preferences is the Android interface to store variables
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private final String scenario = "scenario";
    private final String vibration = "vibration";
    private final String language = "language";

    //Setup for save file
    public VariableHandler(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    //Save the scenario id
    public void saveProgress(int scenarioID) {
        if (scenarioID >= loadProgress()) {
            editor.putInt(scenario, scenarioID);
            editor.apply();
        }
    }

    //Load the current scenario id
    public int loadProgress() {
        //The last completed scenario is saved, so we add one because that is the next available level
        return sharedPreferences.getInt(scenario, 0) + 1;
    }

    //Resets the progress
    public void resetProgress(){
        editor.putInt(scenario, 0);
        editor.apply();
    }

    //Save the settings of the app
    public void saveSettings(String languageValue, boolean vibrationValue){
        editor.putString(language, languageValue);
        editor.putBoolean(vibration, vibrationValue);
        editor.apply();
    }

    //Get the saved vibration preference
    public boolean getVibration(){
        return sharedPreferences.getBoolean(vibration, true);
    }

    //Get the saved language
    public String getLanguage(){
        return sharedPreferences.getString(language, "nl");
    }
}

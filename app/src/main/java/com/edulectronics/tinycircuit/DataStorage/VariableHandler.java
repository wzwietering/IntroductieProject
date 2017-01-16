package com.edulectronics.tinycircuit.DataStorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Wilmer on 11-1-2017.
 */

public class VariableHandler {
    //Shared preferences is the Android interface to store variables
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //Setup for save file
    public VariableHandler(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    //Save the scenario id
    public void saveProgress(int scenarioID) {
        if (scenarioID >= loadProgress()) {
            editor.putInt("scenario", scenarioID);
            editor.apply();
        }
    }

    //Load the current scenario id
    public int loadProgress() {
        //The last completed scenario is saved, so we add one because that is the next available level
        return sharedPreferences.getInt("scenario", 0) + 1;
    }
}

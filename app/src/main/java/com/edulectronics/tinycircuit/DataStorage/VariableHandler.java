package com.edulectronics.tinycircuit.DataStorage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.edulectronics.tinycircuit.Views.CircuitActivity;

/**
 * Created by Wilmer on 11-1-2017.
 */

public class VariableHandler {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //Setup for save file
    public VariableHandler(CircuitActivity circuitActivity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(circuitActivity);
        editor = sharedPreferences.edit();
    }

    //Save the scenario id
    public void saveProgress(int scenarioID) {
        editor.putInt("scenario", scenarioID);
        editor.apply();
    }

    //Load the current scenario id
    public int loadProgress() {
        return sharedPreferences.getInt("scenario", 0);
    }
}

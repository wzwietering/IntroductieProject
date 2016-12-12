package com.edulectronics.tinycircuit.Models.Scenarios;

import com.edulectronics.tinycircuit.Models.Circuit;
import com.edulectronics.tinycircuit.Models.Components.Component;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Maaike on 12-12-2016.
 * Quiz scenario.
 * Has a circuit (which may not be edited!) and quiz question and answers.
 * TODO: Implement quiz answers and the answering of answer.
 */

public class QuizScenario implements IScenario {
    @Override
    public Set<Component> getAvailableComponents() {
        return null;
    }

    @Override
    public boolean isCompleted(Circuit circuit) {
        return false;
    }

    @Override
    public void showPrompt() {
        // Get text from DB
    }

    @Override
    public ArrayList<Component> loadComponents() {
        // Get Components from DB
        return null;
    }
}

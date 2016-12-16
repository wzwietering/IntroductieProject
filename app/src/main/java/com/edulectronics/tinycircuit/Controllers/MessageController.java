package com.edulectronics.tinycircuit.Controllers;

import android.app.FragmentManager;
import android.os.Bundle;

import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.FreePlayScenario;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Message;

/**
 * Created by Maaike on 12-12-2016.
 * Handles the display of all messages in the app.
 */

public class MessageController {

    private FragmentManager fragmentManager;

    public MessageController(FragmentManager fm) {
        this.fragmentManager = fm;
    }

    public void displayScenarioExplanation(IScenario scenario) {
        if (scenario.getClass() != FreePlayScenario.class) {
            Bundle args = new Bundle();
            args.putInt("message", scenario.getPrompt());
            args.putInt("title", R.string.scenario_explanation_title);
            showMessage(args);
        }
    }

    public void displayScenarioCompleteMessage(IScenario scenario) {
        if (scenario.getClass() != FreePlayScenario.class) {
            Bundle args = new Bundle();
            args.putInt("message", R.string.scenario_complete);
            args.putInt("title", R.string.scenario_complete);
            args.putBoolean("end_activity", true);
            showMessage(args);
        }
    }

    private void showMessage(Bundle args) {
        Message dialogFragment = new Message();
        dialogFragment.setArguments(args);
        dialogFragment.show(fragmentManager, "");
    }
}

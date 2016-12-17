package com.edulectronics.tinycircuit.Controllers;

import android.app.FragmentManager;
import android.os.Bundle;

import com.edulectronics.tinycircuit.Models.MessageType;
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

    public void displayMessage(int message, MessageType messageType) {
            Bundle args = new Bundle();
            args.putInt("message", message);
            args.putInt("title", getTitle(messageType));
            args.putBoolean("end_activity", messageType == MessageType.ScenarioComplete);
            showMessage(args);
    }

    private int getTitle(MessageType messageType) {
        switch (messageType){
            case Explanation:
                return R.string.scenario_explanation_title;
            case ScenarioComplete:
                return R.string.scenario_complete;
            default:
                return 0;
        }
    }

    private void showMessage(Bundle args) {
        Message dialogFragment = new Message();
        dialogFragment.setArguments(args);
        dialogFragment.show(fragmentManager, "");
    }
}

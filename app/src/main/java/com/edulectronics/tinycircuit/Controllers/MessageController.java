package com.edulectronics.tinycircuit.Controllers;

import android.app.FragmentManager;
import android.os.Bundle;

import com.edulectronics.tinycircuit.Models.MessageArgs;
import com.edulectronics.tinycircuit.Models.MessageTypes;
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

    public void displayMessage(MessageArgs messageArgs) {
        Bundle args = new Bundle();

        // No title was given. Set a default one.
        if (messageArgs.title == 0) {
            messageArgs.title = getTitle(messageArgs.type);
        }

        args.putSerializable("messageargs", messageArgs);
        showMessage(args);
    }

    private void showMessage(Bundle args) {
        Message message = new Message();
        message.setArguments(args);
        message.show(fragmentManager, "");
    }

    private int getTitle(MessageTypes type) {
        switch (type) {
            case Explanation:
                return R.string.scenario_explanation_title;
            case ScenarioComplete:
                return R.string.scenario_complete;
            case ScenarioLocked:
                return R.string.scenario_locked;
            case Mistake:
                return R.string.scenario_not_complete;
            default:
                return 0;
        }
    }
}

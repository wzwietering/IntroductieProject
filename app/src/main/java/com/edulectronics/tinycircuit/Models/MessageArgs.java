package com.edulectronics.tinycircuit.Models;

import java.io.Serializable;

/**
 * Created by Maaike on 17-12-2016.
 */

public class MessageArgs implements Serializable{
    public MessageTypes type;
    public int title;
    public int message;

    public boolean endActivity;
    public boolean goToNextScenario;

    public MessageArgs(int message, MessageTypes type) {
        this.message = message;
        this.type = type;
    }

    public MessageArgs(int message, MessageTypes type, boolean goToNextScenario) {
        this.message = message;
        this.type = type;
        this.goToNextScenario = goToNextScenario;
    }

}
package com.edulectronics.tinycircuit.Models;

import java.io.Serializable;

public class MessageArgs implements Serializable{
    public MessageTypes type;
    public int title;
    public String message;

    public boolean endActivity;
    public boolean goToNextScenario;

    public MessageArgs(String message, MessageTypes type) {
        this.message = message;
        this.type = type;
    }

    public MessageArgs(String message, MessageTypes type, boolean goToNextScenario) {
        this.message = message;
        this.type = type;
        this.goToNextScenario = goToNextScenario;
    }
}
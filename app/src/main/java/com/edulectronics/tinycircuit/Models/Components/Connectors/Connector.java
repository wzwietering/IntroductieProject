package com.edulectronics.tinycircuit.Models.Components.Connectors;

/**
 * Created by Maaike on 28-11-2016.
 */
public class Connector {
    private static Connector ourInstance = new Connector();

    public static Connector getInstance() {
        return ourInstance;
    }

    private Connector() {
    }

    public void connect(Input input, Output output) {
        if(input != null && output != null) {
            input.connect(output);
            output.connect(input);
        }
    }

    public void disconnect(Input input, Output output) {
        if(input != null && output != null) {
            input.disconnect(output);
            output.disconnect(input);
        }
    }
}

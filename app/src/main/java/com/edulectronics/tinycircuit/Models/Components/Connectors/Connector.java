package com.edulectronics.tinycircuit.Models.Components.Connectors;

/**
 * Created by Maaike on 28-11-2016.
 */
public class Connector {

    public static void connect(Input input, Output output) {
        if(input != null && output != null) {
            input.connect(output);
            output.connect(input);
        }
    }

    public static void disconnect(Input input, Output output) {
        if(input != null && output != null) {
            input.disconnect(output);
            output.disconnect(input);
        }
    }
}

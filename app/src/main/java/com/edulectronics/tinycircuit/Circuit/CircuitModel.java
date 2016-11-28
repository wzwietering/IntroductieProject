package com.edulectronics.tinycircuit.Circuit;

import java.util.List;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitModel {

    protected List<Component> components;

    public CircuitModel(){

    }

    /*Checks if the Circuit has a connection from the first powersource*/
    public boolean checkConnection(){
        for (Component c : components){
            if (c.getClass() == Powersource.class){
                if (c.hasOutputConnection()){
                    return true;
                }
            }
        }
        return false;
    }
}

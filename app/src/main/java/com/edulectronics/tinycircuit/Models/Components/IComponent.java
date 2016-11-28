package com.edulectronics.tinycircuit.Models.Components;

/**
 * Created by Maaike on 28-11-2016.
 */

/*
 * The IComponent interface. All components have to implement this interface.
 */
public interface IComponent {
    public boolean hasOutputConnection();

    public void handleInputChange();

    public void setNewOutputValues();
}

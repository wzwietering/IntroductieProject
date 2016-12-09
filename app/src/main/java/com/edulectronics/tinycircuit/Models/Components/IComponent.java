package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;

/**
 * Created by Maaike on 28-11-2016.
 */

/*
 * The IComponent interface. All components have to implement this interface.
 */
public interface IComponent {
    public boolean hasOutputConnection(ConnectionPoint connectionPoint);

    public void handleInputChange();

    public void setNewOutputValues();

    public int getImage();
}

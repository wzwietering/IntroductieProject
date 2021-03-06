package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;

// The IComponent interface. All components have to implement this interface.
public interface IComponent {
    // Check to see whether this component had an outgoing connection.
    public boolean hasOutputConnection(ConnectionPoint connectionPoint);

    // Get the icon for this component
    public int getImage();

    // User clicked on a component (while not in wire mode)
    public boolean handleClick();
}

package com.edulectronics.tinycircuit.Views.Draggables.Interfaces;

public interface IDragListener {
    /**
     * A drag has begun
     *
     * @param source An object representing where the drag originated
     * @param info   The data associated with the object that is being dragged
     */
    public void onDragStart(IDragSource source, Object info);

    // The drag has ended
    public void onDragEnd();
}

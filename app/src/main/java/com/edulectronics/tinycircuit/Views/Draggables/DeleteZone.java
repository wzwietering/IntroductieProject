package com.edulectronics.tinycircuit.Views.Draggables;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Views.CircuitActivity;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.DragSource;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.DropTarget;


public class DeleteZone extends ImageView
        implements DropTarget
{
    private DragController mDragController;

    // Constructors
    public DeleteZone (Context context) {
        super  (context);
    }
    public DeleteZone (Context context, AttributeSet attrs) {
        super (context, attrs);
    }
    public DeleteZone (Context context, AttributeSet attrs, int style)
    {
        super (context, attrs, style);
    }

    public boolean acceptDrop() {
        return true;
    }

    public DragController getDragController ()
    {
        return mDragController;
    }
    public void setDragController (DragController newValue)
    {
        mDragController = newValue;
    }

    public void onDragEnter(DragSource source, int x, int y, int xOffset, int yOffset,
                            DragView dragView, Object dragInfo)
    {  // Set the image level so the image is highlighted;
        setImageLevel (2);
    }

    public void onDragExit(DragSource source, int x, int y, int xOffset, int yOffset,
                           DragView dragView, Object dragInfo)
    {
        setImageLevel (1);
    }

    public boolean acceptDrop(DragSource source, int x, int y, int xOffset, int yOffset,
                              DragView dragView, Object dragInfo)
    {
        return true;
    }

    public void onDrop(DragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
        Component component;
        if(((GridCell)source).mCellNumber  == -1) {
             CircuitController.getInstance().newComponent = null;
        } else {
            // Source is an existing GridCell. Get its component from the controller.
            CircuitController.getInstance().removeComponent(((GridCell) source).mCellNumber);
        }
    }

    public void onDragOver(DragSource source, int x, int y, int xOffset, int yOffset,
                           DragView dragView, Object dragInfo)
    { // Not implemented
    }

    public Rect estimateDropLocation(DragSource source, int x, int y, int xOffset, int yOffset,
                                     DragView dragView, Object dragInfo, Rect recycle)
    {
        return null;
    }
}

package com.edulectronics.tinycircuit.Views.Draggables;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDragSource;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDropTarget;


public class DeleteZone extends ImageView implements IDropTarget {
    private DragController mDragController;
    private CircuitController circuitController;

    // Constructors
    public DeleteZone(Context context) {
        super(context);
    }

    public DeleteZone(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DeleteZone(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
    }

    public void setCircuitController(CircuitController circuitController) {
        this.circuitController = circuitController;
    }

    public DragController getDragController () {
        return mDragController;
    }
    public void setDragController (DragController newValue) {
        mDragController = newValue;
    }

    public boolean acceptDrop() {
        return true;
    }

    public void onDragEnter(IDragSource source, int x, int y, int xOffset, int yOffset,
                            DragView dragView, Object dragInfo) {
        // Set the image level so the image is highlighted;
        setImageLevel (2);
    }

    public void onDragExit(IDragSource source, int x, int y, int xOffset, int yOffset,
                           DragView dragView, Object dragInfo) {
        setImageLevel (1);
    }

    public boolean acceptDrop(IDragSource source, int x, int y, int xOffset, int yOffset,
                              DragView dragView, Object dragInfo) {
        return true;
    }

    public void onDrop(IDragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
        Component component;
        if(((GridCell)source).mCellNumber == -1) {
             circuitController.newComponent = null;
        } else {
            // Source is an existing GridCell. Get its component from the controller.
            circuitController.getComponent(((GridCell) source).mCellNumber).removeAllConnections();
            circuitController.removeComponent(((GridCell) source).mCellNumber);
        }
    }

    public void onDragOver(IDragSource source, int x, int y, int xOffset, int yOffset,
                           DragView dragView, Object dragInfo) {
        // Not implemented
    }

    public Rect estimateDropLocation(IDragSource source, int x, int y, int xOffset, int yOffset,
                                     DragView dragView, Object dragInfo, Rect recycle) {
        return null;
    }
}

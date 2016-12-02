package com.edulectronics.tinycircuit.ui.Draggables;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.Draggables.Interfaces.DragSource;
import com.edulectronics.tinycircuit.ui.Draggables.Interfaces.DropTarget;

/**
 * This subclass of ImageView is used to display an image on an GridView.
 * a GridCell knows which cell on the grid it is showing and which grid it is attached to
 * Cell numbers are from 0 to NumCells-1.
 * It also knows if it is empty, or if it contains a component.
 *
 * <p> GridCell are places where components can be dragged from and dropped onto.
 * Therefore, this class implements both the DragSource and DropTarget interfaces.
 * 
 */

public class GridCell extends ImageView
    implements DragSource, DropTarget
{
    public boolean isEmpty = true;

    public int mCellNumber = -1;
    public GridView mGrid;
    public Component component;

    private CircuitController circuitController;

    public GridCell (Context context, CircuitController circuitController) {
        super (context);
        this.circuitController = circuitController;
    }

    public void setComponent(Component component) {
        this.isEmpty = false;
        int bg = isEmpty ? R.color.cell_empty : R.color.cell_filled;
        setBackgroundResource (bg);

        this.component = component;
        if(this.mCellNumber > -1) {
            circuitController.addComponent(component, this.mCellNumber);
        }
        this.setImageResource(component.getImage());
    }

    public void removeComponent() {
        isEmpty = true;
        this.component = null;
        int bg = isEmpty ? R.color.cell_empty  : R.color.cell_filled ;
        setBackgroundResource (bg);

        if(this.mCellNumber > -1) {
            circuitController.removeComponent(this.mCellNumber);
        }
        setImageDrawable (null);
    }
    
    public boolean allowDrag () {
        // There is something to drag if the cell is not empty.
        return !this.isEmpty;
    }

    public void setDragController (DragController dragger) {
        // Do nothing. We do not need to know the controller object.
    }

    public void onDropCompleted (View target, boolean success)
    {
        if (success) {
            removeComponent();

            View parent = (View)this.getParent();
            if(((View) this.getParent()).getId() == R.id.component_source_frame)
            {
                parent.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * Handle an object being dropped on the DropTarget.
     * This is the where the drawable of the dragged view gets copied into the ImageCell.
     *
     * @param source DragSource where the drag started
     * @param x X coordinate of the drop location
     * @param y Y coordinate of the drop location
     * @param xOffset Horizontal offset with the object being dragged where the original
     *          touch happened
     * @param yOffset Vertical offset with the object being dragged where the original
     *          touch happened
     * @param dragView The DragView that's being dragged around on screen.
     * @param dragInfo Data associated with the object being dragged
     */
    public void onDrop(DragSource source, int x, int y, int xOffset, int yOffset,
            DragView dragView, Object dragInfo)
    {
        this.component = ((GridCell)source).component;
        this.setComponent(component);
    }

    /**
     * React to a dragged object entering the area of this DropSpot.
     * Provide the user with some visual feedback.
     */
    public void onDragEnter(DragSource source, int x, int y, int xOffset, int yOffset,
            DragView dragView, Object dragInfo)
    {
        int bg = isEmpty ? R.color.cell_empty_hover : R.color.cell_filled_hover;
        setBackgroundResource (bg);
    }

    /**
     * React to something being dragged over the drop target.
     */
    public void onDragOver(DragSource source, int x, int y, int xOffset, int yOffset,
            DragView dragView, Object dragInfo)
    {
    }

    /**
     * React to a drag
     */
    public void onDragExit(DragSource source, int x, int y, int xOffset, int yOffset,
            DragView dragView, Object dragInfo)
    {
        int bg = isEmpty ? R.color.cell_empty : R.color.cell_filled;
        setBackgroundResource (bg);
    }


    public boolean acceptDrop() {
        return isEmpty ;
    }

    public boolean isEmpty ()
    {
        return isEmpty;
    }

    public boolean performClick ()
    {
        if (!isEmpty) return super.performClick ();
        return false;
    }

    public boolean performLongClick ()
    {
        if (!isEmpty) return super.performLongClick ();
        return false;
    }

}
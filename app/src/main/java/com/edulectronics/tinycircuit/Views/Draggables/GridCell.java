package com.edulectronics.tinycircuit.Views.Draggables;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDragSource;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDropTarget;

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

public class GridCell extends ImageView implements IDragSource, IDropTarget {
    public boolean isEmpty = true;
    public int mCellNumber = -1;
    public GridView mGrid;

    // Constructors
    public GridCell(Context context) {
        super(context);
        setBackgroundResource(R.color.cell);
    }

    public GridCell (Context context, AttributeSet attrs) {
        super (context, attrs);
        setBackgroundResource(R.color.cell);
    }

    public GridCell (Context context, AttributeSet attrs, int style) {
        super (context, attrs, style);
        setBackgroundResource(R.color.cell);
    }

    public void setComponent(Component component) {
        this.isEmpty = false;
        setBackgroundResource(R.color.cell);

        if(this.mCellNumber > -1) {
            CircuitController.getInstance().addComponent(component, this.mCellNumber);
        } else {
            CircuitController.getInstance().newComponent = component;
        }
        this.setImageResource(component.getImage());
    }

    public void removeComponent() {
        isEmpty = true;
        setBackgroundResource(R.color.cell);

        if(this.mCellNumber > -1) {
            CircuitController.getInstance().removeComponent(this.mCellNumber);
        } else {
            CircuitController.getInstance().newComponent = null;
        }
        setImageDrawable(null);
    }

    public Component getComponent(){
        return CircuitController.getInstance().circuit.getComponent(this.mCellNumber);
    }
    
    public boolean allowDrag() {
        // There is something to drag if the cell is not empty.
        return !this.isEmpty;
    }

    public void onDropCompleted (View target, boolean success)
    {
        if (success) {
            removeComponent();
            View parent = (View)this.getParent();
            if(parent.getId() == R.id.component_source_frame)
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
    public void onDrop(IDragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
        Component component;
        if(((GridCell)source).mCellNumber  == -1) {
            // The source is a GridCell with index -1, therefore a new component
            component = CircuitController.getInstance().newComponent;
        } else {
            // Source is an existing GridCell. Get its component from the controller.
            component = CircuitController.getInstance().getComponent(((GridCell)source).mCellNumber);
        }
        this.setComponent(component);
    }

    /**
     * React to a dragged object entering the area of this DropSpot.
     * Provide the user with some visual feedback.
     */
    public void onDragEnter(IDragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
        int bg = isEmpty ? R.color.cell_empty_hover : R.color.cell_filled_hover;
        setBackgroundResource(bg);
    }

    /**
     * React to something being dragged over the drop target.
     */
    public void onDragOver(IDragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {}

    /**
     * React to a drag
     */
    public void onDragExit(IDragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
        setBackgroundResource(R.color.cell);
    }

    public boolean acceptDrop() {
        return isEmpty ;
    }

    public boolean isEmpty()
    {
        return isEmpty;
    }

    public boolean performClick()
    {
        if (!isEmpty) return super.performClick ();
        return false;
    }

    public boolean performLongClick()
    {
        if (!isEmpty) return super.performLongClick ();
        return false;
    }
}

package com.edulectronics.tinycircuit.Views.Draggables.Interfaces;

/**
 * Created by Maaike on 30-11-2016.
 */

public interface DragListener {
        /**
         * A drag has begun
         * @param source An object representing where the drag originated
         * @param info The data associated with the object that is being dragged
         */
        public void onDragStart(DragSource source, Object info);

        /**
         * The drag has ended
         */
        public void onDragEnd();
}

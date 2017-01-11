/*
 * This is a modified version of a class from the Android
 * Open Source Project. The original copyright and license information follows.
 * 
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.edulectronics.tinycircuit.Views.Draggables;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.edulectronics.tinycircuit.Controllers.WireController;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDragListener;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDragSource;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDropTarget;

import java.util.ArrayList;

/**
 * This class handles all touch events associated with a user
 * dragging a view around inside a ViewGroup.
 * When a drag starts, it creates a special view (a DragView) that moves around the screen
 * until the user ends the drag. As feedback to the user, this object causes the device to
 * vibrate as the drag begins. It interacts with other objects through methods defined
 * in the DropTarget and DragSource interfaces.
 * 
 * <p> It also supports the DragListener interface for objects that want to be
 * notified when objects are dragged and dropped.
 *
 */

public class DragController {

    private Context mContext;

    // temporaries to avoid gc thrash
    private Rect mRectTemp = new Rect();
    private final int[] mCoordinatesTemp = new int[2];

    /** Whether or not we're dragging. */
    private boolean mDragging;

    /** X coordinate of the down event. */
    private float mMotionDownX;

    /** Y coordinate of the down event. */
    private float mMotionDownY;

    /** Info about the screen for clamping. */
    private DisplayMetrics mDisplayMetrics = new DisplayMetrics();

    /** Original view that is being dragged.  */
    private View mOriginator;

    /** X offset from the upper-left corner of the cell to where we touched.  */
    private float mTouchOffsetX;

    /** Y offset from the upper-left corner of the cell to where we touched.  */
    private float mTouchOffsetY;

    /** Where the drag originated */
    private IDragSource mDragSource;

    /** The data associated with the object being dragged */
    private Object mDragInfo;

    /** The view that moves around while you drag.  */
    private DragView mDragView;

    /** Who can receive drop events */
    private ArrayList<IDropTarget> mDropTargets = new ArrayList<IDropTarget>();

    private IDragListener mListener;

    /** The window token used as the parent for the DragView. */
    private IBinder mWindowToken;

    private View mMoveTarget;

    private IDropTarget mLastDropTarget;

    private InputMethodManager mInputMethodManager;
    private WireController wireController;

    /**
     * Interface to receive notifications when a drag starts or stops
     */

    
    /**
     * Used to create a new DragLayer from XML.
     *
     * @param context The application's context.
     */
    public DragController(Context context) {
        mContext = context;
    }

    /**
     * Starts a drag. 
     * It creates a bitmap of the view being dragged. That bitmap is what you see moving.
     * The actual view can be repositioned if that is what the onDrop handle chooses to do.
     * 
     * @param v The view that is being dragged
     * @param source An object representing where the drag originated
     * @param dragInfo The data associated with the object that is being dragged
     */
    public void startDrag(View v, IDragSource source, Object dragInfo) {
        // Start dragging, but only if the source has something to drag.
        boolean doDrag = source.allowDrag();
        if (!doDrag) return;

        // Vibrate for 500 milliseconds
        Vibrator vibrator = (Vibrator) this.mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

        mOriginator = v;

        Bitmap b = getViewBitmap(v);

        if (b == null) return; // out of memory?

        int[] loc = mCoordinatesTemp;
        v.getLocationOnScreen(loc);
        int screenX = loc[0];
        int screenY = loc[1];

        startDrag(b, screenX, screenY, 0, 0, b.getWidth(), b.getHeight(), source, dragInfo);
        b.recycle();
        v.setVisibility(View.GONE);

        ((Activity)mContext).findViewById(R.id.delete_zone_view).setVisibility(View.VISIBLE);
    }

    /**
     * Starts a drag.
     * 
     * @param b The bitmap to display as the drag image.  It will be re-scaled to the
     *          enlarged size.
     * @param screenX The x position on screen of the left-top of the bitmap.
     * @param screenY The y position on screen of the left-top of the bitmap.
     * @param textureLeft The left edge of the region inside b to use.
     * @param textureTop The top edge of the region inside b to use.
     * @param textureWidth The width of the region inside b to use.
     * @param textureHeight The height of the region inside b to use.
     * @param source An object representing where the drag originated
     * @param dragInfo The data associated with the object that is being dragged
     */
    public void startDrag(Bitmap b, int screenX, int screenY,
            int textureLeft, int textureTop, int textureWidth, int textureHeight,
                          IDragSource source, Object dragInfo) {
        // Hide soft keyboard, if visible
        if (mInputMethodManager == null) {
            mInputMethodManager =
                    (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        mInputMethodManager.hideSoftInputFromWindow(mWindowToken, 0);

        if (mListener != null) {
            mListener.onDragStart(source, dragInfo);
        }

        int registrationX = ((int)mMotionDownX) - screenX;
        int registrationY = ((int)mMotionDownY) - screenY;

        mTouchOffsetX = mMotionDownX - screenX;
        mTouchOffsetY = mMotionDownY - screenY;

        mDragging = true;
        mDragSource = source;
        mDragInfo = dragInfo;

        // mVibrator.vibrate(VIBRATE_DURATION);

        DragView dragView = mDragView = new DragView(mContext, b, registrationX, registrationY,
                textureLeft, textureTop, textureWidth, textureHeight);
        dragView.show(mWindowToken, (int)mMotionDownX, (int)mMotionDownY);
    }


    /**
     * Draw the view into a bitmap.
     */
    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    /**
     * Call this from a drag source view like this:
     *
     * <pre>
     *  @Override
     *  public boolean dispatchKeyEvent(KeyEvent event) {
     *      return mDragController.dispatchKeyEvent(this, event)
     *              || super.dispatchKeyEvent(event);
     * </pre>
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mDragging;
    }

    /**
     * Stop dragging without dropping.
     */
    public void cancelDrag() {
        endDrag();
    }

    private void endDrag() {
        if (mDragging) {
            mDragging = false;
            if (mOriginator != null) {
                mOriginator.setVisibility(View.VISIBLE);
            }
            if (mListener != null) {
                mListener.onDragEnd();
            }
            if (mDragView != null) {
                mDragView.remove();
                mDragView = null;
            }
            DeleteZone deleteZone = (DeleteZone)((Activity)mContext).findViewById(R.id.delete_zone_view);
            if (deleteZone != null) {
                deleteZone.setVisibility(View.INVISIBLE);
            }
            wireController.redrawWires();
        }
    }

    /**
     * Call this from a drag source view.
     */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            recordScreenSize();
        }

        final int screenX = clamp((int)ev.getRawX(), 0, mDisplayMetrics.widthPixels);
        final int screenY = clamp((int)ev.getRawY(), 0, mDisplayMetrics.heightPixels);

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_DOWN:
                // Remember location of down touch
                mMotionDownX = screenX;
                mMotionDownY = screenY;
                mLastDropTarget = null;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mDragging) {
                    drop(screenX, screenY);
                }
                endDrag();
                break;
        }
        return mDragging;
    }

    public boolean dispatchUnhandledMove(View focused, int direction) {
        return mMoveTarget != null && mMoveTarget.dispatchUnhandledMove(focused, direction);
    }

    /**
     * Call this from a drag source view.
     */
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mDragging) {
            return false;
        }

        final int action = ev.getAction();
        final int screenX = clamp((int)ev.getRawX(), 0, mDisplayMetrics.widthPixels);
        final int screenY = clamp((int)ev.getRawY(), 0, mDisplayMetrics.heightPixels);

        switch (action) {
        case MotionEvent.ACTION_DOWN:
            // Remember where the motion event started
            mMotionDownX = screenX;
            mMotionDownY = screenY;
            break;
        case MotionEvent.ACTION_MOVE:
            // Update the drag view.  Don't use the clamped pos here so the dragging looks
            // like it goes off screen a little, intead of bumping up against the edge.
            mDragView.move((int)ev.getRawX(), (int)ev.getRawY());
            // Drop on someone?
            final int[] coordinates = mCoordinatesTemp;
            IDropTarget dropTarget = findDropTarget(screenX, screenY, coordinates);
            if (dropTarget != null) {
                if (mLastDropTarget == dropTarget) {
                    dropTarget.onDragOver(mDragSource, coordinates[0], coordinates[1],
                        (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                } else {
                    if (mLastDropTarget != null) {
                        mLastDropTarget.onDragExit(mDragSource, coordinates[0], coordinates[1],
                            (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                    }
                    dropTarget.onDragEnter(mDragSource, coordinates[0], coordinates[1],
                        (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                }
            } else {
                if (mLastDropTarget != null) {
                    mLastDropTarget.onDragExit(mDragSource, coordinates[0], coordinates[1],
                        (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                }
            }
            mLastDropTarget = dropTarget;

            break;
        case MotionEvent.ACTION_UP:
            if (mDragging) {
                drop(screenX, screenY);
            }
            endDrag();

            break;
        case MotionEvent.ACTION_CANCEL:
            cancelDrag();
        }
        return true;
    }

    private boolean drop(float x, float y) {

        final int[] coordinates = mCoordinatesTemp;
        IDropTarget dropTarget = findDropTarget((int) x, (int) y, coordinates);

        if (dropTarget != null) {
            dropTarget.onDragExit(mDragSource, coordinates[0], coordinates[1],
                    (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
            if (dropTarget.acceptDrop()) {
                dropTarget.onDrop(mDragSource, coordinates[0], coordinates[1],
                        (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                mDragSource.onDropCompleted((View) dropTarget, true);
                return true;
            } else {
                mDragSource.onDropCompleted((View) dropTarget, false);
                return true;
            }
        }
        return false;
    }

    private IDropTarget findDropTarget(int x, int y, int[] dropCoordinates) {
        final Rect r = mRectTemp;

        final ArrayList<IDropTarget> dropTargets = mDropTargets;
        final int count = dropTargets.size();
        for (int i=count-1; i>=0; i--) {
            final IDropTarget target = dropTargets.get(i);
            target.getHitRect(r);
            target.getLocationOnScreen(dropCoordinates);
            r.offset(dropCoordinates[0] - target.getLeft(), dropCoordinates[1] - target.getTop());
            if (r.contains(x, y)) {
                dropCoordinates[0] = x - dropCoordinates[0];
                dropCoordinates[1] = y - dropCoordinates[1];
                return target;
            }
        }
        return null;
    }

    /**
     * Get the screen size so we can clamp events to the screen size so even if
     * you drag off the edge of the screen, we find something.
     */
    private void recordScreenSize() {
        ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(mDisplayMetrics);
    }

    /**
     * Clamp val to be &gt;= min and &lt; max.
     */
    private static int clamp(int val, int min, int max) {
        if (val < min) {
            return min;
        } else if (val >= max) {
            return max - 1;
        } else {
            return val;
        }
    }

    /**
     * Sets the drag listener which will be notified when a drag starts or ends.
     */
    public void setDragListener(IDragListener l) {
        mListener = l;
    }

    /**
     * Add a DropTarget to the list of potential places to receive drop events.
     */
    public void addDropTarget(IDropTarget target) {
        mDropTargets.add(target);
    }

    /**
     * Don't send drop events to <em>target</em> any more.
     */
    public void removeAllDropTargets () {
        mDropTargets = new ArrayList<IDropTarget> ();
    }

    public void setWireController(WireController wireController) {
        this.wireController = wireController;
    }
}

/*
 * This is a modified version of a class from the Android Open Source Project. 
 * The original copyright and license information follows.
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

package com.edulectronics.tinycircuit.Views.Draggables.Interfaces;

import android.graphics.Rect;

import com.edulectronics.tinycircuit.Views.Draggables.DragView;

public interface IDropTarget {
    void onDrop(IDragSource source, int x, int y, int xOffset, int yOffset,
                DragView dragView, Object dragInfo);

    void onDragEnter(IDragSource source, int x, int y, int xOffset, int yOffset,
                     DragView dragView, Object dragInfo);

    void onDragOver(IDragSource source, int x, int y, int xOffset, int yOffset,
                    DragView dragView, Object dragInfo);

    void onDragExit(IDragSource source, int x, int y, int xOffset, int yOffset,
                    DragView dragView, Object dragInfo);

    boolean acceptDrop();

    // These methods are implemented in Views
    void getHitRect(Rect outRect);
    void getLocationOnScreen(int[] loc);
    int getLeft();
    int getTop();
}

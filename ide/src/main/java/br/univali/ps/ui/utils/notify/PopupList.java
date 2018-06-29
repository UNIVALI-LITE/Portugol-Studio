/*
 * Copyright 2017 dorkbox, llc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.univali.ps.ui.utils.notify;

import dorkbox.util.ScreenUtil;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Contains a list of notification popups + the Y offset (if any)
 */
class PopupList {
    private int offsetY = 0;
    private ArrayList<LookAndFeel> popups = new ArrayList<LookAndFeel>(4);

    /**
     * have to adjust for offsets when the window-manager has a toolbar that consumes space and prevents overlap.
     *
     * this is only done on the 2nd popup is added to the list
     */
    void calculateOffset(final boolean showFromTop, final int anchorX, final int anchorY) {
        if (offsetY == 0) {
            Point point = new Point(anchorX, anchorY);
            GraphicsConfiguration gc = 
                    ScreenUtil.getGraphicsDeviceAt(point)
                                                 .getDefaultConfiguration();

            Insets screenInsets = Toolkit.getDefaultToolkit()
                                         .getScreenInsets(gc);

            if (showFromTop) {
                if (screenInsets.top > 0) {
                    offsetY = screenInsets.top - LookAndFeel.MARGIN;
                }
            } else {
                if (screenInsets.bottom > 0) {
                    offsetY = screenInsets.bottom + LookAndFeel.MARGIN;
                }
            }
        }
    }

    int getOffsetY() {
        return offsetY;
    }


    int size() {
        return popups.size();
    }

    void add(final LookAndFeel lookAndFeel) {
        popups.add(lookAndFeel);
    }

    Iterator<LookAndFeel> iterator() {
        return popups.iterator();
    }

    LookAndFeel get(final int index) {
        return popups.get(index);
    }
}

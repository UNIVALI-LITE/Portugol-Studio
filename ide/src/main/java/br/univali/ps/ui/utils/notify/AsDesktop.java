/*
 * Copyright 2015 dorkbox, llc
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

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JWindow;

import dorkbox.util.ScreenUtil;
import dorkbox.util.SwingUtil;

// we can't use regular popup, because if we have no owner, it won't work!
// instead, we just create a JWindow and use it to hold our content
@SuppressWarnings({"Duplicates", "FieldCanBeLocal", "WeakerAccess", "DanglingJavadoc"})
public
class AsDesktop extends JWindow implements INotify {
    private static final long serialVersionUID = 1L;

    private final LookAndFeel look;
    private final Notify notification;


    // this is on the swing EDT
    @SuppressWarnings("NumericCastThatLosesPrecision")
    AsDesktop(final Notify notification, final ImageIcon image, final Theme theme) {
        this.notification = notification;

        setAlwaysOnTop(true);

        final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(preferredSize);
        setMaximumSize(preferredSize);
        setMinimumSize(preferredSize);
        setSize(NotifyCanvas.WIDTH, NotifyCanvas.HEIGHT);
        setLocation(Short.MIN_VALUE, Short.MIN_VALUE);

        Rectangle bounds;
        GraphicsDevice device;

        if (notification.screenNumber == Short.MIN_VALUE) {
            // set screen position based on mouse
            Point mouseLocation = MouseInfo.getPointerInfo()
                                           .getLocation();

            device = ScreenUtil.getGraphicsDeviceAt(mouseLocation);
        }
        else {
            // set screen position based on specified screen
            int screenNumber = notification.screenNumber;
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice screenDevices[] = ge.getScreenDevices();

            if (screenNumber < 0) {
                screenNumber = 0;
            }
            else if (screenNumber > screenDevices.length - 1) {
                screenNumber = screenDevices.length - 1;
            }

            device = screenDevices[screenNumber];
        }

        bounds = device.getDefaultConfiguration()
                       .getBounds();


        NotifyCanvas notifyCanvas = new NotifyCanvas(this, notification, image, theme);
        getContentPane().add(notifyCanvas);

        look = new LookAndFeel(this, this, notifyCanvas, notification, bounds, true);
    }

    @Override
    public
    void onClick(final int x, final int y) {
        look.onClick(x, y);
    }

    /**
     * Shakes the popup
     *
     * @param durationInMillis now long it will shake
     * @param amplitude a measure of how much it needs to shake. 4 is a small amount of shaking, 10 is a lot.
     */
    @Override
    public
    void shake(final int durationInMillis, final int amplitude) {
        look.shake(durationInMillis, amplitude);
    }

    @Override
    public
    void setVisible(final boolean visible) {
        // was it already visible?
        if (visible == isVisible()) {
            // prevent "double setting" visible state
            return;
        }

        // this is because the order of operations are different based upon visibility.
        look.updatePositionsPre(visible);

        super.setVisible(visible);

        // this is because the order of operations are different based upon visibility.
        look.updatePositionsPost(visible);

        if (visible) {
            this.toFront();
        }
    }

    // setVisible(false) with any extra logic
    void doHide() {
        super.setVisible(false);
    }

    @Override
    public
    void close() {
        // this must happen in the Swing EDT. This is usually called by the active renderer
        SwingUtil.invokeLater(new Runnable() {
            @Override
            public
            void run() {
                doHide();
                look.close();

                removeAll();
                dispose();

                notification.onClose();
            }
        });
    }
}

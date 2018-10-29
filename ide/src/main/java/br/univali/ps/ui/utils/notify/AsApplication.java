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

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dorkbox.util.SwingUtil;

// this is a child to a Jframe/window (instead of globally to the screen)
@SuppressWarnings({"Duplicates", "FieldCanBeLocal", "WeakerAccess", "DanglingJavadoc"})
public
class AsApplication implements INotify {
    private final LookAndFeel look;
    private final Notify notification;
    private final NotifyCanvas notifyCanvas;
    private final JFrame appWindow;

    private final ComponentListener parentListener;
    private final WindowStateListener windowStateListener;

    private static final String glassPanePrefix = "dorkbox.notify";

    private JPanel glassPane;

    // this is on the swing EDT
    @SuppressWarnings("NumericCastThatLosesPrecision")
    AsApplication(final Notify notification, final ImageIcon image, final JFrame appWindow, final Theme theme) {
        this.notification = notification;
        this.notifyCanvas = new NotifyCanvas(this, notification, image, theme);
        this.appWindow = appWindow;

        look = new LookAndFeel(this, appWindow, notifyCanvas, notification, appWindow.getBounds(), false);

        // this makes sure that our notify canvas stay anchored to the parent window (if it's hidden/shown/moved/etc)
        parentListener = new ComponentListener() {
            @Override
            public
            void componentShown(final ComponentEvent e) {
                look.reLayout(appWindow.getBounds());
            }

            @Override
            public
            void componentHidden(final ComponentEvent e) {
            }

            @Override
            public
            void componentResized(final ComponentEvent e) {
                look.reLayout(appWindow.getBounds());
            }

            @Override
            public
            void componentMoved(final ComponentEvent e) {
            }
        };

        windowStateListener = new WindowStateListener() {
            @Override
            public
            void windowStateChanged(WindowEvent e) {
                int state = e.getNewState();
                if ((state & Frame.ICONIFIED) == 0) {
                    look.reLayout(appWindow.getBounds());
                }
            }
        };


        appWindow.addWindowStateListener(windowStateListener);
        appWindow.addComponentListener(parentListener);

        Component glassPane_ = appWindow.getGlassPane();
        if (glassPane_ instanceof JPanel) {
            glassPane = (JPanel) glassPane_;
            String name = glassPane.getName();

            if (!name.equals(glassPanePrefix)) {
                // We just tweak the already existing glassPane, instead of replacing it with our own
                // glassPane = new JPanel();
                glassPane.setLayout(null);
                glassPane.setName(glassPanePrefix);
                // glassPane.setSize(appWindow.getSize());
                // glassPane.setOpaque(false);
                // appWindow.setGlassPane(glassPane);
            }

            glassPane.add(notifyCanvas);

            if (!glassPane.isVisible()) {
                glassPane.setVisible(true);
            }
        } else {
            System.err.println("Not able to add notification to custom glassPane");
        }
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
        // this is because the order of operations are different based upon visibility.
        look.updatePositionsPre(visible);
        look.updatePositionsPost(visible);
    }

    @Override
    public
    void close() {
        // this must happen in the Swing EDT. This is usually called by the active renderer
        SwingUtil.invokeLater(new Runnable() {
            @Override
            public
            void run() {
                look.close();

                glassPane.remove(notifyCanvas);

                appWindow.removeWindowStateListener(windowStateListener);
                appWindow.removeComponentListener(parentListener);

                boolean found = false;
                Component[] components = glassPane.getComponents();
                for (Component component : components) {
                    if (component instanceof NotifyCanvas) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // hide the glass pane if there are no more notifications on it.
                    glassPane.setVisible(false);
                }

                notification.onClose();
            }
        });
    }
}

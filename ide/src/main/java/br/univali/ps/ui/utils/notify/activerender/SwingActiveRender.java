/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.utils.notify.activerender;

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

import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.SwingUtilities;

import dorkbox.util.ActionHandlerLong;
import dorkbox.util.swing.SynchronizedEventQueue;

/**
 * Contains all of the appropriate logic to setup and render via "Active" rendering (instead of "Passive" rendering).
 *
 * This permits us to render components OFF of the EDT - even though there are other frames/components that are ON the EDT.
 * <br>
 * Because we still want to react to mouse events, etc on the EDT, we do not completely remove the EDT -- we merely allow us
 * to "synchronize" the EDT object to our thread. It's a little bit hacky, but it works beautifully, and permits MUCH nicer animations.
 * <br>
 */
public final
class SwingActiveRender {
    private static Thread activeRenderThread = null;

    static final List<Component> activeRenders = new ArrayList<Component>();
    static final List<ActionHandlerLong> activeRenderEvents = new CopyOnWriteArrayList<ActionHandlerLong>();

    // volatile, so that access triggers thread synchrony, since 1.6. See the Java Language Spec, Chapter 17
    static volatile boolean hasActiveRenders = false;

    private static final Runnable renderLoop = new ActiveRenderLoop();

    private
    SwingActiveRender() {
    }


    /**
     * Enables the component to to added to an "Active Render" thread, at a target "Frames-per-second". This is to support smooth, swing-based
     * animations.
     * <p>
     * This works by removing this object from EDT updates and manually calls paint on the component, updating it on our own thread, but
     * still remaining synchronized with the EDT.
     *
     * @param component the component to add to the ActiveRender thread.
     */
    @SuppressWarnings("Duplicates")
    public static
    void addActiveRender(final Component component) {
        // this should be on the EDT
        if (!EventQueue.isDispatchThread()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public
                void run() {
                    addActiveRender(component);
                }
            });
            return;
        }

        component.setIgnoreRepaint(true);

        synchronized (activeRenders) {
            if (!hasActiveRenders) {
                setupActiveRenderThread();
            }

            hasActiveRenders = true;
            activeRenders.add(component);
        }
    }

    /**
     * Removes a component from the ActiveRender queue. This should happen when the component is closed.
     *
     * @param component the component to remove
     */
    public static
    void removeActiveRender(final Component component) {
        // this should be on the EDT
        if (!EventQueue.isDispatchThread()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public
                void run() {
                    removeActiveRender(component);
                }
            });
            return;
        }

        synchronized (activeRenders) {
            activeRenders.remove(component);

            final boolean hadActiveRenders = !activeRenders.isEmpty();
            hasActiveRenders = hadActiveRenders;

            if (!hadActiveRenders) {
                activeRenderThread = null;
            }
        }

        component.setIgnoreRepaint(false);
    }

    /**
     * Specifies an ActionHandler to be called when the ActiveRender thread starts to render at each tick.
     *
     * @param handler the handler to add
     */
    public static
    void addActiveRenderFrameStart(final ActionHandlerLong handler) {
        synchronized (activeRenders) {
            activeRenderEvents.add(handler);
        }
    }

    /**
     * Potentially SLOW calculation, as it compares each entry in a queue for equality
     *
     * @param handler this is the handler to check
     *
     * @return true if this handler already exists in the active render, on-frame-start queue
     */
    public static
    boolean containsActiveRenderFrameStart(final ActionHandlerLong handler) {
        synchronized (activeRenders) {
            return activeRenderEvents.contains(handler);
        }
    }

    /**
     * Removes the handler from the on-frame-start queue
     *
     * @param handler the handler to remove
     */
    public static
    void removeActiveRenderFrameStart(final ActionHandlerLong handler) {
        synchronized (activeRenders) {
            activeRenderEvents.remove(handler);
        }
    }

    /**
     * Creates (if necessary) the active-render thread. When there are no active-render targets, this thread will exit
     */
    private static
    void setupActiveRenderThread() {
        if (activeRenderThread != null) {
            return;
        }

        SynchronizedEventQueue.install();

        activeRenderThread = new Thread(renderLoop, "AWT-ActiveRender");
        activeRenderThread.setDaemon(true);
        activeRenderThread.start();
    }
}
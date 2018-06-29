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
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.List;

import dorkbox.util.ActionHandlerLong;
import dorkbox.util.Property;

/**
 * Loop that controls the active rendering process
 */
public class ActiveRenderLoop implements Runnable {

    @Property
    /**
     * How many frames per second we want the Swing ActiveRender thread to run
     * at
     *
     * NOTE: The ActiveRenderLoop replaces the Swing EDT (only for specified
     * JFrames) in order to enable smoother animations. It is also important to
     * REMEMBER -- if you add a component to an actively managed JFrame, YOU
     * MUST make sure to call {@link JComponent#setIgnoreRepaint(boolean)}
     * otherwise this component will "fight" on the EDT for updates. You can
     * completely disable the EDT by calling
     * {@link NullRepaintManager#install()}
     */
    public static int TARGET_FPS = 30;

    @SuppressWarnings("WhileLoopReplaceableByForEach")
    @Override
    public void run() {
        long lastTime = System.nanoTime();

        // 30 FPS is usually just fine. This isn't a game where we need 60+ FPS. We permit this to be changed though, just in case it is.
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        Graphics graphics = null;

        while (SwingActiveRender.hasActiveRenders) {
            long now = System.nanoTime();
            long updateDeltaNanos = now - lastTime;
            lastTime = now;

            // not synchronized, because we don't care. The worst case, is one frame of animation behind.
            for (int i = 0; i < SwingActiveRender.activeRenderEvents.size(); i++) {
                ActionHandlerLong actionHandlerLong = SwingActiveRender.activeRenderEvents.get(i);

                //noinspection unchecked
                actionHandlerLong.handle(updateDeltaNanos);
            }

            // this needs to be synchronized because we don't want to our canvas removed WHILE we are rendering it.
            synchronized (SwingActiveRender.activeRenders) {
                final List<Component> activeRenders = SwingActiveRender.activeRenders;

                for (Component component : activeRenders) {
                    if (!component.isDisplayable()) {
                        continue;
                    }

                    // maybe the frame was closed, so we must be in a try/catch issue #11
                    try {
                        graphics = component.getGraphics();
                        component.paint(graphics);
                    } catch (Exception e) {
                        // the canvas can be closed as well. can get a "java.lang.IllegalStateException: Component must have a valid peer" if
                        // it's already be closed during the getDrawGraphics call.
                        e.printStackTrace();
                    } finally {
                        if (graphics != null) {
                            graphics.dispose();
                        }
                    }
                }
            }

            // Sync the display on some systems (on Linux, this fixes event queue problems)
            Toolkit.getDefaultToolkit()
                    .sync();

            try {
                // Converted to int before the division, because IDIV is
                // 1 order magnitude faster than LDIV (and int's work for us anyways)
                // see: http://www.cs.nuim.ie/~jpower/Research/Papers/2008/lambert-qapl08.pdf
                // Also, down-casting (long -> int) is not expensive w.r.t IDIV/LDIV
                //noinspection NumericCastThatLosesPrecision
                final int l = (int) (lastTime - System.nanoTime() + OPTIMAL_TIME);
                final int millis = l / 1000000;
                if (millis > 1) {
                    Thread.sleep(millis);
                } else {
                    // try to keep the CPU from getting slammed. We couldn't match our target FPS, so loop again
                    Thread.yield();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}
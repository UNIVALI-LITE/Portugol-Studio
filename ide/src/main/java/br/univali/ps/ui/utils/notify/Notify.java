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

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import dorkbox.util.ActionHandler;
import dorkbox.util.LocationResolver;
import dorkbox.util.Property;
import dorkbox.util.SwingUtil;

/**
 * Popup notification messages, similar to the popular "Growl" notification system on macosx, that display in the corner of the monitor.
 * </p>
 * They can follow the mouse (if the screen is unspecified), and have a variety of features, such as "shaking" to draw attention,
 * animating upon movement (for collating w/ multiple in a single location), and automatically hiding after a set duration.
 * </p>
 * These notifications are for a single screen only, and cannot be anchored to an application.
 *
 * <pre>
 * {@code
 * Notify.create()
 *      .title("Title Text")
 *      .text("Hello World!")
 *      .useDarkStyle()
 *      .showWarning();
 * }
 * </pre>
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue"})
public final
class Notify {

    /**
     * This is the title font used by a notification.
     */
    @Property
    public static String TITLE_TEXT_FONT = "Source Code Pro BOLD 16";

    /**
     * This is the main text font used by a notification.
     */
    @Property
    public static String MAIN_TEXT_FONT = "Source Code Pro BOLD 12";

    /**
     * How long we want it to take for the popups to relocate when one is closed
     */
    @Property
    public static float MOVE_DURATION = 1.0F;

    /**
     * Location of the dialog image resources. By default they must be in the 'resources' directory relative to the application
     */
    @Property
    public static String IMAGE_PATH = "resources";

    private static Map<String, SoftReference<ImageIcon>> imageCache = new HashMap<String, SoftReference<ImageIcon>>(4);

    /**
     * Gets the version number.
     */
    public static
    String getVersion() {
        return "3.7";
    }

    /**
     * Builder pattern to create the notification.
     */
    public static
    Notify create() {
        return new Notify();
    }

    /**
     * Gets the size of the image to be used in the notification, which is a 48x48 pixel image.
     */
    public static
    int getImageSize() {
        return 48;
    }

    /**
     * Permits one to override the default images for the dialogs. This is NOT thread safe, and must be performed BEFORE showing a
     * notification.
     * <p>
     * The image names are as follows:
     * <p>
     * 'Notify.DIALOG_CONFIRM' 'Notify.DIALOG_INFORMATION' 'Notify.DIALOG_WARNING' 'Notify.DIALOG_ERROR'
     *
     * @param imageName  the name of the image, either your own if you want want it cached, or one of the above.
     * @param image  the BufferedImage that you want to cache.
     */
    public static
    void overrideDefaultImage(String imageName, BufferedImage image) {
        if (imageCache.containsKey(imageName)) {
            throw new RuntimeException("Unable to set an image that already has been set. This action must be done as soon as possible.");
        }



        imageCache.put(imageName, new SoftReference<ImageIcon>(new ImageIcon(image)));
    }

    private static
    ImageIcon getImage(String imageName) {
        ImageIcon image = null;
        InputStream resourceAsStream = null;

        try {
            SoftReference<ImageIcon> reference = imageCache.get(imageName);

            if (reference != null) {
                image = reference.get();
            }

            if (image == null) {
                String name = IMAGE_PATH + File.separatorChar + imageName;

                resourceAsStream = LocationResolver.getResourceAsStream(name);

                image = new ImageIcon(ImageIO.read(resourceAsStream));
                imageCache.put(imageName, new SoftReference<ImageIcon>(image));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return image;
    }


    String title;
    String text;

    Theme theme;

    Pos position = Pos.BOTTOM_RIGHT;
    int hideAfterDurationInMillis = 0;

    boolean hideCloseButton;
    boolean isDark = false;
    int screenNumber = Short.MIN_VALUE;
    private ImageIcon icon;

    ActionHandler<Notify> onGeneralAreaClickAction;
    private INotify notifyPopup;

    private String name;
    private int shakeDurationInMillis = 0;
    private int shakeAmplitude = 0;
    private JFrame appWindow;

    private
    Notify() {
    }

    /**
     * Specifies the main text
     */
    public
    Notify text(String text) {
        this.text = text;
        return this;
    }

    /**
     * Specifies the title
     */
    public
    Notify title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Specifies the image
     */
    public
    Notify image(Image image) {
        

        this.icon = new ImageIcon(image);
        return this;
    }

    /**
     * Specifies the position of the notification on screen, by default it is {@link Pos#BOTTOM_RIGHT bottom-right}.
     */
    public
    Notify position(Pos position) {
        this.position = position;
        return this;
    }

    /**
     * Specifies the duration that the notification should show, after which it will be hidden. 0 means to show forever. By default it
     * will show forever
     */
    public
    Notify hideAfter(int durationInMillis) {
        if (durationInMillis < 0) {
            durationInMillis = 0;
        }
        this.hideAfterDurationInMillis = durationInMillis;
        return this;
    }

    /**
     * Specifies what to do when the user clicks on the notification (in addition o the notification hiding, which happens whenever the
     * notification is clicked on). This does not apply when clicking on the "close" button
     */
    public
    Notify onAction(ActionHandler<Notify> onAction) {
        this.onGeneralAreaClickAction = onAction;
        return this;
    }

    /**
     * Specifies that the notification should use the built-in dark styling, rather than the default, light-gray notification style.
     */
    public
    Notify darkStyle() {
        isDark = true;
        return this;
    }

    /**
     * Specifies what the theme should be, if other than the default. This will always take precedence over the defaults.
     */
    public
    Notify text(Theme theme) {
        this.theme = theme;
        return this;
    }

    /**
     * Specify that the close button in the top-right corner of the notification should not be shown.
     */
    public
    Notify hideCloseButton() {
        this.hideCloseButton = true;
        return this;
    }


    /**
     * Shows the notification. If the Notification is assigned to a screen, but shown inside a Swing/etc parent, the screen number will be
     * ignored.
     */
    public
    void show() {
        // must be done in the swing EDT
        //noinspection Convert2Lambda
        SwingUtil.invokeAndWaitQuietly(new Runnable() {
            @Override
            public
            void run() {
                final Notify notify = Notify.this;
                final ImageIcon image = notify.icon;

                Theme theme;
                if (notify.theme != null) {
                    // use custom theme.
                    theme = notify.theme;
                } else {
                    theme = new Theme(Notify.TITLE_TEXT_FONT, Notify.MAIN_TEXT_FONT, notify.isDark);
                }

                if (appWindow == null) {
                    notifyPopup = new AsDesktop(notify, image, theme);
                } else {
                    notifyPopup = new AsApplication(notify, image, appWindow, theme);
                }

                notifyPopup.setVisible(true);

                if (shakeDurationInMillis > 0) {
                    notifyPopup.shake(notify.shakeDurationInMillis, notify.shakeAmplitude);
                }
            }
        });

        // don't need to hang onto these.
        icon = null;
    }

    /**
     * "shakes" the notification, to bring user attention to it.
     *
     * @param durationInMillis now long it will shake
     * @param amplitude a measure of how much it needs to shake. 4 is a small amount of shaking, 10 is a lot.
     */
    public
    Notify shake(final int durationInMillis, final int amplitude) {
        this.shakeDurationInMillis = durationInMillis;
        this.shakeAmplitude = amplitude;

        if (notifyPopup != null) {
            // must be done in the swing EDT
            //noinspection Convert2Lambda
            SwingUtil.invokeLater(new Runnable() {
                @Override
                public
                void run() {
                    notifyPopup.shake(durationInMillis, amplitude);
                }
            });
        }

        return this;
    }

    /**
     * Closes the notification. Particularly useful if it's an "infinite" duration notification.
     */
    public
    void close() {
        if (notifyPopup == null) {
            throw new NullPointerException("NotifyPopup");
        }

        // must be done in the swing EDT
        //noinspection Convert2Lambda
        SwingUtil.invokeLater(new Runnable() {
            @Override
            public
            void run() {
                notifyPopup.close();
            }
        });
    }

    /**
     * Specifies which screen to display on. If <0, it will show on screen 0. If > max-screens, it will show on the last screen.
     */
    public
    Notify setScreen(final int screenNumber) {
        this.screenNumber = screenNumber;
        return this;
    }

    /**
     * Attaches this notification to a specific JFrame, instead of having a global notification
     */
    public
    Notify attach(final JFrame frame) {
        this.appWindow = frame;
        return this;
    }


    // called when this notification is closed.
    void onClose() {
        notifyPopup = null;
    }
}


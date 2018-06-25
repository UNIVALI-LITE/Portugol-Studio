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

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("FieldCanBeLocal")
class NotifyCanvas extends Canvas {
    private static final Stroke stroke = new BasicStroke(2);
    private static final int closeX = 282;
    private static final int closeY = 2;

    private static final int Y_1 = closeY + 5;
    private static final int X_1 = closeX + 5;
    private static final int Y_2 = closeY + 11;
    private static final int X_2 = closeX + 11;

    static final int WIDTH = 300;
    static final int HEIGHT = 87;
    private static final int PROGRESS_HEIGHT = HEIGHT - 2;

    private final boolean showCloseButton;
    private BufferedImage cachedImage;
    private final Notify notification;
    private final ImageIcon imageIcon;

    // for the progress bar. we directly draw this onscreen
    // non-volatile because it's always accessed in the active render thread
    private int progress = 0;

    private final Theme theme;
    final INotify parent;


    NotifyCanvas(final INotify parent, final Notify notification, final ImageIcon imageIcon, final Theme theme) {
        this.parent = parent;
        this.notification = notification;
        this.imageIcon = imageIcon;
        this.theme = theme;

        final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(preferredSize);
        setMaximumSize(preferredSize);
        setMinimumSize(preferredSize);
        setSize(WIDTH, HEIGHT);

        setFocusable(false);

        setBackground(this.theme.panel_BG);
        showCloseButton = !notification.hideCloseButton;

        // now we setup the rendering of the image
        cachedImage = renderBackgroundInfo(notification.title, notification.text, this.theme, this.imageIcon);
    }

    void setProgress(final int progress) {
        this.progress = progress;
    }

    int getProgress() {
        return progress;
    }

    @Override
    public
    void paint(final Graphics g) {
        // we cache the text + image (to another image), and then always render the close + progressbar

        // use our cached image, so we don't have to re-render text/background/etc
        try {
            g.drawImage(cachedImage, 0, 0, null);
        } catch (Exception ignored) {
            // have also seen (happened after screen/PC was "woken up", in Xubuntu 16.04):
            // java.lang.ClassCastException:sun.awt.image.BufImgSurfaceData cannot be cast to sun.java2d.xr.XRSurfaceData at sun.java2d.xr.XRPMBlitLoops.cacheToTmpSurface(XRPMBlitLoops.java:148)
            // at sun.java2d.xr.XrSwToPMBlit.Blit(XRPMBlitLoops.java:356)
            // at sun.java2d.SurfaceDataProxy.updateSurfaceData(SurfaceDataProxy.java:498)
            // at sun.java2d.SurfaceDataProxy.replaceData(SurfaceDataProxy.java:455)
            // at sun.java2d.SurfaceData.getSourceSurfaceData(SurfaceData.java:233)
            // at sun.java2d.pipe.DrawImage.renderImageCopy(DrawImage.java:566)
            // at sun.java2d.pipe.DrawImage.copyImage(DrawImage.java:67)
            // at sun.java2d.pipe.DrawImage.copyImage(DrawImage.java:1014)
            // at sun.java2d.pipe.ValidatePipe.copyImage(ValidatePipe.java:186)
            // at sun.java2d.SunGraphics2D.drawImage(SunGraphics2D.java:3318)
            // at sun.java2d.SunGraphics2D.drawImage(SunGraphics2D.java:3296)
            // at dorkbox.notify.NotifyCanvas.paint(NotifyCanvas.java:92)

            // redo the image
            cachedImage = renderBackgroundInfo(notification.title, notification.text, this.theme, imageIcon);

            // try to draw again
            try {
                g.drawImage(cachedImage, 0, 0, null);
            } catch (Exception ignored2) {
            }
        }

        // the progress bar and close button are the only things that can change, so we always draw them every time
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            if (showCloseButton) {
                Graphics2D g3 = (Graphics2D) g.create();

                g3.setColor(theme.panel_BG);
                g3.setStroke(stroke);

                final Point p = getMousePosition();
                // reasonable position for detecting mouse over
                if (p != null && p.getX() >= 280 && p.getY() <= 20) {
                    g3.setColor(Color.RED);
                }
                else {
                    g3.setColor(theme.closeX_FG);
                }

                // draw the X
                g3.drawLine(X_1, Y_1, X_2, Y_2);
                g3.drawLine(X_2, Y_1, X_1, Y_2);
            }

            g2.setColor(theme.progress_FG);
            g2.fillRect(0, PROGRESS_HEIGHT, progress, 2);
        } finally {
            g2.dispose();
        }
    }

    /**
     * @return TRUE if we were over the 'X' or FALSE if the click was in the general area (and not over the 'X').
     */
    boolean isCloseButton(final int x, final int y) {
        return showCloseButton && x >= 280 && y <= 20;
    }

    private static
    BufferedImage renderBackgroundInfo(final String title,
                                       final String notificationText,
                                       final Theme theme,
                                       final ImageIcon imageIcon) {


        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        try {
            g2.setColor(theme.panel_BG);
            g2.fillRect(0, 0, WIDTH, HEIGHT);

            // Draw the title text
            g2.setColor(theme.titleText_FG);
            g2.setFont(theme.titleTextFont);
            g2.drawString(title, 5, 20);


            int posX = 10;
            int posY = -8;
            int textLengthLimit = 108;

            // ICON
            if (imageIcon != null) {
                textLengthLimit = 88;
                posX = 60;
                // Draw the image
                imageIcon.paintIcon(null, g2, 5, 30);
            }

            // Draw the main text
            int length = notificationText.length();
            StringBuilder text = new StringBuilder(length);

            // are we "html" already? just check for the starting tag and strip off END html tag
            if (length >= 13 && notificationText.regionMatches(true, length - 7, "</html>", 0, 7)) {
                text.append(notificationText);
                text.delete(text.length() - 7, text.length());

                length -= 7;
            }
            else {
                text.append("<html>");
                text.append(notificationText);
            }

            // make sure the text is the correct length
            if (length > textLengthLimit) {
                text.delete(6 + textLengthLimit, text.length());
                text.append("...");
            }
            text.append("</html>");

            JLabel mainTextLabel = new JLabel();
            mainTextLabel.setForeground(theme.mainText_FG);
            mainTextLabel.setFont(theme.mainTextFont);
            mainTextLabel.setText(text.toString());
            mainTextLabel.setBounds(0, 0, WIDTH - posX - 2, HEIGHT);

            g2.translate(posX, posY);
            mainTextLabel.paint(g2);
            g2.translate(-posX, -posY);
        } finally {
            g2.dispose();
        }

        return image;
    }
}

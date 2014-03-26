package br.univali.ps;

/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
import static java.util.logging.Logger.getLogger;
import java.lang.ref.WeakReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

/**
 * <p>
 * This class is used to detect Event Dispatch Thread rule violations<br>
 * See <a href="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">How to Use Threads</a> for more info
 * </p>
 * <p/>
 * <p>
 * This is a modification of original idea of Scott Delap<br>
 * Initial version of ThreadCheckingRepaintManager can be found here<br>
 * <a href="http://www.clientjava.com/blog/2004/08/20/1093059428000.html">Easily Find Swing Threading Mistakes</a>
 * </p>
 * <p>
 * This is a modification of original idea of Alexander Potochkin
 * to use Java Logging System to report the errors instead of the standard output.
 * </p>
 *
 * @author Scott Delap
 * @author Alexander Potochkin
 * @author Luiz Fernando Noschang
 *
 * https://swinghelper.dev.java.net/
 */
public final class DetectorViolacoesThreadSwing extends RepaintManager
{
    private static final Logger LOGGER = getLogger(DetectorViolacoesThreadSwing.class.getName());

    // it is recommended to pass the complete check
    private boolean completeCheck = true;

    private WeakReference<JComponent> lastComponent;

    public DetectorViolacoesThreadSwing(boolean completeCheck)
    {
        this.completeCheck = completeCheck;
    }

    public DetectorViolacoesThreadSwing()
    {
        this(true);
    }

    public boolean isCompleteCheck()
    {
        return completeCheck;
    }

    public void setCompleteCheck(boolean completeCheck)
    {
        this.completeCheck = completeCheck;
    }

    @Override
    public synchronized void addInvalidComponent(JComponent component)
    {
        checkThreadViolations(component);
        super.addInvalidComponent(component);
    }

    @Override
    public void addDirtyRegion(JComponent component, int x, int y, int w, int h)
    {
        checkThreadViolations(component);
        super.addDirtyRegion(component, x, y, w, h);
    }

    private void checkThreadViolations(JComponent component)
    {
        if (!SwingUtilities.isEventDispatchThread() && (completeCheck || component.isShowing()))
        {
            boolean repaint = false;
            boolean fromSwing = false;
            boolean imageUpdate = false;

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            for (StackTraceElement st : stackTrace)
            {
                if (repaint && st.getClassName().startsWith("javax.swing."))
                {
                    fromSwing = true;
                }
                if (repaint && "imageUpdate".equals(st.getMethodName()))
                {
                    imageUpdate = true;
                }
                if ("repaint".equals(st.getMethodName()))
                {
                    repaint = true;
                    fromSwing = false;
                }
            }
            if (imageUpdate)
            {
                // assuming it is java.awt.image.ImageObserver.imageUpdate(...)
                // image was asynchronously updated, that's ok
                return;
            }
            if (repaint && !fromSwing)
            {
                // no problems here, since repaint() is thread safe
                return;
            }

            // ignore the last processed component
            if (lastComponent != null && component == lastComponent.get())
            {
                return;
            }

            lastComponent = new WeakReference<>(component);
            violationFound(component, stackTrace);
        }
    }

    protected void violationFound(JComponent component, StackTraceElement[] stackTrace)
    {
        final StringBuilder sb = new StringBuilder();

        sb.append("\nEDT violation detected\n");
        sb.append(component);
        sb.append("\n");

        for (StackTraceElement st : stackTrace)
        {
            sb.append("\tat ");
            sb.append(st);
            sb.append("\n");
        }

        LOGGER.log(Level.SEVERE, sb.toString());
    }
}

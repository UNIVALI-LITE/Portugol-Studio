/*
 * 04/23/2010
 *
 * Copyright (C) 2010 Robert Futrell
 * robert_futrell at users.sourceforge.net
 * http://fifesoft.com/rsyntaxtextarea
 *
 * This library is distributed under a modified BSD license.  See the included
 * RSTALanguageSupport.License.txt file for details.
 */
package br.univali.ps.ui.completion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionCellRenderer;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.Util;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


/**
 * A base class for language support implementations.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public abstract class AbstractLanguageSupport implements LanguageSupport {

	/**
	 * Map of all text areas using this language support to their installed
	 * {@link AutoCompletion} instances.  This should be maintained by
	 * subclasses by adding to, and removing from, it in their
	 * {@link #install(org.fife.ui.rsyntaxtextarea.RSyntaxTextArea)} and
	 * {@link #uninstall(org.fife.ui.rsyntaxtextarea.RSyntaxTextArea)} methods.
	 */
	private Map textAreaToAutoCompletion;

	/**
	 * Whether auto-completion is enabled for this language.
	 */
	private boolean autoCompleteEnabled;

	/**
	 * Whether auto-activation for completion choices is enabled for this
	 * language.  Note that this parameter only matters if
	 * {@link #autoCompleteEnabled} is <code>true</code>.
	 */
	private boolean autoActivationEnabled;

	/**
	 * The delay for auto-activation, in milliseconds.  This parameter is only
	 * honored if both {@link #autoCompleteEnabled} and
	 * {@link #autoActivationEnabled} are <code>true</code>.
	 */
	private int autoActivationDelay;

	/**
	 * Whether parameter assistance should be enabled for this language.
	 */
	private boolean parameterAssistanceEnabled;

	/**
	 * Whether the description window is displayed when the completion list
	 * window is displayed.
	 */
	private boolean showDescWindow;

	/**
	 * The default renderer for the completion list.
	 */
	private ListCellRenderer renderer;


	/**
	 * Constructor.
	 */
	protected AbstractLanguageSupport() {
		setDefaultCompletionCellRenderer(null); // Force default
		textAreaToAutoCompletion = new HashMap();
		autoCompleteEnabled = true;
		autoActivationEnabled = false;
		autoActivationDelay = 300;
	}


	/**
	 * Creates an auto-completion instance pre-configured and usable by
	 * most <code>LanguageSupport</code>s.
	 *
	 * @param p The completion provider.
	 * @return The auto-completion instance.
	 */
	protected AutoCompletion createAutoCompletion(CompletionProvider p) {
		AutoCompletion ac = new AutoCompletion(p);
		ac.setListCellRenderer(getDefaultCompletionCellRenderer());
		ac.setAutoCompleteEnabled(isAutoCompleteEnabled());
		ac.setAutoActivationEnabled(isAutoActivationEnabled());
		ac.setAutoActivationDelay(getAutoActivationDelay());
		ac.setParameterAssistanceEnabled(isParameterAssistanceEnabled());
		ac.setShowDescWindow(getShowDescWindow());
		return ac;
	}


	/**
	 * Creates the default cell renderer to use when none is specified.
	 * Subclasses can override this method if there is a "better" default
	 * renderer for a specific language.
	 *
	 * @return The default renderer for the completion list.
	 */
	protected ListCellRenderer createDefaultCompletionCellRenderer() {
		return new DefaultListCellRenderer();
	}


	/**
	 * Attempts to delegate rendering to a Substance cell renderer.
	 *
	 * @param ccr The cell renderer to modify.
	 */
	private void delegateToSubstanceRenderer(CompletionCellRenderer ccr) {
		try {
			ccr.delegateToSubstanceRenderer();
		} catch (Exception e) {
			// Never happens if Substance is on the classpath.
			e.printStackTrace();
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public int getAutoActivationDelay() {
		return autoActivationDelay;
	}


	/**
	 * Returns the auto completion instance used by a text area.
	 *
	 * @param textArea The text area.
	 * @return The auto completion instance, or <code>null</code> if none
	 *         is installed on the text area.
	 */
	protected AutoCompletion getAutoCompletionFor(RSyntaxTextArea textArea) {
		return (AutoCompletion)textAreaToAutoCompletion.get(textArea);
	}


	/**
	 * {@inheritDoc}
	 */
	public ListCellRenderer getDefaultCompletionCellRenderer() {
		return renderer;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean getShowDescWindow() {
		return showDescWindow;
	}


	/**
	 * Returns the text areas with this language support currently installed.
	 *
	 * @return The text areas.
	 */
	protected Set getTextAreas() {
		return textAreaToAutoCompletion.keySet();
	}


	/**
	 * Registers an auto-completion instance.  This should be called by
	 * subclasses in their
	 * {@link #install(org.fife.ui.rsyntaxtextarea.RSyntaxTextArea)} methods
	 * so that this language support can update all of them at once.
	 *
	 * @param textArea The text area that just installed the auto completion.
	 * @param ac The auto completion instance.
	 * @see #uninstallImpl(RSyntaxTextArea)
	 */
	protected void installImpl(RSyntaxTextArea textArea, AutoCompletion ac) {
		textAreaToAutoCompletion.put(textArea, ac);
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isAutoActivationEnabled() {
		return autoActivationEnabled;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isAutoCompleteEnabled() {
		return autoCompleteEnabled;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isParameterAssistanceEnabled() {
		return parameterAssistanceEnabled;
	}


	/**
	 * {@inheritDoc}
	 */
	public void setAutoActivationDelay(int ms) {
		ms = Math.max(0, ms);
		if (ms!=autoActivationDelay) {
			autoActivationDelay = ms;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setAutoActivationDelay(autoActivationDelay);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setAutoActivationEnabled(boolean enabled) {
		if (enabled!=autoActivationEnabled) {
			autoActivationEnabled = enabled;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setAutoActivationEnabled(enabled);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setAutoCompleteEnabled(boolean enabled) {
		if (enabled!=autoCompleteEnabled) {
			autoCompleteEnabled = enabled;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setAutoCompleteEnabled(enabled);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setDefaultCompletionCellRenderer(ListCellRenderer r) {
		if (r==null) {
			r = createDefaultCompletionCellRenderer();
		}
		if (r instanceof CompletionCellRenderer &&
				Util.getUseSubstanceRenderers()) {
			if (UIManager.getLookAndFeel().getClass().getName().
					indexOf(".Substance")>-1) {
				CompletionCellRenderer ccr = (CompletionCellRenderer)r;
				delegateToSubstanceRenderer(ccr);
			}
		}
		renderer = r;
	}


	/**
	 * {@inheritDoc}
	 */
    @Override
	public void setParameterAssistanceEnabled(boolean enabled) {
		if (enabled!=parameterAssistanceEnabled) {
			parameterAssistanceEnabled = enabled;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setParameterAssistanceEnabled(enabled);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setShowDescWindow(boolean show) {
		if (show!=showDescWindow) {
			showDescWindow = show;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setShowDescWindow(show);
			}
		}
	}


	/**
	 * Unregisters an textArea.  This should be called by subclasses in their
	 * {@link #uninstall(org.fife.ui.rsyntaxtextarea.RSyntaxTextArea)} methods.
	 * This method will also call the <code>uninstall</code> method on the
	 * <code>AutoCompletion</code>.
	 *
	 * @param textArea The text area.
	 * @see #installImpl(RSyntaxTextArea, AutoCompletion)
	 */
	protected void uninstallImpl(RSyntaxTextArea textArea) {
		AutoCompletion ac = getAutoCompletionFor(textArea);
		if (ac!=null) {
			ac.uninstall();
		}
		textAreaToAutoCompletion.remove(textArea);
	}


}
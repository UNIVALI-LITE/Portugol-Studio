/*
 * 03/21/2010
 *
 * Copyright (C) 2010 Robert Futrell
 * robert_futrell at users.sourceforge.net
 * http://fifesoft.com/rsyntaxtextarea
 *
 * This library is distributed under a modified BSD license.  See the included
 * RSTALanguageSupport.License.txt file for details.
 */
package br.univali.ps.ui.rstautil;

import javax.swing.ListCellRenderer;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


/**
 * "Extra" support for a programming language (code completion, parser, etc.).
 *
 * @author Robert Futrell
 * @version 1.0
 */
public interface LanguageSupport {

//	/**
//	 * Client property set on <code>RSyntaxTextArea</code>s referencing the
//	 * <code>AutoCompletion</code> instance providing its completion choices.
//	 */
//	public static final String PROPERTY_AUTO_COMPLETION	=
//										"org.fife.rsta.ac.AutoCompletion";

	/**
	 * Client property set on <code>RSyntaxTextArea</code>s referencing the
	 * <code>Parser</code> instance parsing its source code.  This will be
	 * <code>null</code> if a language support does not install such a parser.
	 */
	public static final String PROPERTY_LANGUAGE_PARSER =
		"org.fife.rsta.ac.LanguageSupport.LanguageParser";


	/**
	 * Returns the delay between when the user types a character and when the
	 * code completion popup should automatically appear (if applicable).
	 * This parameter is only honored if {@link #isAutoActivationEnabled()}
	 * returns <code>true</code>.
	 * 
	 * @return The delay, in milliseconds.
	 * @see #setAutoActivationDelay(int)
	 */
	public int getAutoActivationDelay();


	/**
	 * Returns the default list cell renderer to install for all text areas
	 * with this language support installed.
	 *
	 * @return The renderer.  This will never be <code>null</code>.
	 * @see #setDefaultCompletionCellRenderer(ListCellRenderer)
	 */
	public ListCellRenderer getDefaultCompletionCellRenderer();


	/**
	 * REturns whether the description window is also shown when the
	 * completion list is displayed, for editors of this language.
	 *
	 * @return Whether the description window is shown.
	 * @see #setShowDescWindow(boolean)
	 */
	public boolean getShowDescWindow();


	/**
	 * Returns whether auto-activation is enabled (that is, whether the
	 * completion popup will automatically appear after a delay when the user
	 * types an appropriate character).  Note that this parameter will be
	 * ignored if auto-completion is disabled.
	 *
	 * @return Whether auto-activation is enabled.
	 * @see #setAutoActivationEnabled(boolean)
	 * @see #getAutoActivationDelay()
	 * @see #isAutoCompleteEnabled()
	 */
	public boolean isAutoActivationEnabled();


	/**
	 * Returns whether auto-completion is enabled for this language.  If
	 * this value is <code>false</code>, then <code>ctrl+space</code> will
	 * do nothing.
	 *
	 * @return Whether auto-completion is enabled.
	 * @see #setAutoCompleteEnabled(boolean)
	 */
	public boolean isAutoCompleteEnabled();


	/**
	 * Installs this support.
	 *
	 * @param textArea The text area to install onto.
	 * @see #uninstall(RSyntaxTextArea)
	 */
	public void install(RSyntaxTextArea textArea);


	/**
	 * Returns whether parameter assistance is enabled for editors of this
	 * language.  Note that some language do not support parameter assistance
	 * at all; in those cases, this parameter does nothing.
	 *
	 * @return Whether parameter assistance is enabled for editors of this
	 *         language.
	 * @see #setParameterAssistanceEnabled(boolean)
	 */
	public boolean isParameterAssistanceEnabled();


	/**
	 * Sets the delay between when the user types a character and when the
	 * code completion popup should automatically appear (if applicable).
	 * This parameter is only honored if {@link #isAutoActivationEnabled()}
	 * returns <code>true</code>.
	 *
	 * @param ms The delay, in milliseconds.  This should be greater than zero.
	 * @see #getAutoActivationDelay()
	 */
	public void setAutoActivationDelay(int ms);


	/**
	 * Toggles whether auto-activation is enabled.  Note that auto-activation
	 * also depends on auto-completion itself being enabled.
	 *
	 * @param enabled Whether auto-activation is enabled.
	 * @see #isAutoActivationEnabled()
	 * @see #setAutoActivationDelay(int)
	 */
	public void setAutoActivationEnabled(boolean enabled);


	/**
	 * Toggles whether auto-completion is enabled for this language.  If
	 * this is set to <code>false</code>, then <code>ctrl+space</code> will
	 * do nothing.
	 *
	 * @param enabled Whether auto-completion should be enabled.
	 * @see #isAutoCompleteEnabled()
	 */
	public void setAutoCompleteEnabled(boolean enabled);


	/**
	 * Sets the default list cell renderer to install for all text areas with
	 * this language support installed.  This renderer will be shared amongst
	 * all text areas.
	 *
	 * @param r The renderer.  If this is <code>null</code>, a default will
	 *        be used.
	 * @see #getDefaultCompletionCellRenderer()
	 */
	public void setDefaultCompletionCellRenderer(ListCellRenderer r);


	/**
	 * Toggles whether parameter assistance is enabled for editors of this
	 * language.
	 *
	 * @param enabled Whether parameter assistance is enabled.
	 * @see #isParameterAssistanceEnabled()
	 */
	public void setParameterAssistanceEnabled(boolean enabled);


	/**
	 * Toggles whether the description window should also be shown when the
	 * completion list is displayed, for editors of this language.
	 *
	 * @param show Whether to show the description window.
	 * @see #getShowDescWindow()
	 */
	public void setShowDescWindow(boolean show);


	/**
	 * Uninstalls this support.
	 *
	 * @param textArea The text area to uninstall from.
	 * @see #install(RSyntaxTextArea)
	 */
	public void uninstall(RSyntaxTextArea textArea);


}
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
package br.univali.ps.ui.completion;

import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import org.fife.ui.autocomplete.*;


/**
 * The cell renderer used for the C programming language.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class PortugolCellRender extends CompletionCellRenderer {

	private Icon variableIcon;
	private Icon functionIcon;
	private Icon emptyIcon;


	/**
	 * Constructor.
	 */
	public PortugolCellRender() {
		emptyIcon = new EmptyIcon(16); // Should be done first
		variableIcon = getIcon("var.png");
		functionIcon = getIcon("function.png");
	}


	/**
	 * Returns an icon.
	 *
	 * @param resource The icon to retrieve.  This should either be a file,
	 *        or a resource loadable by the current ClassLoader.
	 * @return The icon.
	 */
	private Icon getIcon(String resource) {
		URL url = getClass().getResource(resource); // Should never be null
		return url!=null ? new ImageIcon(url) : emptyIcon;
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForOtherCompletion(JList list,
			Completion c, int index, boolean selected, boolean hasFocus) {
		super.prepareForOtherCompletion(list, c, index, selected, hasFocus);
		setIcon(emptyIcon);
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForVariableCompletion(JList list,
			VariableCompletion vc, int index, boolean selected,
			boolean hasFocus) {
		super.prepareForVariableCompletion(list, vc, index, selected,
										hasFocus);
		setIcon(variableIcon);
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForFunctionCompletion(JList list,
			FunctionCompletion fc, int index, boolean selected,
			boolean hasFocus) {
		super.prepareForFunctionCompletion(list, fc, index, selected,
										hasFocus);
		setIcon(functionIcon);
	}
}
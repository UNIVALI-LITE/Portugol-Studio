/*
 * 11/15/2011
 *
 * Copyright (C) 2010 Robert Futrell
 * robert_futrell at users.sourceforge.net
 * http://fifesoft.com/rsyntaxtextarea
 *
 * This library is distributed under a modified BSD license.  See the included
 * RSTALanguageSupport.License.txt file for details.
 */
package br.univali.ps.ui.rstautil;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


/**
 * Displays a popup dialog with the "Go to member" tree.  Language support
 * implementations that can do in-depth parsing of the source code in an editor
 * can create an {@link AbstractSourceTree} representing that source, and add
 * this action to <code>RSyntaxTextArea</code>'s input/action maps, so users
 * can easily navigate to functions, methods, etc.<p>
 *
 * The preferred keystroke to bind this action to is Ctrl+Shift+O
 * (Cmd+Shift+O on Mac).  Language supports should also be sure to uninstall
 * this shortcut when they are uninstalled themselves.
 *
 * @author Robert Futrell
 * @version 1.0
 * @see GoToMemberWindow
 */
public class GoToMemberAction extends TextAction {

	/**
	 * The outline tree class appropriate for the current language.
	 */
	private Class outlineTreeClass;


	/**
	 * Constructor.
	 *
	 * @param outlineTreeClass A class extending {@link AbstractSourceTree}.
	 *        This class must have a no-argument constructor.
	 */
	public GoToMemberAction(Class outlineTreeClass) {
		super("GoToType");
		this.outlineTreeClass = outlineTreeClass;
	}


	public void actionPerformed(ActionEvent e) {
		AbstractSourceTree tree = createTree();
		if (tree==null) {
			UIManager.getLookAndFeel().provideErrorFeedback(null);
			return;
		}
		JTextComponent tc = getTextComponent(e);
		if (tc instanceof RSyntaxTextArea) {
			RSyntaxTextArea textArea = (RSyntaxTextArea)tc;
			Window parent = SwingUtilities.getWindowAncestor(textArea);
			GoToMemberWindow gtmw = new GoToMemberWindow(parent, textArea, tree);
			setLocationBasedOn(gtmw, textArea);
			gtmw.setVisible(true);
		}
		else {
			UIManager.getLookAndFeel().provideErrorFeedback(null);
		}
	}


	/**
	 * Creates the outline tree.
	 *
	 * @return An instance of the outline tree.
	 */
	private AbstractSourceTree createTree() {
		AbstractSourceTree tree = null;
		try {
			tree = (AbstractSourceTree)outlineTreeClass.newInstance();
			tree.setSorted(true);
		} catch (RuntimeException re) { // FindBugs
			throw re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tree;
	}


	/**
	 * Centers the window in the text area.
	 *
	 * @param gtmw The window to center.
	 * @param textArea The parent text area to center it in.
	 */
	private void setLocationBasedOn(GoToMemberWindow gtmw,
									RSyntaxTextArea textArea) {
		Rectangle visibleRect = textArea.getVisibleRect();
		Dimension gtmwPS = gtmw.getPreferredSize();
		int x = visibleRect.x + (visibleRect.width-gtmwPS.width)/2;
		int y = visibleRect.y + (visibleRect.height-gtmwPS.height)/2;
		Point p = new Point(x, y);
		SwingUtilities.convertPointToScreen(p, textArea);
		gtmw.setLocation(p);
	}


}
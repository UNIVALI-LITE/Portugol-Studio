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

import br.univali.ps.ui.rstautil.tree.AbstractSourceTree;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.fife.ui.rsyntaxtextarea.focusabletip.TipUtil;
import org.fife.ui.rsyntaxtextarea.PopupWindowDecorator;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


/**
 * A popup window that displays a text field and tree, allowing the user to
 * jump to a specific part of code in the current source file.
 *
 * @author Robert Futrell
 * @version 1.0
 * @see GoToMemberAction
 */
public class GoToMemberWindow extends JWindow {

	private RSyntaxTextArea textArea;
	private JTextField field;
	private AbstractSourceTree tree;
	private Listener listener;


	/**
	 * Constructor.
	 *
	 * @param parent The parent window (hosting the text component).
	 * @param textArea The text area.
	 * @param tree The source tree appropriate for the current language.
	 */
	public GoToMemberWindow(Window parent, RSyntaxTextArea textArea,
							AbstractSourceTree tree) {

		super(parent);
		this.textArea = textArea;
		ComponentOrientation o = parent.getComponentOrientation();
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(TipUtil.getToolTipBorder());

		listener = new Listener();
		addWindowFocusListener(listener);
		parent.addComponentListener(listener);

		field = createTextField();
		contentPane.add(field, BorderLayout.NORTH);

		this.tree = tree;
		tree.setSorted(true);
		tree.setShowMajorElementsOnly(true);
		tree.setGotoSelectedElementOnClick(false);
		tree.setFocusable(false);
		tree.listenTo(textArea);
		tree.addMouseListener(listener);
		JScrollPane sp = new JScrollPane(tree);
		sp.setBorder(null);
		sp.setViewportBorder(BorderFactory.createEmptyBorder());
		contentPane.add(sp);

		Color bg = TipUtil.getToolTipBackground();
		setBackground(bg);
		field.setBackground(bg);
		tree.setBackground(bg);
		((DefaultTreeCellRenderer)tree.getCellRenderer()).setBackgroundNonSelectionColor(bg);

		// Give apps a chance to decorate us with drop shadows, etc.
		setContentPane(contentPane);
		PopupWindowDecorator decorator = PopupWindowDecorator.get();
		//System.out.println(decorator);
		if (decorator!=null) {
			decorator.decorate(this);
		}

		applyComponentOrientation(o);
		pack();
		JRootPane pane = getRootPane();
		InputMap im = pane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "EscapePressed");
		ActionMap am = pane.getActionMap();
		am.put("EscapePressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}


	/**
	 * Creates the text field allowing the user to enter filter text.
	 *
	 * @return The text field.
	 */
	private JTextField createTextField() {
		JTextField field = new JTextField(30);
		field.setUI(new BasicTextFieldUI());
		field.setBorder(new TextFieldBorder());
		field.addActionListener(listener);
		field.addKeyListener(listener);
		field.getDocument().addDocumentListener(listener);
		return field;
	}


	public void dispose() {
		listener.uninstall();
		super.dispose();
		// Force refocus of text area to prevent NPE in GoToMemberAction's
		// (TextAction's) getTextComponent(ActinEvent) when a different
		// component is focused and this action is executed twice.
		textArea.requestFocusInWindow();
	}


	/**
	 * Listens for events in this window.
	 */
	private class Listener extends MouseAdapter implements WindowFocusListener,
			ComponentListener, DocumentListener, ActionListener, KeyListener {

		public void actionPerformed(ActionEvent e) {
			if (tree.gotoSelectedElement()) {
				dispose();
			}
		}

		public void changedUpdate(DocumentEvent e) {
			handleDocumentEvent(e);
		}

		public void componentHidden(ComponentEvent e) {
			dispose();
		}

		public void componentMoved(ComponentEvent e) {
			dispose();
		}

		public void componentResized(ComponentEvent e) {
			dispose();
		}

		public void componentShown(ComponentEvent e) {
		}

		private void handleDocumentEvent(DocumentEvent e) {
			tree.filter(field.getText());
			tree.selectFirstNodeMatchingFilter();
		}

		public void insertUpdate(DocumentEvent e) {
			handleDocumentEvent(e);
		}

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					tree.selectNextVisibleRow();
					break;
				case KeyEvent.VK_UP:
					tree.selectPreviousVisibleRow();
					break;
			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount()==2) {
				tree.gotoSelectedElement();
				dispose();
			}
		}

		public void removeUpdate(DocumentEvent e) {
			handleDocumentEvent(e);
		}

		public void uninstall() {
			field.removeActionListener(this);
			field.getDocument().removeDocumentListener(this);
			tree.removeMouseListener(this);
			removeWindowFocusListener(this);
		}

		public void windowGainedFocus(WindowEvent e) {
		}

		public void windowLostFocus(WindowEvent e) {
			dispose();
		}
		
	}


	/**
	 * The border for the filtering text field.
	 */
	private static class TextFieldBorder implements Border {

		public Insets getBorderInsets(Component c) {
			return new Insets(2, 5, 3, 5);
		}

		public boolean isBorderOpaque() {
			return false;
		}

		public void paintBorder(Component c, Graphics g, int x, int y,
				int w, int h) {
			g.setColor(UIManager.getColor("controlDkShadow"));
			g.drawLine(x,y+h-1, x+w-1,y+h-1);
		}
		
	}


}
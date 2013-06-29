package br.univali.ps.ui.completion;

import javax.swing.ListCellRenderer;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class PortugolLanguageSuport extends AbstractLanguageSupport
{
        /**
	 * The completion provider, shared amongst all text areas editing Portugol.
	 */
	private PortugolCompletionProvider provider;


	/**
	 * Constructor.
	 */
	public PortugolLanguageSuport() {
		setParameterAssistanceEnabled(true);
		setShowDescWindow(true);
	}


	
        @Override
	protected ListCellRenderer createDefaultCompletionCellRenderer() {
		return new PortugolCellRender();
	}


	public PortugolCompletionProvider getProvider() {
		if (provider==null) {
			provider = new PortugolCompletionProvider();
		}
		return provider;
	}


        @Override
	public void install(RSyntaxTextArea textArea) {
		AutoCompletion ac = createAutoCompletion(getProvider());
		ac.install(textArea);
		installImpl(textArea, ac);
		textArea.setToolTipSupplier(getProvider());
	}


	
        @Override
	public void uninstall(RSyntaxTextArea textArea) {
		uninstallImpl(textArea);
		textArea.setToolTipSupplier(null);
	}   
}

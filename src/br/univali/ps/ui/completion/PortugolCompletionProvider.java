
package br.univali.ps.ui.completion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.fife.ui.autocomplete.*;


public class PortugolCompletionProvider extends LanguageAwareCompletionProvider{
    
        public PortugolCompletionProvider() {
		setDefaultCompletionProvider(createCodeCompletionProvider());
		setStringCompletionProvider(createStringCompletionProvider());
		setCommentCompletionProvider(createCommentCompletionProvider());
	}
        
        
        
	/**
	 * Adds shorthand completions to the code completion provider.
	 *
	 * @param codeCP The code completion provider.
	 */
	protected void addShorthandCompletions(DefaultCompletionProvider codeCP) {
		codeCP.addCompletion(new ShorthandCompletion(codeCP, "main",
								"int main(int argc, char **argv)"));
//for (int i=0; i<5000; i++) {
//	codeCP.addCompletion(new BasicCompletion(codeCP, "Number" + i));
//}
	}
        
        protected CompletionProvider createCodeCompletionProvider() {
		DefaultCompletionProvider cp = new DefaultCompletionProvider();
		loadCodeCompletionsFromXml(cp);
		addShorthandCompletions(cp);
                return cp;
	}
        
        /**
	 * Returns the provider to use when in a comment.
	 *
	 * @return The provider.
	 * @see #createCodeCompletionProvider()
	 * @see #createStringCompletionProvider()
	 */
	protected CompletionProvider createCommentCompletionProvider() {
		DefaultCompletionProvider cp = new DefaultCompletionProvider();
		cp.addCompletion(new BasicCompletion(cp, "FAZER:","to-do" ,"Um lembrete para realizar uma tarefa"));
		cp.addCompletion(new BasicCompletion(cp, "CONSERTE-ME:", "bug","Um problema que precisa ser arruamdo"));
		return cp;
	}
        
        /**
	 * Returns the completion provider to use when the caret is in a string.
	 *
	 * @return The provider.
	 * @see #createCodeCompletionProvider()
	 * @see #createCommentCompletionProvider()
	 */
	protected CompletionProvider createStringCompletionProvider() {
		DefaultCompletionProvider cp = new DefaultCompletionProvider();
		cp.addCompletion(new BasicCompletion(cp, "\\n", "Nova linha","Imprime uma nova linha"));
		return cp;
	}
        
        protected void loadCodeCompletionsFromXml(DefaultCompletionProvider cp) {
		ClassLoader cl = getClass().getClassLoader();
		String res = "br/univali/ps/ui/completion/portugol.xml";
		if (res!=null) { // Subclasses may specify a null value
			InputStream in = cl.getResourceAsStream(res);
			try {
				if (in!=null) {
					cp.loadFromXML(in);
					in.close();
				}
				else {
					cp.loadFromXML(new File(res));
				}
			} catch (IOException ioe) {
				ioe.printStackTrace(System.err);
			}
		}
	}
    }

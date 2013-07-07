package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.ps.ui.Editor;
import br.univali.ps.ui.rstautil.PortugolParser;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.fife.ui.autocomplete.*;

public final class PortugolCompletionProvider extends LanguageAwareCompletionProvider implements PropertyChangeListener
{
    private List<Completion> dynamicCompletions = new ArrayList<Completion>();
    private Editor.EscopoCursor escopoCursor;

    public PortugolCompletionProvider()
    {
        setDefaultCompletionProvider(createCodeCompletionProvider());
        setStringCompletionProvider(createStringCompletionProvider());
        setCommentCompletionProvider(createCommentCompletionProvider());
    }

    /**
     * Adds shorthand completions to the code completion provider.
     *
     * @param codeCP The code completion provider.
     */
    protected void addShorthandCompletions(DefaultCompletionProvider codeCP)
    {
        //codeCP.addCompletion(new ShorthandCompletion(codeCP, "main",
        //						"int main(int argc, char **argv)"));
//for (int i=0; i<5000; i++) {
//	codeCP.addCompletion(new BasicCompletion(codeCP, "Number" + i));
//}
    }

    protected CompletionProvider createCodeCompletionProvider()
    {
        DefaultCompletionProvider cp = new DefaultCompletionProvider();
        loadCodeCompletionsFromXml(cp);
        addShorthandCompletions(cp);
        addTemplateCompletions(cp);

        return cp;
    }

    /**
     * Returns the provider to use when in a comment.
     *
     * @return The provider.
     * @see #createCodeCompletionProvider()
     * @see #createStringCompletionProvider()
     */
    protected CompletionProvider createCommentCompletionProvider()
    {
        DefaultCompletionProvider cp = new DefaultCompletionProvider();
        cp.addCompletion(new BasicCompletion(cp, "FAZER:", "to-do", "Um lembrete para realizar uma tarefa"));
        cp.addCompletion(new BasicCompletion(cp, "CONSERTE-ME:", "bug", "Um problema que precisa ser arruamdo"));
        return cp;
    }

    /**
     * Returns the completion provider to use when the caret is in a string.
     *
     * @return The provider.
     * @see #createCodeCompletionProvider()
     * @see #createCommentCompletionProvider()
     */
    protected CompletionProvider createStringCompletionProvider()
    {
        DefaultCompletionProvider cp = new DefaultCompletionProvider();
        cp.addCompletion(new BasicCompletion(cp, "\\n", "Nova linha", "Imprime uma nova linha"));
        return cp;
    }

    protected void loadCodeCompletionsFromXml(DefaultCompletionProvider cp)
    {
        ClassLoader cl = getClass().getClassLoader();
        String res = "br/univali/ps/ui/rstautil/completion/portugol.xml";
        if (res != null)
        { // Subclasses may specify a null value
            InputStream in = cl.getResourceAsStream(res);
            try
            {
                if (in != null)
                {
                    cp.loadFromXML(in);
                    in.close();
                }
                else
                {
                    cp.loadFromXML(new File(res));
                }
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace(System.err);
            }
        }
    }

    private void updateGlobalSimbolsCompletions(DefaultCompletionProvider cp, ResultadoAnalise resultadoAnalise, Editor.EscopoCursor escopoCursor)
    {
        // Se houve erros sintáticos, mantemos o autocomplete gerado        
        // com a última ASA válida

        if (resultadoAnalise.getNumeroErrosSintaticos() == 0)
        {
            if (dynamicCompletions != null)
            {
                for (Completion completion : dynamicCompletions)
                {
                    cp.removeCompletion(completion);
                }
            }

            dynamicCompletions = new ASTCompletionFactory().createCompletions((ArvoreSintaticaAbstrataPrograma) resultadoAnalise.getAsa(), cp, escopoCursor);

            cp.addCompletions(dynamicCompletions);
        }
    }

    public void setEscopoCursor(Editor.EscopoCursor escopoCursor)
    {
        this.escopoCursor = escopoCursor;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (PortugolParser.PROPERTY_RESULTADO_ANALISE.equals(evt.getPropertyName()))
        {
            DefaultCompletionProvider cp = (DefaultCompletionProvider) getDefaultCompletionProvider();
            ResultadoAnalise resultadoAnalise = (ResultadoAnalise) evt.getNewValue();

            updateGlobalSimbolsCompletions(cp, resultadoAnalise, escopoCursor);
        }
    }

    private void addTemplateCompletions(DefaultCompletionProvider cp)
    {
        cp.addCompletion(new TemplateCompletion(cp, "enquanto", "Laço de repetição 'enquanto'", "enquanto(${condicao})\n{\n\t${cursor}\n}", "comando", explicacaoComandoEnquanto()));
        cp.addCompletion(new TemplateCompletion(cp, "para", "Laço de repetição 'para'", "para(inteiro ${i} = 0; ${i} < ${limite}; ${i}++)\n{\n\t${cursor}\n}", "comando", explicacaoComandoPara()));
    }

    private String explicacaoComandoPara()
    {
        return "<body><h2>Comando PARA</h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> <p>O comando <b>PARA</b>  fornece uma maneira compacta para repetir uma"
                + " sequencia de instruções.</p><p> Essa operação faz um loop "
                + "repetidamente até que uma determinada condição é satisfeita.</p>"
                + "<p>A forma de declaração do <b>PARA</b> pode ser expressa da seguinte forma:</p>"
                + "<p><b>para</b> ( &#60inicialização> ; &#60condição> ; &#60incremento> ) {</p>"
                + "<p>	&#60instruções></p>"
                + "<p>}</p>"
                + "<p>Ao utilizar esta versão do de declaração, tenha em mente que:</p>"
                + "<p># A inicialização inicializa a expressão loop; é executado uma vez, tal como o ciclo começa.</p>"
                + "<p># Quando a condição expressão é avaliada como false , o loop termina.</p>"
                + "<p># O incremento de expressão é invocada depois de cada iteração do loop.</p>";
    }

    private String explicacaoComandoEnquanto()
    {
        return "<body>	<h2> Comando Enquanto </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> <p> &nbsp; O comando ENQUANTO permite"
                + " que uma sequ&ecirc;ncia de instru&ccedil;&otilde;es sejam"
                + " executada v&aacute;rias vezes, at&eacute; que uma ou mais"
                + " condi&ccedil;&otilde;es sejam satisfeitas, ou seja, repete "
                + "um conjunto de instru&ccedil;&otilde;es sem que seja "
                + "necess&aacute;rio escreve-l&aacute; v&aacute;rias vezes. </p>"
                + "<p> &nbsp; Para utilizar o comando ENQUANTO voc&ecirc; "
                + "deve usar a palavra reservada <b>enquanto</b> e entre"
                + " par&ecirc;nteses colocar a condi&ccedil;&atilde;o, "
                + "ap&oacute;s o fecha par&ecirc;nteses deve se abrir e "
                + "fechar chaves e entre elas colocar a "
                + "instru&ccedil;&atilde;o(&otilde;es) a ser repetida enquanto"
                + " a condi&ccedil;&atilde;o(&otilde;es) for verdadeira. "
                + "</p>	<table class='tabela2'> <tr class='linha2'> "
                + "<td><b> COMANDO ENQUANTO</b></td></tr><tr class='linha1'><td><p>&nbsp; "
                + "&nbsp; <b>enquanto</b>(&lt;condi&ccedil;&atilde;o,condi&ccedil;&otilde;es&gt;) <br>"
                + "&nbsp; <b>{</b> <br>	&nbsp;&nbsp;&nbsp;&nbsp;	"
                + "&lt;intru&ccedil;&atilde;o,instru&ccedil;&otilde;es&gt; <br>"
                + "&nbsp; <b>}</b> <br>	</td></tr></table>";
    }
}

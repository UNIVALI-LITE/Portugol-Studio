package br.univali.ps.ui.rstautil;

import br.univali.ps.ui.rstautil.completion.ProvedorConclusaoCodigoBibliotecas;
import br.univali.ps.ui.rstautil.completion.ProvedorConclusaoCodigoPortugol;
import br.univali.ps.ui.rstautil.completion.RenderizadorConclusaoCodigoPortugol;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import javax.swing.ToolTipManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;

public class SuportePortugolImpl implements SuportePortugol
{
    private final PortugolParser portugolParser;
    private final AutoCompletion conclusaoCodigo;
    private final ProvedorConclusaoCodigoPortugol provedorConclusao;
    private final DobramentoCodigoPortugol dobramentoCodigoPortugol;
    private final RenderizadorConclusaoCodigoPortugol renderizador;

    public SuportePortugolImpl()
    {
        this.portugolParser = new PortugolParser();
        this.renderizador = new RenderizadorConclusaoCodigoPortugol();
        this.dobramentoCodigoPortugol = new DobramentoCodigoPortugol();
        this.provedorConclusao = criarProvedorConclusao();
        this.conclusaoCodigo = criarConclusaoCodigoPrograma();
        this.provedorConclusao.setConclusaoCodigo(conclusaoCodigo);
    }

    @Override
    public DobramentoCodigoPortugol getDobramentoCodigoPortugol()
    {
        return dobramentoCodigoPortugol;
    }
    
    @Override
    public void atualizar(RSyntaxTextArea textArea)
    {
        
    }

    @Override
    public PortugolParser getPortugolParser()
    {
        return portugolParser;
    }

    @Override
    public void instalar(RSyntaxTextArea textArea)
    {
        textArea.addParser(portugolParser); // Deve ser a primeira coisa a ser configurada

        FoldParserManager.get().addFoldParserMapping("text/por", dobramentoCodigoPortugol);
        ToolTipManager.sharedInstance().registerComponent(textArea);

        conclusaoCodigo.install(textArea);
        provedorConclusao.instalar(textArea);
    }

    private ProvedorConclusaoCodigoPortugol criarProvedorConclusao()
    {
        ProvedorConclusaoCodigoPortugol provedor = new ProvedorConclusaoCodigoPortugol();

        provedor.setParameterizedCompletionParams('(', ", ", ')');

        return provedor;
    }

    private AutoCompletion criarConclusaoCodigoPrograma()
    {
        AutoCompletion conclusao = new AutoCompletion(this.provedorConclusao)
        {
            @Override
            protected String getReplacementText(Completion c, Document doc, int start, int len)
            {
                String texto = super.getReplacementText(c, doc, start, len);

                if (c.getProvider() instanceof ProvedorConclusaoCodigoBibliotecas)
                {
                    try
                    {
                        texto = doc.getText(start, len);

                        texto = texto.substring(0, texto.lastIndexOf("."));
                        texto = texto.concat(".").concat(super.getReplacementText(c, doc, start, len));

                        return texto;
                    }
                    catch (BadLocationException ex)
                    {

                    }
                }

                return texto;
            }
        };

        conclusao.setAutoCompleteEnabled(true);
        conclusao.setAutoActivationDelay(1000);
        conclusao.setAutoActivationEnabled(true);
        conclusao.setAutoCompleteSingleChoices(false);
        conclusao.setParameterAssistanceEnabled(true);
        conclusao.setShowDescWindow(true);
        conclusao.setTriggerKey(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK));
        conclusao.setListCellRenderer(renderizador);
        conclusao.setParamChoicesRenderer(renderizador);

        return conclusao;
    }
}

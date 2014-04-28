package br.univali.ps.ui.rstautil;

import br.univali.ps.ui.rstautil.completion.ProvedorConclusaoCodigoPortugol;
import br.univali.ps.ui.rstautil.completion.RenderizadorConclusaoCodigoPortugol;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import javax.swing.ToolTipManager;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;

public final class SuporteLinguagemPortugol
{
    private final PortugolParser portugolParser;
    private final AutoCompletion conclusaoCodigo;
    private final ProvedorConclusaoCodigoPortugol provedorConclusao;
    private final DobramentoCodigoPortugol dobramentoCodigoPortugol;
    private final RenderizadorConclusaoCodigoPortugol renderizador;

    public SuporteLinguagemPortugol()
    {
        this.portugolParser = new PortugolParser();
        this.renderizador = new RenderizadorConclusaoCodigoPortugol();
        this.dobramentoCodigoPortugol = new DobramentoCodigoPortugol();
        this.provedorConclusao = criarProvedorConclusao();        
        this.conclusaoCodigo = criarConclusaoCodigoPrograma();
        this.provedorConclusao.setConclusaoCodigo(conclusaoCodigo);
    }

    public void atualizar(RSyntaxTextArea textArea)
    {
        textArea.forceReparsing(portugolParser);
    }

    public PortugolParser getPortugolParser()
    {
        return portugolParser;
    }

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
        AutoCompletion conclusao = new AutoCompletion(this.provedorConclusao);
        

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

package br.univali.ps.ui.rstautil;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * @author Elieser
 */
public interface SuportePortugol {

    void atualizar(RSyntaxTextArea textArea);

    DobramentoCodigoPortugol getDobramentoCodigoPortugol();

    PortugolParser getPortugolParser();

    void instalar(RSyntaxTextArea textArea);
    
}

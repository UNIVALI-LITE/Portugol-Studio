package br.univali.ps.ui;

import java.io.File;
import java.util.List;
import javax.swing.RootPaneContainer;

/**
 *
 * @author Elieser
 */
public interface TelaPrincipal extends RootPaneContainer
{
    String ACAO_EXIBIR_AJUDA = "Exibir ajuda";
    String ACAO_EXIBIR_DOCUMENTACAO_BIBLIOTECA = "Documentação das bibliotecas";

    PainelTabuladoPrincipal getPainelTabulado();

    void setArquivosIniciais(List<File> arquivos);
    
    void setVisible(boolean status);
}

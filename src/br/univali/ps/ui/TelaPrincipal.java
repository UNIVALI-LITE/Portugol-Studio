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
    
    PainelTabuladoPrincipal getPainelTabulado();

    void setArquivosIniciais(List<File> arquivos);
    
    void setVisible(boolean status);
}

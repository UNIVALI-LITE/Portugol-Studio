package br.univali.ps.ui;

import br.univali.ps.dominio.pack.PackDownloaderListener;
import java.io.File;
import java.util.List;

/**
 *
 * @author Elieser
 */
public interface ContextoDeTrabalho extends PackDownloaderListener
{

    void setArquivosIniciais(List<File> arquivos);
    
    void inicializar();
}

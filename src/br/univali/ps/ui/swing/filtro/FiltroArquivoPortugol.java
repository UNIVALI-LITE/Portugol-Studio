package br.univali.ps.ui.swing.filtro;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class FiltroArquivoPortugol extends FileFilter
{
    private static final String descricao = "Arquivos do Portugol (*.por, *.pex)";

    @Override
    public boolean accept(File arquivo)
    {
        return (arquivo.isDirectory() || arquivo.getName().toLowerCase().endsWith(".por") || arquivo.getName().toLowerCase().endsWith(".pex"));
    }

    @Override
    public String getDescription()
    {
        return descricao;
    }
}

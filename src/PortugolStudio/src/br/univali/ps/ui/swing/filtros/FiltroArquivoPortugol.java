package br.univali.ps.ui.swing.filtros;

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
    private static final String descricao = "Programa do Portugol (*.por)";

    @Override
    public boolean accept(File arquivo)
    {
        return (arquivo.isDirectory() || arquivo.getName().toLowerCase().endsWith(".por"));
    }

    @Override
    public String getDescription()
    {
        return descricao;
    }
}

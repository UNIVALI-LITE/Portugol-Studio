package br.univali.ps.ui.swing.filtros;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Luiz Fernando Noschang
 */
public class FiltroArquivo extends FileFilter
{
    private String descricao;
    private List<String> extensoes;

    public FiltroArquivo(String descricao, String... extensoes)
    {
        this.descricao = montarDescricao(descricao, extensoes);
        this.extensoes = Arrays.asList(extensoes);
    }
    
    private String montarDescricao(String descricao, String...extensoes)
    {
        StringBuilder construtorTexto = new StringBuilder();
        
        construtorTexto.append(descricao);
        construtorTexto.append(" (");
        
        for (int indice = 0; indice < extensoes.length; indice++)
        {
            construtorTexto.append("*.");
            construtorTexto.append(extensoes[indice]);
            
            if (indice < extensoes.length - 1)
            {
                construtorTexto.append(", ");
            }
        }
        
        construtorTexto.append(")");
        
        return construtorTexto.toString();
    }

    public List<String> getExtensoes()
    {
        return extensoes;
    }

    @Override
    public String getDescription()
    {
        return descricao;
    }    

    @Override
    public boolean accept(File caminho)
    {
        return (caminho.isDirectory() || extensoes.contains(extrairExtensao(caminho.getName())));
    }
    
    private String extrairExtensao(String nome)
    {
        int inicio = nome.lastIndexOf(".") + 1;
        
        return nome.substring(inicio, nome.length()).toLowerCase();
    }
}

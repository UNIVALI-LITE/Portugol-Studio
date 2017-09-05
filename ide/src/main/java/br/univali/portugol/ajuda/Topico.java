package br.univali.portugol.ajuda;

import java.io.File;

/**
 * @author Luiz Fernando Noschang
 */
public interface Topico
{
    public String getTitulo();
    
    public String getConteudo();
    
    public String getIcone();
    
    public Topicos getSubTopicos();
    
    public int getOrdem();
    
    public void setOrdem(int ordem);
    
    public File getArquivoOrigem();
    
    void setArquivoOrigem(File arquivo);
    
}

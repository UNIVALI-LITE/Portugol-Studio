package br.univali.portugol.ajuda;

import java.io.File;

/**
 *
 * @author Luiz Fernando
 */
public final class TopicoHtml implements Topico
{
    private String titulo;
    private String conteudo;
    private String icone;
    private int ordem;
    private Topicos subTopicos;
    private File arquivoOrigem;

    TopicoHtml(File arquivoOrigem)
    {
        this.ordem = Integer.MAX_VALUE;
        this.subTopicos = new Topicos();
        this.arquivoOrigem = arquivoOrigem;
    }
    
    @Override
    public String getTitulo()
    {
        return titulo;
    }

    void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    @Override
    public String getConteudo()
    {
        return conteudo;
    }

    void setConteudo(String conteudo)
    {
        this.conteudo = conteudo;
    }

    @Override
    public String getIcone()
    {
        return icone;
    }

    void setIcone(String icone)
    {
        this.icone = icone;
    }    

    @Override
    public Topicos getSubTopicos()
    {
        return subTopicos;
    }

    @Override
    public int getOrdem()
    {
        return ordem;
    }

    @Override
    public void setOrdem(int ordem)
    {
        this.ordem = ordem;
    }

    @Override
    public File getArquivoOrigem()
    {
        return arquivoOrigem;
    }

    @Override
    public void setArquivoOrigem(File arquivo)
    {
        this.arquivoOrigem = arquivo;
    }
}

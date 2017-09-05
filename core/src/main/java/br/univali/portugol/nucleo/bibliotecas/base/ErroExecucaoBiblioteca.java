package br.univali.portugol.nucleo.bibliotecas.base;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Luiz Fernando Noschang
 */
public class ErroExecucaoBiblioteca extends ErroExecucao
{
    private String mensagem;
    private Throwable causa;

    public ErroExecucaoBiblioteca(String mensagem)
    {
        this.mensagem = mensagem;
    }
    
    public ErroExecucaoBiblioteca(final Throwable causa)
    {
        this.causa = causa;
    }

    @Override
    public synchronized Throwable getCause()
    {
        return causa;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String construirMensagem() 
    {
        return mensagem;
    }
}

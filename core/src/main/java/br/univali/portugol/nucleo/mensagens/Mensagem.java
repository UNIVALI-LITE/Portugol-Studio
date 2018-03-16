package br.univali.portugol.nucleo.mensagens;

/**
 * Classe base para todas as mensagens geradas pelo Portugol.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public abstract class Mensagem extends Exception
{
    private String mensagem = null;

    public Mensagem()
    {

    }

    @Override
    public final String toString()
    {
        return getMensagem();
    }

    @Override
    public final String getMessage()
    {
        return getMensagem();
    }

    /**
     * Obtém o texto desta mensagem.
     * 
     * @return     o texto desta mensagem.
     * @since 1.0
     */
    public final String getMensagem()
    {
        if (mensagem == null)
            mensagem = construirMensagem();

        return mensagem;
    }
    
    /**
     * Constrói o texto desta mensagem.
     * 
     * @return      o texto da mensagem.
     * @since 1.0
     */
    protected abstract String construirMensagem();
}
package br.univali.portugol.ajuda;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroTopicoNaoEncontrado extends Exception
{
    public ErroTopicoNaoEncontrado(String titulo)
    {
        super(String.format("O tópico '%s' não foi encontrado", titulo));
    }    
}

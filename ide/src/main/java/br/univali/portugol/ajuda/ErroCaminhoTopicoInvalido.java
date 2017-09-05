package br.univali.portugol.ajuda;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroCaminhoTopicoInvalido extends Exception
{
    public ErroCaminhoTopicoInvalido(String caminho)
    {
        super(String.format("'%' não é um caminho de pesquisa válido", caminho));
    }
}

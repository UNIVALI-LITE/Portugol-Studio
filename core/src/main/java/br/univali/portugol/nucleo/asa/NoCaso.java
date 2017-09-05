package br.univali.portugol.nucleo.asa;

import java.util.List;

/**
 * Representa um caso do comando escolha.
 * <p>
 * Um caso funciona de forma semelhante a um desvio condicional. O comando
 * escolha irá avaliar uma expressão e irá compará-la com a expressão
 * representada pelo caso. Se ambas forem equivalentes, o caso será escolhido e
 * os blocos de comandos definidos por ele serão executados.
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 * @see NoEscolha
 */
public final class NoCaso extends NoBloco
{
    private List<NoBloco> blocos;
    private NoExpressao expressao;

    /**
     *
     * @param expressao a expressão que será avaliada pelo comando escolha para
     * determinar se este caso deve ser escolhido ou não.
     * @since 1.0
     */
    public NoCaso(NoExpressao expressao)
    {
        this.expressao = expressao;
    }

    /**
     * Obtém a lista de blocos que serão executados se este caso for escolhido
     * pelo comando escolha.
     *
     * @return a lista de blocos a serem executados.
     * @since 1.0
     */
    public List<NoBloco> getBlocos()
    {
        return blocos;
    }

    /**
     *
     * Obtém a expressão representada por este caso. Esta expressão será
     * avaliada pelo comando escolha para determinar se este caso deve ser
     * escolhido ou não.
     *
     * @return a expressão a ser avaliada.
     * @since 1.0
     */
    public NoExpressao getExpressao()
    {
        return expressao;
    }

    /**
     * Define a lista de blocos que deverão ser executados se este caso for
     * escolhido.
     *
     * @param blocos a lista de blocos.
     * @since 1.0
     */
    public void setBlocos(List<NoBloco> blocos)
    {
        this.blocos = blocos;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
}

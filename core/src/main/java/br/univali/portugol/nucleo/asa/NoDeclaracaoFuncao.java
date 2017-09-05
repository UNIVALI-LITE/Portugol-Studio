package br.univali.portugol.nucleo.asa;

import java.util.List;

/**
 * Representa a declaração de uma função no código fonte.
 * <p>
 * No Portugol, uma função é tratada como uma declaração para facilitar a
 * implementação do analisador semântico e do interpretador. Isto também permite
 * que uma função seja acessada globalmente por todas as outras funções,
 * independente da ordem em que aparecem no código fonte, ao contrário do que
 * ocorre na linguagem C, por exemplo.
 * <p>
 * Exemplo de declaração de função:  <code><pre>
 *      programa
 *      {
 *           funcao declarandoUmaFuncao(inteiro a, cadeia b)
 *           {
 *                escreva("Este é um exemplo de declaração de função no Portugol!")
 *           }
 *
 *           funcao real declarandoOutraFuncao()
 *           {
 *                retorne 25.98
 *           }
 *      }
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoDeclaracaoFuncao extends NoDeclaracao
{
    private final Quantificador quantificador;
    private List<NoBloco> blocos;
    private List<NoDeclaracaoParametro> parametros;

    /**
     *
     * @param nome o nome da função.
     *
     * @param tipoDado o tipo de dado retornado pela função.
     *
     * @param quantificador o quantificador do retorno da função (se o tipo de
     * dado retornado for diferente de {@link TipoDado#VAZIO}).
     *
     * @since 1.0
     */
    public NoDeclaracaoFuncao(String nome, TipoDado tipoDado, Quantificador quantificador)
    {
        super(nome, tipoDado, true);
        this.quantificador = quantificador;
    }

    /**
     * Obtém o quantificador do retorno da função (se o tipo de dado retornado
     * for diferente de {@link TipoDado#VAZIO}). Serve para indicar se a função
     * irá retornar um único valor, um vetor ou uma matriz.
     *
     * @return o quantificador do retorno da função.
     *
     * @since 1.0
     */
    public Quantificador getQuantificador()
    {
        return quantificador;
    }

    /**
     * Obtém a lista de blocos da função. Estes blocos serão executados pelo
     * interpretador do Portugol a cada chamada desta função.
     *
     * @return a lista de blocos da função.
     *
     * @since 1.0
     */
    public List<NoBloco> getBlocos()
    {
        return blocos;
    }

    /**
     * Define a lista de blocos da função.
     *
     * @param blocos a lista de blocos a serem executados quando esta função for
     * chamada.
     *
     * @since 1.0
     */
    public void setBlocos(List<NoBloco> blocos)
    {
        this.blocos = blocos;
    }

    /**
     * Obtém a lista de parâmetros esperados por esta função. A lista poderá
     * estar vazia caso a função não necessite de parâmetros.
     *
     * @return a lista de parâmetros esperados.
     *
     * @since 1.0
     */
    public List<NoDeclaracaoParametro> getParametros()
    {
        return parametros;
    }

    /**
     * Define a lista de parãmetros esperados por esta função.
     *
     * @param parametros Define a lista de parâmetros esperados por esta função.
     *
     * @since 1.0
     */
    public void setParametros(List<NoDeclaracaoParametro> parametros)
    {
        this.parametros = parametros;
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

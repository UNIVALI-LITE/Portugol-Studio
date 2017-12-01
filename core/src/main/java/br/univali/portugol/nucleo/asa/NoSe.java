package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.programa.Estado;

import java.util.List;

/**
 * Representa um desvio condicional <code>se</code> no código fonte.
 * <p>
 * O comando <code>se</code> serve para desviar a execução do programa baseado em 
 * uma condição.
 * <p>
 * Este comando possui uma expessão lógica que será avaliada. Caso esta expressão seja 
 * verdadeira uma lista específica de comandos será executada, caso seja falsa, uma lista
 * alternativa de comandos será executada (se houver). Para definir a lista de comandos que 
 * serão executados caso a condição não seja atendida, deve-se usar a palavra reservada
 * <code>senao</code>.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploSe()
 *      {
 *           logico estudo = verdadeiro
 *           logico aprendo
 *           logico ficoInteligente
 *           logico ficoBurro
 * 
 *           se (estudo)
 *           {
 *                aprendo = verdadeiro
 * 
 *                se (aprendo)
 *                {
 *                     ficoInteligete = verdadeiro
 *                     ficoBurro = falso
 *                }
 *                senao
 *                {
 *                    ficoInteligente = falso
 *                    ficoBurro = verdadeiro
 *                }
 *           }
 *           senao
 *           {
 *                aprendo = falso
 *                ficoInteligente = falso
 *                ficoBurro = verdadeiro
 *           }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoSe extends NoBloco
{
    private NoExpressao condicao;
    private List<NoBloco> blocosFalsos;
    private List<NoBloco> blocosVerdadeiros;

    /**
     * 
     * @param condicao     a expressão que será avaliada para determinar quais blocos 
     *                     de comandos serão executados.
     * @since 1.0
     */
    public NoSe(NoExpressao condicao)
    {
        this.condicao = condicao;
    }

    /**
     * Obtém a expressão que será avaliada para determinar quais blocos de comandos serão executados.
     * 
     * @return     a expressão que será avaliada para determinar quais blocos de comandos serão executados.
     * @since 1.0
     */
    public NoExpressao getCondicao()
    {
        return condicao;
    }

    /**
     * Define a lista de blocos que serão executados se a expressão avaliada for falsa.
     * 
     * @param blocosFalsos     a lista de blocos que serão executados se a expressão avaliada for falsa.
     * @since 1.0
     */
    public void setBlocosFalsos(List<NoBloco> blocosFalsos)
    {
        this.blocosFalsos = blocosFalsos;
    }

    /**
     * Define a lista de blocos que serão executados se a expressão avaliada for verdadeira.
     * 
     * @param blocosVerdadeiros     a lista de blocos que serão executados se a expressão avaliada for verdadeira.
     * @since 1.0
     */
    public void setBlocosVerdadeiros(List<NoBloco> blocosVerdadeiros)
    {
        this.blocosVerdadeiros = blocosVerdadeiros;
    }

    /**
     * Obtém a lista de blocos que serão executados se a expressão avaliada for falsa.
     * 
     * @return     a lista de blocos que serão executados se a expressão avaliada for falsa.
     * @since 1.0
     */
    public List<NoBloco> getBlocosFalsos()
    {
        return blocosFalsos;
    }

    /**
     * Obtém a lista de blocos que serão executados se a expressão avaliada for verdadeira.
     * 
     * @return     a lista de blocos que serão executados se a expressão avaliada for verdadeira.
     * @since 1.0
     */
    public List<NoBloco> getBlocosVerdadeiros()
    {
        return blocosVerdadeiros;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }

    @Override
    public boolean ehParavel(Estado estado)
    {
        if(getCondicao() != null){
            return super.ehParavel(estado) || (getCondicao().ehParavel(estado) && estado == Estado.BREAK_POINT);
        }
        return super.ehParavel(estado);
    }
    
    
}

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
public final class NoSenao extends NoBloco
{
    private List<NoBloco> blocosFalsos;
    /**
     * 
     * @param condicao     a expressão que será avaliada para determinar quais blocos 
     *                     de comandos serão executados.
     * @since 1.0
     */
    public NoSenao()
    {
        
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
        return super.ehParavel(estado);
    }
    
    
}

package br.univali.portugol.nucleo.asa;

/**
 * Representa uma operação de negação lógica no código fonte.
 * <p>
 * A negação lógica está sempre associada com uma expressão do tipo {@link TipoDado#LOGICO}.
 * e serve para inverter o valor desta expressão. Uma expressão verdadeira, ao ser negada se 
 * torna falsa e uma expressão falsa, ao ser negada se torna verdadeira.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploNegacaoLogica()
 *      {
 *           logico taChovendo = verdadeiro                     // taChovendo = verdadeiro
 *           logico temSol = nao(taChovendo)                    // temSol = falso
 * 
 *           taChovendo = falso                                 // taChovendo = falso
 *           temSol = nao(taChovendo)                           // temSol = verdadeiro
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * @see NoExpressao
 * @see TipoDado
 */
public final class NoNao extends NoExpressao
{
    private NoExpressao expressao;

    /**
     * 
     * @param expressao     a expressão que está sendo negada. Nenhuma verificação é feita no momento da criação deste nó para garantir
     *                      que a expressão passada seja do tipo {@link TipoDado#LOGICO}. Esta verificação é feita pelo analisador
     *                      semântico do Portugol.
     * @since 1.0
     */
    public NoNao(NoExpressao expressao)
    {
        this.expressao = expressao;
    }

    /**
     * 
     * @return     a expressão que está sendo negada
     * @since 1.0
     */
    public NoExpressao getExpressao()
    {
        return expressao;
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
    public TipoDado getTipoResultante()
    {
        return expressao.getTipoResultante();
    }
}

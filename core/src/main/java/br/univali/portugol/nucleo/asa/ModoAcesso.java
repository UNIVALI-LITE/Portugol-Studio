package br.univali.portugol.nucleo.asa;

/**
 * Utilizado na classe {@link NoDeclaracaoParametro} para definir a forma com que uma variável será acessada ao ser passada 
 * como parâmetro de uma função.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public enum ModoAcesso
{
    /**
     * Define que, se a expressão passada através do parâmetro for uma variável, o parâmetro irá receber uma cópia do valor desta 
     * variável para ser utilizada dentro da função. Desta forma, mesmo que o valor do parâmetro seja modificado dentro da função,
     * após a chamada o valor da variável estará inalterado.
     * 
     * <code><pre>
     * 
     *     funcao exemploModoAcesso(inteiro paramA, inteiro paramB)
     *     {
     *          paramA = paramA - 5
     *          paramB = paramB + paramA
     *          escreva("Os parâmetros paramA e paramB foram passados por valor!")
     *     }
     * 
     * </pre></code>
     * 
     * @since 1.0
     */
    POR_VALOR, 
    
    /**
     * Define que, se a expressão passada através do parâmetro for uma variável, o parâmetro irá receber uma referência direta (ponteiro) 
     * a esta variável para ser utilizada dentro da função. Desta forma, se o valor do parâmetro for modificado dentro da função, após a 
     * chamada o valor também terá sido alterado na variável.
     * 
     * <code><pre>
     * 
     *     funcao exemploModoAcesso(inteiro paramA, inteiro &paramB)
     *     {
     *          paramA = paramA - 5
     *          paramB = paramB + paramA
     *          escreva("O parâmetro paramA foi passado por valor e o parâmetro paramB foi passado por referência!")
     *     }
     * 
     * </pre></code>
     * 
     * @since 1.0
     */
    POR_REFERENCIA
    
}

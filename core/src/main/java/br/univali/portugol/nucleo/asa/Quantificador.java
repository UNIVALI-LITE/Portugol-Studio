package br.univali.portugol.nucleo.asa;

/**
 * Utilizado em declarações de funções para quantificar os parâmetros e o retorno 
 * da função.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public enum Quantificador
{
    /**
     * Esta enumeração define que o valor retornado na função ou recebido por parâmetro
     * será um único valor.
     * <p>
     * Exemplo:
     * <code><pre>
     * 
     *      funcao inteiro exemploQuantificador()
     *      {
     *           escreva("O quantificador desta função é: VALOR")
     *      }
     * 
     * </pre></code>
     * 
     * @since 1.0
     */
    VALOR, 
    
    /**
     * Esta enumeração define que o valor retornado na função ou recebido por parãmetro
     * será um vetor. Este quantificador é definido pelo operador '[]'.
     * <p>
     * Exemplo:
     * <code><pre>
     * 
     *      funcao inteiro[] exemploQuantificador(inteiro param1[])
     *      {
     *           escreva("O quantificador da função é VETOR.")
     *           escreva("O quantificador do parâmetro 'param1' também é VETOR.")
     *      }
     * 
     * </pre></code>
     * 
     * @since 1.0
     */
    VETOR, 
    
    /**
     * Esta enumeração define que o valor retornado na função ou recebido por parâmetro
     * será uma matriz. Este quantificador é definido pelo operador '[][]'.
     * <p>
     * Exemplo:
     * <code><pre>
     * 
     *      funcao inteiro[][] exemploQuantificador(inteiro param1[][])
     *      {
     *           escreva("O quantificador da função é MATRIZ.")
     *           escreva("O quantificador do parâmetro 'param1' também é MATRIZ.")
     *      }
     * 
     * </pre></code>
     * 
     * @since 1.0
     */
    MATRIZ
}

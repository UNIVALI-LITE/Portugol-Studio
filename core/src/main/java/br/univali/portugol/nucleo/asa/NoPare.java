package br.univali.portugol.nucleo.asa;

/**
 * Representa o comando <code>pare</code> no código fonte.
 * <p>
 * O comando <code>pare</code> é utilizado dentro dos laços de repetição para interromper a
 * execução do laço. Quando este comando é chamado, o laço é interrompido imediatamente e 
 * o programa continua sua execução normal, executando os blocos logo após o laço.
 * <p>
 * O comando <code>pare</code> só pode ser utilizado dentro dos laços de repetição e caso haja
 * laços de repetição aninhados, somente o laço no qual o comando foi chamado será interrompido.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploPare()
 *      {
 *           logico corintiansPerdeLibertadores = verdadeiro
 * 
 *           enquanto (corintiansPerdeLibertadores)
 *           {
 *                zoarOsCorintianos()   // Isto só será executado uma vez! :-( 
 *                pare                  // É melhor interrompermos, ou ficaremos zoando os corintianos eternamente! :-D
 *           }
 * 
 *           para (inteiro i = 1; i <= 10; i++)
 *           {
 *                escreva("Isto será executado 10 vezes")
 * 
 *                para (inteiro j = 1; j <= 10; j++)
 *                {
 *                     escreva("Isto será executado 5 vezes para cada iteração de i")
 * 
 *                     se (j == 5) pare
 *                }
 *           }
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see NoRetorne
 */
public final class NoPare extends NoBloco
{
    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
}

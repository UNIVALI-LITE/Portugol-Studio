package br.univali.portugol.nucleo.asa;

/**
 * Esta enumeração representa a operação lógica <code>ou</code> no código fonte.
     * <p>
     * A operação <code>ou</code> é uma operação lógica especial que equivale à operação booleana OR.
     * No Portugol, a operação <code>ou</code> é representada no código fonte pelo operador "ou".
     * O operando esquerdo e o operando direito desta operação só podem ser expressões lógicas. Ao ser 
     * avaliada, esta operação retorna um valor lógico que obeddec à seguinte tabela verdade:
     * <div class="userTable">
     *     <table>
     *        <col width="34%"/>
     *        <col width="34%"/>
     *        <col width="32%"/>
     *        <thead>
     *            <tr>
     *                <th>Operando Esquerdo</th><th>Operando Direito</th><th>Resultado</th>
     *            </tr>
     *        </thead>
     *        <tbody>
     *            <tr class="userTable_oddRow">
     *                <td>falso</td><td>falso</td><td>falso</td>
     *            </tr>
     *            <tr class="userTable_evenRow">
     *                <td>verdadeiro</td><td>falso</td><td>verdadeiro</td>
     *            </tr>
     *            <tr class="userTable_oddRow">
     *                <td>falso</td><td>verdadeiro</td><td>verdadeiro</td>
     *            </tr>
     *            <tr class="userTable_evenRow">
     *                <td>verdadeiro</td><td>verdadeiro</td><td>verdadeiro</td>
     *            </tr>
     *         </tbody>
     *      </table>
     * </div>
     * </p>
     * A operação <code>ou</code> pode ser utilizada utilizada para realizar o controle de
     * laços de repetição e desvios condicionais.
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public class NoOperacaoLogicaOU extends NoOperacaoLogica
{

    public NoOperacaoLogicaOU(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
    {
        super(operandoEsquerdo, operandoDireito);
    }
    
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
}

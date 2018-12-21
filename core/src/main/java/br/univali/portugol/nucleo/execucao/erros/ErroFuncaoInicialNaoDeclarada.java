package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 * Erro gerado pelo interpretador do Portugol quando a função que dá início ao 
 * programa não foi declarada.
 * <p>
 * O Portugol permite definir qual das funções do programa será utilizada como ponto de partida.
 * Assim é possível obter resultados diferentes em um mesmo programa apenas alternando sua função inicial.
 * Se a função definida como a função inicial não tiver sido declarada, este erro será lançado
 * pelo interpretador. Por padrão, a função inicial é a função "inicio", mas pode ser utiilizada qualquer
 * outra função, sem restrição quanto ao tipo de retorno e ao número de parâmetros.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *     programa
 *     {
 *          logico calculou = falso  // Variável de controle para evitar uma recursão infinita
 *          inteiro numero = 200
 * 
 *          funcao subtrair()
 *          {
 *               numero = numero - 50
 *        
 *               se (nao calculou)
 *               {
 *                    calculou = verdadeiro
 *                    dividir()
 *                    imprimir()
 *               }
 *          }
 * 
 *          funcao dividir()
 *          {
 *               numero = numero / 2
 *         
 *               se (nao calculou)
 *               {
 *                    calculou = verdadeiro
 *                    subtrair()
 *                    imprimir()
 *               }
 *          }
 * 
 *          funcao imprimir()
 *          {
 *               escreva(numero)
 *          }
 *     }
 * </pre></code>
 * <p>
 * A saída deste programa será diferente dependendo de qual função for definida
 * como a função inicial:
 * 
 * <div class="userTable">
 *     <table>
 *        <col width="50%"/>
 *        <col width="50%"/>
 *        <thead>
 *            <tr>
 *                <th>Função Inicial</th><th>Saída</th>
 *            </tr>
 *        </thead>
 *        <tbody>
 *            <tr class="userTable_oddRow">
 *                <td>subtrair</td><td>75</td>
 *            </tr>
 *            <tr class="userTable_evenRow">
 *                <td>dividir</td><td>50</td>
 *            </tr>
 *            <tr class="userTable_oddRow">
 *                <td>imprimir</td><td>200</td>
 *            </tr>
 *         </tbody>
 *      </table>
 * </div>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 *
 */
public final class ErroFuncaoInicialNaoDeclarada extends ErroExecucao
{
    private String nomeFuncaoInicial;
    private String codigo = "ErroExecucao.ErroFuncaoInicialNaoDeclarada";

    /**
     * 
     * @param nomeFuncaoInicial     o nome da função definida como função inicial.
     * @since 1.0
     */
    public ErroFuncaoInicialNaoDeclarada(String nomeFuncaoInicial)
    {
        this.nomeFuncaoInicial = nomeFuncaoInicial;
        super.setCodigo(codigo);
    }

    /**
     * Obtém o nome da função definida como função inicial.
     * 
     * @return     o nome da função definida como função inicial.
     * @since 1.0
     */
    public String getNomeFuncaoInicial()
    {
        return nomeFuncaoInicial;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("A função principal \"");
        construtorString.append(nomeFuncaoInicial);
        construtorString.append("\" não foi declarada.");

        return construtorString.toString();
    }
}
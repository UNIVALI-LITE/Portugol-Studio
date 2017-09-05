package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoOperacao;
import br.univali.portugol.nucleo.asa.NoExpressaoLiteral;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 * Erro gerado pelo analisador semântico quando uma operação tenta alterar o valor de uma 
 * expressão constante.
 * <p>
 * Uma expressão constante pode ser uma variável, vetor ou matriz
 * declarados com a palavra reservada const ou um tipo primitivo literal.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploOperacaoInvalida()
 *      { 
 *            const inteiro valor = 50                  // Não gera erro pois está na inicialização
 *            const inteiro vetor[3] = {1, 2, 3}        // Não gera erro pois está na inicialização
 * 
 *            valor +=51                                // Gera erro
 *            valor++                                   // Gera erro
 *            51 -= 90                                  // Gera erro
 *            vetor[1]--                                // Gera erro
 *            parametroPassadoPorReferencia(13)         // Gera erro
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 * @see ErroOperandoEsquerdoAtribuicaoConstante
 */
public final class ErroAtribuirEmExpressao extends ErroSemantico
{
    private NoBloco operacao;
    private NoExpressao expressao;

    /**
     * 
     * @param operacao        a operação que está sendo realizada com a expressão constante.
     * @param expressao       a expressão constante que está sendo alterada.
     */
    public ErroAtribuirEmExpressao(NoBloco operacao, NoExpressao expressao)
    {
        super(operacao.getTrechoCodigoFonte(), "ErroSemantico.ErroOperacaoComExpressaoConstante");

        this.operacao = operacao;
        this.expressao = expressao;
    }

    /**
     * Obtém a operação que está sendo realizada com a expressão constante.
     * 
     * @return     a operação que está sendo realizada com a expressão constante.
     * @since 1.0
     */
    public NoBloco getOperacao()
    {
        return operacao;
    }

    /**
     * Obtém a expressão constante que está sendo alterada.
     * 
     * @return     a expressão constante que está sendo alterada.
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
    protected String construirMensagem()
    {
        if (operacao instanceof NoOperacao) return construirMensagemAtribuicao();

        return null;
    }

    /**
     * Constrói uma mensagem de erro personalizada para a operacao de atribuição.
     * 
     * @return     a mensagem de erro personalizada.
     * @since 1.0
     * 
     * @see NoOperacao
     * @see OperacaoAntiga#ATRIBUICAO
     */    
    private String construirMensagemAtribuicao()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append("Não é possível realizar uma atribuição à ");
                
        if (expressao instanceof NoExpressaoLiteral)
        {
            builder.append("um valor literal.");
        }
        else if (expressao instanceof NoChamadaFuncao)
        {
            builder.append("uma chamada de função.");
        }
        else
        {
            builder.append("uma expressão.");
        }
            
        builder.append(" Você só pode realizar atribuições à variáveis, vetores ou matrizes que não tenham sido declarados como constantes. Se você estiver tentando comparar a igualdade de duas expressões, utilize o operador '==' ao invés do operador '='");

        
        return builder.toString();
    }
}
package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 * Erro gerado pelo analisador sintático quando uma cadeia não é finalizada corretamente.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploCadeiaIncompleta()
 *      {
 *           cadeia var = "Esta cadeia não foi finalizada corretamente
 *      }
 * 
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */

public final class ErroCadeiaIncompleta extends ErroSintatico
{
    /**
     * 
     * @param linha              a linha onde o erro ocorreu.
     * @param coluna             a coluna onde o erro ocorreu.
     * @param mensagemPadrao     a mensagem padrão gerada pelo analisador sintático para este tipo de erro. Será 
     *                           utilizada caso não seja construída uma mensagem personalizada para este erro.
     * @since 1.0
     */
    public ErroCadeiaIncompleta(int linha, int coluna, String mensagemPadrao) 
    {
        super(linha, coluna);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem() 
    {
        return "A expressão do tipo 'cadeia' não foi finalizada corretamente. Insira o caracter '\"' para corrigir o problema. ";
    }
}

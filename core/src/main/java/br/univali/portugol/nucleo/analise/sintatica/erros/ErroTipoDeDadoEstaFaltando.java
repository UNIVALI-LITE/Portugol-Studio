package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 * Erro gerado pelo analisador sintático quando o tipo de dado não foi informado
 * na declaração de variável.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *     funcao exemploTipoDadoFaltando()
 *     {
 *          const var = 45              // Gera erro, pois o tipo de dado da variável não foi informado
 *          const inteiro var2 = 45     // Não gera erro, pois o tipo de dado da variável foi informado
 *     }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */

public final class ErroTipoDeDadoEstaFaltando extends ErroSintatico
{
    /**
     * 
     * @param linha      a linha onde o erro ocorreu.
     * @param coluna     a coluna onde o erro ocorreu.
     * @since 1.0
     */
    public ErroTipoDeDadoEstaFaltando(int linha, int coluna)
    {
        super(linha, coluna, "ErroSintatico.ErroTipoDeDadoEstaFaltando");
    }    
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
       return "Você esqueceu de informar o tipo de dado da variável.";
    }
}

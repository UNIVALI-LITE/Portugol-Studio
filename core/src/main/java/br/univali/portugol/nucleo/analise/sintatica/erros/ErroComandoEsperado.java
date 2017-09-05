package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 * Erro gerado pelo analisador sintático quando um escopo simples não possui
 * comandos.
 * <p>
 * No Portugol, existem dois tipos de escopo, o escopo simples e o escopo composto.
 * O escopo simples suporta apenas um subcomando e obriga que este comando esteja presente.
 * O escopo simples suporta vários comandos, no entanto, pode estar vazio, pois é delimitado
 * pelos tokens "{}".
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploErroEscopo()
 *      {
 *           enquanto (verdadeiro)
 *                escreva("Isto é um escopo simples! Suporta apenas um subcomando!")
 * 
 *           enquanto (verdadeiro)
 *           {
 *               escreva("Isto é um escopo composto!")
 *               escreva("Suporta vários subcomandos")
 *           }
 * 
 *           enquanto (verdadeiro)
 *           {
 *                // Se o escopo composto estiver vazio, não gera erro!
 *           }
 * 
 *           enquanto (verdadeiro)
 *                // Mas, se o escopo simples estiver vazio, gera erro!
 * 
 *      }
 * 
 * </pre><code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */

public final class ErroComandoEsperado extends ErroSintatico
{
    /**
     * 
     * @param linha      a linha onde o erro ocorreu
     * @param coluna     a coluna onde o erro ocorreu.
     * @since 1.0
     */
    public ErroComandoEsperado(int linha, int coluna)
    {
        super(linha, coluna,"ErroSintatico.ErroComandoEsperado");
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        return "Esta construção espera um comando. Insira um comando ou inicie um novo escopo utilizando os caracteres '{' e '}'.";
    }
}

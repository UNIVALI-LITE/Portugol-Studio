package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 * Erro gerado pelo analisador sintático quando uma expressão lógica ou aritmética está
 * incompleta.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *     funcao exemploExpressaoIncompleta()
 *     {
 *          inteiro var1 = 23 +      // Gera este erro
 *          inteiro var2 = ( + 12)   // Gera este erro
 *        
 *          se (var 1 < )            // Gera este erro
 *          {
 * 
 *          }
 *     }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */

public final class ErroRealComVirgula extends ErroSintatico
{
    /**
     * 
     * @param linha      a linha onde o erro ocorreu.
     * @param coluna     a coluna onde o erro ocorreu.
     * @since 1.0
     */
    public ErroRealComVirgula(int linha, int coluna)
    {
        super(linha, coluna,"ErroSintatico.ErroExpressaoIncompleta");
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        return "A expressão foi formada utilizando vírgulas. Valores reais devem ser expressados utilizando pontos. ex: 2.75 ";
    }    
}

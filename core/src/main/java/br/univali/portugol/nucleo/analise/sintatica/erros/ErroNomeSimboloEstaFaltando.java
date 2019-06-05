package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 * Erro gerado pelo analisador sintático quando o nome do símbolo não é informado
 * na declaração.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      /*
 *       * Esta declaração de função gera erro, pois o nome do
 *       * primeiro parãmetro está faltando.
 *       *&#47;
 * 
 *      funcao exemploSimboloFaltando(inteiro , real param2)
 *      {
 *           inteiro = 56    // Esta declaração de variável também gera erro, pois falta o nome da variável
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public final class ErroNomeSimboloEstaFaltando extends ErroSintatico
{
    private String contexto;
    private String codigo = "ErroSintatico.ErroNomeSimboloEstaFaltando.";
    
    /**
     * 
     * @param linha        a linha onde o erro ocorreu.
     * @param coluna       a coluna onde o erro ocorreu.
     * @param contexto     o contexto em que o analisador sintático se encontrava no momento do erro. 
     * @since 1.0
     */
    public ErroNomeSimboloEstaFaltando(int linha, int coluna, String contexto)
    {
        super(linha, coluna);
        this.contexto = contexto;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        if (contexto.equals("parametro"))
        {
            super.setCodigo(codigo+"1");
            return "O nome do parâmetro da função não foi informado";
        }
        else if (contexto.equals("declaracaoFuncao"))
        {
            super.setCodigo(codigo+"2");
            return "O nome da função não foi informado";
        }
        else 
        {
            super.setCodigo(codigo+"3");
            return "O nome da variável não foi informado";
        }
    }
}

package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.Stack;

/**
 * Erro gerado pelo analisador sintático quando um escopo não foi fechado corretamente
 * pela omissão do token "}".
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *     funcao exemploEscopo()
 *     {
 *          enquanto (verdadeiro)
 *          {
 *               // Gera um erro
 *          
 *     }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noscgang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 * @see ErroEscopoNaoFoiAbertoCorretamente
 */
public final class ErroEscopo extends ErroSintatico
{
    public static enum Tipo { ABERTURA, FECHAMENTO };
    
    private final Tipo tipo;
    private final String contexto;
    private String codigo = "ErroSintatico.ErroEscopo.";

    /**
     * 
     * @param linha        a linha onde o erro ocorreu.
     * @param coluna       a coluna onde o erro ocorreu.
     * @param contexto     o contexto/escopo em que o analisador sintático se encontrava quando o erro ocorreu.
     * @since 1.0
     */    
    public ErroEscopo(int linha, int coluna, Tipo tipo, String contexto)
    {
        super(linha, coluna);
        this.tipo = tipo;
        this.contexto = contexto;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorTexto = new StringBuilder("O escopo");
        
        switch (contexto)
        {
            case "arquivo": 
                construtorTexto.append(" do programa ");
                codigo += "1";
                break;                
            case "declaracaoFuncao": 
                construtorTexto.append(" da função ");                 
                codigo += "2";
                break;
            case "inicializacaoArray": 
                construtorTexto.append(" do vetor "); 
                codigo += "3";
                break;
            case "matriz": 
                construtorTexto.append(" da matriz "); 
                codigo += "4";
                break;                
            default:
            {
                construtorTexto.append(" do comando \"");
                construtorTexto.append(contexto.replace("facaEnquanto", "faca-enquanto"));
                construtorTexto.append("\" ");
                codigo += "5";
            } 
        }
        
        construtorTexto.append("não foi fechado corretamente. Insira o caracter '}' para corrigir o problema");
        super.setCodigo(codigo);
        return construtorTexto.toString();
    }    
}

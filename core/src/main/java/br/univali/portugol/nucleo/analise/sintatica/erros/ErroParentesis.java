package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 *
 * Erro gerado pelo analisador sintático quando um parêntesis não foi aberto
 * ou fechado corretamente.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploErroAbreFechaParentesis()
 *      {
 *           inteiro valor
 * 
 *           valor = 2 * 5 + 3)     // Gera um erro, pois o parêntesis não foi aberto no início da expressão
 *           valor = 2 * (5 + 3     // Gera um erro, pois o parêntesis não foi fechado no final da expressão
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public final class ErroParentesis extends ErroSintatico
{
    public static enum Tipo { ABERTURA, FECHAMENTO };
    
    private Tipo tipo;
    private String codigo = "ErroSintatico.ErroParentesis.";
            
    /**
     * 
     * @param linha                a linha onde o erro ocorreu.  
     * @param coluna               a coluna onde o erro ocorreu.
     * @param tipo                 identifica a natureza do erro.
     * 
     * @since 1.0
     */
    public ErroParentesis(int linha, int coluna, Tipo tipo)
    {
        super(linha, coluna);
        this.tipo = tipo;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorTexto = new StringBuilder("A expressão não foi ");
        
        switch (tipo)
        {
            case ABERTURA: 
                construtorTexto.append("iniciada"); 
                codigo+="1";
                break;
            case FECHAMENTO:
                construtorTexto.append("finalizada");
                codigo+="2";
                break;
        }
        
        super.setCodigo(codigo);
        
        construtorTexto.append(" corretamente. Insira o caracter '");
        
        switch (tipo)
        {
            case ABERTURA: construtorTexto.append("("); break;
            case FECHAMENTO: construtorTexto.append(")"); break;
        }
        
        return construtorTexto.append("' para corrigir o problema.").toString();
    }
}

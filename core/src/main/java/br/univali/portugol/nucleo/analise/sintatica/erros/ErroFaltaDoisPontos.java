package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 *
 * Erro gerado pelo analisador sintático quando um caso do escolha nao possui :
  * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploErroFaltoDoisPontos()
 *      {
 *           inteiro valor
 * 
 *           escolha (valor){
 *				caso 1   // aqui esta o erro  
 *					escreva ("oi1")
 *					pare
 *				caso 2: 
 *					escreva ("tchau")
 *					pare
 *				caso contrario:
 *					escreva ("burro")
 *          }
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public final class ErroFaltaDoisPontos extends ErroSintatico
{
            
    /**
     * 
     * @param linha                a linha onde o erro ocorreu.  
     * @param coluna               a coluna onde o erro ocorreu.
     *  @since 1.11
     */
    public ErroFaltaDoisPontos(int linha, int coluna)
    {
        super(linha, coluna, "ErroSintatico.ErroFaltaDoisPontos");
        
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
                       
        return "O caracter ':' é necessário após o caso.";
    }
}

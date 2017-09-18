package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.Programa;
import java.util.List;

/**
 * Representa o laço de repetição <code>para</code> no código fonte.
 * <p>
 * O laço <code>para</code> é um laço de repetição especial que possui uma possui uma 
 * inicialização, uma condição e um contador. É utilizado para realizar um número específico
 * de iterações que são controladas pelo seu contador.
 * <p>
 * Exemplo:
 * 
 * <code><pre>
 * 
 *     funcao exemploPara()
 *     {     
 *          inteiro vidasDoGato
 *          
 *          para (vidasDoGato = 9; vidasDoGato > 0; vidasDoGato = vidasDoGato - 1)
 *          {
 *               /**
 *                * O gato começa com 9 vidas (vidasDoGato = 9).
 *                * Enquanto o gato tiver vidas (vidasDoGato > 0), atiramos um pau no gato para matá-lo.
 *                * Quando o pau acertar o gato, ele perderá uma vida (vidasDoGato - 1).
 *                *&#47;
 * 
 *               atiraUmPauNoGato()
 *               gatoBerra()
 *               donaXicaSeAdmira()
 *          }
 * 
 *          escreva("Matamos o gato! Aprende como se faz Dona Xica! \o/")
 *     }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoPara extends NoBloco
{
    private List<NoBloco> blocos;
    private List<NoBloco> inicializacoes;
    private NoExpressao condicao;
    private NoExpressao incremento;

    public NoPara()
    {
        
    }

    @Override
    public boolean ehParavel(Programa.Estado estado)
    {
        if(getCondicao() != null){
            return super.ehParavel(estado) || getCondicao().ehParavel(estado);
        }
        return super.ehParavel(estado);
    }    
    
    /**
     * Define a lista de blocos que serão executados a cada iteração.
     * 
     * @param blocos     a lista de blocos que serão executados a cada iteração.
     * @since 1.0
     */
    public void setBlocos(List<NoBloco> blocos)
    {
        this.blocos = blocos;
    }

    /**
     * Define a expressão que irá incrementar o contador deste laço de repetição.
     * 
     * @param incremento     a expressão que irá incrementar o contador deste laço de repetição.
     * @since 1.0
     */        
    public void setIncremento(NoExpressao incremento)
    {
        this.incremento = incremento;
    }

    /**
     * Define a expressão que será avaliada para determinar se este laço continuará executando ou não.
     * 
     * @param condicao     a expressão que será avaliada para determinar se este laço continuará executando ou não.
     * @since 1.0
     */
    public void setCondicao(NoExpressao condicao)
    {
        this.condicao = condicao;
    }

    /**
     * Define a expressão que será utilizada para inicializar o contador deste laço de repetição.
     * 
     * @param inicializacoes     a expressão que será utilizada para inicializar o contador deste laço de repetição.
     * @since 1.0
     */
    public void setInicializacoes(List<NoBloco> inicializacoes)
    {
        this.inicializacoes = inicializacoes;
    }

    /**
     * Obtém a lista de blocos que serão executados a cada iteração deste laço de repetição.
     * 
     * @return     a lista de blocos que serão executados.
     * @since 1.0
     */
    public List<NoBloco> getBlocos()
    {
        return blocos;
    }

    /**
     * Obtém a expressão que irá incrementar o contador deste laço de repetição.
     * 
     * @return    a expressão que irá incrementar o contador deste laço de repetição.
     * @since 1.0
     */
    public List<NoBloco> getInicializacoes()
    {
        return inicializacoes;
    }
    
    /**
     * Obtém a expressão que irá incrementar o contador deste laço de repetição.
     * 
     * @return     a expressão que irá incrementar o contador deste laço de repetição.
     * @since 1.0
     */    
    public NoExpressao getIncremento()
    {
        return incremento;
    }

    /**
     * Obtém a expressão que será avaliada para determinar se este laço continuará executando ou não.
     * 
     * @return     a expressão que será avaliada para determinar se este laço continuará executando ou não.
     * @since 1.0
     */
    public NoExpressao getCondicao()
    {
        return condicao;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
    
   
}

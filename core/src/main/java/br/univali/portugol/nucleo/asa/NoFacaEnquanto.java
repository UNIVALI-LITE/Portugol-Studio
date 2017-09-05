package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.Programa;
import java.util.List;

/**
 * Representa o comando <code>faca-enquanto</code> no código fonte.
 * <p>
 * O comando <code>faca-enquanto</code> é um laço de repetição com teste lógico no final. Seus comandos
 * são executados no início de cada iteração e somente após a execução dos comandos uma expressão lógica
 * é avaliada para determinar se o laço deve continuar ou não.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      programa
 *      {
 *          inteiro gramasDeLeiteCondensado = 350
 *          logico temLeiteCondensado = verdadeiro
 *          inteiro numeroDeBrigadeiros = 0
 * 
 *          funcao exemploFacaEnquanto()
 *          {
 *               faca
 *               {
 *                  brigadeiro();
 *               }
 *               enquanto(temLeiteCondensado)
 *          }
 * 
 *          funcao brigadeiro()
 *          {
 *               numeroDeBrigadeiros = numeroDeBrigadeiros + 1
 *               gramasDeLeiteCondensado = gramasDeLeiteCondensado - 10
 * 
 *               se (gramasDeLeiteCondensado == 0)
 *               {
 *                    temLeiteCondensado = falso
 *               }
 *          }
 *      }
 * 
 * </pre></code>
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoFacaEnquanto extends NoBloco
{
    private List<NoBloco> blocos;
    private NoExpressao condicao;

    /**
     * @param condicao     a expressão lógica que será avaliada ao final de cada iteração.
     * @since 1.0
     */
    public NoFacaEnquanto(NoExpressao condicao)
    {
        this.condicao = condicao;
    }

    /**
     * Obtém a lista dos blocos que serão executados a cada iteração. Os blocos serão executados pelo menos 
     * uma vez, na primeira iteração do laço.
     * 
     * @return     a lista dos blocos a serem executtados.
     * @since 1.0
     */
    public List<NoBloco> getBlocos()
    {
        return blocos;
    }

    /**
     * Define a lista dos blocos que serão executados.
     * 
     * @param blocos     a lista dos blocos a serem executados.
     * @since 1.0
     */
    public void setBlocos(List<NoBloco> blocos)
    {
        this.blocos = blocos;
    }

    /**
     * Obtém a expressão lógica que será avaliada ao final de cada iteração para determinar se
     * o laço deve continuar executando ou não.
     * 
     * @return     a expressão que será avaliada ao final de cada iteração.
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
    
     @Override
    public boolean ehParavel(Programa.Estado estado)
    {
        if(getCondicao() != null){
            return super.ehParavel(estado) || getCondicao().ehParavel(estado);
        }
        return super.ehParavel(estado);
    }
    
    
    
}

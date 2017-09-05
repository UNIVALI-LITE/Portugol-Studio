package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.Programa;
import java.util.List;

/**
 * Representa o comando <code>enquanto</code> no código fonte.
 * <p>
 * O comando <code>enquanto</code> é um laço de repetição com teste condicional no início.
 * Este comando possui um expressão lógica que será avaliada a cada iteração do 
 * laço para determinar se deve continuar executando ou não. Exemplo:
 * 
 * <code><pre>
 * 
 *      funcao exemploLacoEnquanto()
 *      {
 *           inteiro valor = 10
 * 
 *           enquanto (valor >= 1)
 *           {
 *                 escreva("Este exemplo se auto destruirá em ", valor, "\n")
 *                 valor = valor - 1
 *           }
 * 
 *           escreva("BBBBBBOOOOOOOOOOOOMMMMMMMM!")
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoEnquanto extends NoBloco
{
    private List<NoBloco> blocos;
    private NoExpressao condicao;

    /**
     * @param condicao     a expressão que será avaliada para determinar se o laço deve
     *                     continuar executando ou não.
     * @since 1.0
     */
    public NoEnquanto(NoExpressao condicao)
    {
        this.condicao = condicao;
    }

    /**
     * Obtém a expressão lógica que será avaliada a cada iteração do laço. Enquanto
     * esta expressão for verdadeira, o laço continuará executando.
     * 
     * @return     a expressão lógica que será avaliada.
     * @since 1.0
     */
    public NoExpressao getCondicao()
    {
        return condicao;
    }

    /**
     * Obtém a lista dos blocos que serão executados a cada iteração do laço caso 
     * a condição seja verdadeira.
     * 
     * @return     a lista dos blocos a serem executados.
     * @since 1.0
     */
    public List<NoBloco> getBlocos()
    {
        return blocos;
    }

    /**
     * Define a lista de blocos a serem executados a cada iteração do laço.
     * 
     * @param blocos     a lista dds blocos a serem executados.
     * @since 1.0
     */
    public void setBlocos(List<NoBloco> blocos)
    {
        this.blocos = blocos;
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
            return super.ehParavel(estado) || (getCondicao().ehParavel(estado));
        }
        return super.ehParavel(estado);
    }    
}

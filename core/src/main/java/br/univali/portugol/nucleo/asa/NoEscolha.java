package br.univali.portugol.nucleo.asa;

import java.util.List;

/**
 * Representa o comando <code>escolha</code> no código fonte.
 * <p>
 * O comando <code>escolha</code> é composto por uma expressão e um conjunto de casos, os quais funcionam
 * de forma similar a um desvio condicional.
 * <p>
 * No momento da execução, o comando <code>escolha</code> avalia sua expressão e compara com cada caso.
 * O caso que possuir valor igual ao da expressão avaliada será escolhido e terá seus comandos
 * executados.
 * <p>
 * O comando <code>escolha</code> possui ainda um caso especial que é executado quando nenhum outro
 * caso é escolhido. No entanto, não é obrigatório definir este caso.
 * <p>
 * Outra característica importante sobre o comando <code>escolha</code> é que cada caso deve definir explicitamente
 * o seu ponto de parada utilizando o comando <code>pare</code>. Se isto não for feito, o comando escolha continuará 
 * executando cada caso após o escolhido, até encontrar um ponto de parada.
 * <p>
 * Exemplo:
 * 
 * <code><pre>
 * 
 *      funcao exemploEscolha(inteiro diaDaSemana)
 *      {
 *           escolha (numeroDeIngresos)
 *           {
 *               caso 2 : escreva("Terça") pare
 *             
 *               caso 3 : escreva ("Quarta") pare
 * 
 *               caso 4 : escreva ("Quinta") pare
 *           
 *               caso 5 : escreva("Sexta") pare
 *
 *               caso 1 : escreva("Domingo")
 *               case 6 : escreva("É final de semana") pare
 * 
 *               caso contrario : 
 *                     escreva("Dia da semana inválido!")
 *               pare
 *           }
 *      }
 * 
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoEscolha extends NoBloco
{
    private List<NoCaso> casos;
    private NoExpressao expressao;

    /**
     * 
     * @param expressao     a expressão que será avaliada e comparada com os casos.
     * @since 1.0
     */
    public NoEscolha(NoExpressao expressao)
    {
        this.expressao = expressao;
    }

    /**
     * Obtém a expressão que será avaliada. Esta expressão terá seu valor comparado com o valor
     * de cada caso, para determinar qual caso deve ser escolhido.
     * 
     * @return     a expressão que será avaliada.
     * @since 1.0
     */
    public NoExpressao getExpressao()
    {
        return expressao;
    }

    /**
     * Obtém a lista de casos deste comando.
     * 
     * @return     a lista de casos deste comando.
     * @since 1.0
     */
    public List<NoCaso> getCasos()
    {
        return casos;
    }

    /**
     * Define a lista de casos deste comando.
     * 
     * @param casos     a lista de casos deste comando.
     * @since 1.0
     */
    public void setCasos(List<NoCaso> casos)
    {
        this.casos = casos;
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

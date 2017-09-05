package br.univali.portugol.nucleo.asa;


/**
 * Esta enumeração representa a operação <code>maior-igual</code> no código fonte.
 * <p>
 * A operação <code>maior-igual</code> serve para verificar se uma expressão é maior ou igual a outra.
 * No Portugol, a operação <code>maior-igual</code> é representada no código fonte pelo operador "&gt;=".
 * A operação <code>maior-igual</code> é uma operação lógica, e portanto, ao ser avaliada retorna um
 * valor lógico: <code>verdadeiro</code> se a expressão à esquerda do operador for maior ou igual à expressão
 * à direita do operador, caso contrário retorna <code>falso</code>.Pode ser utilizada para realizar o 
 * controle de laços de repetição e desvios condicionais.
 * </p>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoLogicaMaiorIgual extends NoOperacaoLogica
{
    public NoOperacaoLogicaMaiorIgual(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
    {
        super(operandoEsquerdo, operandoDireito);
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

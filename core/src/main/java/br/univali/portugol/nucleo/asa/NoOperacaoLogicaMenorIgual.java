package br.univali.portugol.nucleo.asa;

/**
 * Esta enumeração representa a operação <code>menor-igual</code> no código fonte.
 * <p>
 * A operação <code>menor-igual</code> serve para verificar se uma expressão é maior que outra.
 * No Portugol, a operação <code>menor-igual</code> é representada no código fonte pelo operador "&lt;=".
 * A operação <code>menor-igual</code> é uma operação lógica, e portanto, ao ser avaliada retorna um
 * valor lógico: <code>verdadeiro</code> se a expressão à esquerda do operador for menor ou igual á expressão
 * à direita do operador, caso contrário retorna <code>falso</code>.Pode ser utilizada para realizar o 
 * controle de laços de repetição e desvios condicionais.
 * </p>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoLogicaMenorIgual extends NoOperacaoLogica
{
    public NoOperacaoLogicaMenorIgual(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
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

package br.univali.portugol.nucleo.asa;

/**
 * Esta enumeração representa a operação <code>maior</code> no código fonte.
 * <p>
 * A operação <code>maior</code> serve para verificar se uma expressão é maior que outra.
 * No Portugol, a operação <code>maior</code> é representada no código fonte pelo operador "&gt;".
 * A operação <code>maior</code> é uma operação lógica, e portanto, ao ser avaliada retorna um
 * valor lógico: <code>verdadeiro</code> se a expressão à esquerda do operador for maior que à expressão
 * à direita do operador, caso contrário retorna <code>falso</code>.Pode ser utilizada para realizar o 
 * controle de laços de repetição e desvios condicionais.
 * </p>
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoLogicaMaior extends NoOperacaoLogica
{
    public NoOperacaoLogicaMaior(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
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

package br.univali.portugol.nucleo.asa;


/**
 * Representa uma operação de multiplicacao no código fonte.
 * Esta enumeração representa a operação de <code>multiplicação</code> no código fonte e é representada pelo operador "*".
 *
 * @version 1.13
 */
public final class NoOperacaoMultiplicacao extends NoOperacao
{
    public NoOperacaoMultiplicacao(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
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

    @Override
    public String toString()
    {
        return getOperandoEsquerdo().toString() + " * " + getOperandoDireito().toString();
    }
}

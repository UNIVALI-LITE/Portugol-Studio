package br.univali.portugol.nucleo.asa;


/**
 * Representa uma operação de soma no código fonte.
 * 
 * <p>
 * A operação de <code>soma</code> serve para unir duas expressões e é representada no código fonte pelo operador "+".
 * O resultado desta operação, depende do tipo de dado dos seus operandos.
 * </p>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoSoma extends NoOperacao
{
    public NoOperacaoSoma(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
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
        return getOperandoEsquerdo().toString() + " + " + getOperandoDireito().toString();
    }
}

package br.univali.portugol.nucleo.asa;


/**
 * Representa uma operação de soma no código fonte.
 * <p>
 * A operação de <code>diferença</code> serve para determinar se duas expressões são diferentes.
 * No Portugol, a operação de <code>diferença</code> é representada no código fonte pelo operador "!=".
 * </p>
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoLogicaDiferenca extends NoOperacaoLogica
{
    public NoOperacaoLogicaDiferenca(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
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

package br.univali.portugol.nucleo.asa;

/**
 * Representa uma operação de soma no código fonte.
 * Esta enumeração representa a operação de <code>divisão</code> no código fonte e é representada pelo operador "/".
 *
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoDivisao extends NoOperacao
{
    public NoOperacaoDivisao(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
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

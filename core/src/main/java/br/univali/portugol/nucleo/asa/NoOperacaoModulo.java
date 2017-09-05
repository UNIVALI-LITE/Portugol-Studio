package br.univali.portugol.nucleo.asa;


/**
 * Esta enumeração representa a operação de <code>modulo</code> no código fonte.
 * <p>
 * A operação de <code>modulo</code> serve para obter o resto da divisão entre duas expressões e é representada no código 
 * fonte pelo operador "%". O resultado desta operação, depende do tipo de dado dos seus operandos.
 * </p>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoModulo extends NoOperacao
{
    public NoOperacaoModulo(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
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

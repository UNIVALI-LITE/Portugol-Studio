package br.univali.portugol.nucleo.asa;

/**
 * Representa uma operação de Bitwise E no código fonte.
 * Esta enumeração representa a operação de <code>Bitwise E</code> no código fonte e é representada pelo operador "&amp;".
 * 
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoBitwiseE extends NoOperacao
{
    public NoOperacaoBitwiseE(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
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
    public TipoDado getTipoResultante()
    {
        return TipoDado.INTEIRO;
    }
}

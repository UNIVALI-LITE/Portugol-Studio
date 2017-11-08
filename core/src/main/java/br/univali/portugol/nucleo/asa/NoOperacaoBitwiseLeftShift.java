package br.univali.portugol.nucleo.asa;

/**
 * Representa uma operação de Bitwise Left Shift no código fonte.
 * Esta enumeração representa a operação de <code>Bitwise Left Shift</code> no código fonte e é representada pelo operador "&lt;&lt;".
 *
 * @author Luiz Fernando Noschang
 * @version 1.13
 */
public final class NoOperacaoBitwiseLeftShift extends NoOperacao
{
    public NoOperacaoBitwiseLeftShift(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
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

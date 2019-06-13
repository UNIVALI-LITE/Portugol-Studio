package br.univali.portugol.nucleo.asa;

/**
 * Representa uma operação de Bitwise Não no código fonte.
 * Esta enumeração representa a operação de <code>Bitwise Não</code> no código fonte e é representada pelo operador "~".
 * 
 * @author Fillipi Pelz
 * @version 2.0
 */
public final class NoBitwiseNao extends NoExpressao
{
    private NoExpressao expressao;
    
    public NoBitwiseNao(NoExpressao expressao)
    {
        this.expressao = expressao;
    }
    
    /**
     * 
     * @return     a expressão que está sendo negada
     * @since 2.0
     */
    public NoExpressao getExpressao()
    {
        return expressao;
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
        return expressao.getTipoResultante();
    }

}

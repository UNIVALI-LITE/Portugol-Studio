package br.univali.portugol.nucleo.asa;

/**
 * Representa a operação de menos unário no código fonte.
 * <p>
 * A operação de menos unário é utilizada em expressões numéricas para inverter
 * o sinal da expressão. No código fonte ela é representada por um sinal de menos
 * <code>"-"</code> à esquerda da expressão:
 * <code><pre>
 * 
 *   funcao exemploMenosUnario()
 *   {
 *       inteiro a = -1     // a vale -1
 *       inteiro b = -a     // b vale 1
 *       inteiro c = -b     // c vale -1
 *       inteiro d = -(-1)  // d vale 1
 *   }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class NoMenosUnario extends NoExpressao
{
    private final NoExpressao expressao;
    private TrechoCodigoFonte trechoCodigoFonteMenos;

    /**
     * 
     * @param expressao     a expressão que está sendo invertida.
     * @since 1.0
     */
    public NoMenosUnario(NoExpressao expressao)
    {
        this.expressao = expressao;
    }

    /**
     * Obtém a expressão numérica que está sendo invertida por esta operação de menos unário.
     * 
     * @return     a expressão que está sendo invertida.
     * @since 1.0
     */
    public NoExpressao getExpressao()
    {
        return expressao;
    }

    /**
     * Obtém o trecho do código fonte no qual o sinal de menos se encontra.
     * 
     * @return     o trecho do código fonte no qual o sinal de menos se encontra.
     * @since 1.0
     */
    public TrechoCodigoFonte getTrechoCodigoFonteMenos()
    {
        return trechoCodigoFonteMenos;
    }

     /**
     * Define o trecho do código fonte no qual o sinal de menos se encontra.
     * 
     * @param trechoCodigoFonteMenos     Define o trecho do código fonte no qual o sinal de menos se encontra.
     * @since 1.0
     */
    public void setTrechoCodigoFonteMenos(TrechoCodigoFonte trechoCodigoFonteMenos)
    {
        this.trechoCodigoFonteMenos = trechoCodigoFonteMenos;
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

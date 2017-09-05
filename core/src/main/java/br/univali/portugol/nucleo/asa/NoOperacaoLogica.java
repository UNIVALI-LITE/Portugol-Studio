package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/07/2016
 */
public abstract class NoOperacaoLogica extends NoOperacao
{
    public NoOperacaoLogica(NoExpressao operandoEsquerdo, NoExpressao operandoDireito)
    {
        super(operandoEsquerdo, operandoDireito);
    }

    @Override
    public TipoDado getTipoResultante()
    {
        return TipoDado.LOGICO;
    }
}

package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.asa.No;
import org.fife.ui.autocomplete.ParameterizedCompletion;

/**
 *
 * @author Luiz Fernando
 */
final class ParametroConclusaoASA extends ParameterizedCompletion.Parameter
{
    public static enum TipoConclusao
    {
        INDICE_VETOR, ATRIBUICAO_VALOR
    };

    private final No no;
    private final TipoConclusao tipoConclusao;

    public ParametroConclusaoASA(No no, TipoConclusao tipoConclusao, ParameterizedCompletion.Parameter parametro, String tipoParametro)
    {
        super(tipoParametro, parametro.getName(), parametro.isEndParam());
        this.no = no;
        this.tipoConclusao = tipoConclusao;
    }

    public No getNo()
    {
        return no;
    }

    public TipoConclusao getTipoConclusao()
    {
        return tipoConclusao;
    }
}

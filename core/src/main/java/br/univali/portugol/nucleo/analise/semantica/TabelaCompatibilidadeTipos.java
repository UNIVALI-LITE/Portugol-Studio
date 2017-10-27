package br.univali.portugol.nucleo.analise.semantica;

import br.univali.portugol.nucleo.analise.semantica.erros.ExcecaoImpossivelDeterminarTipoDado;
import br.univali.portugol.nucleo.analise.semantica.erros.ExcecaoValorSeraConvertido;
import br.univali.portugol.nucleo.asa.NoOperacao;
import br.univali.portugol.nucleo.asa.TipoDado;

/**
 *
 * @author fillipi
 */
public interface TabelaCompatibilidadeTipos
{
    public TipoDado obterTipoRetornoOperacao(Class<? extends NoOperacao> operacao, TipoDado tipoDadoOperandoEsquerdo, TipoDado tipoDadoOperandoDireito) throws ExcecaoImpossivelDeterminarTipoDado, ExcecaoValorSeraConvertido;
    public TipoDado obterTipoRetornoPassagemParametro(TipoDado tipoDadoEsperado, TipoDado tipoDadoPassado) throws ExcecaoImpossivelDeterminarTipoDado, ExcecaoValorSeraConvertido;
    public TipoDado obterTipoRetornoFuncao(TipoDado tipoDadoEsperado, TipoDado tipoDadoPassado) throws ExcecaoImpossivelDeterminarTipoDado, ExcecaoValorSeraConvertido;
}

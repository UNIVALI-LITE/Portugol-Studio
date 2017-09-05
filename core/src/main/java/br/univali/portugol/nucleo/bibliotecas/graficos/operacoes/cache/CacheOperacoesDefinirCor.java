package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.OperacaoDefinirCor;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDefinirCor extends CacheOperacoesGraficas<OperacaoDefinirCor>
{
    public CacheOperacoesDefinirCor(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected OperacaoDefinirCor criarInstancia()
    {
        return new OperacaoDefinirCor(this);
    }
}

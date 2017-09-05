package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.OperacaoDefinirFonte;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDefinirFonte extends CacheOperacoesGraficas<OperacaoDefinirFonte>
{
    public CacheOperacoesDefinirFonte(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected OperacaoDefinirFonte criarInstancia()
    {
        return new OperacaoDefinirFonte(this);
    }
}

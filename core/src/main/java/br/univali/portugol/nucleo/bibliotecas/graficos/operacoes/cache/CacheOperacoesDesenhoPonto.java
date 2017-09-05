package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.DesenhoPonto;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDesenhoPonto extends CacheOperacoesGraficas<DesenhoPonto>
{
    public CacheOperacoesDesenhoPonto(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected DesenhoPonto criarInstancia()
    {
        return new DesenhoPonto(this);
    }
}

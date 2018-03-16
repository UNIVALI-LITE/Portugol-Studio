package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.OperacaoDefinirGradiente;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDefinirGradiente extends CacheOperacoesGraficas<OperacaoDefinirGradiente>
{
    public CacheOperacoesDefinirGradiente(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected OperacaoDefinirGradiente criarInstancia()
    {
        return new OperacaoDefinirGradiente(this);
    }
}

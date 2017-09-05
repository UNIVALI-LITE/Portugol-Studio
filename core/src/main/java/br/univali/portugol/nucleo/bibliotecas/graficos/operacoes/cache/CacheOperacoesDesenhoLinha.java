package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.DesenhoLinha;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDesenhoLinha extends CacheOperacoesGraficas<DesenhoLinha>
{
    public CacheOperacoesDesenhoLinha(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected DesenhoLinha criarInstancia()
    {
        return new DesenhoLinha(this);
    }
}

package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.DesenhoImagem;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDesenhoImagem extends CacheOperacoesGraficas<DesenhoImagem>
{
    public CacheOperacoesDesenhoImagem(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected DesenhoImagem criarInstancia()
    {
        return new DesenhoImagem(this);
    }
}

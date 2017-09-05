package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.DesenhoPoligono;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDesenhoPoligono extends CacheOperacoesGraficas<DesenhoPoligono>
{
    public CacheOperacoesDesenhoPoligono(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected DesenhoPoligono criarInstancia()
    {
        return new DesenhoPoligono(this);
    }
}

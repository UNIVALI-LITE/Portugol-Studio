package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.DesenhoElipse;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDesenhoElipse extends CacheOperacoesGraficas<DesenhoElipse>
{
    public CacheOperacoesDesenhoElipse(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }
    
    @Override
    protected DesenhoElipse criarInstancia()
    {
        return new DesenhoElipse(this);
    }
}

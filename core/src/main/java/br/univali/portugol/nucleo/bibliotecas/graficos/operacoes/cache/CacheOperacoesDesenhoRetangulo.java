package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.DesenhoRetangulo;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDesenhoRetangulo extends CacheOperacoesGraficas<DesenhoRetangulo>
{
    public CacheOperacoesDesenhoRetangulo(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected DesenhoRetangulo criarInstancia()
    {
        return new DesenhoRetangulo(this);
    }
}

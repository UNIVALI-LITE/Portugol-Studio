package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.DesenhoTexto;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDesenhoTexto extends CacheOperacoesGraficas<DesenhoTexto>
{
    public CacheOperacoesDesenhoTexto(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected DesenhoTexto criarInstancia()
    {
        return new DesenhoTexto(this);
    }
}

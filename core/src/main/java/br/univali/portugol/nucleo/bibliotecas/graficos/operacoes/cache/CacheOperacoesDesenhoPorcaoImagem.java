package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.DesenhoPorcaoImagem;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CacheOperacoesDesenhoPorcaoImagem extends CacheOperacoesGraficas<DesenhoPorcaoImagem>
{
    public CacheOperacoesDesenhoPorcaoImagem(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected DesenhoPorcaoImagem criarInstancia()
    {
        return new DesenhoPorcaoImagem(this);
    }
}

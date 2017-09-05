package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.OperacaoLimpar;

/**
 *
 * @author Luiz Fernando Noschang
 */
public class CacheOperacoesLimpar extends CacheOperacoesGraficas<OperacaoLimpar>
{
    public CacheOperacoesLimpar(int quantidadeMaxima, int quantidadeAlocacao)
    {
        super(quantidadeMaxima, quantidadeAlocacao);
    }

    @Override
    protected OperacaoLimpar criarInstancia()
    {
        return new OperacaoLimpar(this);
    }
}
